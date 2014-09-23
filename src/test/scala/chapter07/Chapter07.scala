package chapter07

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by parkhoyong on 2014. 8. 21..
 */
class Chapter07 extends FlatSpec with Matchers {
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
  import random._

  setSeed(System.currentTimeMillis().toInt)

  for (i <- 1 to 5)
    println("nextInt(): " + nextInt())

  for (i <- 1 to 5)
    println("nextDouble(): " + nextDouble())


  //
  // 연습문제 4
  //


  //
  // 연습문제 5
  //


  //
  // 연습문제 6
  //

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
