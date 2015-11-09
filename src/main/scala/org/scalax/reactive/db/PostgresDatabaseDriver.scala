package org.scalax.reactive.db

import slick.driver.PostgresDriver
import slick.jdbc.JdbcBackend.Database

object PostgresDatabaseDriver extends DatabaseDriver {
  override val db = Database.forConfig("org.scalax.reactive.db")
  override val driver = PostgresDriver
}
