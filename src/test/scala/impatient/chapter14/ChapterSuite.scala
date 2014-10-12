package impatient.chapter14

import org.scalatest.FunSuite

class ChapterSuite extends FunSuite {

  //
  // 연습문제 14-1
  //

  /*
    TODO: 일단 생략
   */

  //
  // 연습문제 14-2
  //

  def tupleSwap(pair: (Int, Int)): (Int, Int) = pair match {
    case (a, b) => (b, a)
  }

  test("tupleSwap") {
    assertResult((1, 2))(tupleSwap((2, 1)))
  }

  //
  // 연습문제 14-3
  //

  val arraySwap: PartialFunction[Array[Int], Array[Int]] = {
    case Array(a, b, rest@_*) => Array(b, a) ++ rest
  }

  test("arraySwap") {
    assertResult(Array(1, 2))(arraySwap(Array(2, 1)))
    assertResult(Array(1, 2, 3))(arraySwap(Array(2, 1, 3)))
    intercept[MatchError] {
      arraySwap(Array(1))
    }
  }

  //
  // 연습문제 14-4
  //


  //
  // 연습문제 14-5
  //


  //
  // 연습문제 14-6
  //


  //
  // 연습문제 14-7
  //


  //
  // 연습문제 14-8
  //


  //
  // 연습문제 14-9
  //


  //
  // 연습문제 14-10
  //

}
