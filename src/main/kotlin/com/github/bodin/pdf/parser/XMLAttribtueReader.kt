package com.github.bodin.pdf.parser

import com.github.bodin.pdf.ast.Node
import com.lowagie.text.Element
import com.lowagie.text.Font
import com.lowagie.text.Rectangle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.util.*

class XMLAttribtueReader {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)
    fun apply(c: Node, attributes: org.xml.sax.Attributes) {
        val a = c.attributes
        for (i in 0..attributes.length) {
            val name = attributes.getLocalName(i)
            val value = attributes.getValue(i);
            when (name) {
                "pageSize", "page-size" -> a.pageSize = pageSize(value)
                "layout" -> a.layout = layout(value)
                "bookmark" -> a.bookmark = value
                "fontName", "font-name" -> a.fontName = value
                "fontSize", "font-size" -> a.fontSize = value.toFloatOrNull()
                "fontStyle", "font-style" -> a.fontStyle = fontStyle(value)
                "fontColor", "font-color" -> color(value)?.let { a.fontColor = it }
                "backgroundColor", "background-color" ->  color(value)?.let { a.backgroundColor = it }
                "marginTop", "margin-top" -> a.marginTop = value.toFloatOrNull()
                "marginBottom", "margin-bottom"  -> a.marginBottom = value.toFloatOrNull()
                "marginLeft", "margin-left"  -> a.marginLeft = value.toFloatOrNull()
                "marginRight", "margin-right"  -> a.marginRight = value.toFloatOrNull()
                "margin" -> {
                    a.marginTop = value.toFloatOrNull()
                    a.marginBottom = value.toFloatOrNull()
                    a.marginLeft = value.toFloatOrNull()
                    a.marginRight = value.toFloatOrNull()
                }

                "paddingTop", "padding-top" -> a.paddingTop = value.toFloatOrNull()
                "paddingBottom", "padding-bottom"  -> a.paddingBottom = value.toFloatOrNull()
                "paddingLeft", "padding-left"  -> a.paddingLeft = value.toFloatOrNull()
                "paddingRight", "padding-right"  -> a.paddingRight = value.toFloatOrNull()
                "padding" -> {
                    a.paddingTop = value.toFloatOrNull()
                    a.paddingBottom = value.toFloatOrNull()
                    a.paddingLeft = value.toFloatOrNull()
                    a.paddingRight = value.toFloatOrNull()
                }
                "alignVertical", "align-vertical" -> when(value){
                    "top" -> a.alignV = Element.ALIGN_TOP
                    "middle" -> a.alignV = Element.ALIGN_MIDDLE
                    "bottom" -> a.alignV = Element.ALIGN_BOTTOM
                }
                "alignHorizontal", "align-horizontal"-> when(value){
                    "left" -> a.alignH = Element.ALIGN_LEFT
                    "center" -> a.alignH = Element.ALIGN_CENTER
                    "right" -> a.alignH = Element.ALIGN_RIGHT
                }
                "borderTop", "border-top" -> border(value) { cl: Color?, s:Float? -> a.borderColorTop = cl; a.borderWidthTop = s}
                "borderBottom", "border-bottom" -> border(value) { cl: Color?, s:Float? -> a.borderColorBottom = cl; a.borderWidthBottom = s}
                "borderLeft", "border-left" -> border(value) { cl: Color?, s:Float? -> a.borderColorLeft = cl; a.borderWidthLeft = s}
                "borderRight", "border-right" -> border(value) { cl: Color?, s:Float? -> a.borderColorRight = cl; a.borderWidthRight = s}
                "border" -> border(value) { cl: Color?, s:Float? ->
                    a.borderColorTop = cl; a.borderWidthTop = s
                    a.borderColorBottom = cl; a.borderWidthBottom = s
                    a.borderColorLeft = cl; a.borderWidthLeft = s
                    a.borderColorRight = cl; a.borderWidthRight = s
                }
                "width" -> a.width = value.toFloatOrNull()
                "height" -> a.height = value.toFloatOrNull()
                "colspan" -> a.colspan = value.toIntOrNull()
                "rowspan" -> a.rowspan = value.toIntOrNull()
            }
        }
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
            Class.forName("com.lowagie.text.PageSize")?.getField(value.uppercase(Locale.getDefault()))?.get(null) as Rectangle
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