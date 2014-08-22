package impatient.chapter01

import org.scalatest._

class ChapterSpec extends FlatSpec with Matchers {
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

  "2 ^ 1024" should "be " + BigInt(2).pow(1024) in {
    true shouldEqual true
  }

  //
  // 연습문제 1-7
  //

  "probablePrime(100, Random)" should "be compiled" in {
    import scala.math.BigInt._
    import scala.util.Random

    probablePrime(100, Random)
  }

  //
  // 연습문제 1-8
  //

  "Temporary file name" should "look like " +
    BigInt.probablePrime(100, util.Random).toString(36) in {
    true shouldEqual true
  }

  //
  // 연습문제 1-9
  //

  "First character of \"scala\"" should "be 's'" in {
    "scala".head shouldEqual 's'
    "scala"(0) shouldEqual 's'
  }

  "Last character of \"scala\"" should "be 'a'" in {
    "scala".last shouldEqual 'a'
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

  "take, drop, takeRight, dropRight" should "be better than substring" in {
    val str = "Scala for the Impatient"

    str take 5 shouldEqual "Scala"
    str.substring(0, 5) shouldEqual "Scala"

    str drop 5 shouldEqual " for the Impatient"
    str.substring(5) shouldEqual " for the Impatient"

    str takeRight 5 shouldEqual "tient"
    str.substring(str.length - 5, str.length) shouldEqual "tient"

    str dropRight 5 shouldEqual "Scala for the Impa"
    str.substring(0, str.length - 5) shouldEqual "Scala for the Impa"

    str drop 6 dropRight 4 shouldEqual "for the Impat"
    str.substring(6, str.length - 4) shouldEqual "for the Impat"

    str drop 4 take 6 shouldEqual "a for "
    str.substring(4, 10) shouldEqual "a for "
  }
}
