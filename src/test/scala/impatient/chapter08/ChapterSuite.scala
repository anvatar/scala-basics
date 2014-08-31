package impatient.chapter08

import org.scalatest.FunSuite

import scala.collection.immutable.Stack
import scala.collection.mutable.ArrayBuffer

class ChapterSuite extends FunSuite {

  //
  // 연습문제 8-1
  //

  class BankAccount(initialBalance: Double) {
    private var balance = initialBalance

    def currentBalance = balance

    def deposit(amount: Double) = {
      balance += amount
      balance
    }

    def withdraw(amount: Double) = {
      balance -= amount
      balance
    }
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
    bundle add {
      val anotherBundle = new Bundle
      anotherBundle add new SimpleItem(2, "simple2")
      anotherBundle add new SimpleItem(3, "simple3")
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

  class Square(topLeftPoint: Point, width: Int) extends java.awt.Rectangle(topLeftPoint.x.toInt, topLeftPoint.y.toInt, width, width) {
    def this(width: Int) = this(new Point(0, 0), width)

    def this() = this(0)
  }

  test("Square") {
    def minXyAndMaxXy(square: Square): (Int, Int, Int, Int) =
      (square.getMinX.toInt, square.getMinY.toInt, square.getMaxX.toInt, square.getMaxY.toInt)

    assert(minXyAndMaxXy(new Square(new Point(100, 200), 10)) ==(100, 200, 110, 210))
    assert(minXyAndMaxXy(new Square(10)) ==(0, 0, 10, 10))
    assert(minXyAndMaxXy(new Square) ==(0, 0, 0, 0))
  }

  //
  // 연습문제 8-8
  //

  /*
        $ javap -c -p -classpath target/scala-2.11/classes impatient.chapter08.Person
        Compiled from "Persons.scala"
        public class impatient.chapter08.Person {
          private final java.lang.String name;

          public java.lang.String name();
            Code:
               0: aload_0
               1: getfield      #13                 // Field name:Ljava/lang/String;
               4: areturn

          public java.lang.String toString();
            Code:
               0: new           #18                 // class scala/collection/mutable/StringBuilder
               3: dup
               4: invokespecial #22                 // Method scala/collection/mutable/StringBuilder."<init>":()V
               7: aload_0
               8: invokevirtual #26                 // Method java/lang/Object.getClass:()Ljava/lang/Class;
              11: invokevirtual #31                 // Method java/lang/Class.getName:()Ljava/lang/String;
              14: invokevirtual #35                 // Method scala/collection/mutable/StringBuilder.append:(Ljava/lang/Object;)Lscala/collection/mutable/StringBuilder;
              17: ldc           #37                 // String [name=
              19: invokevirtual #35                 // Method scala/collection/mutable/StringBuilder.append:(Ljava/lang/Object;)Lscala/collection/mutable/StringBuilder;
              22: aload_0
              23: invokevirtual #39                 // Method name:()Ljava/lang/String;
              26: invokevirtual #35                 // Method scala/collection/mutable/StringBuilder.append:(Ljava/lang/Object;)Lscala/collection/mutable/StringBuilder;
              29: ldc           #41                 // String ]
              31: invokevirtual #35                 // Method scala/collection/mutable/StringBuilder.append:(Ljava/lang/Object;)Lscala/collection/mutable/StringBuilder;
              34: invokevirtual #43                 // Method scala/collection/mutable/StringBuilder.toString:()Ljava/lang/String;
              37: areturn

          public impatient.chapter08.Person(java.lang.String);
            Code:
               0: aload_0
               1: aload_1
               2: putfield      #13                 // Field name:Ljava/lang/String;
               5: aload_0
               6: invokespecial #45                 // Method java/lang/Object."<init>":()V
               9: return
        }

        $ javap -c -p -classpath target/scala-2.11/classes impatient.chapter08.SecretAgent
        Compiled from "Persons.scala"
        public class impatient.chapter08.SecretAgent extends impatient.chapter08.Person {
          private final java.lang.String name;

          private final java.lang.String toString;

          public java.lang.String name();
            Code:
               0: aload_0
               1: getfield      #14                 // Field name:Ljava/lang/String;
               4: areturn

          public java.lang.String toString();
            Code:
               0: aload_0
               1: getfield      #18                 // Field toString:Ljava/lang/String;
               4: areturn

          public impatient.chapter08.SecretAgent(java.lang.String);
            Code:
               0: aload_0
               1: aload_1
               2: invokespecial #22                 // Method impatient/chapter08/Person."<init>":(Ljava/lang/String;)V
               5: aload_0
               6: ldc           #24                 // String secret
               8: putfield      #14                 // Field name:Ljava/lang/String;
              11: aload_0
              12: ldc           #24                 // String secret
              14: putfield      #18                 // Field toString:Ljava/lang/String;
              17: return
        }

      Person과 SecretAgent가 각각 1개씩 name 필드와 name 게터를 가지고 있다.

      Person#name()은 객체 생성 시에 생성자에서 name 파라미터의 값으로 받은 String 객체를 리턴한다.
      SecretAgent#name()은 객체 생성 시에 SecretAgent#name 필드에 할당된, "secret"이라는 내용의 String 객체를 리턴한다.
   */

  //
  // 연습문제 8-9
  //

  test("Creatures") {
    val ant = new Ant
    //assert(ant.env.size == 0)     // Ant#range를 def로 선언하기 전에는 결과가 0
    assert(ant.env.size == 2) // Ant#range를 def로 선언하면 결과는 2
  }

  /*
    Creature#range를 def로 선언하면, Ant#range를 val 또는 def로 선언하는 것과 관계없이 바이트 코드 내용은 아래와 같다.
    생성자에서 env 배열을 생성할 때 배열 크기를 얻기 위해 가상함수 range를 호출한다.

        $ javap -c -p -classpath target/scala-2.11/classes impatient.chapter08.Creature
        Compiled from "Creatures.scala"
        public class impatient.chapter08.Creature {
          private final int[] env;

          public int range();
            Code:
               0: bipush        10
               2: ireturn

          public int[] env();
            Code:
               0: aload_0
               1: getfield      #17                 // Field env:[I
               4: areturn

          public impatient.chapter08.Creature();
            Code:
               0: aload_0
               1: invokespecial #21                 // Method java/lang/Object."<init>":()V
               4: aload_0
               5: aload_0
               6: invokevirtual #23                 // Method range:()I
               9: newarray       int
              11: putfield      #17                 // Field env:[I
              14: return
        }

    Ant#range를 val로 선언하면 아래와 같이 생성자에서 초기값을 지정하는 private 필드와 이 필드 값을 리턴하는 range() 함수가 만들어진다.
    필드 값 초기화 이전과 이후에 서로 다른 결과를 리턴할 수 있다.

        $ javap -c -p -classpath target/scala-2.11/classes impatient.chapter08.Ant
        Compiled from "Creatures.scala"
        public class impatient.chapter08.Ant extends impatient.chapter08.Creature {
          private final int range;

          public int range();
            Code:
               0: aload_0
               1: getfield      #13                 // Field range:I
               4: ireturn

          public impatient.chapter08.Ant();
            Code:
               0: aload_0
               1: invokespecial #19                 // Method impatient/chapter08/Creature."<init>":()V
               4: aload_0
               5: iconst_2
               6: putfield      #13                 // Field range:I
               9: return
        }

    Ant#range를 def로 선언하면 range() 함수가 만들어진다. 이 경우에는 상수 값을 리턴하므로 Ant 객체 생성 과정 중 어느 시점에서 range()를 호출해도 맞는 값을 리턴한다.

        $ javap -c -p -classpath target/scala-2.11/classes impatient.chapter08.Ant
        Compiled from "Creatures.scala"
        public class impatient.chapter08.Ant extends impatient.chapter08.Creature {
          public int range();
            Code:
               0: iconst_2
               1: ireturn

          public impatient.chapter08.Ant();
            Code:
               0: aload_0
               1: invokespecial #16                 // Method impatient/chapter08/Creature."<init>":()V
               4: return
        }
   */

  //
  // 연습문제 8-10
  //

  /*
      Stack의 기본 생성자는 Stack 클래스 및 서브클래스의 기본 생성자에서만 호출 가능하다.

      elems 필드는 Stack 클래스 및 서브클래스에서만 접근 가능하다.
   */

  class MyStack[A](elements: List[A]) extends Stack[A](elements) {
    def currentElements() = elems
  }

  test("Stacks") {
    val intList = List(1, 2, 3)

    //val stack = new Stack[Int](intList)   // 컴파일 에러
    val myStack = new MyStack[Int](intList)

    //assert(stack.elems == List(1, 2, 3))  // 컴파일 에러
    assert(myStack.currentElements == List(1, 2, 3))
  }
}
