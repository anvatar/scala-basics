package impatient.chapter13

import org.scalatest.FunSuite

import scala.collection._
import scala.collection.mutable.ArrayBuffer

class ChapterSuite extends FunSuite {

  //
  // 연습문제 13-1
  //

  def mutableIndexes(str: String): mutable.Map[Char, mutable.LinkedHashSet[Int]] = {
    val indexes = mutable.Map[Char, mutable.LinkedHashSet[Int]]()
    str.zipWithIndex.foreach { case (c, i) => if (indexes.contains(c)) indexes(c).add(i) else indexes(c) = mutable.LinkedHashSet[Int](i)}
    indexes
  }

  test("mutableIndexes") {
    val indexes: mutable.Map[Char, mutable.LinkedHashSet[Int]] = mutableIndexes("Mississippi")
    assert(indexes('M').mkString(" ") == "0")
    assert(indexes('i').mkString(" ") == "1 4 7 10")
  }

  //
  // 연습문제 13-2
  //

  def immutableIndexes(str: String): Map[Char, List[Int]] = {
    str.zipWithIndex.foldRight(Map[Char, List[Int]]())({ case ((c, i), indexes) => indexes + (c -> (i :: indexes.getOrElse(c, Nil)))})

    // 다음과 같이 작성하는 것도 가능하다.
    /*
    str.zipWithIndex.groupBy(_._1).mapValues(_.map(_._2).toList)
     */
  }

  test("immutableIndexes") {
    val indexes: Map[Char, List[Int]] = immutableIndexes("Mississippi")
    assert(indexes('M').mkString(" ") == "0")
    assert(indexes('i').mkString(" ") == "1 4 7 10")
  }

  //
  // 연습문제 13-3
  //

  def removeZeros(lst: mutable.LinkedList[Int]): Unit = {
    val filtered = lst.filterNot(_ == 0)
    lst.next = lst
    if (filtered != mutable.LinkedList.empty) {
      lst.elem = filtered.elem
      lst.next = filtered.next
    }
  }

  test("removeZeros") {
    def assertRemoveZeros(input: mutable.LinkedList[Int], expected: List[Int]): Unit = {
      removeZeros(input)
      assert(input.toList == expected)
    }

    assertRemoveZeros(mutable.LinkedList(1, 2, 3), List(1, 2, 3))
    assertRemoveZeros(mutable.LinkedList(1, 2, 0), List(1, 2))
    assertRemoveZeros(mutable.LinkedList(1, 0, 2), List(1, 2))
    assertRemoveZeros(mutable.LinkedList(0, 1, 2), List(1, 2))
    assertRemoveZeros(mutable.LinkedList(0, 1, 0, 2, 0), List(1, 2))
    assertRemoveZeros(mutable.LinkedList(0, 0, 1, 0, 0, 2, 0, 0), List(1, 2))
    assertRemoveZeros(mutable.LinkedList(0), Nil)
    assertRemoveZeros(mutable.LinkedList[Int](), Nil)
  }

  //
  // 연습문제 13-4
  //

  def flatMapExample(strings: Array[String], stringToInt: Map[String, Int]): Array[Int] = strings.flatMap(stringToInt.get)

  test("flatMapExample") {
    assert(flatMapExample(Array("Tom", "Fred", "Harry"), Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5)).toBuffer == ArrayBuffer(3, 5))
  }

  //
  // 연습문제 13-5
  //

  object mkString {
    private val EMPTY = ""

    def apply(seq: Seq[_]): String = mkString(seq, EMPTY)

    def apply(seq: Seq[_], sep: String): String = mkString(seq, EMPTY, sep, EMPTY)

    def apply(seq: Seq[_], start: String, sep: String, end: String): String =
      start + (if (seq.isEmpty) EMPTY else seq.reduceLeft(_ + sep + _.toString)) + end
  }

  test("mkString") {
    def assertMkString(seq: Seq[_]): Unit = {
      assertResult(seq.mkString)(mkString(seq))
      assertResult(seq.mkString(", "))(mkString(seq, ", "))
      assertResult(seq.mkString("( ", ", ", " )"))(mkString(seq, "( ", ", ", " )"))
    }

    assertMkString(Array(1, 2, 3))
    assertMkString("abc")
    assertMkString(Nil)
  }

  //
  // 연습문제 13-6
  //


  //
  // 연습문제 13-7
  //


  //
  // 연습문제 13-8
  //


  //
  // 연습문제 13-9
  //


  //
  // 연습문제 13-10
  //

}
