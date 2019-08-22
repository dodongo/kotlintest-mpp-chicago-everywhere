package com.sksamuel.kotlineverywhere

expect fun validateEmail(email: String): Boolean

expect fun validateSocial(ssn: String): Boolean

fun validatePassword(password: String): Boolean = password.length in 8..16