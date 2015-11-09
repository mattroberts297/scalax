package org.scalax.reactive.db

import java.util.UUID

import org.scalax.reactive.model.Patient

trait Daos {
  def patients: Dao[UUID, Patient]
}
