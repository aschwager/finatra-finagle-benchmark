package com.foldleft.finagle

import com.twitter.finagle.Http
import com.twitter.finagle.http._
import com.twitter.finagle.http.path._
import com.twitter.finagle.http.service.RoutingService
import com.twitter.util.Await

object FinagleServer extends App {

  lazy val server = {
    val router = RoutingService.byMethodAndPathObject[Request]{
      case (Method.Post, Root / "fibonacci") => FinagleFibonacciService
    }
    HttpMuxer.addRichHandler("/", router)
    Http.serve(":8888", HttpMuxer)
  }

  Await.ready(server)
}
