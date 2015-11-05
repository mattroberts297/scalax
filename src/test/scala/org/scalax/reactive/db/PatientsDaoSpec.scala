package org.scalax.reactive.db

import java.util.UUID

import org.scalatest.{Matchers, WordSpec}
import org.scalax.reactive.http.PatientRoutes
import org.scalax.reactive.model.Patient
import org.scalax.reactive.GlobalExecutionContext

import scala.concurrent.Await
import scala.concurrent.duration._

class PatientsDaoSpec extends WordSpec with Matchers {
  s"The $classUnderTest" should {
    "return None" when {
      "put is invoked on an empty database" in new PatientsDaoContext {
        val result = Await.result(dao.put(patient), atMost)
        result should be (None)
      }

      "get is invoked on an empty database" in new PatientsDaoContext {
        val result = Await.result(dao.get(uuid), atMost)
        result should be (None)
      }
    }

    "return Some(patient)" when {
      "put and then get is invoked" in new PatientsDaoContext {
        Await.result(dao.put(patient), atMost)
        val result = Await.result(dao.get(uuid), atMost)
        result should be (Some(patient))
      }

      "put and then put is invoked" in new PatientsDaoContext {
        Await.result(dao.put(patient), atMost)
        val result = Await.result(dao.put(updatedPatient), atMost)
        result should be (Some(patient))
      }
    }

    "return Some(updatedPatient)" when {
      "put, put and then get is invoked" in new PatientsDaoContext {
        Await.result(dao.put(patient), atMost)
        Await.result(dao.put(updatedPatient), atMost)
        val result = Await.result(dao.get(uuid), atMost)
        result should be (Some(updatedPatient))
      }
    }
  }

  lazy val classUnderTest = classOf[PatientRoutes].getSimpleName

  trait PatientsDaoContext {
    val atMost = 5.second
    val dao = new PatientsDao with H2DatabaseDriver with GlobalExecutionContext
    Await.result(dao.create(), atMost)
    val uuid = UUID.randomUUID()
    val patient = Patient(uuid, "Matt Roberts", 0l)
    val updatedPatient = Patient(uuid, "Matt Roberts", 541468800l)
  }
}
