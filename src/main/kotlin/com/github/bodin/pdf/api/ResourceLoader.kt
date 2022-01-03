package com.github.bodin.pdf.api


import java.io.File
import java.io.IOException
import java.net.URI

fun interface ResourceLoader {
    companion object {
        val Default = ResourceLoader { location: String ->
            if(location.startsWith("classpath://")) {
                TemplateSource.URL(
                    Thread.currentThread().contextClassLoader?.getResource(location.removePrefix("classpath://"))
                        ?: throw IOException("Resource $location not found on the classpath")
                )
            }else if(location.contains(Regex("(file|http|https)[:][/][/]"))){
                TemplateSource.URL(URI.create(location).toURL())
            } else {
                TemplateSource.URL(File(location).toURI().toURL())
            }
        }
    }

    fun load(location:String): TemplateSource
}