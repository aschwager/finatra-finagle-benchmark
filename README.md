# finatra-finagle-benchmark

The goal of this project is the detect the amount of overhead using [Finatra](https://github.com/twitter/finatra) vs [Finagle](https://github.com/twitter/finagle).

Each exposes a single endpoint `POST /fibonacci` which does the following:

1. Parses the request body (JSON) which contains a `name: String` and `fib: Int`
2. Calculates the fibonacci sequence up to `fib`
3. Creates a Scala case class response object with the following information
  * a randomly generated Java UUID, 
  * the current time
  * the calculated fibonacci number
  * the name of the service (finagle or finatra)
  * the name from the request
4. Returns a 200 with the above response converted to a JSON body

### Build and Server Startup instructions

Run the following command to compile and generate executable scripts

```
sbt stage
```

By default each server runs on port 8888, so they cannot be running at the same time unless this behavior is modified
* Finagle Server: ./finagle/target/universal/stage/bin/finagle 
* Finatra Server: ./finatra/target/universal/stage/bin/finatra 

### Testing Method

For benchmark testing I used [wg/wrk](https://github.com/wg/wrk) with the following lua script

```
wrk.method = "POST"
wrk.body   = "{\"name\":\"benchmark\",\"fib\":500}"
wrk.headers["Content-Type"] = "application/json"
```

### Results - Execution 1

* MacBook Pro (Retina, Mid 2012)
* Processor 2.6 GHz Intel Core i7
* Memory 8 GB 1600 MHz DDR3

##### Finagle

```
[~/wrk]$ ./wrk -t16 -c400 -d60s -s fin-fan.lua http://localhost:8888/fibonacci                                                                                                                                                                                     *[master]
Running 1m test @ http://localhost:8888/fibonacci
  16 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     9.27ms    2.45ms 162.97ms   90.65%
    Req/Sec     2.58k   371.64    12.49k    92.92%
  2453297 requests in 1.00m, 713.59MB read
  Socket errors: connect 0, read 703, write 0, timeout 0
Requests/sec:  40833.13
Transfer/sec:     11.88MB
```

##### Finatra

```
[~/wrk]$ ./wrk -t16 -c400 -d60s -s fin-fan.lua http://localhost:8888/fibonacci                                                                                                                                                                                     *[master]
Running 1m test @ http://localhost:8888/fibonacci
  16 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     9.81ms    4.36ms 174.85ms   90.49%
    Req/Sec     2.50k   307.14     5.04k    91.01%
  2385607 requests in 1.00m, 696.18MB read
  Socket errors: connect 0, read 618, write 0, timeout 0
Requests/sec:  39731.10
Transfer/sec:     11.59MB
```
