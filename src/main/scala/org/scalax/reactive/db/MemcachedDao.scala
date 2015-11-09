package org.scalax.reactive.db

import java.util.UUID

import org.scalax.reactive.ImplicitExecutionContext
import org.scalax.reactive.model.Patient

import shade.memcached._
import com.github.kxbmap.configs.syntax._
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

object MemcachedImplicits {
  import spray.json._
  import org.scalax.reactive.http.JsonProtocol._

  implicit object PatientCodec extends Codec[Patient] {
    override def serialize(value: Patient): Array[Byte] = {
      value.toJson.compactPrint.getBytes("utf-8")
    }

    override def deserialize(data: Array[Byte]): Patient = {
      new String(data, "utf-8").parseJson.convertTo[Patient]
    }
  }

  implicit object PatientsCodec extends Codec[List[Patient]] {
    override def serialize(value: List[Patient]): Array[Byte] = {
      value.toJson.compactPrint.getBytes("utf-8")
    }

    override def deserialize(data: Array[Byte]): List[Patient] = {
      new String(data, "utf-8").parseJson.convertTo[List[Patient]]
    }
  }
}

class MemcachedDao(val underlying: Dao[UUID, Patient])
                  (implicit val context: ExecutionContext) extends Dao[UUID, Patient] {
  import org.scalax.reactive.db.MemcachedImplicits._

  case class MemcachedConfig(addresses: String)

  private val config = ConfigFactory.load()

  private val memCachedConfig = config.get[MemcachedConfig]("org.scalax.reactive.memcached")

  private val cache = Memcached(Configuration(memCachedConfig.addresses), context)

  override def create(): Future[Unit] = underlying.create()

  override def put(patient: Patient): Future[Option[Patient]] = {
    val result = underlying.put(patient)
    result.map { _ => cache.set(patient.id.toString, patient, Duration.Inf) }
    result
  }

  override def get(id: UUID): Future[Option[Patient]] = {
    cache.get[Patient](id.toString).flatMap {
      case some@Some(patient) => Future(some)
      case None =>
        val result = underlying.get(id)
        result.map {
          case Some(patient) => cache.set(patient.id.toString, patient, Duration.Inf)
          case None =>
        }
        result
    }
  }

  override def delete(id: UUID): Future[Option[Patient]] = {
    val result = underlying.delete(id)
    result.map { _ => cache.delete(id.toString) }
    result
  }

  override def list(offset: Int)(limit: Int): Future[List[Patient]] = {
    val key = s"patients-$offset-$limit"
    cache.get[List[Patient]](key).flatMap {
      case Some(list) => Future(list)
      case None =>
        val result = underlying.list(offset)(limit)
        result.map { patients => cache.set(key, patients, 10.seconds)}
        result
    }
  }
}
