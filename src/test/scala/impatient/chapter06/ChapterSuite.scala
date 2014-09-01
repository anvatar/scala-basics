package impatient.chapter06

import org.scalatest.FunSuite

class ChapterSuite extends FunSuite {

  //
  // 연습문제 6-1
  //

  object Conversions {
    def inchesToCentimeters(inches: Double) = inches * 2.54

    def gallonsToLiters(gallons: Double) = gallons * 3.7854118

    def milesToKilometers(miles: Double) = miles * 1.609344
  }

  test("Conversions") {
    assertResult(2.54)(Conversions.inchesToCentimeters(1))
    assertResult(3.7854118)(Conversions.gallonsToLiters(1))
    assertResult(1.609344)(Conversions.milesToKilometers(1))
  }

  //
  // 연습문제 6-2
  //

  abstract class UnitConversion {
    def apply(value: Double): Double
  }

  object InchesToCentimeters extends UnitConversion {
    override def apply(inches: Double) = inches * 2.54
  }

  object GallonsToLiters extends UnitConversion {
    override def apply(gallons: Double) = gallons * 3.7854118
  }

  object MilesToKilometers extends UnitConversion {
    override def apply(miles: Double) = miles * 1.609344
  }

  test("UnitConversions") {
    assertResult(2.54)(InchesToCentimeters(1))
    assertResult(3.7854118)(GallonsToLiters(1))
    assertResult(1.609344)(MilesToKilometers(1))

    val testData = Array(
      (InchesToCentimeters, 2.54),
      (GallonsToLiters, 3.7854118),
      (MilesToKilometers, 1.609344)
    )

    for ((uc, expected) <- testData)
      assertResult(expected)(uc(1))
  }

  //
  // 연습문제 6-3
  //

  object Origin extends java.awt.Point {
    def apply() = new java.awt.Point

    def apply(x: Int, y: Int) = new java.awt.Point(x, y)

    def apply(other: java.awt.Point) = new java.awt.Point(other)
  }

  test("Origin is mutable") {
    val o1 = Origin(100, 200)
    val o2 = Origin(o1)

    o1.move(200, 300)
    assert(o1 !== o2)
  }

  //
  // 연습문제 6-4
  //

  class Point(val x: Int, val y: Int)

  object Point {
    def apply(x: Int, y: Int) = new Point(x, y)
  }

  test("Point") {
    val p = Point(3, 4)
    assertResult(3)(p.x)
    assertResult(4)(p.y)
  }

  //
  // 연습문제 6-5
  //

  /*
  $ scala -classpath target/scala-2.11/classes impatient.chapter06.Reverse Hello World
  World Hello
   */

  //
  // 연습문제 6-6
  //

  object PlayingCardSuits extends Enumeration {
    val Club = Value("♣")
    val Diamond = Value("♦")
    val Heart = Value("♥")
    val Spade = Value("♠")
  }

  test("PlayingCardSuits toString") {
    assertResult("♣")(PlayingCardSuits.Club.toString)
    assertResult("♦")(PlayingCardSuits.Diamond.toString)
    assertResult("♥")(PlayingCardSuits.Heart.toString)
    assertResult("♠")(PlayingCardSuits.Spade.toString)
    assertResult("♣♦♥♠")(PlayingCardSuits.values.mkString(""))
  }

  //
  // 연습문제 6-7
  //

  def isRedSuit(suit: PlayingCardSuits.Value) = {
    Set(PlayingCardSuits.Diamond, PlayingCardSuits.Heart).contains(suit)
  }

  test("isRedSuit") {
    val testData = Array(
      (PlayingCardSuits.Club, false),
      (PlayingCardSuits.Diamond, true),
      (PlayingCardSuits.Heart, true),
      (PlayingCardSuits.Spade, false)
    )

    for ((suit, expected) <- testData) {
      assertResult(expected)(isRedSuit(suit))
    }
  }

  //
  // 연습문제 6-8
  //

  object RgbCubeCorners extends Enumeration {
    val Black = Value(0x000000)
    val Blue = Value(0x0000ff)
    val Green = Value(0x00ff00)
    val Cyan = Value(0x00ffff)
    val Red = Value(0xff0000)
    val Magenta = Value(0xff00ff)
    val Yellow = Value(0xffff00)
    val White = Value(0xffffff)
  }

  test("RgbCubeCorners") {
    val testData = Array(
        (0xff0000, "Red"),
        (0x00ff00, "Green"),
        (0x0000ff, "Blue"),
        (0xffffff, "White"),
        (0x000000, "Black"),
        (0xffff00, "Yellow"),
        (0xff00ff, "Magenta"),
        (0x00ffff, "Cyan")
    )

    for ((value, name) <- testData) {
      assertResult(name)(RgbCubeCorners(value).toString)
    }
  }
}
