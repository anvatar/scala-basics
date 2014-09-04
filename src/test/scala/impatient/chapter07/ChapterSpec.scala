package impatient.chapter07

import org.scalatest._

class ChapterSpec extends FlatSpec with Matchers {
  //
  // 연습문제 1
  //

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
  "Q6" should "be" + {var x:Long = 1L; for (e <- "Hello") x *= e.toLong; x} in {
    9415087488L shouldEqual {var x:Long = 1L; for (e <- "Hello") x *= e.toLong; x}
  }

  //
  // 연습문제 7
  //
  "Q7" should "be" + {var x:Long=1L; "Hello".foreach(x *= _); x} in {
    9415087488L shouldEqual {var x:Long=1L; "Hello".foreach(x *= _); x}
  }


  //
  // 연습문제 8
  //
  /*
          scala> def product(s: String) = {
               |   var x:Long = 1L
               |   s.foreach(x*=_)
               |   x
               | }
          product: (s: String)Long

          scala> product("Hello")
          res0: Long = 9415087488
   */

  //
  // 연습문제 9
  //
  /*
          scala> def product(s: String):Long = {
               |   if (s.length == 1) s.charAt(0).toLong else s.charAt(0).toLong * product(s.drop(1))
               | }
          product: (s: String)Long

          scala> product("Hello")
          res1: Long = 9415087488
   */

  //
  // 연습문제 10
  //
  /*
  
   */
}
