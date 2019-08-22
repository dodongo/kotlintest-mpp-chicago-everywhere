package com.sksamuel.kotlineverywhere

expect fun validateEmail(email: String): Boolean

expect fun validateSocial(ssn: String): Boolean

expect fun validateUsername(username: String): Boolean