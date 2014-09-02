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
          scala> math.sqrt(3)
          res0: Double = 1.7320508075688772

          scala> math.pow(res0, 2)
          res1: Double = 2.9999999999999996

          scala> 3 - res1
          res2: Double = 4.440892098500626E-16
   */

  //
  // 연습문제 3
  //
  /*
          val
   */

  //
  // 연습문제 4
  //
  /*
          scala> "crazy" * 3
          res3: String = crazycrazycrazy

          string concat, StringOps
   */


  //
  // 연습문제 5
  //
  /*
          10과 2중 큰 값, Ordering
   */

  //
  // 연습문제 6
  //
  /*
          scala> BigInt(2).pow(1024)
          res4: scala.math.BigInt = 179769313486231590772930519078902473361797697894230657273430081157732675805500963132708477322407536021120113879871393357658789768814416622492847430639474124377767893424865485276302219601246094119453082952085005768838150682342462881473913110540827237163350510684586298239947245938479716304835356329624224137216
   */
  "2 ^ 1024" should "be " + BigInt(2).pow(1024) in {
    true shouldEqual true
  }

  //
  // 연습문제 7
  //
  /*
          util
   */

  //
  // 연습문제 8
  //
  /*
          ?
   */


  //
  // 연습문제 9
  //
  /*
      "".head, "".last
   */

  //
  // 연습문제 10
  //
  /*
          take: 처음부터 n개의 문자열
          drop: 처음부터 n개의 문자를 제외한 나머지 문자열
          takeRight: 끝부터 n개의 문자열
          dropRight: 끝부터 n개의 문자를 제외한 나머지 문자열
   */

}
