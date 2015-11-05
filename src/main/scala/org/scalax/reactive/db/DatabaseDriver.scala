package org.scalax.reactive.db

import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend._

trait DatabaseDriver {
  val db: Database
  val driver: JdbcProfile
}
