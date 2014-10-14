package impatient.chapter15

import java.io.IOException

import scala.annotation.varargs
import scala.io.Source

object ScalaUtil {
  @varargs def sum(values: Int*) = values.sum

  @throws(classOf[IOException]) def readAll(filePath: String) = Source.fromFile(filePath).mkString

  def allDifferent[@specialized T](x: T, y: T, z: T) = x != y && x != z && y != z
}
