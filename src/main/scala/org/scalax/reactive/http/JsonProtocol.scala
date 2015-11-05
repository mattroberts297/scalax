package org.scalax.reactive.http

import java.util.UUID
import org.scalax.reactive.model.Patient
import spray.json._

object JsonProtocol extends DefaultJsonProtocol {
  implicit object UUIDJsonFormat extends RootJsonFormat[UUID] {
    def write(uuid: UUID) = JsString(uuid.toString)
    def read(value: JsValue) = value match {
      case JsString(name) => UUID.fromString(name)
      case _ => deserializationError("UUID expected")
    }
  }
  implicit val patientsFormat = jsonFormat3(Patient)
}