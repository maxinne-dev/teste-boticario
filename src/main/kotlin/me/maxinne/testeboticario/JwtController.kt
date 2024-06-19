package me.maxinne.testeboticario

import me.maxinne.testeboticario.security.JwtRequest
import me.maxinne.testeboticario.security.JwtToken
import me.maxinne.testeboticario.security.JwtUserDetail
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin
class JwtAuthController(
    val manager: AuthenticationManager,
    val jwtToken: JwtToken,
    val jwtUser: JwtUserDetail
) {
    @RequestMapping(value = ["/authenticate"], method = [RequestMethod.POST])
    @Throws(Exception::class)
    fun createAuthToken(@RequestBody authReq: JwtRequest): ResponseEntity<Any> {
        val (usr, pwd) = authReq.getCredentials()
        if (usr == null || pwd == null) {
            throw BadCredentialsException("Invalid username or password")
        }
        authenticate(usr, pwd)
        val userDetails = jwtUser.loadUserByUsername(usr)
        val token = jwtToken.generateToken(userDetails)
        return ResponseEntity.ok(token)
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            manager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }
}