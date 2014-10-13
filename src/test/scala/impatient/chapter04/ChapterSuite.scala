package impatient.chapter04

import org.scalatest.FunSuite
import scala.collection.JavaConversions.mapAsScalaMap
import scala.collection.JavaConversions.propertiesAsScalaMap

class ChapterSuite extends FunSuite {
  //
  // 연습문제 4-1
  //

  def discountPrices(prices: Map[String, Int]) = {
    (for ((k, v) <- prices) yield (k, v * 0.9)).toMap

    // 대안 1
    /*prices.map({ case (k, v) => (k, v * 0.9) })*/

    // 대안 2
    /*prices.map(kv => (kv._1, kv._2 * 0.9))*/

    // 대안 3
    /*prices.mapValues(_ * 0.9)*/
  }

  test("discountPrices") {
    val input = Map(
      "MacBook Air" -> 1490000,
      "MacBook Pro" -> 2990000,
      "iMac" -> 2490000,
      "Mac Pro" -> 4890000,
      "Mac Mini" -> 990000
    )
    val expected = Map(
      "MacBook Air" -> 1490000 * 0.9,
      "MacBook Pro" -> 2990000 * 0.9,
      "iMac" -> 2490000 * 0.9,
      "Mac Pro" -> 4890000 * 0.9,
      "Mac Mini" -> 990000 * 0.9
    )

    assert(discountPrices(input) === expected)
  }

  //
  // 연습문제 4-2
  //

  val testInputFilePath = "impatient/scala_school-collections-en.txt"

  def countWordsWithMutableMap(filePath: String) = {
    val resultMap = scala.collection.mutable.Map[String, Int]()

    val in = new java.util.Scanner(new java.io.File(ClassLoader.getSystemClassLoader.getResource(filePath).toURI))
    while (in.hasNext) {
      val word = in.next
      resultMap(word) = resultMap.getOrElse(word, 0) + 1
    }

    resultMap
  }

  val wordCountMutableMap = countWordsWithMutableMap(testInputFilePath)

  test("countWordsWithMutableMap") {
    for (kv <- wordCountMutableMap) println(kv)
  }

  //
  // 연습문제 4-3
  //

  def countWordsWithImmutableMap(filePath: String) = {
    var resultMap = Map[String, Int]()

    val in = new java.util.Scanner(new java.io.File(ClassLoader.getSystemClassLoader.getResource(filePath).toURI))
    while (in.hasNext) {
      val word = in.next
      resultMap = resultMap + (word -> (resultMap.getOrElse(word, 0) + 1))
    }

    resultMap
  }

  test("countWordsWithImmutableMap") {
    val immutableMap = countWordsWithImmutableMap(testInputFilePath)

    assert(compareWordCounts(wordCountMutableMap, immutableMap))
  }

  //
  // 연습문제 4-4
  //

  def countWordsWithSortedMap(filePath: String) = {
    var resultMap = scala.collection.immutable.SortedMap[String, Int]()

    val in = new java.util.Scanner(new java.io.File(ClassLoader.getSystemClassLoader.getResource(filePath).toURI))
    while (in.hasNext) {
      val word = in.next
      resultMap = resultMap + (word -> (resultMap.getOrElse(word, 0) + 1))
    }

    resultMap
  }

  test("countWordsWithSortedMap") {
    val sortedMap = countWordsWithSortedMap(testInputFilePath)
    assert(compareWordCounts(wordCountMutableMap, sortedMap))

    val keys: List[String] = (for ((k, _) <- sortedMap) yield k).toList
    assert(keys === keys.sorted)
  }

  //
  // 연습문제 4-5
  //

  def countWordsWithJavaTreeMap(filePath: String) = {
    val resultMap: scala.collection.mutable.Map[String, Int] = new java.util.TreeMap[String, Int]

    val in = new java.util.Scanner(new java.io.File(ClassLoader.getSystemClassLoader.getResource(filePath).toURI))
    while (in.hasNext) {
      val word = in.next
      resultMap(word) = resultMap.getOrElse(word, 0) + 1
    }

    resultMap
  }

  test("countWordsWithJavaTreeMap") {
    val javaTreeMap = countWordsWithJavaTreeMap(testInputFilePath)
    assert(compareWordCounts(wordCountMutableMap, javaTreeMap))

    val keys: List[String] = (for ((k, _) <- javaTreeMap) yield k).toList
    assert(keys === keys.sorted)
  }

  //
  // 연습문제 4-6
  //

  val daysMap = scala.collection.mutable.LinkedHashMap(
    "Monday" -> java.util.Calendar.MONDAY,
    "Tuesday" -> java.util.Calendar.TUESDAY,
    "Wednesday" -> java.util.Calendar.WEDNESDAY,
    "Thursday" -> java.util.Calendar.THURSDAY,
    "Friday" -> java.util.Calendar.FRIDAY,
    "Saturday" -> java.util.Calendar.SATURDAY,
    "Sunday" -> java.util.Calendar.SUNDAY
  )

  test("daysMap") {
    assert((for ((_, v) <- daysMap) yield v) === Array(2, 3, 4, 5, 6, 7, 1))
  }

  //
  // 연습문제 4-7
  //

  test("system properties table") {
    val systemProperties: scala.collection.Map[String, String] = java.lang.System.getProperties
    val maxKeyLength = (for (k <- systemProperties.keys) yield k.length).max

    for ((k, v) <- systemProperties) printf("%-" + maxKeyLength + "s | %s\n", k, v)
  }

  //
  // 연습문제 4-8
  //

  def minmax(values: Array[Int]) = {
    (values.min, values.max)
  }

  test("minmax") {
    val values = Array(4, -5, -1, 0, -2, 2, -3, 1, 3, -4)
    assert(minmax(values) === ((-5, 4)))
  }

  //
  // 연습문제 4-9
  //

  def lteqgt(values: Array[Int], v: Int) = {
    val (lt, eqgt) = values.partition(_ < v)
    val (eq, gt) = eqgt.partition(_ == v)
    (lt.length, eq.length, gt.length)
  }

  test("lteqgt") {
    val values = Array(4, -5, -1, 0, -2, 2, -3, 1, 3, -4)
    val pivot = 1
    val expected = (6, 1, 3)

    assert(lteqgt(values, pivot) === expected)
  }

  //
  // 연습문제 4-10
  //

  /*
          scala> "Hello".zip("World")
              res0: scala.collection.immutable.IndexedSeq[(Char, Char)] = Vector((H,W), (e,o), (l,r), (l,l), (o,d))

      유스케이스
      - 글자 단위 diff
      - 글자 단위 encoder/decoder ring
      - 두 개의 단어를 나란히 세로로 출력하기
   */

  //
  // 유틸리티 함수
  //

  def compareWordCounts(lhs: scala.collection.Map[String, Int], rhs: scala.collection.Map[String, Int]): Boolean = {
    if (lhs.size != rhs.size) return false

    for ((k, v) <- lhs) if (rhs(k) != v) return false

    true
  }
}
