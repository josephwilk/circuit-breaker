# Circuit Breaker

[![Build Status](https://travis-ci.org/josephwilk/circuit-breaker.png?branch=master)](https://travis-ci.org/josephwilk/circuit-breaker)

A Clojure library for the circuit breaker pattern:

http://en.wikipedia.org/wiki/Circuit_breaker_design_pattern

##Installation

Add the dependency from https://clojars.org/circuit-breaker to your project.clj file

## Usage

```clojure

;If it errors more than 2 times stop trying the *thing* until 30 seconds have passed
(defncircuitbreaker :memcache {:timeout 30 :threshold 2})

(wrap-with-circuit-breaker :memcache (fn [] do-something-that-might-exception))

;Testing a circuit outside of the wrap-with-circuit-breaker
(with-circuit-breaker :memcache {:connected (fn [] "ok") :tripped (fn [] "panic")})
```

##Compatibilty

Tested against:
* Java 6
* Java 7

Clojure versions:

* Clojure 1.3
* Clojure 1.4
* Clojure 1.5

##License
(The MIT License)

Copyright (c) 2013 Joseph Wilk

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the 'Software'), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
