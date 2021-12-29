package net.webspite.pdf.ast

import be.quodlibet.boxable.HorizontalAlignment
import be.quodlibet.boxable.VerticalAlignment
import net.webspite.pdf.model.DrawContext
import java.awt.Color

abstract class Content {
    var x: Float = -1f
    var y: Float = -1f
    var widthPct: Float = 0f
    var widthPt: Float = 0f

    //Preferences
    var fontSize: Float = 0f
    var alignH: HorizontalAlignment? = null
    var alignV: VerticalAlignment? = null
    var colorText: Color? = null
    var colorFill: Color? = null

    abstract fun draw(ctx: DrawContext): Float
    abstract fun calculate(ctx: DrawContext);
    override fun toString(): String {
        return "${this.javaClass.name}"
    }

    fun copyTo(c: Content){
        if(c.fontSize == 0f) c.fontSize = this.fontSize
        if(c.alignH == null) c.alignH = this.alignH
        if(c.alignV == null) c.alignV = this.alignV
        if(c.colorFill == null) c.colorFill = this.colorFill
        if(c.colorText == null) c.colorText = this.colorText
    }
}