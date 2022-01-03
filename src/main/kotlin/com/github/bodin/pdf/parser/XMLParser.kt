package com.github.bodin.pdf.parser

import com.github.bodin.pdf.ast.*
import com.lowagie.text.Element
import com.lowagie.text.Font
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.xml.sax.helpers.DefaultHandler
import java.awt.Color
import java.io.InputStream
import java.util.*
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class XMLParser {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun parse(stream: InputStream): Document {
        val parserFactory:SAXParserFactory = SAXParserFactory.newInstance()
        val saxParser:SAXParser = parserFactory.newSAXParser()

        val content: Stack<Content> = Stack()

        val defaultHandler = object : DefaultHandler() {
            var builder: StringBuilder = StringBuilder()

            //overriding the startElement() method of DefaultHandler
            override fun startElement(uri: String, localName: String, qName: String, attributes: org.xml.sax.Attributes) {
                when (qName) {
                    "document" -> content.push(Document())
                    "page" -> content.push(Page())
                    "table" -> content.push(Table())
                    "row" -> content.push(Row())
                    "text" -> content.push(TextCell())
                    "blank" -> content.push(BlankCell())
                    "image" -> content.push(ImageCell())
                    else -> println("ERROR: Unknown Element $qName")
                }
                copyAttributes(content.peek(), attributes)
            }

            //overriding the endElement() method of DefaultHandler
            override fun endElement(uri: String, localName: String, qName: String) {
                val top : Content = content.pop()
                when (qName) {
                    "document" -> content.push(top)
                    "page" -> (content.peek() as NestedContent).content.add(top)
                    "table" -> (content.peek() as NestedContent).content.add(top)
                    "row" -> (content.peek() as NestedContent).content.add(top)
                    "image", "text", "blank" -> {
                        (top as ContentCell).content = builder.toString().trim()
                        (content.peek() as NestedContent).content.add(top)
                    }
                }
                builder.clear()
            }

            override fun characters(ch: CharArray, start: Int, length: Int) {
                if(content.peek() is ContentCell) {
                    builder.append(ch, start, length)
                }
            }

            fun copyAttributes(c: Content, attributes: org.xml.sax.Attributes) {
                for (i in 0..attributes.length) {
                    val name = attributes.getLocalName(i)
                    val value = attributes.getValue(i);
                    when (name) {
                        "layout" -> if (c is Table) c.layout = layout(value)
                        "fontName" -> c.fontName = value
                        "fontSize" -> c.fontSize = value.toFloat()
                        "fontStyle" -> when(value){
                            "bold" -> c.fontStyle = Font.BOLD
                            "strikethrough" -> c.fontStyle = Font.STRIKETHRU
                            "italic" -> c.fontStyle = Font.ITALIC
                            "underline" -> c.fontStyle = Font.UNDERLINE
                        }
                        "fontColor" -> c.fontColor = color(value)
                        "backgroundColor" -> c.backgroundColor = color(value)
                        "paddingTop" -> c.paddingTop = value.toFloat()
                        "paddingBottom" -> c.paddingBottom = value.toFloat()
                        "paddingLeft" -> c.paddingLeft = value.toFloat()
                        "paddingRight" -> c.paddingRight = value.toFloat()
                        "padding" -> {
                            c.paddingTop = value.toFloat()
                            c.paddingBottom = value.toFloat()
                            c.paddingLeft = value.toFloat()
                            c.paddingRight = value.toFloat()
                        }
                        "alignV" -> when(value){
                            "top" -> c.alignV = Element.ALIGN_TOP
                            "middle" -> c.alignV = Element.ALIGN_MIDDLE
                            "bottom" -> c.alignV = Element.ALIGN_BOTTOM
                        }
                        "alignH" -> when(value){
                            "left" -> c.alignH = Element.ALIGN_LEFT
                            "center" -> c.alignH = Element.ALIGN_CENTER
                            "right" -> c.alignH = Element.ALIGN_RIGHT
                        }
                    }
                }
            }
        }

        saxParser.parse(stream, defaultHandler)
        return content.pop() as Document
    }
    private fun color(s : String): Color? {
        if(s.startsWith("#")){
            return Color.decode(s);
        }else {
            return try {
                Class.forName("java.awt.Color")?.getField(s)?.get(null) as Color
            } catch (e: Exception) {
                log.error(e.message, e)
                return null
            }
        }
    }
    private fun layout(s: String): FloatArray? {
        return s
            .split(Regex("[ ,|]"))
            .toTypedArray()
            .map { it.toFloat() }
            .toFloatArray()
    }
}