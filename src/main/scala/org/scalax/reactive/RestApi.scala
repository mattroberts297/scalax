package org.scalax.reactive

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

trait RestApi {
  val route: Route = {
    pathPrefix("patients") {
      pathEndOrSingleSlash {
        post {
          complete(StatusCodes.NotImplemented)
        }
      } ~
      pathSuffix(JavaUUID) { uuid =>
        get {
          complete(StatusCodes.NotImplemented)
        } ~
        put {
          complete(StatusCodes.NotImplemented)
        } ~
        delete {
          complete(StatusCodes.NotImplemented)
        }
      }
    }
  }
}
