package impatient.chapter11

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer
import scala.math.abs

class ChapterSuite extends FunSuite {

  //
  // 연습문제 11-1
  //

  /*
      `+` 연산자와 `->` 연산자의 우선순위는 같고, 둘 다 왼쪽으로 결합하는(left associative) 일반적인 경우에 해당한다.

      3 + 4 -> 5 ==> (3 + 4) -> 5

          scala> 3 + 4 -> 5
          res0: (Int, Int) = (7,5)

      3 -> 4 + 5 ==> (3 -> 4) + 5

          scala> 3 -> 4 + 5
          <console>:8: error: type mismatch;
           found   : Int(5)
           required: String
                        3 -> 4 + 5
                                 ^
   */

  //
  // 연습문제 11-2
  //

  /*
      일반 수식에서는 pow에 해당하는 연산의 우선순위가 곱셈, 나눗셈보다 높아야 하는데,
      Scala의 연산자 우선순위 규칙 상 **나 ^ 연산자를 사용했을 때는 곱셈, 나눗셈과 우선순위가 같거나 더 낮아지는 문제가 있다.
   */

  //
  // 연습문제 11-3
  //

  class Fraction(n: Int, d: Int) {
    /*
     * 책에서 제공
     */

    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d)
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d)

    override def toString = num + "/" + den

    def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0

    def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)

    /*
     * IntelliJ IDEA로 생성
     */

    def canEqual(other: Any): Boolean = other.isInstanceOf[Fraction]

    override def equals(other: Any): Boolean = other match {
      case that: Fraction =>
        (that canEqual this) &&
          num == that.num &&
          den == that.den
      case _ => false
    }

    override def hashCode(): Int = {
      val state = Seq(num, den)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }

    /*
     * 직접 구현
     */

    def +(other: Fraction): Fraction = Fraction(this.num * other.den + other.num * this.den, this.den * other.den)

    def -(other: Fraction): Fraction = this + Fraction(-1 * other.num, other.den)

    def *(other: Fraction): Fraction = Fraction(this.num * other.num, this.den * other.den)

    def /(other: Fraction): Fraction = this * Fraction(other.den, other.num)

  }

  object Fraction {
    def apply(n: Int, d: Int) = new Fraction(n, d)
  }

  test("Fraction") {
    assertResult(Fraction(7, 6))(Fraction(1, 2) + Fraction(2, 3))
    assertResult(Fraction(-5, 12))(Fraction(1, 3) - Fraction(3, 4))
    assertResult(Fraction(-7, 8))(Fraction(7, 6) * Fraction(3, -4))
    assertResult(Fraction(7, 4))(Fraction(7, 6) / Fraction(2, 3))
  }

  //
  // 연습문제 11-4
  //

  /*
      / 연산자는 정확히 나누어 떨어지지 않을 경우에 대한 처리가 애매하므로 제공하지 않는 것이 낫다.
   */

  class Money private(asCents: Int) {
    val dollars: Int = abs(asCents) / 100 * sign(asCents)
    val cents: Int = abs(asCents) % 100 * sign(asCents)

    def +(other: Money): Money = Money(this.dollars + other.dollars, this.cents + other.cents)

    def -(other: Money): Money = Money(this.dollars - other.dollars, this.cents - other.cents)

    def *(factor: Int): Money = Money(this.dollars * factor, this.cents * factor)

    def ==(other: Money): Boolean = (this.dollars, this.cents) ==(other.dollars, other.cents)

    def <(other: Money): Boolean = if (this.dollars == other.dollars) this.cents < other.cents else this.dollars < other.dollars

    override def toString = "$" + (if (asCents < 0) "-" else "") + f"${abs(dollars)}%d.${abs(cents)}%02d"

    private def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0
  }

  object Money {
    def apply(d: Int, c: Int) = new Money(d * 100 + c)
  }

  test("Money") {
    assert(Money(1, 75) + Money(0, 50) == Money(2, 25))

    assert(Money(0, 50) - Money(2, 25) == Money(-1, -75))
    assert(Money(2, 25) - Money(1, 75) == Money(0, 50))

    assert(Money(2, 25) * 5 == Money(11, 25))
    assert(Money(2, 25) * -5 == Money(-11, -25))

    assert(Money(0, 75) - Money(0, 25) * 4 == Money(0, -25))

    assert(Money(0, -25) * 5 == Money(-1, -25))
    assert(Money(0, 50) < Money(1, 75))
  }

  //
  // 연습문제 11-5
  //

  class Table {

    private class Row {
      private val cells = ArrayBuffer[String]()

      def append(cell: String) = cells += cell

      def produce(): String = "  <tr> " + cells.map(cell => "<td>" + cell + "</td>").mkString(" ") + " </tr>"
    }

    private val rows = ArrayBuffer[Row]()

    def |(cell: String) = {
      if (rows.isEmpty) rows += new Row
      rows.last.append(cell)
      this
    }

    def ||(cell: String) = {
      rows += new Row
      this | cell
    }

    def produce(): String = "<table>\n" + rows.map(_.produce()).mkString("\n") + "\n</table>"
  }

  object Table {
    def apply() = new Table
  }

  test("HTML Table") {
    val expected =
      """
        |<table>
        |  <tr> <td>Java</td> <td>Scala</td> </tr>
        |  <tr> <td>Gosling</td> <td>Odersky</td> </tr>
        |  <tr> <td>JVM</td> <td>JVM, .NET</td> </tr>
        |</table>
      """.stripMargin.trim
    assertResult(expected)((Table() | "Java" | "Scala" || "Gosling" | "Odersky" || "JVM" | "JVM, .NET").produce())
  }

  //
  // 연습문제 11-6
  //

  class ASCIIArt(private val lines: Array[String]) {
    private val width: Int = lines.map(_.length).max

    def +(other: ASCIIArt) = {
      val height = Array(this, other).map(_.lines.length).max

      val thisLines = padTopBottom(this.lines, height).map(fillRight(_, this.width))
      val otherLines = padTopBottom(other.lines, height)

      new ASCIIArt((for (i <- 0 until height) yield thisLines(i) + " " + otherLines(i)).toArray)
    }

    def /(other: ASCIIArt) = {
      val width = Array(this.width, other.width).max

      def paddedLines(lines: Array[String]) = lines.map(padLeft(_, (width - this.width) / 2))

      new ASCIIArt(paddedLines(this.lines) ++ Array("") ++ paddedLines(other.lines))
    }

    override def toString: String = lines.mkString("\n")

    private def padTopBottom(lines: Array[String], height: Int): Array[String] = {
      def emptyLines(n: Int) = (for (i <- 1 to n) yield "").toArray

      val top = (height - lines.length) / 2
      val bottom = height - top - lines.length

      emptyLines(top) ++ lines ++ emptyLines(bottom)
    }

    private def padLeft(line: String, pads: Int): String = " " * pads ++ line

    private def fillRight(line: String, width: Int): String = line ++ " " * (width - line.length)
  }

  object ASCIIArt {
    def apply(str: String) = new ASCIIArt(str.split("\n"))
  }

  test("ASCIIArt") {
    val cat = ASCIIArt(
      """
        | /\_/\
        |( ' ' )
        |(  -  )
        | | | |
        |(__|__)
      """.stripMargin.trim)

    val bubble = ASCIIArt(
      """
        |  -----
        | / Hello \
        |<  Scala |
        | \ Coder /
        |   -----
      """.stripMargin.trim)

    println(cat + bubble)
    println(cat / bubble)
  }

  //
  // 연습문제 11-7
  //

  class BitSequence(longVal: Long) {
    private val bits = new Array[Boolean](BitSequence.numBits)

    initWith(
      if (longVal >= 0) BitSequence.positiveLongToBits(abs(longVal))
      else BitSequence.increment(BitSequence.negate(BitSequence.positiveLongToBits(abs(longVal))))
    )
    bits(BitSequence.numBits - 1) = longVal < 0

    def apply(index: Int): Boolean = bits(index)

    def update(index: Int, bit: Boolean): Unit = bits(index) = bit

    def toLong: Long = {
      if (bits(BitSequence.numBits - 1)) BitSequence.bitsToPositiveLong(BitSequence.negate(BitSequence.decrement(bits))) * -1
      else BitSequence.bitsToPositiveLong(bits)
    }

    override def toString: String = bits.map(b => if (b) 1 else 0).reverse.mkString("[", "", "]")

    private def initWith(newBits: Array[Boolean]): Unit =
      for (i <- 0 until BitSequence.numBits) bits(i) = newBits(i)
  }

  object BitSequence {
    private val numBits = 64

    def apply(longVal: Long): BitSequence = new BitSequence(longVal)

    private def positiveLongToBits(longVal: Long): Array[Boolean] =
      (for (i <- 0 until numBits) yield (longVal & 1L << i) > 0).toArray

    private def bitsToPositiveLong(bits: Array[Boolean]): Long =
      (for (i <- 0 until numBits) yield (if (bits(i)) 1 else 0) * (1L << i)).sum

    private def negate(bits: Array[Boolean]): Array[Boolean] = bits.map(!_)

    private def increment(bits: Array[Boolean]): Array[Boolean] = incrementOrDecrement(bits, 1)

    private def decrement(bits: Array[Boolean]): Array[Boolean] = incrementOrDecrement(bits, -1)

    private def incrementOrDecrement(bits: Array[Boolean], delta: Int): Array[Boolean] = {
      val newBits = new Array[Boolean](64)

      var inc = delta
      var carry = 0
      for (i <- 0 until numBits) {
        val sum = (if (bits(i)) 1 else 0) + inc + carry

        newBits(i) = if (sum % 2 == 0) false else true
        inc = 0
        carry = if (sum >= 2) 1 else if (sum < 0) -1 else 0
      }

      newBits
    }
  }

  test("BitSequence") {
    for (i <- 0 until 64) assert(!BitSequence(0)(i), i)

    assert(BitSequence(1)(0))
    assert(BitSequence(2)(1))
    assert(BitSequence(1024)(10))

    def checkEncodingDecoding(longVal: Long): Unit = assert(BitSequence(longVal).toLong == longVal)

    checkEncodingDecoding(Long.MinValue)
    checkEncodingDecoding(Long.MaxValue)
    checkEncodingDecoding(Int.MinValue)
    checkEncodingDecoding(Int.MaxValue)
  }

  //
  // 연습문제 11-8
  //

  class Matrix(val rows: Int, val cols: Int, private val data: Array[Int]) {
    def apply(row: Int, col: Int): Int = data(row * cols + col)

    def +(other: Matrix): Matrix = Matrix(rows, cols, (for (r <- 0 until this.rows; c <- 0 until this.cols) yield this(r, c) + other(r, c)).toArray)

    def *(other: Matrix): Matrix =
      Matrix(this.rows, other.cols, (for (r <- 0 until this.rows; c <- 0 until other.cols) yield (for (i <- 0 until this.cols) yield this(r, i) * other(i, c)).sum).toArray)

    def *(scalar: Int): Matrix = Matrix(rows, cols, data.map(_ * scalar))

    override def toString: String = data.grouped(cols).map(row => row.mkString("( ", " ", " )")).mkString("\n")

    /*
     * IntelliJ IDEA로 생성
     */

    def canEqual(other: Any): Boolean = other.isInstanceOf[Matrix]

    override def equals(other: Any): Boolean = {
      other match {
        case that: Matrix =>
          (that canEqual this) &&
            rows == that.rows &&
            cols == that.cols &&
            data.toBuffer == that.data.toBuffer // 수정됨
        case _ => false
      }
    }

    override def hashCode(): Int = {
      val state = Seq(rows, cols, data)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
  }

  object Matrix {
    def apply(rows: Int, cols: Int, data: Array[Int]) = new Matrix(rows, cols, data)
  }

  test("Matrix") {
    val mat1 = Matrix(3, 3, Array(1, 3, 7, 1, 0, 0, 1, 2, 2))
    val mat2 = Matrix(3, 3, Array(0, 0, 5, 7, 5, 0, 2, 1, 1))
    val mat3 = Matrix(3, 3, Array(1, 3, 12, 8, 5, 0, 3, 3, 3))
    assert(mat1 + mat2 == mat3)
    println(ASCIIArt(mat1.toString) + ASCIIArt("+") + ASCIIArt(mat2.toString) + ASCIIArt("=") + ASCIIArt(mat3.toString))

    println()

    val mat4 = Matrix(2, 3, Array(1, 0, 2, -1, 3, 1))
    val mat5 = Matrix(3, 2, Array(3, 1, 2, 1, 1, 0))
    val mat6 = Matrix(2, 2, Array(5, 1, 4, 2))
    assert(mat4 * mat5 == mat6)
    println(ASCIIArt(mat4.toString) + ASCIIArt("*") + ASCIIArt(mat5.toString) + ASCIIArt("=") + ASCIIArt(mat6.toString))

    println()

    val mat7 = Matrix(2, 3, Array(1, 8, -3, 4, -2, 5))
    val mat8 = Matrix(2, 3, Array(2, 16, -6, 8, -4, 10))
    assert(mat7 * 2 == mat8)
    println(ASCIIArt(mat7.toString) + ASCIIArt("* 2 =") + ASCIIArt(mat8.toString))
  }

  //
  // 연습문제 11-9
  //

  object RichFile1 {
    def unapply(richFile: sbt.RichFile): Option[(String, String, String)] =
      if (richFile.isDirectory) None else Some(richFile.asFile.getParent, richFile.base, richFile.ext)
  }

  test("RichFile unapply") {
    {
      val RichFile1(path, name, extension) = new sbt.RichFile(new java.io.File("/home/cay/readme.txt"))
      assert(path == "/home/cay")
      assert(name == "readme")
      assert(extension == "txt")
    }

    {
      /*
        RichFile#isDirectory 가 원하는대로 동작하지 않는 것 같다.
       */

      val RichFile1(path, name, extension) = new sbt.RichFile(new java.io.File("/home/cay/"))
      assert(path == "/home")
      assert(name == "cay")
      assert(extension == "")
    }
  }

  //
  // 연습문제 11-10
  //

  object RichFile2 {
    def unapplySeq(richFile: sbt.RichFile): Option[Seq[String]] =
      if (richFile.asFile.getPath.trim.isEmpty) None else Some(richFile.asFile.getPath.split("/").filterNot(_.isEmpty))
  }

  test("RichFile unapplySeq") {
    {
      val richFile = new sbt.RichFile(new java.io.File("/home/cay/readme.txt"))
      richFile match {
        case RichFile2("home", "cay", "readme.txt") => assertResult(true)(true)
        case _ => fail(RichFile2.unapplySeq(richFile).toString)
      }
    }

    {
      val richFile = new sbt.RichFile(new java.io.File("/home"))
      richFile match {
        case RichFile2("home") => assertResult(true)(true)
        case _ => fail(RichFile2.unapplySeq(richFile).toString)
      }
    }

    {
      val richFile = new sbt.RichFile(new java.io.File("/"))
      richFile match {
        case RichFile2() => assertResult(true)(true)
        case _ => fail(RichFile2.unapplySeq(richFile).toString)
      }
    }

    assertResult(None)(RichFile2 unapplySeq new sbt.RichFile(new java.io.File("")))
  }
}
