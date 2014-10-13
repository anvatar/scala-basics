package impatient.chapter03

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class ChapterSuite extends FunSuite {
  //
  // 연습문제 3-1
  //

  def randomArray(n: Int) = {
    val result = ArrayBuffer[Int]()
    while (result.length < n) {
      val random: Int = Random.nextInt(n)
      if (!(result contains random)) result += random
    }
    result.toArray
  }

  test("randomArray") {
    for (n <- 10 to 20) {
      val result = randomArray(n)
      //println(mkString(result))

      val sortedExpected = 0 until n

      assert(result.sorted === sortedExpected)
      assert(result !== sortedExpected)
      assert(result !== sortedExpected.reverse)
    }
  }

  //
  // 연습문제 3-2
  //

  def bubbleArrayMutable(arr: Array[Int]): Unit = {
    for (i <- 0 until arr.length if i % 2 == 1) {
      val temp = arr(i)
      arr(i) = arr(i - 1)
      arr(i - 1) = temp
    }
  }

  test("bubbleArray mutable version") {
    val input = Array(1, 2, 3, 4, 5)
    bubbleArrayMutable(input)
    assert(input === Array(2, 1, 4, 3, 5))
  }

  //
  // 연습문제 3-3
  //

  def bubbleArrayImmutable(arr: Array[Int]): Array[Int] = {
    (for (i <- 0 until arr.length) yield {
      if (i % 2 == 1) arr(i - 1)
      else if (i == arr.length - 1) arr(i)
      else arr(i + 1)
    }).toArray
  }

  test("bubbleArray immutable version") {
    val input = Array(1, 2, 3, 4, 5)
    val result = bubbleArrayImmutable(input)
    assert(result === Array(2, 1, 4, 3, 5))
    assert(input !== result)
  }

  //
  // 연습문제 3-4
  //

  def positivesFirstArray(arr: Array[Int]): Array[Int] = {
    val positives = ArrayBuffer[Int]()
    val nonPositives = ArrayBuffer[Int]()

    for (a <- arr) if (a > 0) positives += a else nonPositives += a

    (positives ++ nonPositives).toArray
  }

  test("positivesFirstArray") {
    val input = Array(4, -5, -1, 0, -2, 2, -3, 1, 3, -4)
    val expected = Array(4, 2, 1, 3, -5, -1, 0, -2, -3, -4)

    assert(positivesFirstArray(input) === expected)
  }

  //
  // 연습문제 3-5
  //

  def average(doubles: Array[Double]): Double = doubles.sum / doubles.length

  test("average") {
    val input = Array[Double](4.4, 5.3, 0.1, 2.3, 2.0)
    assert((average(input) - 2.82) < 0.000001)
  }

  //
  // 연습문제 3-6
  //

  def reverseArray(ints: Array[Int]): Array[Int] = {
    (for (i <- 1 to ints.length) yield ints(ints.length - i)).toArray
  }

  test("reverseArray") {
    val input = Array(4, -5, -1, 0, -2, 2, -3, 1, 3, -4)
    assert(reverseArray(input) === input.reverse)
  }

  def reverseBuffer(ints: ArrayBuffer[Int]): ArrayBuffer[Int] = {
    val result = ArrayBuffer[Int]()
    for (int <- ints) result.prepend(int)
    result
  }

  test("reverseBuffer") {
    val input = ArrayBuffer(4, -5, -1, 0, -2, 2, -3, 1, 3, -4)
    assert(reverseBuffer(input) === input.reverse)
  }

  //
  // 연습문제 3-7
  //

  test("distinct") {
    val input = Array(2, 6, 7, 3, 4, 9, 5, 9, 2, 0, 0, 6, 4, 8, 7, 5, 1, 1, 8, 3)
    val expected = 0 until 10

    assert(input.distinct.sorted === expected)
  }

  //
  // 연습문제 3-8
  //

  /*
      이 연습문제에서 제안한 방식은 아래 noMoreThanOneNegativeArray()와 같이 구현할 수 있다.
      첫 번째 for 루프는 O(n), 두 번째 for 루프는 O(n log n) 정도 될 것 같다.
      (두 번째 루프를 정확히 따져본 건 아니지만, 뒷쪽에서부터 삭제하다보면 앞쪽부터 삭제하는 경우보다는
      앞으로 당겨와야 하는 횟수가 적을 것 같다)
      따라서 O(n log n)

      3.4절 첫 번째 구현 방식은 while 루프 내에 앞쪽부터 삭제하는 작업을 수행하기 때문에 O(n^2)

      3.4절 두 번째 구현 방식은 두 for 루프가 모두 O(n)이기 때문에 O(n)

      시간복잡도는 3.4절 두 번째 구현 방식이 가장 낮으나, 연습문제에서 제안한 방식이 가장 명확하고 이해하기 쉽다.
   */

  def noMoreThanOneNegativeArray(ints: ArrayBuffer[Int]): Unit = {
    val negativeIndexes = for (i <- 0 until ints.length if ints(i) < 0) yield i

    if (negativeIndexes.nonEmpty)
      for (i <- negativeIndexes.reverse dropRight 1) ints.remove(i)
      //for (i <- negativeIndexes.tail.reverse) ints.remove(i)
  }

  test("noMoreThanOneNegativeArray1") {
    val input = ArrayBuffer(4, -5, -1, 0, -2, 2, -3, 1, 3, -4)
    val expected = ArrayBuffer(4, -5, 0, 2, 1, 3)

    noMoreThanOneNegativeArray(input)
    assert(input === expected)
  }

  test("noMoreThanOneNegativeArray2") {
    val input = ArrayBuffer[Int]()
    val expected = ArrayBuffer[Int]()

    noMoreThanOneNegativeArray(input)
    assert(input === expected)
  }

  test("noMoreThanOneNegativeArray3") {
    val input = ArrayBuffer(4, 0, 2, 1, 3)
    val expected = ArrayBuffer(4, 0, 2, 1, 3)

    noMoreThanOneNegativeArray(input)
    assert(input === expected)
  }

  //
  // 연습문제 3-9
  //

  def timeZonesOfRegion(region: String): Array[String] = {
    val prefix = region + "/"
    (for (id <- java.util.TimeZone.getAvailableIDs if id.startsWith(prefix))
    yield id.drop(prefix.length)).sorted
  }

  test("timeZonesOfRegion") {
    val result = timeZonesOfRegion("America")

    assert(result.head === "Adak")
    assert(result.last === "Yellowknife")
  }

  //
  // 연습문제 3-10
  //

  /*
      scala> import java.awt.datatransfer._
          import java.awt.datatransfer._

      scala> import scala.collection.JavaConversions.asScalaBuffer
          import scala.collection.JavaConversions.asScalaBuffer

      scala> import scala.collection.mutable.Buffer
          import scala.collection.mutable.Buffer

      scala> val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]
          flavors: java.awt.datatransfer.SystemFlavorMap = java.awt.datatransfer.SystemFlavorMap@7a6885e2

      scala> val imageFlavors: Buffer[String] = flavors.getNativesForFlavor(DataFlavor.imageFlavor)
          imageFlavors: scala.collection.mutable.Buffer[String] = Buffer(PNG, JFIF)
   */

  //
  // 유틸리티 함수
  //

  def mkString(arr: Array[Int]) = arr.mkString("[", ", ", "]")
  def mkString(arr: Array[Double]) = arr.mkString("[", ", ", "]")
}
