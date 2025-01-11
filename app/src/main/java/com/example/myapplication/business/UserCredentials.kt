package com.example.myapplication.business

data class User(
    val username: String,
    var password: String,
    val email: String,
)

object UserManager {
    private val users = mutableListOf(
        User(
            username="antonia",
            password="admin",
            email="admin@example.com"),
        User(
            username="cecy",
            password="admin",
            email="admin@example.com"),
    )

    fun getUsersWhoPreviouslyHaveLoggedIn(): List<User> {
        return users
    }

    fun authenticate(username: String, password: String): Boolean {
        return users.any { it.username == username && it.password == password }
    }

    fun register(username: String, password: String, email: String): Boolean {
        if (users.any { it.username == username || it.email == email }) {
            return false
        }
        users.add(User(username, password, email))
        return true
    }

    fun resetPassword(email: String, newPassword: String): Boolean {
        val user = users.find { it.email == email }
        if (user != null) {
            user.password = newPassword
            return true
        }
        return false
    }

    fun verifyEmail(email: String): Boolean {
        return users.any { it.email == email }
    }

    fun getUsername(email: String): String? {
        return users.find { it.email == email }?.username
    }
} 