package impatient.chapter01

import org.scalatest._

class ChapterSpec extends FlatSpec with Matchers {
  //
  // 연습문제 1
  //

  /*
          scala> 3.
          %   +   >    >>>            isInstanceOf   toDouble   toLong     unary_+   |
          &   -   >=   ^              toByte         toFloat    toShort    unary_-
          *   /   >>   asInstanceOf   toChar         toInt      toString   unary_~
   */


  //
  // 연습문제 2
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
  // 연습문제 3
  //

  /*
          scala> res1 = 1
          <console>:12: error: reassignment to val
                 res1 = 2
                      ^
      res 변수들은 val이다.
   */

  //
  // 연습문제 4
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
  // 연습문제 5
  //

  /*
      10 max 2 는 scala.math.max(10, 2) 와 같다.

      Int는 RichInt로 암묵적으로 변환 가능한데, RichInt에 max 메써드가 정의되어 있다.
      (선언은 ScalaNumberProxy에서)
   */

  //
  // 연습문제 6
  //

  "2 ^ 1024" should "be " + BigInt(2).pow(1024) in {
    true should be === true
  }

  //
  // 연습문제 7
  //

  "probablePrime(100, Random)" should "be compiled" in {
    import scala.math.BigInt._
    import scala.util.Random

    probablePrime(100, Random)
  }

  //
  // 연습문제 8
  //

  "Temporary file name" should "look like " +
    BigInt.probablePrime(100, util.Random).toString(36) in {
    true should be === true
  }

  //
  // 연습문제 9
  //

  "First character of \"scala\"" should "be 's'" in {
    "scala".head should be === 's'
    "scala"(0) should be === 's'
  }

  "Last character of \"scala\"" should "be 'a'" in {
    "scala".last should be === 'a'
  }

  //
  // 연습문제 10
  //

  /*
      string#substring()은 인자로 (beginIndex, length)를 받는지, (beginIndex, endIndex)를 받는지 헷갈린다.
      string#substring()의 인자에는 string#length() 값을 이용해야 하는 경우가 많다.

      take, drop, takeRight, dropRight를 사용하는 것이 의미가 더 명확하며,
      대부분의 경우 코드도 더 간결해진다.
   */

  "take, drop, takeRight, dropRight" should "be better than substring" in {
    val str = "Scala for the Impatient"

    str take 5 should be === "Scala"
    str.substring(0, 5) should be === "Scala"

    str drop 5 should be === " for the Impatient"
    str.substring(5) should be === " for the Impatient"

    str takeRight 5 should be === "tient"
    str.substring(str.length - 5, str.length) should be === "tient"

    str dropRight 5 should be === "Scala for the Impa"
    str.substring(0, str.length - 5) should be === "Scala for the Impa"

    str drop 5 dropRight 5 should be === " for the Impa"
    str.substring(5, str.length - 5) should be === " for the Impa"

    str take 10 takeRight 5 should be === " for "
    str.substring(5, 10) should be === " for "
  }
}
