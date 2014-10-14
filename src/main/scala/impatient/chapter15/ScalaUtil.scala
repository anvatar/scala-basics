package impatient.chapter15

import scala.annotation.varargs

object ScalaUtil {
  @varargs def sum(values: Int*) = values.sum
}
