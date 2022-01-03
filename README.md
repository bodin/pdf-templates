# pdf-templates
PDF Templates is a simple XML to PDF conversion tool.  The layout 
is table based, including nested tables.  Additional flexibility 
can be added using a template engine to preprocess the document.
Handlebars is included as a preprocessing engine.


```xml
<document>
    <page>
        <table padding="10">
            <row>
                <text>column next to a nested table with 50% of the space</text>
                <table layout="30 70">
                    <row>
                        <text>cell with 30%</text>
                        <text>this is longer text given 70% of the nested table space</text>
                    </row>
                </table>
            </row>
        </table>
    </page>
</document>
```

# TODO
## Cleanup
1. [x] Fix when missing some cells in a row

## Features
5. [ ] add template engine support (freemarker, handlebars, etc)
6. [ ] TOC
7. [ ] Font
8. [ ] bold, italic, etc
9. [x] image cell
10. [ ] image loading by generic protocol (https, file, classpath)
11. [ ] add a 'cell' markup so we can style the cell of nested content. 
For example if we want a cell with padding and then a full bordered table.
12. [ ] font style - how do you apply multiple
13. [ ] font style - if a table is bold, and a cell is italics - should it 
combine or change?
