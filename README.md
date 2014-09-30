# Functionly
Simple functional language created as a test case for [compiler-framework](https://github.com/andrewromanenco/compilerframework).

## Implementation
Run functionly.Functionly <source-file-name> to test.

Result is set of files:

- tokens.out: all lexical tokens with types and file positions
- parsing-tree.out.xml: xml with parsing tree
- ast.out: print out of the abstract syntax tree

## Grammar
See Functionly.txt for grammar details

## Rules
- One public function per source file
- Source file can have many functions
- Functions are global
- Variables are bounded to scopes (begin-end)
- Variable has to be declared before use (appear at right side of assign operation, before being part of an expression)
- All variables represent numbers

## Samples
Sample source files are under src/test/resources/functionly/

## License
The code is released under Apache License Version 2.0

## Contacts
- andrew@romanenco.com
- [romanenco.com](http://www.romanenco.com)
- https://twitter.com/andrewromanenco
