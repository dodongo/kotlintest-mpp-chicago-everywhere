package com.sksamuel.kotlineverywhere

expect fun validateEmail(email: String): Boolean
expect fun validateSocial(ssn: String): Boolean
expect fun validateAge(dob: String, minAge: Long): Boolean