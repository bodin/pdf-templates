package net.webspite.pdf.parser

import net.webspite.pdf.ast.*
import org.xml.sax.helpers.DefaultHandler
import java.awt.Color
import java.io.InputStream
import java.util.*
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class XMLParser {

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
                    "table" -> {
                        if(content.peek() is Row) {
                            content.push(TableCell())
                        }else{
                            content.push(Table())
                        }
                    }
                    "row" -> content.push(Row())
                    "text" -> content.push(TextCell())
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
                    "image", "text" -> {
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

            fun copyAttributes(c: Content, attributes: org.xml.sax.Attributes){
                if (attributes.getValue("fontSize") != null) {
                    c.fontSize = attributes.getValue("fontSize").toFloat()
                }
                when(attributes.getValue("alignH")){
                    //"left" -> c.alignH = HorizontalAlignment.LEFT
                    //"right" -> c.alignH = HorizontalAlignment.RIGHT
                    //"center" -> c.alignH = HorizontalAlignment.CENTER
                }
                when(attributes.getValue("alignV")){
                    //"bottom" -> c.alignV = VerticalAlignment.BOTTOM
                    //"top" -> c.alignV = VerticalAlignment.TOP
                    //"middle" -> c.alignV = VerticalAlignment.MIDDLE
                }

                var color = attributes.getValue("colorFill")
                try {
                    c.colorFill = Class.forName("java.awt.Color")?.getField(color)?.get(null) as Color
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                color = attributes.getValue("colorText")
                try {
                    c.colorText = Class.forName("java.awt.Color")?.getField(color)?.get(null) as Color
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        saxParser.parse(stream, defaultHandler)
        return content.pop() as Document
    }
}