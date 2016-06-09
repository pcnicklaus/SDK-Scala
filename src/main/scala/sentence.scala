import play.api.libs.json._
import recast.entity.Entity

package recast.sentence {
    class Sentence(json: JsValue) {
      val source        = (json \ "source").as[String]
      val sentence_type = (json \ "type").as[Option[String]]
      val action        = (json \ "action").as[Option[String]]
      val agent         = (json \ "agent").as[Option[String]]
      val polarity      = (json \ "polarity").as[Option[String]]
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
