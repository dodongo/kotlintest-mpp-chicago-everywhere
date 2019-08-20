package kotlineverywhere

import com.sksamuel.kotlineverywhere.validateSocial
import io.kotlintest.Description
import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.kotlintest.TestType
import io.kotlintest.core.TestCaseConfig
import io.kotlintest.core.TestContext
import io.kotlintest.core.fromSpecClass
import io.kotlintest.core.sourceRef
import io.kotlintest.shouldBe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.coroutines.CoroutineContext
import kotlin.test.FrameworkAdapter
import kotlin.test.Test

abstract class SpecParent : Spec {

//  companion object {
//    init {
//      val adapter = KotlinTestFrameworkAdapter()
//   //   println("ADapter = " + adapter)
//      js("require")("kotlin-test").setAdapter(adapter)
//    }
//  }

  protected val rootTestCases = mutableListOf<TestCase>()
}

external fun describe(name: String, fn: () -> Unit)
external fun xdescribe(name: String, fn: () -> Unit)
external fun it(name: String, fn: () -> Any?)
external fun xit(name: String, fn: () -> Any?)

external object QUnit {
  fun module(name: String, suiteFn: () -> Unit): Unit
  fun test(name: String, testFn: (dynamic) -> Any?): Unit
  fun skip(name: String, testFn: (dynamic) -> Any?): Unit
  val config: dynamic
}

fun testContext(d: Description,
                coroutineContext: CoroutineContext): TestContext = object : TestContext(coroutineContext) {

  override suspend fun registerTestCase(testCase: TestCase) {
    QUnit.test(testCase.name) { assert ->
      assert.ok(true)
      val t = testCase.test
      GlobalScope.promise {
        testContext(d.append(testCase.name), coroutineContext).t()
      }
    }
  }

  override fun description(): Description = d
}

// we need to use this: https://youtrack.jetbrains.com/issue/KT-22228
fun generateTests2(rootTests: List<TestCase>) {

  fun runner(testCase: TestCase) = GlobalScope.promise {
    val t = testCase.test
    testContext(testCase.description, coroutineContext).t()
  }

  rootTests.forEach {
    when (it.type) {
      TestType.Container -> QUnit.module(it.name) { runner(it) }
      TestType.Test -> QUnit.test(it.name) { assert ->
        assert.ok(true)
        runner(it)
      }
    }
  }
}

class KotlinTestFrameworkAdapter : FrameworkAdapter {

  init {
    println("creating adapter!!!")
  }

  override fun suite(name: String, ignored: Boolean, suiteFn: () -> Unit) {
    println("suitttttt!!!")
    throw RuntimeException("boom")
    // QUnit.module(name, suiteFn)
  }

  override fun test(name: String, ignored: Boolean, testFn: () -> Any?) {
    println("tesssssss!!!")
    //  if (name === "kotlintest_test_generator") {
    //    testFn()
    //  }
  }
}

abstract class SuiteSpec2(body: SuiteSpec2.() -> Unit = {}) : SpecParent() {

  init {
    body()
  }

  // this is a dummy method, intercepted by the kotlin.js framework adapter to generate tests
  @Test
  fun kotlintest_test_generator() {
    println("Foo")
    println("Foo")
 //   QUnit.test("hello") { it.ok(true) }
  }

  @Test
  fun kotlintest_test_generator2() {
  //  QUnit.test("hello2") { it.ok(true) }
  }

  override fun testCases(): List<TestCase> = rootTestCases

  override fun closeResources() {}

  fun suite(name: String, test: suspend SuiteScope.() -> Unit) {
    rootTestCases.add(
        TestCase(
            Description.fromSpecClass(this::class).append(name),
            this,
            { SuiteScope(this).test() },
            sourceRef(),
            TestType.Container,
            TestCaseConfig()
        )
    )
  }

  inner class SuiteScope(private val context: TestContext) {
    suspend fun test(name: String, test: suspend TestContext.() -> Unit) {
      context.registerTestCase(name, this@SuiteSpec2, test, TestCaseConfig(), TestType.Test)
    }
  }
}

class ValidationTest : SuiteSpec2({

  suite("social regex") {
    test("should pass valid numbers") {
      validateSocial("123-456-1111") shouldBe true
      validateSocial("999-333-4444") shouldBe true
    }
    test("should fail invalid numbers") {
      validateSocial("a12-456-1111") shouldBe false
      validateSocial("a12-456-cccc") shouldBe false
      validateSocial("123456-1111") shouldBe false
      validateSocial("123-4561117") shouldBe false
      validateSocial("122") shouldBe false
      validateSocial("") shouldBe false
      validateSocial("1234567899") shouldBe false
    }
  }

  suite("email regex") {
    test("should require an @ and tld") {
      validateSocial("a@a.com") shouldBe true
      validateSocial("sam@sam.com") shouldBe true
    }
    test("should fail without tld") {
      validateSocial("a@a") shouldBe false
      validateSocial("@a") shouldBe false
    }
    test("should fail without an @") {
      validateSocial("a.com") shouldBe false
    }
  }
})