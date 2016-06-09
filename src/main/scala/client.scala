import scalaj.http._
import play.api.libs.json._
import java.nio.file.{Files, Paths}

import recast.response.Response
import recast.error.RecastError

package recast.client {
  object ClientConstants {
    val languages: Array[String] = Array("EN", "FR")
  }

  class Client(token: String, language: String) {
    def textRequest(text: String, options: Map[String, String] = Map("" -> "")): Response = {
      val token: String = if (options.contains("token")) options.get("token").get else this.token
      val language: String = if (options.contains("language")) options.get("language").get else this.language 

      if (token == None)
        throw new RecastError("Missing token")
      else if (!ClientConstants.languages.contains(language))
        throw new RecastError("Invalid language provided")
      val res = Http("https://api.recast.ai/v1/request")
        .postForm(Seq("text" -> text, "language" -> language))
        .header("Authorization", "Token " + token)
        .asString
      if (!res.isSuccess)
        throw new RecastError((Json.parse(res.body) \ "message").as[String])
      new Response(Json.parse(res.body) \ "results")
    }

    def fileRequest(file: String, options: Map[String, String] = Map("" -> "")): Response = {
      val token: String = if (options.contains("token")) options.get("token").get else this.token
      val language: String = if (options.contains("language")) options.get("language").get else this.language 

      if (token == None)
        throw new RecastError("Missing token")
      else if (!ClientConstants.languages.contains(language))
        throw new RecastError("Invalid language provided")
      val res = Http("https://api.recast.ai/v1/request")
        .postMulti(MultiPart("voice", file, "audio/wav", Files.readAllBytes(Paths.get(file))))
        .param("language", language)
        .timeout(connTimeoutMs = 2000, readTimeoutMs = 10000)
        .header("Authorization", "Token " + token)
        .asString
      if (!res.isSuccess)
        throw new RecastError((Json.parse(res.body) \ "message").as[String])
      new Response(Json.parse(res.body) \ "results")
    }
  }
}
