package com.example.halanchallenge.utils

object ValidationUtil {

    /*
    * Validate Username and password in login screen
    * if username/ password is empty return false
    * if username length is less than 6 chars or bigger than 15 chars returns false
    * if password length is less than 8 chars returns false
    * the criteria of user name must be 6 to 15 char in length and only alphanumeric
    * */

    data class ValidationModel(
        val result: Boolean, val message: String ="", val tag: REASONS = REASONS.NONE
    )

    enum class REASONS{
        NONE,
        USERNAME,
        PASSWORD
    }
    fun validateLogin(
        username: String,
        password: String
    ): ValidationModel{
        return when {
            username.isEmpty()  -> {
                ValidationModel(result = false, message = "Username is empty", tag = REASONS.USERNAME)
            }
            password.isEmpty() ->{
                ValidationModel(result = false, message = "Password is empty", tag = REASONS.PASSWORD)
            }
            username.length < 6 || username.length > 15 -> {
                ValidationModel(result = false, message = "Username must be 6 to 15 char in length", tag = REASONS.USERNAME)
            }
            username.any { !it.isLetterOrDigit()} -> {
                ValidationModel(result = false, message = "Username must be only alphanumeric", tag = REASONS.USERNAME)
            }
            password.length < 8 -> {
                ValidationModel(result = false, message = "Password is less than 8 letters", tag = REASONS.PASSWORD)
            }
            else -> ValidationModel(result = true, message = "Validation Success", tag = REASONS.NONE)

        }

    }
}