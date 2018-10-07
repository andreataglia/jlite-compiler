# jlite front end compiler 


This is a Java front end compiler for jLite programs. The grammar of jlite can be found in the pdf file `assignment_text.pdf`.

The compiler aims at isolating in the best possible the way all the programming language details so as to isolate from the backend compiler work. This allows any backend compilers to be used as it can just take care of the machine code details.

The project consists of two main parts: the Lexer and the Parser. The Lexer has been created using the jflex tool which creates java classes starting from a .lex specification file. The Parsers has been produced using Java Cup, which produces java classes out of a .cup specification file. The Main class starts the parsing process using the generated classes as described above taking an input file name from command line argument.  

Other than just parsing, the parser builds the whole AST. Although the AST is not of great use so far, it will be necessary for further compiler expansion. The AST nodes can be found at path `jnodes/` and the root node is `JProgram.java`. Every node has a unique toString() function which is forced to implement being the class extending the abstract JNode. The root toString() will just call in chain all the nodes toString() function to end up with a printed version of the AST. However, it has been preferred to just print out the parsed program from the lexer. The AST will be used later on as mentioned above.


The reference JLite grammar was not well formatted and threw a lot of shift/reduce and reduce/reduce conflicts at first. All of them have been resolved by rearranging the grammar so as to avoid ambiguity. One exception has been seen on the production involving `ClassDecl` where java code has been inserted in the parser to force correct behaviour after have loosened the grammar rules.

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

