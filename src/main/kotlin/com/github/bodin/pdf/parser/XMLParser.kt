package com.github.bodin.pdf.parser

import com.github.bodin.pdf.ast.node.*
import java.io.InputStream
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class XMLParser {
    fun parse(stream: InputStream): DocumentNode {
        val parserFactory:SAXParserFactory = SAXParserFactory.newInstance()
        val saxParser:SAXParser = parserFactory.newSAXParser()

        val handler = PDFXMLHandler()
        saxParser.parse(stream, handler)
        return handler.content.pop() as DocumentNode
    }
}