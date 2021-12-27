package net.webspite.pdf.parser

import net.webspite.pdf.ast.*
import org.xml.sax.helpers.DefaultHandler
import java.io.InputStream
import java.util.*
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class XMLParser {

    fun parse(stream: InputStream): Content<*>{
        val parserFactory:SAXParserFactory = SAXParserFactory.newInstance()
        val saxParser:SAXParser = parserFactory.newSAXParser()

        var content: Stack<Content<*>> = Stack()

        val defaultHandler = object : DefaultHandler() {
            var builder: StringBuilder = StringBuilder()

            //overriding the startElement() method of DefaultHandler
            override fun startElement(uri: String, localName: String, qName: String, attributes: org.xml.sax.Attributes) {
                when (qName) {
                    "document" -> content.push(Document())
                    "page" -> content.push(Page())
                    "table" -> content.push(Table())
                    "row" -> content.push(Row())
                    "column" -> content.push(Cell())
                    else -> println("ERROR: Unknown Element $qName")
                }
            }
            //overriding the endElement() method of DefaultHandler
            override fun endElement(uri: String, localName: String, qName: String) {
                val top : Content<*> = content.pop();
                when (qName) {
                    "document" -> content.push(top);
                    "page" -> (content.peek() as Document).content?.add(top as Page)
                    "table" -> (content.peek() as Page).content?.add(top as Table)
                    "row" -> (content.peek() as Table).content?.add(top as Row)
                    "column" -> (content.peek() as Row).content?.add(Cell(mutableListOf(StringContent(builder.toString().trim()))))
                }
                builder.clear()
            }

            override fun characters(ch: CharArray, start: Int, length: Int) {
                builder.append(ch, start, length)
            }
        }

        saxParser.parse(stream, defaultHandler)
        return content.pop()
    }
}