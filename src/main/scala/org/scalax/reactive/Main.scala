package org.scalax.reactive

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import org.scalax.reactive.db.{PostgresDatabaseDriver, PatientsDao, Daos}
import org.scalax.reactive.http.PatientRoutes

object Main extends App with PatientRoutes with Daos with ImplicitExecutionContext with ImplicitMaterializer {
  implicit val system = ActorSystem("reactive-system")
  implicit val context = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val patients = new PatientsDao with PostgresDatabaseDriver with ImplicitExecutionContext {
    implicit val context = Main.context
  }

  patients.create().onComplete(_ =>
    Http().bindAndHandle(route, "0.0.0.0", 8080).map { binding =>
      val address = binding.localAddress
      val hostName = address.getHostName
      val port = address.getPort
      println(s"Spray up at http://$hostName:$port")
    }
  )
}
