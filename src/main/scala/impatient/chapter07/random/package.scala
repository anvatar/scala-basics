package impatient.chapter07

package object random {

  val a = 1664525
  val b = 1013904223
  val n = 32
  var previousIntVal:Int = 0
  var previousDoubleVal:Double = 0

  def nextInt() = {
    previousIntVal= (previousIntVal * a + b) % math.pow(2, n).toInt
    previousIntVal
  }

  def nextDouble() = {
    previousDoubleVal = (previousDoubleVal * a + b) % math.pow(2, n)
    previousDoubleVal
  }

  def setSeed(n:Int ) { previousIntVal = n; previousDoubleVal = n.toDouble }
}
