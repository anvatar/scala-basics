package impatient.chapter09

import java.io.PrintWriter
import java.net.URI

import org.scalatest.FunSuite

import scala.io.Source

class ChapterSuite extends FunSuite {

  //
  // 연습문제 9-1
  //

  def getReversedLines(uri: URI): Array[String] = {
    val source = Source.fromFile(uri)
    val lines = source.getLines().toArray.reverse
    source.close()
    lines
  }

  test("getReversedLines") {
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

    val resultLines: Array[String] = getReversedLines(resourceUri("impatient/scala_school-collections-en.txt"))

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
      val source = Source.fromURI(resourceUri(testResourcePath))
      val lines = source.getLines().toArray
      source.close()

      val out = new PrintWriter(testFilePath)
      for (l <- lines) out.println(l)
      out.close()
    }

    // 홀수 줄은 탭으로 구분된 라인, 짝수 줄은 같은 내용인데 공백문자로 구분된 라인으로 구성된 텍스트 파일
    val tempFilePath = "/tmp/with-tabs-then-spaces.txt"

    prepareTestFile("impatient/with-tabs-then-spaces.txt", tempFilePath)

    convertTabsToSpaces(tempFilePath, 4)

    val source = Source.fromFile(tempFilePath)
    val lines = source.getLines().toArray
    source.close()

