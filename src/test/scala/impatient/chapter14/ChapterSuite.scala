package impatient.chapter14

import org.scalatest.FunSuite

class ChapterSuite extends FunSuite {

  //
  // 연습문제 14-1
  //

  /*
    TODO: 일단 생략
   */

  //
  // 연습문제 14-2
  //

  def tupleSwap(pair: (Int, Int)): (Int, Int) = pair match {
    case (a, b) => (b, a)
  }

  test("tupleSwap") {
    assertResult((1, 2))(tupleSwap((2, 1)))
  }

  //
  // 연습문제 14-3
  //

  val arraySwap: PartialFunction[Array[Int], Array[Int]] = {
    case Array(a, b, rest@_*) => Array(b, a) ++ rest
  }

  test("arraySwap") {
    assertResult(Array(1, 2))(arraySwap(Array(2, 1)))
    assertResult(Array(1, 2, 3))(arraySwap(Array(2, 1, 3)))
    intercept[MatchError] {
      arraySwap(Array(1))
    }
  }

  //
  // 연습문제 14-4
  //

  sealed abstract class Item

  case class Article(description: String, price: Double) extends Item

  case class Bundle(description: String, discount: Double, items: Item*) extends Item

  case class Multiple(amount: Int, item: Item) extends Item

  def price(it: Item): Double = it match {
    case Article(_, p) => p
    case Bundle(_, discount, its@_*) => its.map(price).sum - discount
    case Multiple(amount, item) => amount * price(item)
  }

  test("Item") {
    val bundle = Bundle("Father's day special", 20.0,
      Article("Scala for the Impatient", 39.95),
      Bundle("Anchor Distillery Sampler", 10.0,
        Article("Old Potrero Straight Rye Whiskey", 79.95),
        Article("Junîpero Gin", 32.95)))
    assert(price(bundle) == 39.95 + (79.95 + 32.95 - 10.0) - 20.0)

    assert(price(Multiple(7,
      Article("Scala for the Impatient", 39.95))) == 7 * 39.95)

    assert(price(Multiple(3,
      Bundle("Anchor Distillery Sampler", 10.0,
        Article("Old Potrero Straight Rye Whiskey", 79.95),
        Article("Junîpero Gin", 32.95)))) == 3 * (79.95 + 32.95 - 10.0))
  }

  //
  // 연습문제 14-5
  //

  {
    def leafSum(list: List[Any]): Int = list match {
      case Nil => 0
      case (head: Int) :: tail => head + leafSum(tail)
      case (head: List[Any]) :: tail => leafSum(head) + leafSum(tail)
      case _ => throw new MatchError(list) // 컴파일러 경고를 없애기 위해 필요
    }

    test("leafSum (exercise 14-5)") {
      val input = List(List(3, 8), 2, List(5)) // ((3 8) 2 (5))
      assert(leafSum(input) == 18)
    }
  }

  //
  // 연습문제 14-6
  //

  {
    sealed abstract class BinaryTree
    case class Leaf(value: Int) extends BinaryTree
    case class Node(left: BinaryTree, right: BinaryTree) extends BinaryTree

    def leafSum(bt: BinaryTree): Int = bt match {
      case Leaf(v) => v
      case Node(l, r) => leafSum(l) + leafSum(r)
    }

    test("leafSum (exercise 14-6)") {
      val input = Node(Node(Node(Leaf(3), Leaf(8)), Leaf(2)), Leaf(5)) // (((3 8) 2) 5)
      assert(leafSum(input) == 18)
    }
  }

  //
  // 연습문제 14-7
  //

  {
    sealed abstract class Tree
    case class Leaf(value: Int) extends Tree
    case class Node(trees: Tree*) extends Tree

    def leafSum(tree: Tree): Int = tree match {
      case Leaf(v) => v
      case Node(trees@_*) => trees.map(leafSum).sum
    }

    test("leafSum (exercise 14-7)") {
      val input = Node(Node(Leaf(3), Leaf(8)), Leaf(2), Node(Leaf(5))) // ((3 8) 2 (5))
      assert(leafSum(input) == 18)
    }
  }

  //
  // 연습문제 14-8
  //

  {
    object Op extends Enumeration {
      val +, -, * = Value
    }

    sealed abstract class Tree
    case class Leaf(value: Int) extends Tree
    case class Node(op: Op.Value, trees: Tree*) extends Tree

    def eval(tree: Tree): Int = tree match {
      case Leaf(v) => v
      case Node(Op.-, head) => -1 * eval(head)
      case Node(op, head, tail@_*) => (head +: tail).map(eval).reduceLeft(op match {
        case Op.+ => (_: Int) + (_: Int)
        case Op.- => (_: Int) - (_: Int)
        case Op.* => (_: Int) * (_: Int)
      })
    }

    test("eval") {
      val input = Node(Op.+, Node(Op.*, Leaf(3), Leaf(8)), Leaf(2), Node(Op.-, Leaf(5))) // (3 * 8) + 2 + (-5)
      assert(eval(input) == ((3 * 8) + 2 + (-5)))

      assert(eval(Node(Op.-, Leaf(3), Leaf(2), Leaf(1))) == 0) // 3 - 2 - 1
    }
  }

  //
  // 연습문제 14-9
  //


  //
  // 연습문제 14-10
  //

}
