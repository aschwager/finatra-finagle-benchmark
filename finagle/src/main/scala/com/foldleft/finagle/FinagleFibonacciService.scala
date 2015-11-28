package com.foldleft.finagle

import com.fasterxml.jackson.databind.{PropertyNamingStrategy, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Status, Version, Response, Request}
import com.twitter.util.Future
import com.foldleft.common.{FibonacciCalculator, FibonacciRequest}

object FinagleFibonacciService extends Service[Request, Response] {

  private val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)

  private val calculator = FibonacciCalculator("finagle") _

  override def apply(request: Request): Future[Response] = {

    val fibRequest = mapper.readValue[FibonacciRequest](request.contentString)
    val fibResponse = calculator(fibRequest)

    fibResponse map { answer =>
      val response = Response(Version.Http11, Status.Ok)
      response.setContentTypeJson()
      val body = mapper.writeValueAsString(answer)
      response.setContentString(body)
      response
    }
  }
}
