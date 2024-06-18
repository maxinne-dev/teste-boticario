package me.maxinne.testeboticario

import org.springframework.http.CacheControl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
class ApiController(val dataSource: DummyDataSource) {
    @GetMapping(value = ["/healthcheck"])
    fun healthcheck(): String {
        return "ok"
    }

    @GetMapping(value = ["/essences"])
    fun essences(): ResponseEntity<List<DummyShortData>> {
        return ResponseEntity
            .ok()
            .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES).mustRevalidate())
            .body(dataSource.getData())
    }

    @GetMapping(value = ["/essences/{essence-id}"])
    fun essence(@PathVariable("essence-id") essenceId: String): ResponseEntity<DummyData> {
        return try {
            ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES).mustRevalidate())
                .body(dataSource.getDataById(essenceId))
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
}