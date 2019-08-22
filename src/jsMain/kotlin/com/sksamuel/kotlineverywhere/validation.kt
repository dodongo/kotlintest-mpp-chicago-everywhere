package com.sksamuel.kotlineverywhere

import kotlin.js.RegExp

val emailRegex = RegExp(".+?@")
val socialRegex = RegExp("^\\d{3}-\\d{3}-\\d{4}$")
val usernameRegex = RegExp("\\s")

actual fun validateSocial(ssn: String): Boolean =  socialRegex.test(ssn)

actual fun validateEmail(email: String): Boolean = emailRegex.test(email)

actual fun validatePassword(password: String): Boolean = password.length in 8..16 && password.matches("\\s")