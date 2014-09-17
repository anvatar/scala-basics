package chap06

/**
 * Created by kwonyoungjoo on 14. 9. 1..
 * example 6-2
 */

import org.scalatest.FunSuite

class example06 extends FunSuite {
  /*
    example 6.1
   */
  object Conversions{
    def inchesToCentimeters(inches:Float):Double = inches * 2.54
    def gallonsToLiters(gallons:Float):Double = gallons * 3.785
    def milesToKilometers(miles:Float):Double = miles * 0.000001
  }

  test("Conversion"){
    assertResult(2.54)(Conversions.inchesToCentimeters(1))
    assertResult(3.785)(Conversions.gallonsToLiters(1))
    assertResult(0.000001)(Conversions.milesToKilometers(1))
  }

  /*
      example 6.2
  */
  abstract class UnitConversion(){
    def conversion(value:Float): Double
  }

  object inchesToCentimeters extends UnitConversion{
    override def conversion(inches:Float):Double = inches * 2.54
  }

  object gallonsToLiters extends UnitConversion{
    override def conversion(gallons:Float):Double = gallons * 3.785
  }

  object milesToKilometers extends UnitConversion{
    override def conversion(miles:Float):Double = miles * 1.609
  }

  test("UnitConversion"){
    assertResult(2.54)(inchesToCentimeters.conversion(1))
    assertResult(3.785)(gallonsToLiters.conversion(1))
    assertResult(1.609)(milesToKilometers.conversion(1))
  }



  /*
    example 6.3
   */
  object Origin extends java.awt.Point {

  }
  /*
    example 6.3
   */
  class Point(val x:Int, val y:Int) ;

  object Point{
    def apply(x:Int, y:Int) = new Point(x,y)
  }

  test("Point"){
    val p1 = new Point(3,4)
    val p2 = Point(3,4)

    assertResult(3)(p1.x)
    assertResult(4)(p1.y)

    assertResult(3)(p2.x)
    assertResult(4)(p2.y)
  }

  /*
   example 6.5
   */
  object ReversionStr extends App{
    if (args.length >0){
      println(args.reverse)
    }

    /*
    example 6.5
    */
   object CardSymbol extends Enumeration{
      //val tmp = ["♥", "♠", "♦", "♣"]
      //val Heart, Spade, Diamond, Club = Value(tmp)
      val Heart = "♥"
      val Spade = "♠"
      val Diamond ="♦"
      val Club = "♣"

    }
    test("PlayingCardSuits toString") {
      assertResult("♥")(CardSymbol.Heart.toString)
      assertResult("♠")(CardSymbol.Spade.toString)
      assertResult("♦")(CardSymbol.Diamond.toString)
      assertResult("♣")(CardSymbol.Club.toString)
    }
  }



}
