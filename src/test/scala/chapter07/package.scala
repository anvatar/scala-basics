/**
 * Created by parkhoyong on 2014. 9. 4..
 */
package object random {
  var seed: Int = 0

  def nextInt(): Int = {
    seed = seed * 1664525 + 1013904223 % (2 ^ 32)
    seed
  }

  def nextDouble(): Double = {
    seed = seed * 1664525 + 1013904223 % (2 ^ 32)
    seed
  }

  def setSeed(seed: Int) = this.seed = seed
}
