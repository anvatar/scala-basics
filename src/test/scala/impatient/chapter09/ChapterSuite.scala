package impatient.chapter09

import java.io.PrintWriter
import java.net.URI

import org.scalatest.FunSuite

import scala.io.Source

class ChapterSuite extends FunSuite {

  //
  // 연습문제 9-1
  //

  def reversedLines(uri: URI): Array[String] = {
    val source = Source.fromFile(uri)
    val lines = source.getLines().toArray.reverse
    source.close()
    lines
  }

  test("reversed lines") {
    val testInputFilePath = "impatient/scala_school-collections-en.txt"

    val expectedFirstLines = Array(
      "Licensed under the Apache License v2.0.",
      "",
      "Built at @twitter by @stevej, @marius, and @lahosken with much help from @evanm, @sprsquish, @kevino, @zuercher, @timtrueman, @wickman, and @mccv; Russian translation by appigram; Chinese simple translation by jasonqu; Korean translation by enshahar;"
    )
    val expectedLastLines = Array(
      "This lesson covers:",
      "Next»",
      "«Previous",
      "Collections"
    )

    val resultLines: Array[String] = reversedLines(ClassLoader.getSystemClassLoader.getResource(testInputFilePath).toURI)

    for ((expected, actual) <- expectedFirstLines zip (resultLines take expectedFirstLines.length))
      assertResult(expected)(actual)
    for ((expected, actual) <- expectedLastLines zip (resultLines takeRight expectedLastLines.length))
      assertResult(expected)(actual)
  }

  //
  // 연습문제 9-2
  //

  def convertTabsToSpaces(filePath: String, tabWidth: Int): Unit = {
    def convert(str: String): String = {
      val builder = new StringBuilder
      for (c <- str) {
        builder ++= (if (c == '\t') " " * ((builder.length / tabWidth + 1) * tabWidth - builder.length) else c.toString)
      }
      builder.mkString
    }

    val source = Source.fromFile(filePath)
    val lines = source.getLines().toArray.map(convert)
    source.close()

    val out = new PrintWriter(filePath)
    for (l <- lines) out.println(l)
    out.close()
  }

  test("convertTabsToSpaces") {
    def prepareTestFile(testResourcePath: String, testFilePath: String) {
      val source = Source.fromURI(ClassLoader.getSystemClassLoader.getResource(testResourcePath).toURI)
      val lines = source.getLines().toArray
      source.close()

      val out = new PrintWriter(testFilePath)
      for (l <- lines) out.println(l)
      out.close()
    }

    // 홀수 줄은 탭으로 구분된 라인, 짝수 줄은 같은 내용인데 공백문자로 구분된 라인으로 구성된 텍스트 파일
    val testFilePath = "/tmp/with-tabs-then-spaces.txt"

    prepareTestFile("impatient/with-tabs-then-spaces.txt", testFilePath)

    convertTabsToSpaces(testFilePath, 4)

    val source = Source.fromFile(testFilePath)
    val lines = source.getLines().toArray
    source.close()

    for (i <- (1 to lines.length) filter (_ % 2 == 1))
      assert(lines(i - 1) == lines(i), "line " + (i - 1) + " ~ " + i)
  }

  //
  // 연습문제 9-3
  //


  //
  // 연습문제 9-4
  //


  //
  // 연습문제 9-5
  //


  //
  // 연습문제 9-6
  //


  //
  // 연습문제 9-7
  //


  //
  // 연습문제 9-8
  //


  //
  // 연습문제 9-9
  //


  //
  // 연습문제 9-10
  //

}
