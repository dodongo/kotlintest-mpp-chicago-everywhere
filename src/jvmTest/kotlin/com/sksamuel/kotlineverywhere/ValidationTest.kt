package com.sksamuel.kotlineverywhere

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class ValidationTest : FunSpec({

  test("social regex") {
    validateSocial("123-456-1111") shouldBe true
    validateSocial("999-333-4444") shouldBe true

    validateSocial("a12-456-1111") shouldBe false
    validateSocial("a12-456-cccc") shouldBe false
    validateSocial("123456-1111") shouldBe false
    validateSocial("123-4561117") shouldBe false
    validateSocial("122") shouldBe false
    validateSocial("") shouldBe false
    validateSocial("1234567899") shouldBe false
  }

})