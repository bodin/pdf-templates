package com.github.bodin.pdf.pre.freemarker

import com.github.bodin.pdf.api.TemplateProcessor
import freemarker.template.Configuration
import freemarker.template.Template
import java.io.*


class FreemarkerProcessor: TemplateProcessor {
    private val cfg: Configuration

    constructor() {
        cfg = Configuration(freemarker.template.Configuration.VERSION_2_3_29)
        cfg.defaultEncoding = "UTF-8";
    }

    constructor(cfg:Configuration) {
        this.cfg = cfg
    }

    override fun process(ctx: Any?, ins: InputStream): InputStream {
        val temp: Template = Template("inline", InputStreamReader(ins), cfg);

        val out: Writer = StringWriter()
        temp.process(ctx, out)
        return out.toString().byteInputStream()
    }
}