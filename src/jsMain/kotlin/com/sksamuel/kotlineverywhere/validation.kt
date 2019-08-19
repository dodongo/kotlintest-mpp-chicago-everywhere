package com.sksamuel.kotlineverywhere

import kotlin.js.Date
import kotlin.js.RegExp

val emailRegex = RegExp(".*?@")
val socialRegex = RegExp("^\\d{3}-\\d{3}-\\d{4}$")

actual fun validateSocial(ssn: String): Boolean =  socialRegex.test(ssn)

actual fun validateEmail(email: String): Boolean = emailRegex.test(email)

actual fun validateAge(dob: String, minAge: Long): Boolean {
  val now = Date()
  val limit = Date(now.getFullYear(), now.getMonth(), now.getDay())
  return Date(dob).getMilliseconds() <= limit.getMilliseconds()
}