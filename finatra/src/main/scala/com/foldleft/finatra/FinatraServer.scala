package com.foldleft.finatra

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter

object FinatraServerMain extends FinatraServer

class FinatraServer extends HttpServer {
  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .add[FinatraFibonacciController]
  }
}
