package org.scalax.reactive.db

import scala.concurrent.Future

trait Dao[Key, Model] {
  def create: Future[Unit]
  def put(model: Model): Future[Option[Model]]
  def get(key: Key): Future[Option[Model]]
  def delete(key: Key): Future[Option[Model]]
  def list(offset: Int)(limit: Int): Future[List[Model]]
}
