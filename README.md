# pdf-templates

[![Build](https://img.shields.io/github/workflow/status/bodin/pdf-templates/Full%20Build?label=Build&style=for-the-badge&logo=github)](https://github.com/bodin/pdf-templates/actions)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.bodin/pdf-templates.svg?label=Maven%20Central&color=informational&style=for-the-badge&logo=apachemaven)](https://search.maven.org/artifact/org.apache.maven.plugins/maven-release-plugin)


PDF Templates is a simple XML to PDF conversion tool.  The layout 
is table based, including nested tables.  Additional flexibility 
and dynamic content can be added using a provided preprocessor.

Preprocessors for Handlebars and Freemarker are included as  
options, however your project will need to be included the 
dependencies explicitly if they are used, otherwise you will have a 
runtime exception for `ClassNotFound`.  

Additional Preprocessors can be created by implementing the 
`TemplateProcessor` interface

***

## Examples

Below is a simple template that does not use a preprocessor
```xml
<document>
    <page>
        <table padding="10">
            <row>
                <text>column next to a nested table with 50% of the space</text>
                <table layout="30 70">
                    <row>
                        <text>cell with 30%</text>
                        <text>this is <f fontStyle="bold">longer</b> text given 70% of the nested table space</text>
                    </row>
                </table>
            </row>
        </table>
    </page>
</document>
```

Handlebars
* [Input - invoice.hb.xml](https://github.com/bodin/pdf-templates/blob/main/src/test/resources/samples/input/invoice.hb.xml)
* [Ouput - invoice.hb.pdf](https://github.com/bodin/pdf-templates/blob/main/src/test/resources/samples/output/invoice.hb.pdf)

Freemarker
* [Input - invoice.fm.xml](https://github.com/bodin/pdf-templates/blob/main/src/test/resources/samples/input/invoice.fm.xml)
* [Ouput - invoice.fm.pdf](https://github.com/bodin/pdf-templates/blob/main/src/test/resources/samples/output/invoice.fm.pdf)

## API Usage

```java
    var engine = TemplateEngine(
        processor = TemplateProcessor.NoOp,
        loader = ResourceLoader.Default
    )
        
    //  No handlebars processor - context if given is ignored
    FileOutputStream("build/test-simple.pdf").use {
        engine.executeFile("classpath://test-simple.xml", it)
    }

    engine = TemplateEngine(
        processor = HandlebarsProcessor(),
        loader = ResourceLoader.Default
    )    
        
    //  No handlebars context - handlebars still runs
    FileOutputStream("build/test-simple.pdf").use {
        engine.executeFile("classpath://test-simple.xml", it)
    }
        
    //  With Handlebars context
    FileOutputStream("build/test-nested.hb.pdf").use {
        var ctx = arrayOf("val1", "val2")
        engine.executeFile(ctx, "classpath://test-nested.hb.xml", it)
    }

```

## Grammar

The grammar is straight forward, with the only interesting rule being `row` 
which has multiple different `cell` representations

```
document := page+
page     := (table | text | blank)+
table    := row+
row      := (text | image | table | blank)+
image    := <string>
text     := (<string> | f)+
f        := (<string> | f)+
```

## Attributes
Attributes by default cascade down into their children.  Specifying 
`fontColor="red"` on a table will mean every `text` cell is printed in red font - 
unless another parent changes it between the `table` and `text` cell.

`text` cells can have different font styles mixed by using the `f` element with font attributes 

** All attributes can be written in camel case, or dash syntax.  `paddingTop` or `padding-top`**

```
pageSize = "int int | string"
 * defaults to A4
 non cascading, only available on document
 Sets the page size to either the specified witdth  height - or the named paper size (letter, A4, etc)

layout = "int int*"  
  * defaults to an even split 
  non cascading, only available on `table`
  Represents the number of colums and their propartion relative to each other.
    
bookmark = "string"
  cascades down to the first `text` cell
  This represents an entry in the PDF outline that can be used by a user to navigate 
  
fontName = "string"
  * defaults to Helvetica
  font name that is known to OpenPDF
  
fontColor = "red|green|..." or "#RRGGBB"
  * defaults to Black
  color name known to java.awt.Color as a field or hex value
  
fontSize = "int"
  * defaults to 12
  size in points of font
  
fontStyle = "bold | italic | strikethrough | underline | normal"
  * defaults to normal
  font style

alignVertical = "top | middle | bottom"
  * defaults to middle
  vertical alignment  
                            
alignHorizontal = "left | center | right"
  * defaults to left
  horizontal alignment   
  
backgroundColor = "red|green|..." or "#RRGGBB"
  * defaults to White
  color name known to java.awt.Color as a field or hex value

border = "size color" | "size" | "color" | "color size"
  * defaults to 0 black
  applies border styling to all sides of the cell

borderTop = "size color" | "size" | "color" | "color size"
  * defaults to 0 black
  applies border styling to the top side of the cell
  
borderBottom = "size color" | "size" | "color" | "color size"
  * defaults to 0 black
  applies border styling to the bottom side of the cell
  
borderLeft = "size color" | "size" | "color" | "color size"
  * defaults to 0 black
  applies border styling to the left side of the cell
  
borderRight = "size color" | "size" | "color" | "color size"
  * defaults to 0 black
  applies border styling to the right side of the cell  
  
padding = "int"
  * defaults to 0
  padding dimension for all sides
  
paddingTop = "int"
  * defaults to 0
  padding dimension for top
  
paddingBottom = "int"
  * defaults to 0
  padding dimension for bottom
  
paddingLeft = "int"
  * defaults to 0
  padding dimension for left
  
paddingRight = "int"
  * defaults to 0
  padding dimension for right

margin = "int"
  * defaults to 36pt / .5in
  cascading to page, only available on `document` or `page`
  margin dimension for all sides
  
marginTop = "int"
  * defaults to 36pt / .5in
  cascading to page, only available on `document` or `page`
  margin dimension for top
  
marginBottom = "int"
  * defaults to 36pt / .5in
  cascading to page, only available on `document` or `page`
  margin dimension for bottom
  
marginLeft = "int"
  * defaults to 36pt / .5in
  cascading to page, only available on `document` or `page`
  margin dimension for left
  
marginRight = "int"
  * defaults to 36pt / .5in
  cascading to page, only available on `document` or `page`
  margin dimension for right

width = "int"
  only available on image and table elements
  sets the image width in px, sets the table width in %

height = "int"                           
  only available on image and blank
  sets the image height
   
colspan = "int"
  non cascading, only available on `cells`
  sets the number of columns to use  
  
rowspan = "int"
  non cascading, only available on `cells`
  sets the number of rows to use     
```


# TODO
## Cleanup
### Not Done
3. [ ] DrawContext should be non-mutatable
5. [ ] ascender=true hardcoded
6. [ ] leading hardcoded

### Done
4. [x] Real samples
1. [x] Fix when missing some cells in a row
2. [x] proper test cases, moving resources to test folder

## Features
### Not Done
3. [ ] allow leading to be set
6. [ ] allow paragraph settings (before, after, indent, etc)
8. [ ] allow height on all elements (table, row, paragraph)
9. [ ] Headers and Footers (document level and override at the page level)
10. [ ] page numbering options
11. [ ] add a 'cell' markup so we can style the cell of nested content.  For example if we want a cell with padding and then a full bordered table.
12. [ ] Add CLI Main class


### Won't do
4. Font (all in one directive) - don't like the complexity and as of now, does not seem to add much benefit
 
### Done
7. [x] fix underline
2. [x] allow paragraphs at the page level
5. [x] images at teh top level
4. [x] specify page type (A4, letter, etc)
5. [x] colspan
6. [x] rowspan
7. [x] image loading by generic protocol (https, file, classpath)
8. [x] image width and height
9. [x] margin properties
10. [x] font style - how do you apply multiple
11. [x] bold, italic, etc
12. [x] Border properties
13. [x] TOC
14. [x] allow 'dash based' attribute names
15. [x] add template engine support (freemarker, handlebars, etc)
16. [x] image cell
17. [x] Add RGB colors
18. [x] Set default values
19. [x] font style - if a table is bold, and a cell is italics - should it
        combine or change?