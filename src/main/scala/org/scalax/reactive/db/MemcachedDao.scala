package org.scalax.reactive.db

import org.scalax.reactive.ImplicitExecutionContext
import org.scalax.reactive.model.Patient

import shade.memcached._
import com.github.kxbmap.configs.syntax._
import com.typesafe.config.ConfigFactory
import spray.json._
import org.scalax.reactive.http.JsonProtocol._

class MemcachedDao { self: Daos with ImplicitExecutionContext =>

  case class MemcachedConfig(addresses: String)

  implicit object PatientCodec extends Codec[Patient] {
    override def serialize(value: Patient): Array[Byte] = value.toJson.compactPrint.getBytes("utf-8")

    override def deserialize(data: Array[Byte]): Patient = new String(data, "utf-8").parseJson.convertTo[Patient]
  }

  private val config = ConfigFactory.load()

  private val memCachedConfig = config.get[MemcachedConfig]("org.scalax.reactive.memcached")

  private val memcached = Memcached(Configuration(memCachedConfig.addresses), context)
}
