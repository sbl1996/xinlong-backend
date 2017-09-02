package xinlong
package utils

import spray.json._
import java.sql.Timestamp

import models._

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit object TimestampJsonFormat extends RootJsonFormat[Timestamp] {
    def write(t: Timestamp) = JsNumber(t.getTime)

    def read(v: JsValue) = v match {
      case JsNumber(t) => new Timestamp(t.toLong)
      case _ => deserializationError("java.sql.Timestamp expected")
    }
  }

  implicit val productFormat = jsonFormat6(ProductEntity)
  implicit val godownEntryFormat = jsonFormat4(GodownEntryEntity)
  implicit val godownEntryItemFormat = jsonFormat4(GodownEntryItem)
  implicit val godownEntryItemCreateFormat = jsonFormat3(GodownEntryItemCreate)
  implicit val godownEntryCreateFormat = jsonFormat4(GodownEntryCreate)
  implicit val completeGodownEntryFormat = jsonFormat5(CompleteGodownEntry)
}