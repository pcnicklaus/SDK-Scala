import play.api.libs.json._

import recast.sentence.Sentence
import recast.entity.Entity

package recast.response {
  class Response(json: JsValue) {
    val intents    = (json \ "intents").as[List[String]]
    val source     = json \ "source"
    val status     = json \ "status"
    val version    = json \ "version"
    val timestamp  = json \ "timestamp"
    val sentences  = (json \ "sentences").as[List[JsObject]].map(x => new Sentence(x))

    def intent(): Option[String] = this.intents.headOption

    def sentence(): Option[Sentence] = this.sentences.headOption

    def get(name: String): Option[Entity] = {
      for (sentence <- this.sentences) {
        for (entity <- sentence.entities) {
          if (entity.head.name == name)
            return Some(entity.head)
        }
      }
      None
    }

    def all(name: String): List[Entity] = {
      var array = List[Entity]()

      for (sentence <- this.sentences)
        for (entity <- sentence.entities)
          if (entity.head.name == name)
            entity.foreach(x => array ::= x)
      array
    }
  }
}
