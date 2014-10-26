package impatient.chapter16

import org.scalatest.FunSuite

import scala.xml._
import scala.xml.parsing.XhtmlParser

class ScalaSuite extends FunSuite {

  //
  // 연습문제 16-1
  //

  test("<fred/>") {
    /*
      <fred/>는 NodeSeq이고, <fred/>에 해당하는 Elem 하나만 포함하고 있다.
     */
    assert(<fred/>(0) === Elem(null, "fred", Null, TopScope, minimizeEmpty = true))

    /*
      Elem은 NodeSeq를 확장한다. Elem#apply(Int)는 NodeSeq에서 왔다.
     */
    assert(<fred/>(0)(0) === Elem(null, "fred", Null, TopScope, minimizeEmpty = true))
  }

  //
  // 연습문제 16-2
  //

  test("brackets & braces") {
    /*
        <ul>
          <li>Opening bracket: [</li>
          <li>Closing bracket: ]</li>
          <li>Opening brace: {</li>
          <li>Closing brace: }</li>
        </ul>

      스칼라 코드 블럭을 표시하는 {, }는 {{, }}와 같이 escape 처리 해야한다.
    */

    val corrected = <ul>
      <li>Opening bracket: [</li>
      <li>Closing bracket: ]</li>
      <li>Opening brace: {{</li>
      <li>Closing brace: }}</li>
    </ul>

    assert(corrected.label === "ul")
    assert(corrected.child.length === 9)
    assert(corrected.child.zipWithIndex.filter({ case (_, index) => index % 2 == 1}).map(_._1.text) ===
      Array("Opening bracket: [", "Closing bracket: ]", "Opening brace: {", "Closing brace: }")
    )
  }

  //
  // 연습문제 16-3
  //

  def fredMatch(nodeSeq: NodeSeq): String = nodeSeq match {
    case <li>{Text(t)}</li> => t
  }

  test("<li>Fred</li> vs <li>{\"Fred\"}</li>") {
    val fred1 = <li>Fred</li>
    val fred2 = <li>{"Fred"}</li>

    assert(fred1 === Elem(null, "li", Null, TopScope, false, Text("Fred")))
    assert(fredMatch(fred1) === "Fred")

    assert(fred2 === Elem(null, "li", Null, TopScope, false, new Atom("Fred")))
    intercept[MatchError] {
      fredMatch(fred2) === "Fred"
    }
  }

  //
  // 연습문제 16-4
  //

  def xhtmlDocumentFromReosurcePath(resourcePath: String): Document = {
    val parser = new XhtmlParser(scala.io.Source.fromURL(ClassLoader.getSystemClassLoader.getResource(resourcePath)))
    parser.initialize.document()
  }

  test("img without @alt") {
    val doc: Document = xhtmlDocumentFromReosurcePath("impatient/www_w3c_org.html")

    val images = for (img <- doc \\ "img" if img.attribute("alt").isEmpty) yield img
    assert(images.nonEmpty)

    images.foreach(image => println("img without @alt: " + image))
  }

  //
  // 연습문제 16-5
  //

  test("img@src") {
    val doc: Document = xhtmlDocumentFromReosurcePath("impatient/www_w3c_org.html")

    val imageNames = doc \\ "img" \\ "@src"
    assert(imageNames.nonEmpty)

    imageNames.foreach(imgSrc => println("img@src: " + imgSrc))
  }

}
