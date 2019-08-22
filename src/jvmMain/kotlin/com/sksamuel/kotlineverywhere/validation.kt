package com.sksamuel.kotlineverywhere

import java.util.regex.Pattern

val emailRegex = Pattern.compile(".+?@.+?")
val socialRegex = Pattern.compile("\\d{3}-\\d{3}-\\d{4}")

actual fun validateSocial(ssn: String): Boolean = socialRegex.matcher(ssn).matches()

actual fun validateEmail(email: String): Boolean = emailRegex.matcher(email).matches()


val usernameRegex = Pattern.compile("[a-zA-Z0-9]+")
actual fun validateUsername(username: String): Boolean = usernameRegex.matcher(username.trim()).matches()