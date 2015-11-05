package org.scalax.reactive.http

import java.util.UUID

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.mock.MockitoSugar
import org.scalatest.{MustMatchers, WordSpec}
import org.scalax.reactive._
import org.scalax.reactive.db.{Daos, PatientsDao}

class PatientRoutesSpec extends WordSpec with MustMatchers with ScalatestRouteTest
  with MockitoSugar with PatientRoutes with Daos with ImplicitExecutionContext with ImplicitMaterializer {

  override implicit val context = system.dispatcher
  override val patients = mock[PatientsDao]

  s"The $classUnderTest" should {
    "return 501 Not Implemented" when {
//      s"GET /$resourceUnderTest/:uuid is invoked" in new RestApiContext {
//        Get(s"/$resourceUnderTest/$uuid") ~> route ~> check { status must be (NotImplemented) }
//      }
//      s"PUT /$resourceUnderTest/:uuid is invoked" in new RestApiContext {
//        Put(s"/$resourceUnderTest/$uuid") ~> route ~> check { status must be (NotImplemented) }
//      }
//      s"DELETE /$resourceUnderTest/:uuid is invoked" in new RestApiContext {
//        Delete(s"/$resourceUnderTest/$uuid") ~> route ~> check { status must be (NotImplemented) }
//      }
    }
  }

  lazy val resourceUnderTest = "patients"
  lazy val classUnderTest = classOf[PatientRoutes].getSimpleName

  trait RestApiContext {
    val uuid = UUID.randomUUID()
  }
}
