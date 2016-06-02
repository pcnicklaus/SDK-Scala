import org.scalatest.FunSpec
import play.api.libs.json._

import recast.client._
import recast.response._
import recast.sentence._
import recast.entity._
import recast.error._

class SetSpec extends FunSpec {
  val json = Json.parse("""
    {
      "source": "What can I cook with asparagus and potatoes ?",
      "intents": [
          "recipe"
      ],
      "sentences": [
          {
              "source": "What can I cook with asparagus and potatoes ?",
              "type": "what",
              "action": "can cook",
              "agent": "i",
              "polarity": "positive",
              "entities": {
                  "pronoun": [
                      {
                          "person": 1,
                          "number": "singular",
                          "gender": "unkown",
                          "raw": "I"
                      }
                  ],
                  "ingredient": [
                      {
                          "value": "asparagus",
                          "raw": "asparagus"
                      },
                      {
                          "value": "potato",
                          "raw": "potatoes"
                      }
                  ]
              }
          }
      ],
      "version": "0.1.4",
      "timestamp": "2016-06-02T09:37:56+02:00",
      "status": 200
    }
    """)

  describe("Client class") {
    it("should be instanciable") {
      val client = new Client("YOUR_TOKEN")
      assert(client.getClass.getName == "recast.client.Client")
    }
  }

  describe("Response class") {
    it("should be instanciable and respond to getter") {
      val response = new Response(json)

      // Testing the getters
      assert(response.getClass.getName == "recast.response.Response")
      assert(response.source == "What can I cook with asparagus and potatoes ?")
      assert(response.intents == Seq("recipe"))
      assert(response.sentences.length == 1)
      assert(response.version == "0.1.4")
      assert(response.timestamp == "2016-06-02T09:37:56+02:00")
      assert(response.status == 200)
    }
    it("should have methods") {
      val response = new Response(json)

      assert(response.intent() == Some("recipe"))
      assert(response.sentence() != None)
      assert(response.sentence().get.getClass.getName == "recast.sentence.Sentence")
      assert(response.get("ingredient") != None)
      assert(response.get("ingredient").get.getClass.getName == "recast.entity.Entity")
      assert(response.all("ingredient").length == 2)
    }
  }

  describe("Sentence class") {
    it("should be instanciable and respond to getter") {
      val list = (json \ "sentences").as[List[JsObject]]
      val sentence = new Sentence(list(0))

      assert(sentence.getClass.getName == "recast.sentence.Sentence")
      assert(sentence.source == "What can I cook with asparagus and potatoes ?")
      assert(sentence.sentence_type == "what")
      assert(sentence.action == "can cook")
      assert(sentence.agent == "i")
      assert(sentence.polarity == "positive")
      assert(sentence.entities.length == 2)
    }

    it("should have methods") {
      val list = (json \ "sentences").as[List[JsObject]]
      val sentence = new Sentence(list(0))

      assert(sentence.get("ingredient") != None)
      assert(sentence.get("ingredient").get.getClass.getName == "recast.entity.Entity")
      assert(sentence.all("ingredient").length == 2)
    }
  }

  describe("Entity class") {
    it("should be instanciable and have methods") {
      val sentences = (json \ "sentences").as[List[JsObject]]
      val entities = ((sentences(0) \ "entities").as[Map[String, List[JsObject]]])
      val entity = new Entity("pronoun", entities("pronoun")(0))

      assert(entity.name == "pronoun")
      assert(entity.person().get == 1)
      assert(entity.number().get == "singular")
      assert(entity.gender().get == "unkown")
      assert(entity.raw().get == "I")
      assert(entity.hex() == None)
      assert(entity.lng() == None)
      assert(entity.lat() == None)
      assert(entity.unit() == None)
      assert(entity.code() == None)
      assert(entity.next() == None)
      assert(entity.grain() == None)
      assert(entity.order() == None)
    }
  }

  describe("Error class") {
    it("should be instanciable") {
      var error = new Error("test")
      assert(error.getClass.getName == "java.lang.Error")
    }
  }
}
