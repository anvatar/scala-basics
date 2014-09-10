package impatient.chapter07

object SecureGreeter extends App {
  // 연습문제 7-9의 의도가 import java.lang.System._ 을 한 뒤 아래 문장을
  //    val name = getProperty("user.name")
  // 과 같이 쓰라는 의미인지 확실치 않다.
  val name = System.getProperty("user.name")

  printf("Enter password for user name [%s]: ", name)
  val password = Console.in.readLine()

  if (password == "secret")
    println("Greetings, " + name)
  else
    Console.err.println("Wrong user name or password")
}
