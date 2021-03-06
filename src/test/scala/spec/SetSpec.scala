package spec

import org.scalatest.FunSpec
import play.api.libs.json._
import recast._

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
                          "value": 1.9, 
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
    it("should be instanciable with all the params") {
      val client = new Client("YOUR_TOKEN", "EN")

      assert(client.getClass.getName == "recast.Client")
    }

    it("should be instanciable without token") {
      val client = new Client()

      assert(client.getClass.getName == "recast.Client")
    }

    it("should be instanciable without language param") {
      val client = new Client("YOUR_TOKEN")

      assert(client.getClass.getName == "recast.Client")
    }

    describe("textRequest method") {
      it("should fail if no token") {
        val client = new Client

        intercept[RecastError] {
          client.textRequest("Some text")
        }
      }

      it("should fail if invalid language") {
        val client = new Client("YOUR_TOKEN", "en")

        intercept[RecastError] {
          client.textRequest("MY TEXT", Map("language" -> "INVALID_LANGUAGE"))
        }
      }

      it("should succeed with token and language") {
        val client = new Client(sys.env("RECAST_TOKEN"), "en")
        val response = client.textRequest("Test")

        assert(response.status == 200)
        assert(response.language.get == "en")
      }
    }

    describe("fileRequest method") {
      it("should fail if no token") {
        val client = new Client
        val file = getClass.getResource("/test.wav")

        intercept[RecastError] {
          client.fileRequest(file.getPath)
        }
      }

      it("should succeed with token and language") {
        val client = new Client(sys.env("RECAST_TOKEN"), "fr")
        val file = getClass.getResource("/test.wav")
        val response = client.fileRequest(file.getPath)

        assert(response.status == 200)
        assert(response.language.get == "fr")
      }
    }
  }

  describe("Response class") {
    it("should be instanciable and respond to getter") {
      val response = new Response(json)

      // Testing the getters
      assert(response.getClass.getName == "recast.Response")
      assert(response.source == "What can I cook with asparagus and potatoes ?")
      assert(response.intents == Some(Seq("recipe")))
      assert(response.sentences.length == 1)
      assert(response.version == "0.1.4")
      assert(response.timestamp == "2016-06-02T09:37:56+02:00")
      assert(response.status == 200)
    }
    it("should have methods") {
      val response = new Response(json)

      assert(response.intent() == Some("recipe"))
      assert(response.sentence().isDefined)
      assert(response.sentence().get.getClass.getName == "recast.Sentence")
      assert(response.get("ingredient").isDefined)
      assert(response.get("ingredient").get.getClass.getName == "recast.Entity")
      assert(response.all("ingredient").length == 2)
    }
  }

  describe("Sentence class") {
    it("should be instanciable and respond to getter") {
      val list = (json \ "sentences").as[List[JsObject]]
      val sentence = new Sentence(list.head)

      assert(sentence.getClass.getName == "recast.Sentence")
      assert(sentence.source == "What can I cook with asparagus and potatoes ?")
      assert(sentence.sentence_type == Some("what"))
      assert(sentence.action == Some("can cook"))
      assert(sentence.agent == Some("i"))
      assert(sentence.polarity == Some("positive"))
      assert(sentence.entities.length == 2)
    }

    it("should have methods") {
      val list = (json \ "sentences").as[List[JsObject]]
      val sentence = new Sentence(list.head)

      assert(sentence.get("ingredient").isDefined)
      assert(sentence.get("ingredient").get.getClass.getName == "recast.Entity")
      assert(sentence.all("ingredient").length == 2)
    }
  }

  describe("Entity class") {
    it("should be instanciable and have methods") {
      val sentences = (json \ "sentences").as[List[JsObject]]
      val entities = (sentences.head \ "entities").as[Map[String, List[JsObject]]]
      val entity = new Entity("pronoun", entities("pronoun").head)

      assert(entity.name == "pronoun")
      assert(entity.person.get == 1)
      assert(entity.number.get == "singular")
      assert(entity.gender.get == "unkown")
      assert(entity.raw.get == "I")
      assert(entity.hex.isEmpty)
      assert(entity.lng.isEmpty)
      assert(entity.lat.isEmpty)
      assert(entity.unit.isEmpty)
      assert(entity.code.isEmpty)
      assert(entity.next.isEmpty)
      assert(entity.grain.isEmpty)
      assert(entity.order.isEmpty)
      assert(entity.value.get == "1.9")
    }
  }

  describe("Error class") {
    it("should be instanciable") {
      val error = RecastError("test")
      assert(error.getClass.getName == "recast.RecastError")
    }
  }
}
