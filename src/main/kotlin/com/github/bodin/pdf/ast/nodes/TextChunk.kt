package com.github.bodin.pdf.ast.nodes

import com.github.bodin.pdf.ast.CharacterAware
import com.github.bodin.pdf.ast.Content
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.NestedContent
import com.lowagie.text.Chunk

class TextChunk(var text: String = "", content: MutableList<TextChunk> = mutableListOf())
    : NestedContent(content as MutableList<Content>), CharacterAware {
    override fun draw(ctx: DrawContext) {
        if(content.isEmpty()){

            if(ctx.paragraph?.size?:0 > 0) this.text = " " + this.text

            val ch = Chunk(this.text, ctx.getFont(this))

            ctx.paragraph?.add(ch)

        }else{
            this.drawChildren(ctx)
        }
    }
}