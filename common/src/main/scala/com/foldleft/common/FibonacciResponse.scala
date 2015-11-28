package com.foldleft.common

import java.util.UUID

case class FibonacciResponse(id: UUID, time: Long, name: String, service: String, answer: BigInt)
