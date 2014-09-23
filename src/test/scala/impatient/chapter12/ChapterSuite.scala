package impatient.chapter12

import org.scalatest.FunSuite

class ChapterSuite extends FunSuite {

  //
  // 연습문제 12-1
  //

  def values(fun: Int => Int, low: Int, high: Int): Array[(Int, Int)] =
    (low to high map { input => (input, fun(input))}).toArray

  test("values func") {
    val expected = Array((-5, 25), (-4, 16), (-3, 9), (-2, 4), (-1, 1), (0, 0), (1, 1), (2, 4), (3, 9), (4, 16), (5, 25))
    assertResult(expected)(values(x => x * x, -5, 5))
  }

  //
  // 연습문제 12-2
  //

  def maxWithReduceLeft(x: Int, y: Int): Int = if (x > y) x else y

  test("max() with reduceLeft") {
    val input = Array(4, -3, 2, -5, 0)
    assert(input.reduceLeft(maxWithReduceLeft) == input.max)
    assert(input.reduceLeft((x, y) => if (x > y) x else y) == input.max)
  }

  //
  // 연습문제 12-3
  //

  def factorialWithReduceLeft(n: Int): Long = if (n < 1) 1 else 1L to n reduceLeft (_ * _)

  test("factorial() with reduceLeft") {
    assert(factorialWithReduceLeft(0) == 1)
    assert(factorialWithReduceLeft(1) == 1)
    assert(factorialWithReduceLeft(2) == 2)
    assert(factorialWithReduceLeft(3) == 6)
    assert(factorialWithReduceLeft(4) == 24)
    assert(factorialWithReduceLeft(5) == 120)
    assert(factorialWithReduceLeft(6) == 720)
    assert(factorialWithReduceLeft(20) == 2432902008176640000L)
  }

  //
  // 연습문제 12-4
  //

  /*
      빈 컬렉션에 대해 reduceLeft가 UnsupportedOperationException을 발생시키는 반면,
      foldLeft는 지정된 초기값만 돌려주고 문제 없이 동작한다.
   */

  def factorialWithFoldLeft(n: Int): Long = (1 to n).foldLeft(1L)(_ * _)

  test("factorial() with foldLeft") {
    assert(factorialWithFoldLeft(0) == 1)
    assert(factorialWithFoldLeft(1) == 1)
    assert(factorialWithFoldLeft(2) == 2)
    assert(factorialWithFoldLeft(3) == 6)
    assert(factorialWithFoldLeft(4) == 24)
    assert(factorialWithFoldLeft(5) == 120)
    assert(factorialWithFoldLeft(6) == 720)
    assert(factorialWithFoldLeft(20) == 2432902008176640000L)
  }

  //
  // 연습문제 12-5
  //

  def largest(func: Int => Int, inputs: Seq[Int]): Int = inputs.map(func).sorted.last

  test("largest func") {
    assertResult(25)(largest(x => 10 * x - x * x, 1 to 10))
  }

  //
  // 연습문제 12-6
  //


  //
  // 연습문제 12-7
  //


  //
  // 연습문제 12-8
  //


  //
  // 연습문제 12-9
  //


  //
  // 연습문제 12-10
  //

}
