package impatient.chapter05

import org.scalatest.{Matchers, FlatSpec}

class ChapterSpec extends FlatSpec with Matchers {
  //
  // 연습문제 5-1
  //

  class Counter {
    private var value = 0

    def increment() {
      if (value < Int.MaxValue) value += 1
    }
    def current() = value
  }

  "Counter" should "not overflow at (" + Int.MaxValue + " + 1)" in {
    val counter = new Counter
    for (i <- 0 until Int.MaxValue) {
      counter.increment()
    }

    counter.increment()
    counter.current >= 0 shouldEqual true
  }

  //
  // 연습문제 5-2
  //

  class BankAccount {
    private var currentAmount = 0

    def deposit(amount: Int) = { currentAmount += amount; this }
    def withdraw(amount: Int) = { currentAmount -= amount; this }

    def balance = currentAmount
  }

  val bankAccount = new BankAccount
  //bankAccount.balance = 100   /* 컴파일 에러 */

  "BankAccount: 150 - 200 + 100" should "be 50" in {
    bankAccount.deposit(150).withdraw(200).deposit(100).balance shouldEqual 50
  }

  //
  // 연습문제 5-3
  //

  {
    class Time(val hours: Int, val minutes: Int) {
      def before(other: Time) = inMinutes < other.inMinutes

      private def inMinutes() = hours * 60 + minutes
    }

    val time1 = new Time(1, 45)
    val time2 = new Time(12, 30)
    val time3 = new Time(23, 15)

    "" + time1 should "be before " + time2 in {
      time1 before time2 shouldEqual true
    }

    "" + time3 should "not be before " + time2 in {
      time3 before time2 shouldEqual false
    }
  }

  //
  // 연습문제 5-4
  //

  {
    class Time private(val inMinutes: Int) {
      def this(hours: Int, minutes: Int) {
        this(hours * 60 + minutes)
      }

      def before(other: Time) = inMinutes < other.inMinutes
    }

    // 대안
    /*
    class Time(hours: Int, minutes: Int) {
      def before(other: Time) = inMinutes < other.inMinutes

      private def inMinutes() = hours * 60 + minutes
    }
    */

    val time1 = new Time(1, 45)
    val time2 = new Time(12, 30)
    val time3 = new Time(23, 15)
    //val time4 = new Time(2 * 60 + 40) /* 컴파일 에러 */

    "" + time1 should "be before " + time2 in {
      time1 before time2 shouldEqual true
    }

    "" + time3 should "not be before " + time2 in {
      time3 before time2 shouldEqual false
    }
  }

  //
  // 연습문제 5-5
  //

  /*
      class Student(@BeanProperty var name: String, @BeanProperty var id: Long)

      $ javap -classpath target/scala-2.11/classes impatient.chapter05.Student
      Compiled from "Student.scala"
      public class impatient.chapter05.Student {
        // 기본 생성자
        public impatient.chapter05.Student(java.lang.String, long);

        // 기본 getter/setter
        public java.lang.String name();
        public long id();
        public void name_$eq(java.lang.String);
        public void id_$eq(long);

        // 자바빈 getter/setter
        public java.lang.String getName();
        public void setName(java.lang.String);
        public long getId();
        public void setId(long);
      }
   */

  {
    val name = "HongJoon Ahn"
    val id = 3505
    val id2 = 3263

    val anvatar = new Student(name, id)
    "getXxx/setXxx call" should "be inconvenient" in {
      anvatar.getName shouldEqual name
      anvatar.getId shouldEqual id
      anvatar.setId(id2)
      anvatar.getId shouldEqual id2

      anvatar.name shouldEqual name
      anvatar.id shouldEqual id2
      anvatar.id = id
      anvatar.id shouldEqual id
    }
  }

  /*
      스칼라에서 자바빈 getter/setter를 호출할 수 있다.
      하지만 동일한 작업에 기본 getter/setter를 호출하는 것이 더 간결하고 명확하다.
      스칼라 내에서만 사용할 클래스라면 자바빈 getter/setter를 제공할 필요가 없다.

      자바에서도 사용해야 하는 클래스인 경우, 자바 코딩 컨벤션을 따르기 위해 필요할 수 있다.
      자바빈 getter/setter가 존재할 것을 기대하는 자바 프레임워크와 연동할 경우에는 반드시 필요하게 된다.
   */

  //
  // 연습문제 5-6
  //

  {
    class Person(private var privateAge: Int = 0) {
      if (privateAge < 0) privateAge = 0

      def age = privateAge
      def age_=(newValue: Int): Unit = {
        if (newValue > privateAge) privateAge = newValue
      }
    }

    val fred = new Person(-2)
    "Fred's age" should "be 0" in {
      fred.age shouldEqual 0
    }

    val wilma = new Person
    "Wilma's age" should "be 0" in {
      wilma.age shouldEqual 0
    }
  }

  //
  // 연습문제 5-7
  //

  {
    class Person(name: String) {
      val firstName = name.split("\\s")(0)
      val lastName = name.split("\\s")(1)
    }

    val fred = new Person("Fred Smith")
    "Fred's first name and last name" should "be 'Fred' and 'Smith'" in {
      fred.firstName shouldEqual "Fred"
      fred.lastName shouldEqual "Smith"
    }

    /*
        기본 생성자의 인자는 일반 인자면 된다. firstName, lastName 필드 값을 계산한 뒤에는 별도로 이용할 필요가 없기 때문이다.
     */
  }

  //
  // 연습문제 5-8
  //

  {
    "Cars" should "instantiate fine" in {
      val i30 = new Car("Hyundai", "i30", 2008, "6048")
      i30.manufacturer shouldEqual "Hyundai"
      i30.modelName shouldEqual "i30"
      i30.modelYear shouldEqual 2008
      i30.registeredNum shouldEqual "6048"

      val forte = new Car("Kia", "Forte")
      forte.modelYear shouldEqual -1
      forte.registeredNum shouldEqual ""

      val sonata = new Car("Hyundai", "Sonata", 2009)
      sonata.modelYear shouldEqual 2009
      sonata.registeredNum shouldEqual ""

      val spark = new Car("Chevrolet", "Spark", "2236")
      spark.modelYear shouldEqual -1
      spark.registeredNum shouldEqual "2236"
    }
  }

  //
  // 연습문제 5-9
  //

  /*
      src/main/java/impatient/chapter05/JavaCar.java
   */

  //
  // 연습문제 5-10
  //

  class Employee1(val name: String, var salary: Double) {
    def this() { this("John Q. Public", 0.0) }
  }

  /*
      이걸 default primary constructor라고 부를 수 있는지 모르겠다.
      하지만 givenName을 생성자 인자에 추가하지 않고는 name을 읽기 전용으로 만들 방법이 없는 것 같다.
  */
  class Employee2(givenName: String = "John Q. Public") {
    val name: String = givenName
    var salary: Double = 0.0
  }

  class Employee3(val name: String = "John Q. Public", var salary: Double = 0.0)

  /*
      Employee2보다는 Employee1이 낫다.
      하지만 가장 좋은 선택은 Employee3이다.
   */

  val john = new Employee2
  "John's name" should "be John Q. Public" in {
    john.name shouldEqual "John Q. Public"
  }

  val anvatar = new Employee2("HongJoon Ahn")
  anvatar.salary = 0.1
  "anvatar's name" should "HongJoon Ahn" in {
    anvatar.name shouldEqual "HongJoon Ahn"
  }
}
