package me.maxinne.testeboticario.security

import org.springframework.core.env.Environment
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class JwtUserDetail(private val env: Environment): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        val defaultUser = env.getProperty("spring.security.user.name")
        if (username != defaultUser) {
            throw UsernameNotFoundException("Username $username not found or incorrect password provided")
        }
        return User(username, env.getProperty("spring.security.user.password"), emptyList())
    }
}