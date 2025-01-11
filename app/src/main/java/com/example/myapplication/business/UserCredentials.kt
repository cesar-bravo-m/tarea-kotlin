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

    // Lista de correos de los usuarios que han iniciado sesión en este teléfono
    private val usersWhoHavePreviouslyLoggedIn = mutableListOf<String>(
        "antonia@example.com",
        "cecy@example.com",
    )

    private var currentUser: User? = null

    fun getUsersWhoPreviouslyHaveLoggedIn(): List<User> {
        return users.filter { usersWhoHavePreviouslyLoggedIn.contains(it.email) }
    }

    fun removeFromUsersWhoHavePreviouslyLoggedIn(email: String): Unit {
        usersWhoHavePreviouslyLoggedIn.remove(email)
    }

    fun authenticate(email: String, password: String): Boolean {
        val user = users.find { it.email == email && it.password == password }
        if (user != null) {
            currentUser = user
            if (!usersWhoHavePreviouslyLoggedIn.contains(email)) {
                usersWhoHavePreviouslyLoggedIn.add(email)
            }
            return true
        }
        return false
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
        usersWhoHavePreviouslyLoggedIn.add(email)
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

    fun getCurrentUser(): User? = currentUser

    fun updateProfile(
        email: String,
        newPassword: String?,
        fullName: String,
        avatar: Int?
    ): Boolean {
        val user = currentUser ?: return false
        
        // Update user in the list
        val index = users.indexOfFirst { it.email == user.email }
        if (index == -1) return false

        users[index] = User(
            email = email,
            password = newPassword ?: user.password,
            fullName = fullName,
            avatar = avatar
        )
        
        // Update current user
        currentUser = users[index]
        return true
    }
} 