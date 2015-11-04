package org.scalax.reactive

import java.util.UUID

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{MustMatchers, WordSpec}

class RestApiSpec extends WordSpec with MustMatchers with ScalatestRouteTest {
  s"The $classUnderTest" should {
    "return 501 Not Implemented" when {
      s"POST /$resourceUnderTest is invoked" in new RestApiContext {
        Post(s"/$resourceUnderTest") ~> route ~> check { status must be (NotImplemented) }
      }
      s"GET /$resourceUnderTest/:uuid is invoked" in new RestApiContext {
        Get(s"/$resourceUnderTest/$uuid") ~> route ~> check { status must be (NotImplemented) }
      }
      s"PUT /$resourceUnderTest/:uuid is invoked" in new RestApiContext {
        Put(s"/$resourceUnderTest/$uuid") ~> route ~> check { status must be (NotImplemented) }
      }
      s"DELETE /$resourceUnderTest/:uuid is invoked" in new RestApiContext {
        Delete(s"/$resourceUnderTest/$uuid") ~> route ~> check { status must be (NotImplemented) }
      }
    }
  }
  lazy val resourceUnderTest = "patients"
  lazy val classUnderTest = classOf[RestApi].getSimpleName
  trait RestApiContext extends RestApi { val uuid = UUID.randomUUID() }
}
