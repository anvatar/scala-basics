package impatient.chapter15

import java.io.IOException

import scala.annotation.varargs
import scala.io.Source

object ScalaUtil {
  @varargs def sum(values: Int*) = values.sum

  @throws(classOf[IOException]) def readAll(filePath: String) = Source.fromFile(filePath).mkString
}
