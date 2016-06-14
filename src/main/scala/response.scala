import play.api.libs.json._

import recast.sentence.Sentence
import recast.entity.Entity

package recast.response {
  class Response(json: JsValue) {
    val intents    = (json \ "intents").as[Option[List[String]]]
    val source     = (json \ "source").as[String]
    val status     = (json \ "status").as[Int]
    val language   = (json \ "language").as[Option[String]]
    val version    = (json \ "version").as[String]
    val timestamp  = (json \ "timestamp").as[String]
    val sentences  = (json \ "sentences").as[List[JsObject]].map(x => new Sentence(x))

    def intent(): Option[String] = {
      this.intents match {
        case None => None
        case Some(intents) => intents.headOption
      }
    }

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
