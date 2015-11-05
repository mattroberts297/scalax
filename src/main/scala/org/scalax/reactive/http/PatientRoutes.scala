package org.scalax.reactive.http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import org.scalax.reactive.db.Daos
import org.scalax.reactive.model.Patient
import org.scalax.reactive.{ImplicitExecutionContext, ImplicitMaterializer}
import spray.json._

import scala.math.min

trait PatientRoutes extends SprayJsonSupport { self: Daos with ImplicitExecutionContext with ImplicitMaterializer =>
  import JsonProtocol._

  val route: Route = {
    pathPrefix("patients") {
      pathSuffix(JavaUUID) { uuid =>
        get {
          complete(patients.get(uuid).map(p => json(p).getOrElse(notFound)))
        } ~
        put {
          entity(as[Patient]) { patient =>
            complete(patients.put(patient).map(p => json(p).getOrElse(created)))
          }
        } ~
        delete {
          complete(patients.delete(uuid).map(p => json(p).getOrElse(notFound)))
        }
      } ~
      pathEnd {
        get {
          parameters("drop".as[Int].?(0), "take".as[Int].?(10)) { (drop, take) =>
            complete(patients.list(drop)(min(take, 10)).map(l => json(l)))
          }
        }
      }
    }
  }

  def json[T : JsonWriter](o: Option[T]): Option[HttpResponse] = {
    o.map(t => json(t))
  }

  def json[T : JsonWriter](t: T): HttpResponse = {
    HttpResponse(
      status = StatusCodes.OK,
      entity = HttpEntity(ContentTypes.`application/json`, t.toJson.compactPrint))
  }

  def created: HttpResponse = {
    HttpResponse(status = StatusCodes.Created)
  }

  def notFound: HttpResponse = {
    HttpResponse(status = StatusCodes.NotFound)
  }
}


