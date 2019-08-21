package com.sksamuel.kotlineverywhere

import io.kotlintest.core.QuickSpec
import io.kotlintest.shouldBe

class ValidationTest : QuickSpec({

  t("social regex should pass valid numbers") {
    validateSocial("123-456-1111") shouldBe true
    validateSocial("999-333-4444") shouldBe true
  }

  t("social regex should fail invalid numbers") {
    validateSocial("a12-456-1111") shouldBe false
    validateSocial("a12-456-cccc") shouldBe false
    validateSocial("123456-1111") shouldBe false
    validateSocial("123-4561117") shouldBe false
    validateSocial("122") shouldBe false
    validateSocial("") shouldBe false
    validateSocial("1234567899") shouldBe false
  }

  t("email regex should require an @ and tld") {
    validateSocial("a@a.com") shouldBe true
    validateSocial("sam@sam.com") shouldBe true
  }

  t("email regex should fail without tld") {
    validateSocial("a@a") shouldBe false
    validateSocial("@a") shouldBe false
  }

  t("email regex should fail without an @") {
    validateSocial("a.com") shouldBe false
  }
})