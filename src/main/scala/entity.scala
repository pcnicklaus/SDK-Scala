import play.api.libs.json._

package recast.entity {
  class Entity(key: String, json: JsValue) {
    val name      = key
    val raw       = json \ "raw"
    val hex       = json \ "hex"
    val lng       = json \ "lng"
    val deg       = json \ "deg"
    val lat       = json \ "lat"
    val unit      = json \ "unit"
    val code      = json \ "code"
    val next      = json \ "next"
    val grain     = json \ "grain"
    val value     = json \ "value"
    val order     = json \ "order"
    val gender    = json \ "gender"
    val person    = json \ "person"
    val number    = json \ "number"
    val formated  = json \ "formated"
  }
}
