package recast

import java.nio.file.{Files, Paths}

import play.api.libs.json._

import scalaj.http._

class Client(token: String = null, language: String = null) {
    def textRequest(text: String, options: Map[String, String] = Map("" -> "")): Response = {
      val token: String = if (options.contains("token")) options.get("token").get else this.token
      val language: String = if (options.contains("language")) options.get("language").get else this.language
      var params = Seq("text" -> text)

      if (language != null) {
        params = params :+ ("language" -> language)
      }

      if (token == null)
        throw RecastError("Missing token")
      val res = Http("https://api.recast.ai/v1/request")
        .postForm(params)
        .header("Authorization", "Token " + token)
        .asString
      if (!res.isSuccess)
        throw RecastError((Json.parse(res.body) \ "message").as[String])
      new Response(Json.parse(res.body) \ "results")
    }

    def fileRequest(file: String, options: Map[String, String] = Map("" -> "")): Response = {
      val token: String = if (options.contains("token")) options.get("token").get else this.token
      val language: String = if (options.contains("language")) options.get("language").get else this.language
      var params = Seq("language" -> language)

      if (token == null)
        throw new RecastError("Missing token")
      val request = Http("https://api.recast.ai/v1/request")
        .postMulti(MultiPart("voice", file, "audio/wav", Files.readAllBytes(Paths.get(file))))
        .params(params)
        .timeout(connTimeoutMs = 2000, readTimeoutMs = 10000)
        .header("Authorization", "Token " + token)

      if (language != null)
        request.params(Seq("language" -> language))

      val res = request.asString
      if (!res.isSuccess)
        throw new RecastError((Json.parse(res.body) \ "message").as[String])
      new Response(Json.parse(res.body) \ "results")
    }
  }
