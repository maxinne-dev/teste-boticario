package me.maxinne.testeboticario.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.function.Function
import java.util.Date
import kotlin.collections.HashMap

@Component
class JwtToken: Serializable {
    private val serialVersionUID = -8172938108397129837L
    val JWT_TOKEN_VALIDITY: Long = (5 * 60 * 60).toLong()

    @Value("\${jwt.secret}")
    private val secret: String? = null

    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken(token, Claims::getSubject)
    }

    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token, Claims::getExpiration)
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        val secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).payload
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration: Date = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any?> = HashMap()
        return doGenerateToken(claims, userDetails.username)
    }

    private fun doGenerateToken(claims: Map<String, Any?>, subject: String): String {
        val secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
        return Jwts.builder()
                   .claims(claims)
                   .subject(subject)
                   .issuedAt(Date(System.currentTimeMillis()))
                   .expiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                   .signWith(secret)
                   .compact()
    }

    //valida o token
    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }
}