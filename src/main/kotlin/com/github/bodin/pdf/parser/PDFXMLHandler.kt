package com.github.bodin.pdf.parser

import com.github.bodin.pdf.ast.InteriorNode
import com.github.bodin.pdf.ast.LeafNode
import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.leaf.BlankLeaf
import com.github.bodin.pdf.ast.leaf.ImageLeaf
import com.github.bodin.pdf.ast.leaf.TextLeaf
import com.github.bodin.pdf.ast.node.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.xml.sax.helpers.DefaultHandler
import java.util.*

class PDFXMLHandler(private val attribtueParser : XMLAttribtueReader = XMLAttribtueReader()): DefaultHandler() {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)
    var builder: StringBuilder = StringBuilder()
    val content: Stack<Node> = Stack()
    init {
        //so the stack is never empty
        content.push(DocumentNode())
    }
    //overriding the startElement() method of DefaultHandler
    override fun startElement(uri: String, localName: String, qName: String, attributes: org.xml.sax.Attributes) {
        val peek = content.peek();
        when (qName) {
            "document" -> content.push(DocumentNode())
            "page" -> content.push(PageNode(peek))
            "table" -> content.push(TableNode(peek))
            "row" -> content.push(RowNode(peek))
            "blank" -> content.push(BlankLeaf(peek))
            "image" -> content.push(ImageLeaf(peek))
            "text" -> content.push(TextNode(peek))
            "f" -> {
                text(peek)
                content.push(FormatNode(peek))
            }
            else -> log.error("ERROR: Unknown Element $qName")
        }
        attribtueParser.apply(peek, attributes)
    }

    override fun endElement(uri: String, localName: String, qName: String) {
        val top : Node = content.pop()
        val peek = content.peek()
        when (qName) {
            "document" -> content.push(top)
            "page" -> add(peek, top)
            "table" -> add(peek, top)
            "row" -> add(peek, top)
            "image", "blank" -> {
                if(top is LeafNode) {
                    top.content = builder.toString().trim()
                    add(peek, top)
                }
            }
            "text", "f" -> {
                text(top)
                add(peek, top)
            }
        }
        builder.clear()
    }

    fun add(top: Node, child: Node){
        if(top is InteriorNode<*>) {
            //this is safe since the type bound of the class is Node ... types are just lost at runtime
            @Suppress("UNCHECKED_CAST")
            val typedTop = top as InteriorNode<Node>
            typedTop.content.add(child)
        }else{
            log.error("Setting child on non-interior node: $top")
        }
    }

    fun text(top: Node){
        if(top is InteriorNode<*>) {
            //this is safe since the type bound of the class is Node ... types are just lost at runtime
            @Suppress("UNCHECKED_CAST")
            val typedTop = top as InteriorNode<Node>
            val str = builder.toString().trim()
            builder.clear()
            if (str.isNotEmpty()) {
                typedTop.content.add(TextLeaf(top, str))
            }
        }else{
            log.error("Setting text on non-interior node: $top")
        }
    }

    override fun characters(ch: CharArray, start: Int, length: Int) {
        when(content.peek()){
            is TextLeaf, is ImageLeaf, is TextNode, is FormatNode -> {
                builder.append(ch, start, length)
            }
        }
    }
}