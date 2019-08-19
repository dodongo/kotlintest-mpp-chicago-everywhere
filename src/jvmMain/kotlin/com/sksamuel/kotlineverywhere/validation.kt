package com.sksamuel.kotlineverywhere

import java.time.LocalDate

val emailRegex = ".*?@.*?".toRegex()
val socialRegex = "\\d{3}-\\d{3}-\\d{4}".toRegex()

actual fun validateEmail(email: String): Boolean = emailRegex.matches(email)

actual fun validateSocial(ssn: String): Boolean = socialRegex.matches(ssn)

actual fun validateAge(dob: String, minAge: Long): Boolean = try {
  !LocalDate.parse(dob).isAfter(LocalDate.now().minusYears(minAge))
} catch (e: Exception) {
  false
}