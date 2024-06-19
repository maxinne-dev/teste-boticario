package me.maxinne.testeboticario.security

import java.io.Serializable

class JwtResponse(private val token: String?): Serializable {
    private val serialVersionUID = -182739812739812739L
    fun getToken(): String? {return token}
}