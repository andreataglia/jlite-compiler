# jlite-parser

This is a Java parser for jLite programs. The grammar of jlite can be found in the pdf file `assignment_text.pdf`.
The project consists of two main parts: the Lexer and the Parser. The Lexer has been created using the jflex tool which creates java classes starting from a .lex specification file. The Parsers has been produced using Java Cup, which produces java classes out of a .cup specification file.
The Main class starts the parsing process using the generated classes as described above taking an input file name from command line argument.  

## Prerequisites

The only prerequisites is having Java installed in you machine.
Jflex and Cup dependencies are embedded in the project folder.

## Building

Run `make` to build the parser.

    $ make

If everything goes fine you should simply not see any error messages.

## Running

Run `make run in=filepath` to feed the input file to be parsed to the parser. You can try one of the test files, for example:

    $ make run in=test_ok/e.j

If everything goes fine you will see the following as last line:

    Successfully parse! Look at output.txt for the outcome

Indeed, you can look into the file `output.txt` for the parsed input.

If you were to feed a bad program to the parser the makefile recipe will fail and you can look into `output.txt` to have more info about where the parser got stuck.

## Tests

The parsers has been tested using the .j files in the folders test_ok and test_ko which in turn contain good syntax code and wrong written code.

