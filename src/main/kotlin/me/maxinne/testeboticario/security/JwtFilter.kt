package me.maxinne.testeboticario.security

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Component
class JwtFilter(val userDetail: JwtUserDetail, val jwtToken: JwtToken): OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val requestTokenHeader = request.getHeader("Authorization")

        var username: String? = null
        var token: String? = null

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7)
            try {
                username = jwtToken.getUsernameFromToken(token)
            } catch (e: IllegalArgumentException) {
                println("Token is invalid, are you logged in?")
            } catch (e: ExpiredJwtException) {
                println("The token has expired, please log in again")
            }
        } else {
            logger.warn("Authentication header lacks the word \"Bearer \" on it's value")
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null && !token.isNullOrEmpty()) {
            val userDetails: UserDetails = this.userDetail.loadUserByUsername(username)

            if (jwtToken.validateToken(token, userDetails)) {
                val userPassAuthToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                userPassAuthToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = userPassAuthToken
            }
        }
        chain.doFilter(request, response)
    }
}