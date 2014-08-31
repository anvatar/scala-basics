package impatient.chapter09

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
