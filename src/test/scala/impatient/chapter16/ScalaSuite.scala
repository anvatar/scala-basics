package impatient.chapter16

import org.scalatest.FunSuite

import scala.xml._
import scala.xml.dtd.DocType
import scala.xml.parsing.XhtmlParser
import scala.xml.transform.{RuleTransformer, RewriteRule}

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

    /* 결국 <fred/>, <fred/>(0), <fred/>(0)(0), <fred/>(0)(0)(0), ... 이 모두 같다. */
    assert(<fred/> == <fred/>(0))
    assert(<fred/> == <fred/>(0)(0))
    assert(<fred/> == <fred/>(0)(0)(0))

    /* https://github.com/Sungjick 가 작성한 https://github.com/anvatar/scala-basics/pull/23/files#diff-1 를 참고하여
       아래 테스트를 추가함 */
    val fred = <fred/>

    assert(fred == <fred/>)
    assert(fred(0) == <fred/>)
    assert(fred(0)(0) == <fred/>)

    assert(!(fred eq <fred/>))

    /* 모두 같은 객체 */
    assert(fred(0) eq fred)
    assert(fred(0)(0) eq fred)

    assert(fred.getClass.getName == "scala.xml.Elem")
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

  //
  // 연습문제 16-6
  //

  test("hyperlinks") {
    val doc: Document = xhtmlDocumentFromReosurcePath("impatient/www_w3c_org.html")

    val hyperlinks = for (a <- doc \\ "a") yield
      (a.text.split( """\s+""").mkString(" ").trim, a.attribute("href").getOrElse(Null).toString())
    assert(hyperlinks.nonEmpty)

    val maxWidths = (hyperlinks.map(_._1.length).max, hyperlinks.map(_._2.length).max)
    hyperlinks.foreach { hl =>
      print("| ")
      print(hl._1 + " " * (maxWidths._1 - hl._1.length))
      print(" | ")
      print(hl._2 + " " * (maxWidths._2 - hl._2.length))
      println(" |")
    }
  }

  //
  // 연습문제 16-7
  //

  def definitionList(definitions: Map[String, String]): NodeSeq =
    <dl>{for ((k, v) <- definitions) yield <dt>{Text(k)}</dt><dd>{Text(v)}</dd>}</dl>

  test("definitionList") {
    assert(definitionList(Map("A" -> "1", "B" -> "2")) match {
      case <dl><dt>A</dt><dd>1</dd><dt>B</dt><dd>2</dd></dl> => true
    })
  }

  //
  // 연습문제 16-8
  //

  def definitionMap(nodeSeq: NodeSeq): Map[String, String] = nodeSeq match {
    case <dl>{children@_*}</dl> => children.grouped(2).map({
      case Seq(Elem(_, "dt", _, _, Text(t)), Elem(_, "dd", _, _, Text(d))) => t -> d
    }).toMap
  }

  test("definitionMap") {
    assert(definitionMap(<dl><dt>A</dt><dd>1</dd><dt>B</dt><dd>2</dd></dl>) === Map("A" -> "1", "B" -> "2"))
  }

  //
  // 연습문제 16-9 ~ 16-10
  //

  def transform(inputResourcePath: String, outputFilePath: String, rewriteRules: RewriteRule*): Unit = {
    val doc: Document = xhtmlDocumentFromReosurcePath(inputResourcePath)

    val convertedRoot = new RuleTransformer(rewriteRules: _*).transform(doc.docElem)(0)
    val docType = new DocType(convertedRoot.label, doc.dtd.externalID, doc.dtd.decls)

    XML.save(outputFilePath, convertedRoot, "UTF-8", doctype = docType)
  }

  test("alt=\"TODO\"") {
    val inputResourcePath = "impatient/www_w3c_org.html"
    val outputFilePath = "/tmp/www_w3c_org.html"

    val originalDoc = xhtmlDocumentFromReosurcePath(inputResourcePath)
    assert((originalDoc \\ "img").count(_.attribute("alt").isEmpty) === 1)
    assert((originalDoc \\ "img" \\ "@alt").count(_.text == "TODO") === 0)

    transform(inputResourcePath, outputFilePath, new RewriteRule {
      override def transform(n: Node): Seq[Node] = n match {
        case e@ <img/> if e.attribute("alt").isEmpty =>
          e.asInstanceOf[Elem] % Attribute(null, "alt", "TODO", Null)
        case e@ <img>_*</img> if e.attribute("alt").isEmpty =>
          e.asInstanceOf[Elem] % Attribute(null, "alt", "TODO", Null)
        case _ => n
      }
    })

    val convertedDoc: Document = new XhtmlParser(scala.io.Source.fromFile(outputFilePath)).initialize.document()
    assert((convertedDoc \\ "img").count(_.attribute("alt").isEmpty) === 0)
    assert((convertedDoc \\ "img" \\ "@alt").count(_.text == "TODO") === 1)
  }
}
