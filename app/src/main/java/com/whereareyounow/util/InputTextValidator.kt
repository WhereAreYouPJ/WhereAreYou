package com.whereareyounow.util

class InputTextValidator {

    // 2~4자의 한글, 영문 대/소문자
    private val userNameRule = Regex("^[\\uAC00-\\uD7A3a-zA-Z]{2,4}\$")
    // 영문 소문자로 시작하는 4~10자의 영문 소문자, 숫자
    private val userIdRule = Regex("^[a-z][a-z0-9]{3,9}\$")
    // 영문 대/소문자로 시작하는 4~10자의 영문 대/소문자, 숫자
    // 영문 대문자, 소문자, 숫자를 최소 하나 이상씩 포함
    private val passwordRule = Regex("^(?=[A-Za-z])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9]{4,10}\$")
    // []@[].[] 형식
    private val emailRule = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")

    fun validateUserName(userName: String): ValidationResult {
        return ValidationResult(
            userName.matches(userNameRule)
        )
    }

    fun validateUserId(userId: String): ValidationResult {
        return ValidationResult(
            userId.matches(userIdRule)
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            password.matches(passwordRule)
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            email.matches(emailRule)
        )
    }
}

data class ValidationResult(
    val result: Boolean = false
)