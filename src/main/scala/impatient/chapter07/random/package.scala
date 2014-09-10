package impatient.chapter07

package object random {
  private val a = 1664525
  private val b = 1013904223
  private val n = 32

  private var previous: BigInt = 0

  def nextInt(): Int = next().toInt

  def nextDouble(): Double = (next().toInt - Int.MinValue.toLong).toDouble / (Int.MaxValue - Int.MinValue.toLong)

  def setSeed(seed: Int): Unit = {
    previous = seed
  }

  private def next(): BigInt = {
    previous = (previous * a + b).mod(BigInt(2).pow(n))
    previous
  }
}
