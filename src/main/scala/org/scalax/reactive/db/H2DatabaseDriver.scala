package org.scalax.reactive.db

import java.util.UUID

import slick.driver.H2Driver
import slick.jdbc.JdbcBackend.Database

trait H2DatabaseDriver extends DatabaseDriver {
  Class.forName("org.h2.Driver")
  private val url = s"jdbc:h2:mem:${UUID.randomUUID()};DB_CLOSE_DELAY=-1"
  val driver = H2Driver
  val db = Database.forURL(url)
}
