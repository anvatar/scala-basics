package impatient.chapter08

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class ChapterSuite extends FunSuite {
  //
  // 연습문제 8-1
  //

  class BankAccount(initialBalance: Double) {
    private var balance = initialBalance
    def currentBalance = balance
    def deposit(amount: Double) = { balance += amount; balance }
    def withdraw(amount: Double) = { balance -= amount; balance }
  }

  test("BankAccount") {
    val account = new BankAccount(100)
    assertResult(150)(account deposit 50)
    assertResult(120)(account withdraw 30)
  }

  class CheckingAccount(initialBalance: Double) extends BankAccount(initialBalance) {
    override def deposit(amount: Double) = {
      super.withdraw(1)
      super.deposit(amount)
    }

    override def withdraw(amount: Double) = {
      super.withdraw(1)
      super.withdraw(amount)
    }
  }

  test("CheckingAccount") {
    val account = new CheckingAccount(100)
    assertResult(150 - 1)(account deposit 50)
    assertResult(120 - 2)(account withdraw 30)
  }

  //
  // 연습문제 8-2
  //

  class SavingsAccount(initialBalance: Double) extends BankAccount(initialBalance) {
    private var numOfRemainingFreeTransactions = 0
    restoreNumOfRemainingFreeTransactions()

    override def deposit(amount: Double): Double = {
      payTransactionFee()
      super.deposit(amount)
    }

    override def withdraw(amount: Double): Double = {
      payTransactionFee()
      super.withdraw(amount)
    }

    def earnMonthlyInterest(interestRate: Double) = {
      restoreNumOfRemainingFreeTransactions()
      super.deposit(currentBalance * interestRate)
    }

    private def payTransactionFee(): Unit = {
      if (numOfRemainingFreeTransactions <= 0) super.withdraw(1)
      numOfRemainingFreeTransactions -= 1
    }

    private def restoreNumOfRemainingFreeTransactions(): Unit = {
      numOfRemainingFreeTransactions = 3
    }
  }

  test("SavingsAccount") {
    val account = new SavingsAccount(100)
    assertResult(150)(account deposit 50)
    assertResult(120)(account withdraw 30)
    assertResult(110)(account withdraw 10)
    assertResult(150 - 1)(account deposit 40)
    assertResult((150 - 1) * (1 + 0.1))(account earnMonthlyInterest 0.1)
    assertResult((150 - 1) * (1 + 0.1) - 20)(account withdraw 20)
  }

  //
  // 연습문제 8-3
  //

  /*
      http://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html
   */

  class Bicycle(var cadence: Integer, var speed: Integer, var gear: Integer) {
    override def toString: String =
      "Bike is " + "in gear " + gear + " with a cadence of " + cadence + " and travelling at a speed of " + speed + "."
  }

  class MountainBike(cadence: Integer, speed: Integer, gear: Integer, var suspension: String) extends Bicycle(cadence, speed, gear) {
    override def toString: String = super.toString + "\n" +
      "The " + "MountainBike has a " + suspension + " suspension."
  }

  class RoadBike(cadence: Integer, speed: Integer, gear: Integer, var tireWidth: Integer) extends Bicycle(cadence, speed, gear) {
    override def toString: String = super.toString + "\n" +
      "The RoadBike" + " has " + tireWidth + " MM tires."
  }

  test("Bikes") {
    val bikes: Array[Bicycle] = Array(new Bicycle(20, 10, 1), new MountainBike(20, 10, 5, "Dual"), new RoadBike(40, 20, 8, 23))

    val expectedValues = Array(
      "Bike is in gear 1 with a cadence of 20 and travelling at a speed of 10.",
      "Bike is in gear 5 with a cadence of 20 and travelling at a speed of 10.\nThe MountainBike has a Dual suspension.",
      "Bike is in gear 8 with a cadence of 40 and travelling at a speed of 20.\nThe RoadBike has 23 MM tires."
    )

    for (i <- 0 until 3) assertResult(expectedValues(i), "at index = " + i)(bikes(i).toString)
  }

  //
  // 연습문제 8-4
  //

  abstract class Item {
    def price: Double
    def description: String
  }

  class SimpleItem(val price: Double, val description: String) extends Item

  class Bundle extends Item {
    val items = ArrayBuffer[Item]()

    def add(item: Item): Unit = items += item

    def price = {
      var result: Double = 0
      for (item <- items) result += item.price
      result
    }

    def description = (for (item <- items) yield item.description).mkString("( ", ", ", " )")
  }

  test("Items") {
    val bundle = new Bundle
    bundle add new SimpleItem(1, "simple1")

    assertResult(1)(bundle.price)
    
    bundle add {
      val anotherBundle = new Bundle
      anotherBundle add new SimpleItem(2, "simple2")
      anotherBundle add new SimpleItem(3, "simple3")

      assertResult(5)(anotherBundle.price)
      assertResult("( simple2, simple3 )")(anotherBundle.description)

      anotherBundle
    }

    assertResult(1 + 2 + 3)(bundle.price)
    assertResult("( simple1, ( simple2, simple3 ) )")(bundle.description)
  }

  //
  // 연습문제 8-5
  //

  class Point(val x: Double, val y: Double) {
    override def toString: String = "(" + x + ", " + y + ")"

    final override def equals(other: scala.Any): Boolean = {
      if (other.isInstanceOf[Point]) {
        val p = other.asInstanceOf[Point]
        if (p == null) false
        else p.x == x && p.y == y
      }
      else false
    }

    final override def hashCode(): Int = 13 * x.hashCode() + 17 * y.hashCode()

    // 아래는 InteilliJ IDEA로 생성한 메써드
    /*
        def canEqual(other: Any): Boolean = other.isInstanceOf[Point]

        override def equals(other: Any): Boolean = other match {
          case that: Point =>
            (that canEqual this) &&
              x == that.x &&
              y == that.y
          case _ => false
        }

        override def hashCode(): Int = {
          val state = Seq(x, y)
          state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
        }
    */
  }

  class LabeledPoint(val label: String, x: Double, y: Double) extends Point(x, y) {
    override def toString: String = "(" + label + ")" + super.toString

    // 아래는 InteilliJ IDEA로 생성한 메써드
    /*
        def canEqual(other: Any): Boolean = other.isInstanceOf[LabeledPoint]

        override def equals(other: Any): Boolean = other match {
          case that: LabeledPoint =>
            super.equals(that) &&
              (that canEqual this) &&
              label == that.label
          case _ => false
        }

        override def hashCode(): Int = {
          val state = Seq(super.hashCode(), label)
          state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
        }
    */
  }

  test("LabeledPoint") {
    assertResult("(Black Thursday)(1929.0, 230.07)")(new LabeledPoint("Black Thursday", 1929.0, 230.07).toString)
  }

  //
  // 연습문제 8-6
  //

  abstract class Shape {
    def centerPoint: Point
  }
  
  class Rectangle(val topLeftPoint: Point, val width: Double, val height: Double) extends Shape {
    override def centerPoint: Point = new Point(topLeftPoint.x + width / 2, topLeftPoint.y + height / 2)
  }
  
  class Circle(val centerPoint: Point, val radius: Double) extends Shape

  test("Shapes") {
    val rectangle = new Rectangle(new Point(100, 200), 100, 75)
    assertResult(new Point(100 + 100 / 2, 200 + 75.0 / 2))(rectangle.centerPoint)

    val circle = new Circle(new Point(200, 150), 100)
    assertResult(new Point(200, 150))(circle.centerPoint)
  }

  //
  // 연습문제 8-7
  //


  //
  // 연습문제 8-8
  //


  //
  // 연습문제 8-9
  //


  //
  // 연습문제 8-10
  //


}
