package com.sksamuel.kotlineverywhere

import io.kotlintest.core.QuickSpec
import io.kotlintest.inspectors.forAll
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.properties.string
import io.kotlintest.shouldBe

class ValidationTest : QuickSpec({

  test("social validation") {

    validateSocial("123-456-1111") shouldBe true

    listOf("a12-456-cccc", "", "123-4561117", "122", "1234567899").forAll {
      validateSocial(it) shouldBe false
    }
  }

  test("email validation") {

    listOf("a@a.com", "sam@sam.com").forAll {
      validateEmail(it) shouldBe true
    }

    listOf("a@", "@a", "a.com").forAll {
      validateEmail(it) shouldBe false
    }
  }

  test("validate password") {
    assertAll(Gen.string(minSize = 8, maxSize = 16)) { username ->
      validatePassword(username) shouldBe true
    }
  }
})

