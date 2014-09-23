package chapter10

import org.scalatest.FunSuite

class Chapter10 extends FunSuite {

  //
  // 연습문제 1
  //

  /*
          scala> 3.
          %   +   >    >>>            isInstanceOf   toDouble   toLong     unary_+   |
          &   -   >=   ^              toByte         toFloat    toShort    unary_-
          *   /   >>   asInstanceOf   toChar         toInt      toString   unary_~
   */


  //
  // 연습문제 2
  //

  //
  // 연습문제 3
  //

  //
  // 연습문제 4
  //

  {
    trait Logger {
      def log(msg: String): String
    }

    trait CaesarCipher {
      def encode(msg: String, shift: Int): String = {
        (for(x <- msg) yield (x + shift).toChar).mkString
      }

      def decode(msg: String, shift: Int): String = {
        (for(x <- msg) yield (x - shift).toChar).mkString
      }
    }

    class CaesarLogger extends Logger with CaesarCipher {
      val key = 3
      def log(msg: String): String = {
       encode(msg, key)
      }
    }

    test("CryptoLogger") {
      val mockLogger1 = new CaesarLogger
      assertResult("defGHI")(mockLogger1.log("abcDEF"))

      val mockLogger2 = new CaesarLogger {override val key = -3}
      assertResult("abcDEF")(mockLogger2.log("defGHI"))
    }
  }

  /*
  {
    trait Logger {
      def log(msg: String)
    }

    trait CryptoLogger extends Logger {
      var caesarDistance = 3

      abstract override def log(msg: String): Unit = {
        super.log(msg.map(c => (c + caesarDistance).toChar))
      }
    }

    trait MockLogger extends Logger {
      //val messages = new ArrayBuffer[String]()

      def log(msg: String): Unit = {
        messages += msg
      }
    }

    test("CryptoLogger") {
      val mockLogger = new MockLogger with CryptoLogger

      mockLogger.log("abcDEF")
      assertResult("defGHI")(mockLogger.messages.last)

      mockLogger.caesarDistance = -3

      mockLogger.log("defGHI")
      assertResult("abcDEF")(mockLogger.messages.last)
    }
  }
  */

  //
  // 연습문제 5
  //


  //
  // 연습문제 6
  //

  //
  // 연습문제 7
  //


  //
  // 연습문제 8
  //


  //
  // 연습문제 9
  //


  //
  // 연습문제 10
  //

}
