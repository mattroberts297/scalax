package org.scalax.reactive

import akka.actor.ActorSystem
import akka.stream.{Materializer, ActorMaterializer}
import akka.http.scaladsl.Http

import scala.io.StdIn

object Main extends App with RestApi {
  implicit val system = ActorSystem("akka-http-example-system")
  import system.dispatcher
  implicit val materializer: Materializer = ActorMaterializer()
  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  bindingFuture.map { binding =>
    val address = binding.localAddress
    val hostName = address.getHostName
    val port = address.getPort
    println(s"Server online at http://$hostName:$port")
    println("Press RETURN to stop...")
    StdIn.readLine()
    binding.unbind().onComplete(_ => system.shutdown())
  }
}
