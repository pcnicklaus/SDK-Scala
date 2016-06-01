import play.api.libs.json._
import recast.entity.Entity

package recast.sentence {
    class Sentence(json: JsValue) {
      val source        = json \ "source"
      val sentence_type = json \ "type"
      val action        = json \ "action"
      val agent         = json \ "agent"
      val polarity      = json \ "polarity"
      val entities      = (json \ "entities").as[Map[String, JsValue]].toList.map{
        case(name, data) => data.as[List[JsObject]].map(x => new Entity(name, x))
      }

      def get(name: String): Option[Entity] = {
        for (entity <- this.entities) {
          if (entity.head.name == name)
            return Some(entity.head)
        }
        None
      }

      def all(name: String): List[Entity] = {
        var array = List[Entity]()

        for (entity <- this.entities)
          if (entity.head.name == name)
            entity.foreach(x => array ::= x)
        array
      }
    }
}
