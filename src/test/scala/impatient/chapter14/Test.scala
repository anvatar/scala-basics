package si.cp14

import org.scalatest.FlatSpec
import org.scalatest.FunSuite

class TestTest extends FunSuite {
  /*
   * No. 5
   */
  def leafSum_no5(lst: List[Any]): Int = lst match {
    case Nil => 0
    case h :: t => h match {
      case i: Int => i + leafSum_no5( t)
      case l: List[Any] => leafSum_no5(l) + leafSum_no5(t)
    }
  }

  test("List Tree") {
	var l2:List[Any] = List[Any]()
	l2 = l2 :+ List(3, 8)
	l2 = l2 :+ 2
    l2 = l2 :+ List(5)
    l2.foreach( println)
    
    assert(leafSum_no5(l2) == 18)
  }
	
  /*
   * No.6
   */
  sealed abstract class BinaryTree
  case class Leaf(value: Int) extends BinaryTree
  case class Node(left: BinaryTree, right: BinaryTree) extends BinaryTree
  
  def leafSum_no6(bt: BinaryTree): Int = bt match {
    case null => 0
    case l: Leaf => l.value
    case n: Node => leafSum_no6(n.left) + leafSum_no6(n.right)
  }
  
  test("Binary Tree") {
    val bt = Node( Node(Leaf(3), Leaf(8)), Node(Leaf(2), Leaf(5)))
    assert(leafSum_no6(bt) == 18)
  }
  
  /*
   * No.7
   */
  sealed abstract class BinaryTree7
  case class Leaf7(value: Int) extends BinaryTree7
  case class Node7(children: BinaryTree7*) extends BinaryTree7
  
  
  val f7 = (x:Int, y:BinaryTree7) => x +  leafSum_no7(y)
  
  def leafSum_no7(bt: BinaryTree7): Int = bt match {
    case null => 0
    case l: Leaf7 => l.value
    case n: Node7 => (0 /: n.children )(f7)
  }
  
  test("Binary Tree multiple children") {
    val bt = Node7( Node7(Leaf7(3), Leaf7(8)), Leaf7(2), Node7( Leaf7(5)))
    assert(leafSum_no7(bt) == 18)
  }
  
  /*
   * No.8
   */
}