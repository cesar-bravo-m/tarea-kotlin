package com.example.myapplication.business

import com.example.myapplication.R

data class User(
    val email: String,
    var password: String,
    val fullName: String,
    val avatar: Int? = null,
)

object UserManager {
    private val users = mutableListOf(
        User(
            email="antonia@example.com",
            password="admin",
            fullName="Antonia Molina",
            avatar=R.drawable.female1
        ),
        User(
            email="cecy@example.com",
            password="admin",
            fullName="Cecy Molina",
            avatar=R.drawable.female2
        ),
    )

    fun getUsersWhoPreviouslyHaveLoggedIn(): List<User> {
        return users
    }

    fun authenticate(email: String, password: String): Boolean {
        return users.any { it.email == email && it.password == password }
    }

    fun register(email: String, password: String, fullName: String, avatar: Int): Boolean {
        if (users.any { it.email == email }) {
            return false
        }
        users.add(User(
            email=email,
            password=password,
            fullName=fullName,
            avatar=avatar
        ))
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
} 