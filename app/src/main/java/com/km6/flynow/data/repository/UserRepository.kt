package com.km6.flynow.data.repository

import com.km6.flynow.data.model.User
import com.km6.flynow.data.source.local.pref.UserPreference

interface UserRepository {
    fun saveUser(user: User)
    fun getUser(): User?
    fun clearUser()
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}

class UserRepositoryImpl(private val userPreferences: UserPreference) : UserRepository {

    override fun saveUser(user: User) {
        userPreferences.saveUserData(user)
    }

    override fun getUser(): User? {
        return userPreferences.getUserData()
    }

    override fun clearUser() {
        userPreferences.clearUserData()
    }

    override fun saveToken(token: String) {
        userPreferences.saveToken(token)
    }


    override fun getToken(): String? {
        return userPreferences.getToken()
    }

    override fun clearToken() {
        userPreferences.clearToken()
    }

}
