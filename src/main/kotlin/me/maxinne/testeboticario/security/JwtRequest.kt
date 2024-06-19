package me.maxinne.testeboticario.security

import java.io.Serializable

class JwtRequest: Serializable {
    private val serialVersionUID = -182819273900812739L
    private var username: String?
    private var password: String?

    constructor(username: String?, password: String?) {
        this.username = username
        this.password = password
    }

    fun getUsername(): String? {return username}
    fun setUsername(username: String) {this.username = username}

    fun getPassword(): String? {return password}
    fun setPassword(password: String) {this.password = password}

    fun getCredentials(): List<String?> { return listOf(username, password) }
}