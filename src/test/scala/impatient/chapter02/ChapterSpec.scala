package impatient.chapter02

import org.scalatest.{Matchers, FlatSpec}

class ChapterSpec extends FlatSpec with Matchers {
  //
  // 연습문제 2-1
  //

  def signum(num: Int) = if (num > 0) 1 else if (num < 0) -1 else 0

  "signum(양수)" should "be 1" in {
    signum(1) shouldEqual 1
    signum(2) shouldEqual 1
  }

  "signum(음수)" should "be -1" in {
    signum(-1) shouldEqual -1
    signum(-2) shouldEqual -1
  }

  "signum(0)" should "be 0" in {
    signum(0) shouldEqual 0
  }

  //
  // 연습문제 2-2
  //

  /*
      scala> def fun = {}
          fun: Unit

      scala> fun == ()
          <console>:9: warning: comparing values of types Unit and Unit using `==' will always yield true
                        fun == ()
                            ^
          warning: there was one deprecation warning; re-run with -deprecation for details
          res2: Boolean = true

      scala> {} == ()
          <console>:8: warning: comparing values of types Unit and Unit using `==' will always yield true
                        {} == ()
                           ^
          warning: there was one deprecation warning; re-run with -deprecation for details
          res3: Boolean = true
   */

  //
  // 연습문제 2-3
  //

  /*
      이미 x, y의 타입이 각각 Unit, Int인 상황에서 x에는 (), y에는 1을 할당하고 싶은 경우
   */

  //
  // 연습문제 2-4
  //

  /*
      scala> for (i <- 10.to(1, -1)) println(i)
          10
          9
          8
          7
          6
          5
          4
          3
          2
          1
   */

  //
  // 연습문제 2-5
  //

  /*
      scala> def countdown(n: Int) { for (i <- n.to(0, -1)) println(i) }
          countdown: (n: Int)Unit

      scala> countdown(10)
          10
          9
          8
          7
          6
          5
          4
          3
          2
          1
          0
   */

  //
  // 연습문제 2-6
  //

  {
    def product(s: String) = {
      var result: Long = 1
      for (c <- "Hello") result *= c
      result
    }

    "6: product(\"Hello\")" should "be " + 9415087488L in {
      product("Hello") shouldEqual 9415087488L
    }
  }

  //
  // 연습문제 2-7
  //

  {
    def product(s: String) = s.map(_.toLong).product

    "7: product(\"Hello\")" should "be " + 9415087488L in {
      product("Hello") shouldEqual 9415087488L
    }
  }

  //
  // 연습문제 2-8
  //

  /*
      연습문제 2-6, 2-7에 포함된 것으로 봐야 하나?
   */

  //
  // 연습문제 2-9
  //

  {
    def product(s: String): Long = if (s.isEmpty) 1 else s.head * product(s.tail)

    "9: product(\"Hello\")" should "be " + 9415087488L in {
      product("Hello") shouldEqual 9415087488L
    }
  }

  //
  // 연습문제 2-10
  //

  def pow(x: Double, n: Int): Double = {
    if (n > 0) {
      if (n % 2 == 0) { val y = pow(x, n / 2); y * y }
      else x * pow(x, n - 1)
    }
    else if (n == 0) 1
    else 1 / pow(x, -n)
  }

  for (x <- 0.1.to(3.0, 0.1); n <- -3 to 3) {
    "" + x + "^" + n should "be " + math.pow(x, n) in {
      math.abs(pow(x, n) - math.pow(x, n)) < 0.0001 shouldEqual true
    }
  }
}
