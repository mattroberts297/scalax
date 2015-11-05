package org.scalax.reactive

import scala.concurrent.ExecutionContext

trait ImplicitExecutionContext {
  implicit val context: ExecutionContext
}
