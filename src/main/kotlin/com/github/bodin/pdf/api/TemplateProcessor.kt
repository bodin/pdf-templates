package com.github.bodin.pdf.api

import java.io.InputStream

fun interface TemplateProcessor {
    companion object {
        val NoOp = TemplateProcessor { _: Any?, ins: InputStream -> ins }
    }

    fun process(ctx: Any?, ins : InputStream): InputStream
}