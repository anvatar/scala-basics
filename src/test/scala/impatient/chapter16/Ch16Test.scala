package si.cp16

import org.scalatest.FlatSpec
import org.scalatest.FunSuite
import scala.xml.Text
import java.lang.Class.Atomic
import scala.collection.mutable.ArrayBuffer

class Ch16Test extends FunSuite {

  test("1. <fred/>(0)") {
    
    val a = <fred/>
    assert( a == <fred/>)
    assert( a(0) == <fred/>)  
    assert( a(0)(0) == <fred/>)
    assert( !(a eq <fred/>))
    assert( a(0) eq a)  
    assert( a(0)(0) eq a)
    assert( a.getClass().getName() == "scala.xml.Elem")
    
    println( "* print a")
    for (n <- a) println( n)
    
    val b = <like>this</like>
    assert( b == <like>this</like>)
    assert( b(0) == <like>this</like>)
    assert( b.getClass().getName()  == "scala.xml.Elem" )
    
    val c = <likes><like><special>a</special></like><like>b</like></likes>
    println( "* print c")
    for (n <- c.child) println( n)
  }
  
  test("2. brakets") {
    /*
    val a = <ul>
    			<li>Opening bracket: [</li>
    			<li>Closing bracket: ]</li>
    			<li>Opening brace: {</li>
    			<li>Closing brace: }</li>
    		</ul>
    */
    val a = <ul>
    			<li>Opening brace: {{ </li>
    			<li>Closing brace: }} </li>
    		</ul>
      
    val b = <ul>
    			<li>{Text("Opening brace: { ")}</li>
    			<li>{Text("Closing brace: } ")}</li>
    		</ul>
    assert( a == b)
    
    for ( c <- a.child) 
      println( c.child)
      
    val c = <ul>
    			<li>{ArrayBuffer("Opening brace: { ")} </li>
    			<li>{ArrayBuffer("Closing brace: } ")} </li>
    		</ul>
    assert( c != b)
      
  }
  
}