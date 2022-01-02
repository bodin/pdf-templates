package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext
import java.awt.Color

abstract class Content {
    //Preferences
    var fontSize: Float = 0f
    //var alignH: HorizontalAlignment? = null
    //var alignV: VerticalAlignment? = null
    var colorText: Color? = null
    var colorFill: Color? = null

    abstract fun draw(ctx: DrawContext)

    open fun cells(): Int {
        return 1
    }

    fun copyTo(c: Content){
        if(c.fontSize == 0f) c.fontSize = this.fontSize
        //if(c.alignH == null) c.alignH = this.alignH
        //if(c.alignV == null) c.alignV = this.alignV
        if(c.colorFill == null) c.colorFill = this.colorFill
        if(c.colorText == null) c.colorText = this.colorText
    }
}