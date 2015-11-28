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
    Latency    11.14ms   10.51ms 268.47ms   97.55%
    Req/Sec     2.39k   437.70     3.41k    90.66%
  2285670 requests in 1.00m, 667.01MB read
  Socket errors: connect 0, read 594, write 0, timeout 0
Requests/sec:  38071.96
Transfer/sec:     11.11MB
```

##### Finatra

```
[~/wrk]$ ./wrk -t16 -c400 -d60s -s fin-fan.lua http://localhost:8888/fibonacci                                                                                                                                                                                     *[master]
Running 1m test @ http://localhost:8888/fibonacci
  16 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    12.36ms   13.65ms 253.15ms   95.41%
    Req/Sec     2.31k   498.43     3.58k    88.12%
  2204590 requests in 1.00m, 645.46MB read
  Socket errors: connect 0, read 637, write 0, timeout 0
Requests/sec:  36702.41
Transfer/sec:     10.75MB
```

##### Difference

Finatra / Finagle = 0.964027331

### Results - Execution 2

* MacBook Pro (Retina, Mid 2012)
* Processor 2.6 GHz Intel Core i7
* Memory 8 GB 1600 MHz DDR3

##### Finagle

```
[~/wrk]$ ./wrk -t16 -c400 -d60s -s fin-fan.lua http://localhost:8888/fibonacci                                                                                                                                                                                     *[master]
Running 1m test @ http://localhost:8888/fibonacci
  16 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    11.21ms   15.40ms 595.90ms   98.02%
    Req/Sec     2.42k   482.14     9.93k    91.05%
  2293522 requests in 1.00m, 669.31MB read
  Socket errors: connect 0, read 682, write 0, timeout 0
Requests/sec:  38180.35
Transfer/sec:     11.14MB
```

##### Finatra

```
[~/wrk]$ ./wrk -t16 -c400 -d60s -s fin-fan.lua http://localhost:8888/fibonacci                                                                                                                                                                                     *[master]
Running 1m test @ http://localhost:8888/fibonacci
  16 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    13.34ms   21.33ms 650.00ms   94.92%
    Req/Sec     2.41k   571.21     4.15k    85.70%
  2286147 requests in 1.00m, 669.33MB read
  Socket errors: connect 0, read 663, write 0, timeout 0
Requests/sec:  38056.51
Transfer/sec:     11.14MB
```

##### Difference

Finatra / Finagle = 0.996756447

### Results - Execution 3

* MacBook Pro (Retina, Mid 2012)
* Processor 2.6 GHz Intel Core i7
* Memory 8 GB 1600 MHz DDR3

##### Finagle

```
[~/wrk]$ ./wrk -t16 -c400 -d60s -s fin-fan.lua http://localhost:8888/fibonacci                                                                                                                                                                                     *[master]
Running 1m test @ http://localhost:8888/fibonacci
  16 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    11.08ms   12.78ms 593.52ms   98.40%
    Req/Sec     2.30k   509.77     8.67k    84.29%
  2182630 requests in 1.00m, 636.94MB read
  Socket errors: connect 0, read 632, write 0, timeout 0
Requests/sec:  36337.01
Transfer/sec:     10.60MB
```

##### Finatra

```
[~/wrk]$ ./wrk -t16 -c400 -d60s -s fin-fan.lua http://localhost:8888/fibonacci                                                                                                                                                                                     *[master]
Running 1m test @ http://localhost:8888/fibonacci
  16 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    12.55ms   19.34ms 614.73ms   95.33%
    Req/Sec     2.47k   554.22     3.85k    87.00%
  2337786 requests in 1.00m, 684.45MB read
  Socket errors: connect 0, read 635, write 0, timeout 0
Requests/sec:  38920.92
Transfer/sec:     11.40MB
```

##### Difference

Finatra / Finagle = 1.071109593