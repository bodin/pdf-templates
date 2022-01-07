package com.github.bodin.pdf.parser

import com.github.bodin.pdf.ast.*
import com.github.bodin.pdf.ast.nodes.*
import com.lowagie.text.Element
import com.lowagie.text.Font
import com.lowagie.text.PageSize
import com.lowagie.text.Rectangle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.xml.sax.helpers.DefaultHandler
import java.awt.Color
import java.io.InputStream
import java.util.*
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class XMLParser {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

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
                    "blank" -> content.push(BlankCell())
                    "image" -> content.push(ImageCell())
                    "text" -> content.push(TextCell())
                    "f" -> {
                        text(content.peek() as NestedContent)
                        content.push(TextChunk())
                    }
                    else -> log.error("ERROR: Unknown Element $qName")
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
                    "image", "blank" -> {
                        (top as ContentCell).content = builder.toString().trim()
                        (content.peek() as NestedContent).content.add(top)
                    }
                    "text", "f" -> {
                        text(top as NestedContent)
                        (content.peek() as NestedContent).content.add(top);
                    }
                }
                builder.clear()
            }
            fun text(top: NestedContent){
                val str = builder.toString().trim()
                builder.clear()
                if(str.isNotEmpty()){
                    top.content.add(TextChunk(str))
                }
            }
            override fun characters(ch: CharArray, start: Int, length: Int) {
                if(content.peek() is CharacterAware) {
                    builder.append(ch, start, length)
                }
            }

            fun copyAttributes(c: Content, attributes: org.xml.sax.Attributes) {
                for (i in 0..attributes.length) {
                    val name = attributes.getLocalName(i)
                    val value = attributes.getValue(i);
                    when (name) {
                        "pageSize", "page-size" -> if(c is Document) c.pageSize = pageSize(value)
                        "layout" -> if (c is Table) c.layout = layout(value)
                        "bookmark" -> c.bookmark = value
                        "fontName", "font-name" -> c.fontName = value
                        "fontSize", "font-size" -> c.fontSize = value.toFloatOrNull()
                        "fontStyle", "font-style" -> c.fontStyle = fontStyle(value)
                        "fontColor", "font-color" -> color(value)?.let { c.fontColor = it }
                        "backgroundColor", "background-color" ->  color(value)?.let { c.backgroundColor = it }
                        "marginTop", "margin-top" -> c.marginTop = value.toFloatOrNull()
                        "marginBottom", "margin-bottom"  -> c.marginBottom = value.toFloatOrNull()
                        "marginLeft", "margin-left"  -> c.marginLeft = value.toFloatOrNull()
                        "marginRight", "margin-right"  -> c.marginRight = value.toFloatOrNull()
                        "margin" -> {
                            c.marginTop = value.toFloatOrNull()
                            c.marginBottom = value.toFloatOrNull()
                            c.marginLeft = value.toFloatOrNull()
                            c.marginRight = value.toFloatOrNull()
                        }

                        "paddingTop", "padding-top" -> c.paddingTop = value.toFloatOrNull()
                        "paddingBottom", "padding-bottom"  -> c.paddingBottom = value.toFloatOrNull()
                        "paddingLeft", "padding-left"  -> c.paddingLeft = value.toFloatOrNull()
                        "paddingRight", "padding-right"  -> c.paddingRight = value.toFloatOrNull()
                        "padding" -> {
                            c.paddingTop = value.toFloatOrNull()
                            c.paddingBottom = value.toFloatOrNull()
                            c.paddingLeft = value.toFloatOrNull()
                            c.paddingRight = value.toFloatOrNull()
                        }
                        "alignVertical", "align-vertical" -> when(value){
                            "top" -> c.alignV = Element.ALIGN_TOP
                            "middle" -> c.alignV = Element.ALIGN_MIDDLE
                            "bottom" -> c.alignV = Element.ALIGN_BOTTOM
                        }
                        "alignHorizontal", "align-horizontal"-> when(value){
                            "left" -> c.alignH = Element.ALIGN_LEFT
                            "center" -> c.alignH = Element.ALIGN_CENTER
                            "right" -> c.alignH = Element.ALIGN_RIGHT
                        }
                        "borderTop", "border-top" -> border(value) {cl:Color?, s:Float? -> c.borderColorTop = cl; c.borderWidthTop = s}
                        "borderBottom", "border-bottom" -> border(value) {cl:Color?, s:Float? -> c.borderColorBottom = cl; c.borderWidthBottom = s}
                        "borderLeft", "border-left" -> border(value) {cl:Color?, s:Float? -> c.borderColorLeft = cl; c.borderWidthLeft = s}
                        "borderRight", "border-right" -> border(value) {cl:Color?, s:Float? -> c.borderColorRight = cl; c.borderWidthRight = s}
                        "border" -> border(value) {cl:Color?, s:Float? ->
                            c.borderColorTop = cl; c.borderWidthTop = s
                            c.borderColorBottom = cl; c.borderWidthBottom = s
                            c.borderColorLeft = cl; c.borderWidthLeft = s
                            c.borderColorRight = cl; c.borderWidthRight = s
                        }
                        "width" -> c.width = value.toFloatOrNull()
                        "height" -> c.height = value.toFloatOrNull()
                        "colspan" -> c.colspan = value.toIntOrNull()
                    }
                }
            }
        }

        saxParser.parse(stream, defaultHandler)
        return content.pop() as Document
    }

    private fun fontStyle(value:String): Int? {
        var s: Int = Font.NORMAL

        value.split(Regex("[ ,|]")).forEach{
            when(it) {
                "bold" -> s = s or Font.BOLD
                "strikethrough" -> s = s or Font.STRIKETHRU
                "italic" -> s = s or Font.ITALIC
                "underline" -> s = s or Font.UNDERLINE
                "normal" -> s = Font.NORMAL
            }
        }
        return s
    }

    private fun border(value:String, f: (Color?, Float?) -> Unit) {
        var c: Color? = null
        var s: Float? = null

        value.split(Regex("[ ,|]")).forEach{
            var tmpC = color(it)
            var tmpS = it.toFloatOrNull()
            if(c == null) c = tmpC
            if(s == null) s = tmpS
        }
        f.invoke(c, s)
    }
    private fun pageSize(value : String): Rectangle? {
        val vals = value.split(Regex("[ ,|]"))
        if(vals.size == 2){
            val w = vals[0].toFloatOrNull()
            val h = vals[1].toFloatOrNull()
            if(w != null && h != null) return Rectangle(w, h)
        }
        return try {
            Class.forName("com.lowagie.text.PageSize")?.getField(value.toUpperCase())?.get(null) as Rectangle
        } catch (e: Exception) {
            log.error(e.message, e)
            return null
        }
    }

    private fun color(value : String): Color? {
        if(value.startsWith("#")){
            return Color.decode(value);
        }else {
            return try {
                Class.forName("java.awt.Color")?.getField(value)?.get(null) as Color
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
            .mapNotNull { it.toFloatOrNull() }
            .toFloatArray()
    }
}