package org.scalax.reactive

import scala.concurrent.ExecutionContext.global

trait GlobalExecutionContext extends ImplicitExecutionContext {
  implicit val context = global
}
