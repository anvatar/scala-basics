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

  def largestAt(func: Int => Int, inputs: Seq[Int]): Int = inputs.zip(inputs.map(func)).sortBy(_._2).last._1

  test("largestAt func") {
    assertResult(5)(largestAt(x => 10 * x - x * x, 1 to 10))
  }

  //
  // 연습문제 12-7
  //

  def adjustToPair(func: (Int, Int) => Int)(pair: (Int, Int)): Int = func(pair._1, pair._2)

  test("adjustToPair func") {
    assertResult(42)(adjustToPair(_ * _)((6, 7)))
    assertResult(12 to (30, 2))(((1 to 10) zip (11 to 20)).map(adjustToPair(_ + _)))
  }

  //
  // 연습문제 12-8
  //

  test("corresponds") {
    assert(Array("a", "abc", "ab").corresponds(Array(1, 3, 2))(_.length == _))
  }

  //
  // 연습문제 12-9
  //

  def nonCurryingCorresponds[A, B](input: Seq[A], expected: Seq[B], func: A => B): Boolean =
    input.map(func).zip(expected).filter(p => p._1 != p._2).isEmpty

  test("nonCurryingCorresponds") {
    /*
      Currying 방식의 corresponds보다 타입 추론이 잘 안 되어, func 부분을 아래와 같이 간단하게(_.length == _) 작성할 수 없다.

          assert(nonCurryingCorresponds(Array("a", "abc", "ab"), Array(1, 3, 2), _.length == _))
     */
    assert(nonCurryingCorresponds(Array("a", "abc", "ab"), Array(1, 3, 2), (str: String) => str.length))
  }

  //
  // 연습문제 12-10
  //

  /*
    루프의 경우와 달리 condition은 unless 호출 시점에 한 번만 평가하면 되므로 첫 번째 인자는 call-by-name 인자가 아니어도 된다.
   */
  def unless(condition: Boolean)(body: => Unit): Unit = {
    if (!condition) body
  }

  test("unless control") {
    var count = 0

    unless (count != 0) {
      count += 1
    }
    assert(count == 1)

    unless (count != 0) {
      count += 1
    }
    assert(count == 1)
  }

  def nonCurryingUnless(condition: Boolean, body: => Unit): Unit = {
    if (!condition) body
  }

  /*
      커링 방식을 사용하지 않으면 아래와 같이 일반적이지 않은 어색한 형태로 코드를 작성해야 한다.
   */
  test("nonCurryingUnless control") {
    var count = 0

    nonCurryingUnless(count != 0, {
      count += 1
    })
    assert(count == 1)

    nonCurryingUnless(count != 0, {
      count += 1
    })
    assert(count == 1)
  }
}
