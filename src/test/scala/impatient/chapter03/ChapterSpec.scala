package impatient.chapter03

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class ChapterSpec extends FlatSpec with Matchers {
  //
  // 연습문제 1
  //

  def randomArray(n: Int) = {
    val result = ArrayBuffer[Int]()
    while (result.length < n) {
      val random: Int = Random.nextInt(n)
      if (!(result contains random)) result += random
    }
    result.toArray
  }

  for (n <- 10 to 20) {
    val result = randomArray(n)
    //println(mkString(result))

    val sortedExpected = 0 until n

    "randomArray(" + n + ")" should "be equal to (0 until n) when sorted" in {
      result.sorted shouldEqual sortedExpected
    }

    "randomArray(" + n + ")" should "not be sorted" in {
      result should not equal sortedExpected
    }

    "randomArray(" + n + ")" should "not be reverse-sorted" in {
      result should not equal sortedExpected.reverse
    }
  }

  //
  // 연습문제 2
  //

  {
    def bubbleArray(arr: Array[Int]): Unit = {
      for (i <- 0 until arr.length if i % 2 == 1) {
        val temp = arr(i)
        arr(i) = arr(i - 1)
        arr(i - 1) = temp
      }
    }

    val input = Array(1, 2, 3, 4, 5)
    val expected = Array(2, 1, 4, 3, 5)

    mkString(input) + " => bubbleArray" should "be " + mkString(expected) in {
      bubbleArray(input)
      input shouldEqual expected
    }
  }

  //
  // 연습문제 3
  //

  {
    def bubbleArray(arr: Array[Int]): Array[Int] = {
      (for (i <- 0 until arr.length) yield {
        if (i % 2 == 1) arr(i - 1)
        else if (i == arr.length - 1) arr(i)
        else arr(i + 1)
      }).toArray
    }

    val input = Array(1, 2, 3, 4, 5)
    val expected = Array(2, 1, 4, 3, 5)

    "bubbleArray(" + mkString(input) + ")" should "be " + mkString(expected) in {
      val result = bubbleArray(input)
      result shouldEqual expected
      input should not equal result
    }
  }

  //
  // 연습문제 4
  //

  {
    def positivesFirstArray(arr: Array[Int]): Array[Int] = {
      val positives = ArrayBuffer[Int]()
      val nonPositives = ArrayBuffer[Int]()

      for (a <- arr) if (a > 0) positives += a else nonPositives += a

      (positives ++ nonPositives).toArray
    }

    val input = Array(4, -5, -1, 0, -2, 2, -3, 1, 3, -4)
    val expected = Array(4, 2, 1, 3, -5, -1, 0, -2, -3, -4)

    "positivesFirstArray(" + mkString(input) + ")" should "be " + mkString(expected) in {
      positivesFirstArray(input) shouldEqual expected
    }
  }

  //
  // 연습문제 5
  //

  {
    def average(doubles: Array[Double]): Double = doubles.sum / doubles.length

    val input = Array[Double](4.4, 5.3, 0.1, 2.3, 2.0)
    val expected = 2.82

    "average(" + mkString(input) + ")" should "be " + expected in {
      (average(input) - expected) < 0.000001 shouldEqual true
    }
  }

  //
  // 연습문제 6
  //

  {
    def reverseArray(ints: Array[Int]): Array[Int] = {
      (for (i <- 1 to ints.length) yield ints(ints.length - i)).toArray
    }

    val input = Array(4, -5, -1, 0, -2, 2, -3, 1, 3, -4)
    val expected = input.reverse

    "reverseArray(" + mkString(input) + ")" should "be " + expected in {
      reverseArray(input) shouldEqual expected
    }
  }

  {
    def reverseBuffer(ints: ArrayBuffer[Int]): ArrayBuffer[Int] = {
      val result = ArrayBuffer[Int]()
      for (int <- ints) result.prepend(int)
      result
    }

    val input = ArrayBuffer(4, -5, -1, 0, -2, 2, -3, 1, 3, -4)
    val expected = input.reverse

    "reverseBuffer(" + input + ")" should "be " + expected in {
      reverseBuffer(input) shouldEqual expected
    }
  }

  //
  // 연습문제 7
  //

  {
    val input = Array(2, 6, 7, 3, 4, 9, 5, 9, 2, 0, 0, 6, 4, 8, 7, 5, 1, 1, 8, 3)
    val expected = (0 until 10).toArray

    "distinct(" + mkString(input) + ")" should "be equal to " + mkString(expected) in {
      input.distinct.sorted shouldEqual expected
    }
  }

  //
  // 연습문제 8
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

  {
    val input = ArrayBuffer(4, -5, -1, 0, -2, 2, -3, 1, 3, -4)
    val expected = ArrayBuffer(4, -5, 0, 2, 1, 3)

    "noMoreThanOneNegativeArray(" + input + ")" should "be " + expected in {
      noMoreThanOneNegativeArray(input)
      input shouldEqual expected
    }
  }
  {
    val input = ArrayBuffer[Int]()
    val expected = ArrayBuffer[Int]()

    "noMoreThanOneNegativeArray(" + input + ")" should "be " + expected in {
      noMoreThanOneNegativeArray(input)
      input shouldEqual expected
    }
  }
  {
    val input = ArrayBuffer(4, 0, 2, 1, 3)
    val expected = ArrayBuffer(4, 0, 2, 1, 3)

    "noMoreThanOneNegativeArray(" + input + ")" should "be " + expected in {
      noMoreThanOneNegativeArray(input)
      input shouldEqual expected
    }
  }

  //
  // 연습문제 9
  //

  def timeZonesOfRegion(region: String): Array[String] = {
    val prefix = region + "/"
    (for (id <- java.util.TimeZone.getAvailableIDs if id.startsWith(prefix))
    yield id.drop(prefix.length)).sorted
  }

  {
    val result = timeZonesOfRegion("America")

    {
      val expected = "Adak"
      "first of America time zone" should "be " + expected in {
        result.head shouldEqual expected
      }
    }
    {
      val expected = "Yellowknife"
      "last of America time zone" should "be " + expected in {
        result.last shouldEqual expected
      }
    }
  }

  //
  // 연습문제 10
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
