import com.github.bodin.pdf.TemplateEngine
import com.github.bodin.pdf.api.ResourceLoader
import com.github.bodin.pdf.pre.handlebars.HandlebarsProcessor
import java.io.FileOutputStream
import java.nio.file.Paths


fun main() {

    val src = "${Paths.get("").toAbsolutePath().toString()}/src/main/resources"

    val engine = TemplateEngine(
        processor = HandlebarsProcessor(),
        loader = ResourceLoader.Default
    )


    // uri
    FileOutputStream("build/test-image.pdf").use {
        engine.executeFile("file://$src/test-image.xml", it)
    }
/*
    // classpath
    FileOutputStream("build/test-simple.pdf").use {
        engine.executeFile("classpath://test-simple.xml", it)
    }
    // absolute
    FileOutputStream("build/test-nested.pdf").use {
        engine.executeFile("$src/test-nested.xml", it)
    }
    // relative
    FileOutputStream("build/test-hard.pdf").use {
        engine.executeFile("src/main/resources/test-hard.xml", it)
    }

    //with handlebars preprocessing
    FileOutputStream("build/test-nested.hb.pdf").use {
        var ctx = arrayOf("val1", "val2")
        engine.executeFile(ctx, "classpath://test-nested.hb.xml", it)
    }
    */
}
