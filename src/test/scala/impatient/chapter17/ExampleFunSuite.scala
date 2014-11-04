package impatient.chapter17

import org.scalatest.FunSuite

class ExampleFunSuite extends FunSuite {

  //
  // 17.8절
  //

  /*
    T <:< Ordered[T]는 적절한 T를 찾기가 불편해서 T <:< Comparable[T]로 변경했다.
   */
  class Pair[T](val first: T, val second: T) {
    def smaller(implicit ev: T <:< Comparable[T]) =
      if (first.compareTo(second) < 0) first else second
  }

  test("(17.8) Pair") {
    val stringPair = new Pair("a", "b")
    assert(stringPair.smaller === "a")

    val intPair = new Pair(1, 2)
    /*
      컴파일 에러
     */
    //assert(intPair.smaller === 1)
    assert(intPair.first === 1)
  }

  def firstLast_wrong[A, C <: Iterable[A]](it: C) = (it.head, it.last)

  test("(17.8) firstLast_bad") {
    /*
      컴파일 에러
     */
    //assert(firstLast_bad(List(1, 2, 3)) === (1, 3))
  }

  def firstLast[A, C](it: C)(implicit ev: C <:< Iterable[A]) = (it.head, it.last)

  test("(17.8) firstLast") {
    assert(firstLast(List(1, 2, 3)) === (1, 3))
  }

}
