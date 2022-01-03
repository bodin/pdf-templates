package com.github.bodin.pdf.api

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.URL

fun interface TemplateSource {
    companion object {
        fun URL(url:URL): TemplateSource {
            return TemplateSource{ url.openStream() }
        }
        fun Inline(template: String): TemplateSource {
            return TemplateSource{ ByteArrayInputStream(template.toByteArray()) }
        }
        fun Inline(template: ByteArray): TemplateSource {
            return TemplateSource{ ByteArrayInputStream(template) }
        }
    }
    fun inputStream(): InputStream
}