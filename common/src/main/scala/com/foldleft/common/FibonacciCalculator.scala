package com.foldleft.common

import java.util.UUID

import com.twitter.util.Future
import org.joda.time.DateTimeUtils

import scala.annotation.tailrec

object FibonacciCalculator {

  //  private val fibs: Stream[BigInt] = 0 #:: 1 #:: (fibs zip fibs.tail).map{ t => t._1 + t._2 }

  private def fibs(n: Int): BigInt = {
    @tailrec
    def fib_tail(n: Int, a: BigInt, b: BigInt): BigInt = n match {
      case 0 => a
      case _ => fib_tail(n - 1, b, a + b)
    }
    fib_tail(n, 0, 1)
  }

  def apply(service: String)(request: FibonacciRequest): Future[FibonacciResponse] = {
    val fib = request.fib
    val uuid = UUID.randomUUID()
    val time = DateTimeUtils.currentTimeMillis()

    Future(FibonacciResponse(uuid, time, request.name, service, fibs(fib)))
  }
}
