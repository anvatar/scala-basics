package impatient.chapter01

import org.scalatest.FunSuite

class ChapterSuite extends FunSuite {
  //
  // 연습문제 1-1
  //

  /*
      scala> 3.
      %   +   >    >>>            isInstanceOf   toDouble   toLong     unary_+   |
      &   -   >=   ^              toByte         toFloat    toShort    unary_-
      *   /   >>   asInstanceOf   toChar         toInt      toString   unary_~
   */


  //
  // 연습문제 1-2
  //

  /*
      scala> import scala.math._
      import scala.math._

      scala> sqrt(3)
      res0: Double = 1.7320508075688772

      scala> 3 - res0.
      %   +   /   >=             isInstanceOf   toChar     toFloat   toLong    toString   unary_-
      *   -   >   asInstanceOf   toByte         toDouble   toInt     toShort   unary_+

      scala> 3 - res0 * res0
      res1: Double = 4.440892098500626E-16
   */

  //
  // 연습문제 1-3
  //

  /*
          scala> res1 = 1
          <console>:12: error: reassignment to val
                 res1 = 2
                      ^
      res 변수들은 val이다.
   */

  //
  // 연습문제 1-4
  //

  /*
          scala> "crazy" * 3
          res3: String = crazycrazycrazy

      String이 StringOps로 암묵적으로 변환된다.
      http://www.scala-lang.org/api/current/index.html#scala.Predef$@augmentString(x:String):scala.collection.immutable.StringOps

      StringOps 클래스에 * 메써드가 있다.
      http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.StringOps@*(n:Int):String
   */

  //
  // 연습문제 1-5
  //

  /*
      10 max 2 는 scala.math.max(10, 2) 와 같다.

      Int는 RichInt로 암묵적으로 변환 가능한데, RichInt에 max 메써드가 정의되어 있다.
      (선언은 ScalaNumberProxy에서)
   */

  //
  // 연습문제 1-6
  //

  test("2 ^ 1024") {
    println(BigInt(2).pow(1024))
  }

  //
  // 연습문제 1-7
  //

  test("probablePrime(100, Random)") {
    import scala.math.BigInt._
    import scala.util.Random

    println(probablePrime(100, Random))
  }

  //
  // 연습문제 1-8
  //

  test("temporary file name") {
    println(BigInt.probablePrime(100, util.Random).toString(36))
  }

  //
  // 연습문제 1-9
  //

  test("first and last characters") {
    assert("scala".head == 's')
    assert("scala"(0) == 's')

    assert("scala".last == 'a')
  }

  //
  // 연습문제 1-10
  //

  /*
      substring의 단점
        - 인자로 (beginIndex, length)를 받는지, (beginIndex, endIndex)를 받는지 헷갈린다.
        - 인자에 string#length() 값을 이용해야 하는 경우가 많다.
        - beginIndex나 endIndex를 잘못 계산하면 StringIndexOutOfBoundsException이 발생하기 쉽다.

      take, drop, takeRight, dropRight를 사용하는 것이 거의 항상 의미가 더 명확하며, 코드도 간결하다.
   */

  test("(take, drop, takeRight, dropRight) vs substring") {
    val str = "Scala for the Impatient"

    assert((str take 5) == "Scala")
    assert(str.substring(0, 5) == "Scala")

    assert((str drop 5) == " for the Impatient")
    assert(str.substring(5) == " for the Impatient")

    assert((str takeRight 5) == "tient")
    assert(str.substring(str.length - 5, str.length) == "tient")

    assert((str dropRight 5) == "Scala for the Impa")
    assert(str.substring(0, str.length - 5) == "Scala for the Impa")

    assert((str drop 6 dropRight 4) == "for the Impat")
    assert(str.substring(6, str.length - 4) == "for the Impat")

    assert((str drop 4 take 6) == "a for ")
    assert(str.substring(4, 10) == "a for ")
  }
}
