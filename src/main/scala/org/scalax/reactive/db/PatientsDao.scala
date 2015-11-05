package org.scalax.reactive.db

import java.util.UUID

import org.scalax.reactive.ImplicitExecutionContext
import org.scalax.reactive.model.Patient

import scala.concurrent.Future

class PatientsDao extends Dao[UUID, Patient] { self: DatabaseDriver with ImplicitExecutionContext =>
  import driver.api._

  class Patients(tag: Tag) extends Table[Patient](tag, "patients") {
    def id = column[UUID]("id", O.PrimaryKey)
    def name = column[String]("name", O.Length(254))
    def dob = column[Long]("dob")
    def * = (id, name, dob) <> (Patient.tupled, Patient.unapply)
    def dobIndex = index("dob_index", dob, unique = false)
  }

  private val patients = TableQuery[Patients]

  private def deleteOne(id: UUID) = patients.filter(p => p.id === id).delete

  private def findOne(id: UUID) = patients.filter(p => p.id === id).take(1).result.headOption

  private def insert(patient: Patient) = patients += patient

  override def create(): Future[Unit] = db.run { patients.schema.create }

  override def put(patient: Patient): Future[Option[Patient]] = db.run {
    findOne(patient.id).flatMap { oldPatient =>
      deleteOne(patient.id).flatMap { _ =>
        insert(patient).map { _ =>
          oldPatient
        }
      }
    }
  }

  override def get(id: UUID): Future[Option[Patient]] = db.run {
    findOne(id)
  }

  override def delete(id: UUID): Future[Option[Patient]] = db.run {
    findOne(id).flatMap { oldPatient =>
      deleteOne(id).map { _ =>
        oldPatient
      }
    }
  }

  override def list(offset: Int)(limit: Int): Future[List[Patient]] = db.run {
    patients.sortBy(p => p.dob).drop(offset).take(limit).result
  }.map(_.toList)
}
