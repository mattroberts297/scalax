package org.scalax.reactive

import akka.stream.Materializer

trait ImplicitMaterializer {
  implicit val materializer: Materializer
}
