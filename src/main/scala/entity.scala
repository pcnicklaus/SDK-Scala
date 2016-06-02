import play.api.libs.json._

package recast.entity {
  class Entity(key: String, json: JsValue) {
    val name = key
    private val raw_      = json \ "raw"
    private val hex_      = json \ "hex"
    private val lng_      = json \ "lng"
    private val deg_      = json \ "deg"
    private val lat_      = json \ "lat"
    private val unit_     = json \ "unit"
    private val code_     = json \ "code"
    private val next_     = json \ "next"
    private val grain_    = json \ "grain"
    private val value_    = json \ "value"
    private val order_    = json \ "order"
    private val gender_   = json \ "gender"
    private val person_   = json \ "person"
    private val number_   = json \ "number"
    private val formated_ = json \ "formated"

    def raw():Option[String] = {
      this.raw_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.raw_.as[String])
      }
    }

    def hex():Option[String] = {
      this.hex_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.hex_.as[String])
      }
    }

    def lng():Option[Float] = {
      this.lng_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.lng_.as[Float])
      }
    }

    def deg():Option[Float] = {
      this.deg_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.deg_.as[Float])
      }
    }

    def lat():Option[Float] = {
      this.lat_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.lat_.as[Float])
      }
    }

    def unit():Option[String] = {
      this.unit_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.unit_.as[String])
      }
    }

    def code():Option[String] = {
      this.code_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.code_.as[String])
      }
    }

    def next():Option[Int] = {
      this.next_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.next_.as[Int])
      }
    }

    def grain():Option[String] = {
      this.grain_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.grain_.as[String])
      }
    }

    def order():Option[String] = {
      this.order_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.order_.as[String])
      }
    }

    def gender():Option[String] = {
      this.gender_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.gender_.as[String])
      }
    }

    def person():Option[Int] = {
      this.person_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.person_.as[Int])
      }
    }

    def number():Option[String] = {
      this.number_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.number_.as[String])
      }
    }

    def formated():Option[String] = {
      this.formated_.isInstanceOf[JsUndefined] match {
        case true => None
        case _ => Some(this.formated_.as[String])
      }
    }
  }
}
