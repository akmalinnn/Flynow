package com.km6.flynow.data.source.local.pref

import android.content.SharedPreferences
import com.km6.flynow.data.model.User
import com.km6.flynow.utils.SharedPreferenceUtils.set

interface UserPreference {
    fun saveUserData(user: User)
    fun getUserData(): User?
    fun clearUserData()

    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}

class UserPreferenceImpl(private val pref: SharedPreferences) : UserPreference {

    override fun saveUserData(user: User) {
        with(pref.edit()) {
            putInt(KEY_USER_ID, user.id)
            putString(KEY_USERNAME, user.name)
            putString(KEY_EMAIL, user.email)
            putString(KEY_IMAGE, user.image)
            putString(KEY_PHONE_NUMBER, user.phoneNumber)
            putBoolean(KEY_IS_VERIFIED, user.isVerified)
            putString(KEY_DELETED_AT, user.deletedAt)
            putString(KEY_CREATED_AT, user.createdAt)
            putString(KEY_UPDATED_AT, user.updatedAt)
            apply()
        }
    }

    override fun getUserData(): User? {
        val userId = pref.getInt(KEY_USER_ID, -1)
        if (userId == -1) {
            return null
        }
        return User(
            id = userId,
            name = pref.getString(KEY_USERNAME, "") ?: "",
            email = pref.getString(KEY_EMAIL, "") ?: "",
            image = pref.getString(KEY_IMAGE, "") ?: "",
            phoneNumber = pref.getString(KEY_PHONE_NUMBER, "") ?: "",
            isVerified = pref.getBoolean(KEY_IS_VERIFIED, false),
            deletedAt = pref.getString(KEY_DELETED_AT, null),
            createdAt = pref.getString(KEY_CREATED_AT, "") ?: "",
            updatedAt = pref.getString(KEY_UPDATED_AT, "") ?: ""
        )
    }

    override fun clearUserData() {
        with(pref.edit()) {
            remove(KEY_USER_ID)
            remove(KEY_USERNAME)
            remove(KEY_EMAIL)
            remove(KEY_IMAGE)
            remove(KEY_PHONE_NUMBER)
            remove(KEY_IS_VERIFIED)
            remove(KEY_DELETED_AT)
            remove(KEY_CREATED_AT)
            remove(KEY_UPDATED_AT)
            apply()
        }
    }

    override fun saveToken(token: String) {
        pref[KEY_TOKEN] = token
    }

    override fun getToken(): String? {
        return pref.getString(KEY_TOKEN, null)
    }

    override fun clearToken() {
        pref.edit().remove(KEY_TOKEN).apply()
    }

    companion object {
        const val PREF_NAME = "FlynowUserPref"
        const val KEY_USER_ID = "userId"
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"
        const val KEY_IMAGE = "image"
        const val KEY_PHONE_NUMBER = "phoneNumber"
        const val KEY_IS_VERIFIED = "isVerified"
        const val KEY_DELETED_AT = "deletedAt"
        const val KEY_CREATED_AT = "createdAt"
        const val KEY_UPDATED_AT = "updatedAt"
        const val KEY_TOKEN = "token"
    }
}