    for (i <- (1 to lines.length) filter (_ % 2 == 1))
      assert(lines(i - 1) == lines(i), "line " + (i - 1) + " ~ " + i)
  }

  //
  // 연습문제 9-3
  //

  def findLongWords(source: Source, longerThan: Int) =
    for (l <- source.getLines(); w <- l.split( """\s""") if w.length > longerThan) yield w

  test("findLongWords") {
    val source = Source.fromURI(resourceUri("impatient/scala_school-collections-en.txt"))
    for (w <- findLongWords(source, 12 - 1)) assert(w.length >= 12)
    source.close()
  }

  //
  // 연습문제 9-4
  //

  /*
    impatient.chapter07.random 패키지를 이용하여 다음 코드로 floating-point-numbers.txt 파일을 준비했다.

        for (i <- 1 to 50) {
          val sign = if (nextInt() % 2 == 0) 1 else -1
          val exp = nextInt() % 16 + 8
          val mantissa = nextDouble() * 10
          val str = (if (sign > 0) "" else "-") + mantissa.toString.dropRight(3) + "E" + exp.toString
          println(str.toDouble)
        }
   */

  class DoubleStat {
    private var _sum: Double = 0
    private var _count: Int = 0
    private var _max: Double = 0
    private var _min: Double = 0

    def sum = _sum

    def count = _count

    def max = _max

    def min = _min

    def average = if (isEmpty) 0.0 else sum / count

    private def append(d: Double): Unit = {
      if (isEmpty) {
        _max = d
        _min = d
      }

      _max = math.max(_max, d)
      _min = math.min(_min, d)
      _sum += d
      _count += 1
    }

    private def isEmpty = _count <= 0
  }

  object DoubleStat {
    def fromResource(resourcePath: String): DoubleStat = {
      val doubleStat = new DoubleStat

      val source = Source.fromURI(resourceUri(resourcePath))
      for (l <- source.getLines()) doubleStat.append(l.toDouble)
      source.close()

      doubleStat
    }
  }

  test("DoubleStat") {
    val doubleStat = DoubleStat.fromResource("impatient/floating-point-numbers.txt")


    assertResult(1.180796815842919E+24)(doubleStat.sum)
    assertResult(2.361593631685838E+22)(doubleStat.average)
    assertResult(9.36467935083E+23)(doubleStat.max)
    assertResult(-5.851349620113E+22)(doubleStat.min)
  }

  //
  // 연습문제 9-5
  //

  def dumpPowerOfTwos(maxExponent: Int, filePath: String) = {
    val powerOfTwosTo20 = for (i <- 0 to maxExponent) yield math.pow(2, i).toLong
    val maxLength = powerOfTwosTo20.last.toString.length

    val out = new java.io.PrintWriter(filePath)
    for (l <- powerOfTwosTo20)
      out.println(("%" + maxLength + "d    %s").format(l, (1.toDouble / l).toString))
    out.close()
  }

  test("dumpPowerOfTwos") {
    val expectedLines = Array(
      "      1    1.0",
      "      2    0.5",
      "      4    0.25",
      "      8    0.125",
      "     16    0.0625",
      "     32    0.03125",
      "     64    0.015625",
      "    128    0.0078125",
      "    256    0.00390625",
      "    512    0.001953125",
      "   1024    9.765625E-4",
      "   2048    4.8828125E-4",
      "   4096    2.44140625E-4",
      "   8192    1.220703125E-4",
      "  16384    6.103515625E-5",
      "  32768    3.0517578125E-5",
      "  65536    1.52587890625E-5",
      " 131072    7.62939453125E-6",
      " 262144    3.814697265625E-6",
      " 524288    1.9073486328125E-6",
      "1048576    9.5367431640625E-7"
    )

    val tempFilePath = "/tmp/dump-power-of-twos.txt"
    dumpPowerOfTwos(20, tempFilePath)

    val source = Source.fromFile(tempFilePath)
    val actualLines = source.getLines().toArray
    source.close()

    assertResult(expectedLines.length)(actualLines.length)
    for (i <- 0 until expectedLines.length)
      assertResult(expectedLines(i), "at LINE " + (i + 1))(actualLines(i))
  }

  //
  // 연습문제 9-6
  //

  /*
      문제의 의도와, 어떤 패턴을 찾으라는 건지 모르겠음
   */

  //
  // 연습문제 9-7
  //

  def isFloatingPointNumber(value: String): Boolean = {
    """^-?\d+\.\d+(E(\+|-)?\d+)?$""".r.findFirstIn(value).isDefined
  }

  test("filter out isFloatingPointNumber") {
    val source = Source.fromURI(resourceUri("impatient/floating-point-numbers.txt"))
    assertResult(Array[String]())(source.mkString.split( """\s+""").filterNot(isFloatingPointNumber))
    source.close()
  }

  //
  // 연습문제 9-8
  //

  val imageSourcePattern = """<img.*\ssrc="([^"]+)".*>""".r

  test("imageSourcePattern") {
    val source = Source.fromURL("http://www.daum.net")
    for (imageSourcePattern(imageUrl) <- imageSourcePattern.findAllIn(source.mkString)) {
      println(imageUrl)
      // wget이나 curl로 HTTP 헤더를 얻어와 Content-Type이 image인지 확인해보고 싶었으나 잘 안 되었음
    }
    source.close()
  }

  //
  // 연습문제 9-9
  //

  import java.io.File

  def subdirs(dir: File): Iterator[File] = {
    val children = dir.listFiles().filter(_.isDirectory)
    children.toIterator ++ children.toIterator.flatMap(subdirs)
  }

  def findFilesWithExtension(targetDirectory: String, extension: String) =
    subdirs(new File(targetDirectory)).flatMap(_.listFiles).filter(_.isFile).filter(_.getName.endsWith("." + extension))

  test("findFilesWithExtension") {
    import sys.process._

    val targetDirectoryPath = "/Users/anvatar/home/idea/scala-basics"

    val expected = (("find " + targetDirectoryPath + " -name *.class") #| "wc -l").!!.trim.toInt
    val actual = findFilesWithExtension(targetDirectoryPath, "class").length

    assertResult(expected)(actual)
  }

  //
  // 연습문제 9-10
  //

  test("friendship") {
    def checkFriendship(friends: Array[Person]) {
      assert(friends.length == 3)
      assert(friends(0).friends.length == 2)
      assert(friends(0).friends(0) == friends(1))
      assert(friends(0).friends(1) == friends(2))
    }

    import java.io._

    val tempFilePath = "/tmp/friends.obj"

    {
      val p0 = new Person
      val p1 = new Person
      val p2 = new Person

      p0 addFriend p1
      p0 addFriend p2

      val friends = Array(p0, p1, p2)
      checkFriendship(friends)

      val out = new ObjectOutputStream(new FileOutputStream(tempFilePath))
      out.writeObject(friends)
      out.close()
    }

    {
      val in = new ObjectInputStream(new FileInputStream(tempFilePath))
      val friends = in.readObject().asInstanceOf[Array[Person]]
      in.close()

      checkFriendship(friends)
    }
  }

  //
  // 유틸리티 함수
  //

  private def resourceUri(resourceFilePath: String): URI =
    ClassLoader.getSystemClassLoader.getResource(resourceFilePath).toURI
}
