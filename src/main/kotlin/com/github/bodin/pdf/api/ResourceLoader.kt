package com.github.bodin.pdf.api


import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URL

fun interface ResourceLoader {
    companion object {
        val Default = ResourceLoader { location: String ->
            if(location.startsWith("classpath://")) {
                Thread.currentThread().contextClassLoader?.getResource(location.removePrefix("classpath://"))
                    ?: throw IOException("Resource $location not found on the classpath")
            }else if(location.contains(Regex("(file|http|https)[:][/][/]"))){
                URI.create(location).toURL()
            } else {
                File(location).toURI().toURL()
            }
        }
    }

    fun load(location:String): URL
}