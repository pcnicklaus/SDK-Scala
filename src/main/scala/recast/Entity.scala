package recast

import play.api.libs.json._

class Entity(key: String, json: JsValue) {
  val name = key
  val raw = extractStringOption(json \ "raw")
  val hex = extractStringOption(json \ "hex")
  val lng = extractFloatOption(json \ "lng")
  val deg = extractFloatOption(json \ "deg")
  val lat = extractFloatOption(json \ "lat")
  val unit = extractStringOption(json \ "unit")
  val code = extractStringOption(json \ "code")
  val next = extractIntOption(json \ "next")
  val grain = extractStringOption(json \ "grain")
  val order = extractStringOption(json \ "order")
  val gender = extractStringOption(json \ "gender")
  val person = extractIntOption(json \ "person")
  val number = extractStringOption(json \ "number")
  val formatted = extractStringOption(json \ "formatted")
  val value = json \ "value" match {
    case undefined: JsUndefined => None
    case p => Some(p.toString) // Special case
  }

  private def extractFloatOption(value: JsValue): Option[Float] = value match {
    case undefined: JsUndefined => None
    case _ => Some(value.as[Float])
  }

  private def extractIntOption(value: JsValue): Option[Int] = value match {
    case undefined: JsUndefined => None
    case _ => Some(value.as[Int])
  }

  private def extractStringOption(value: JsValue): Option[String] = value match {
    case _: JsUndefined => None
    case _: JsString => Some(value.as[String])
  }

}
