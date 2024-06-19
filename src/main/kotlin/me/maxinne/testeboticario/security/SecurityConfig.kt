package me.maxinne.testeboticario.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    jwtUserDetail: JwtUserDetail) {
    @Bean
    fun filterChain(
        http: HttpSecurity,
        jwtFilter: JwtFilter,
        jwtEntryPoint: AuthenticationEntryPoint
    ): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeRequests {
                authorize("/healthcheck", permitAll)
                authorize("/authenticate", permitAll)
                authorize("/essences/**", authenticated)
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            exceptionHandling {
                authenticationEntryPoint = jwtEntryPoint
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtFilter)
        }
        return http.build()
    }

    // O certo seria usar um encoder como BCrypt, mas pra que complicar
    // se podemos simplificar, não é mesmo? ;-)
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        // TODO: Mudar para BCrypt
        return NoOpPasswordEncoder.getInstance()
    }

    @Bean
    @Throws(Exception::class)
    fun authManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}