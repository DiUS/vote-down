import play.api.test.PlaySpecification

class ExampleUnitSpec extends PlaySpecification {
  "in this context my target" should {
    def foo(i:Int) = { i + 1}

    "test a thing" in {
      foo(4) must beEqualTo(5)
    }
  }
}
