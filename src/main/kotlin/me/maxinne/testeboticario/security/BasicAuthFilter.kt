package me.maxinne.testeboticario.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*

@Service
class BasicAuthFilter: OncePerRequestFilter() {
    private val usr = "xmrGmb7PWFwSzzx6TBxxEGyA7n9zGaC7UWs6GWMruGZMGMNG"
    private val pwd = "0y1GaVyJ6szGu7O3dR2Ax8ijucQujxkUZs6Y715MiQ5hhLiEi6NAbfMJGHqpad96"

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            filterChain.doFilter(request, response)
            return
        }

        // Seria possivel fazer tudo numa linha só, mas separei para melhor leitura.
        val token = authHeader.substring(6) // Removendo o "Basic " do inicio
        val decodedToken = Base64.getDecoder().decode(token)
        val stringToken = String(decodedToken)
        val (username, password) = stringToken.split(":")

        if (username == usr && password == pwd) {
            val authToken = UsernamePasswordAuthenticationToken(usr, null, null)
            SecurityContextHolder.getContext().authentication = authToken
        }

        filterChain.doFilter(request, response)
    }
}