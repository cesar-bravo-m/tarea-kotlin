package com.example.myapplication.business

import com.example.myapplication.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

data class User(
    val email: String,
    var password: String,
    val fullName: String,
    val avatar: Int? = null,
)

object UserManager {
    private val db = Firebase.firestore
    // private val users = mutableListOf<User>()
    private val users: List<User>
        get() = runBlocking {
            val snapshot = db.collection("usuarios").get().await()
            val resultado = mutableListOf<User>()

            for (document in snapshot) {
                val user = User(
                    email = document.getString("username") ?: "",
                    password = document.getString("password") ?: "",
                    fullName = document.getString("fullName") ?: "",
                    avatar = document.getField<Int>("avatar")?.toInt() ?: 0,
                )
                resultado.add(user)
            }
            resultado
        }

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
        val user = hashMapOf(
            "username" to email,
            "password" to password,
            "fullName" to fullName,
            "avatar" to avatar
        )
        db.collection("usuarios")
            .add(user)
        // users.add(User(
        //     email=email,
        //     password=password,
        //     fullName=fullName,
        //     avatar=avatar
        // ))
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

        // users[index] = User(
        //     email = email,
        //     password = newPassword ?: user.password,
        //     fullName = fullName,
        //     avatar = avatar
        // )
        
        // Update current user
        // currentUser = users[index]
        return true
    }
} 