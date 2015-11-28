package com.foldleft.common

import java.util.UUID

import com.twitter.util.Future
import org.joda.time.DateTimeUtils

object FibonacciCalculator {

  private val fibs: Stream[BigInt] = 0 #:: 1 #:: (fibs zip fibs.tail).map{ t => t._1 + t._2 }

  def apply(service: String)(request: FibonacciRequest): Future[FibonacciResponse] = {
    val fib = request.fib
    val uuid = UUID.randomUUID()
    val time = DateTimeUtils.currentTimeMillis()

    Future(FibonacciResponse(uuid, time, request.name, service, fibs.take(fib).last))
  }
}
