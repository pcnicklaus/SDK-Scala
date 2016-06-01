import scalaj.http._
import play.api.libs.json._
import java.nio.file.{Files, Paths}

import recast.response.Response
import recast.error.RecastError

package recast.client {
  class Client(token: String) {
    def textRequest(text: String, options: Map[String, String] = Map("" -> "")): Response = {
      val token = if (options.contains("token")) options.get("token") else this.token

      if (token == None)
        throw new RecastError("Missing token")
      val res = Http("https://api.recast.ai/v1/request")
        .postForm(Seq("text" -> text))
        .header("Authorization", "Token " + this.token)
        .asString
      if (!res.isSuccess)
        throw new RecastError((Json.parse(res.body) \ "message").as[String])
      new Response(Json.parse(res.body) \ "results")
    }

    def fileRequest(file: String, options: Map[String, String] = Map("" -> "")): Response = {
      val token = if (options.contains("token")) options.get("token") else this.token

      if (token == None)
        throw new RecastError("Missing token")
      val res = Http("https://api.recast.ai/v1/request")
        .postMulti(MultiPart("voice", file, "audio/wav", Files.readAllBytes(Paths.get(file))))
        .timeout(connTimeoutMs = 2000, readTimeoutMs = 10000)
        .header("Authorization", "Token " + this.token)
        .asString
      if (!res.isSuccess)
        throw new RecastError((Json.parse(res.body) \ "message").as[String])
      new Response(Json.parse(res.body) \ "results")
    }
  }
}
