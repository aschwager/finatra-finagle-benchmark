package com.foldleft.finatra

import com.twitter.finatra.http.Controller
import com.foldleft.common.{FibonacciCalculator, FibonacciRequest}

class FinatraFibonacciController extends Controller {

  private val calculator = FibonacciCalculator("finatra") _

  post("/fibonacci"){ request: FibonacciRequest =>
    calculator(request) map (response.ok(_))
  }
}
