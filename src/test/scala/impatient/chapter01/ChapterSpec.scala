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


  //
  // 연습문제 3
  //


  //
  // 연습문제 4
  //


  //
  // 연습문제 5
  //


  //
  // 연습문제 6
  //

  "2 ^ 1024" should "be " + BigInt(2).pow(1024) in {
    true shouldEqual true
  }

  //
  // 연습문제 7
  //


  //
  // 연습문제 8
  //


  //
  // 연습문제 9
  //


  //
  // 연습문제 10
  //

}
