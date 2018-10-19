# jLite Front-End Compiler 


This is a Java front end compiler for jLite programs.
The compiler aims at isolating in the best possible the way all the programming language details so as to isolate from the backend compiler work. This allows any backend compilers to be used as it can just take care of the machine code details.
The project front end compiler is dived into two main parts:

### Lexer & Parser
The grammar of jlite can be found in the pdf file `assignment_text.pdf`.
The Lexer has been created using the jflex tool which creates java classes starting from a .lex specification file. The Parsers has been produced using Java Cup, which produces java classes out of a .cup specification file. The Main class starts the parsing process using the generated classes as described above taking an input file name from command line argument.  

Other than just parsing, the parser builds the whole AST. Although the AST is not of great use so far, it will be necessary for the next compilation step. The AST nodes can be found at path `jnodes/` and the root node is `JProgram.java`. Every node has a unique toString() function which is forced to implement being the class extending the abstract JNode. The root toString() will just call in chain all the nodes toString() function to end up with a printed version of the AST. However, it has been preferred to just print out the parsed program from the lexer. The AST will be used later on as mentioned above.

The reference JLite grammar was not well formatted and threw a lot of shift/reduce and reduce/reduce conflicts at first. All of them have been resolved by rearranging the grammar so as to avoid ambiguity. One exception has been seen on the production involving `ClassDecl` where java code has been inserted in the parser to force correct behaviour after have loosened the grammar rules.

### Static Checking & Intermediate Code Generation
To better tackle this part the previous AST is taken as input and a whole new tree is built, that is the concrete tree structure. The nodes of these new tree are well separated from the previous tree and are placed in the package `concrete_nodes`. From this point onwards there has been implemented a Visitor pattern to conveniently explore the tree multiple times and with different options. The following code is implemented in classes placed inside `utils` package.

A first implementation of the Visitor interface is the PrettyPrintingVisitor which just gives a nice and debugging useful view of the tree.

A second implementation is the StaticCheckingVisitor, which checks for the Distinct-Name Checking at first, and then goes on subsequently applying the typing rules as formally specified in the document `assignment2_text.pdf`. During the exploration it fills the Symbol Table and adds all the required type information for the use of IR Generator right after. 

A third implementation is the IRGenerator. It creates another, simpler tree which is the structure that will be given as input to the back-end compiler. It holds the required information for abstracting from the initial source code, so the back-end compiler doesn't need to know about the compiled language. The visitor generates an intermediate code representation which adhere to the IR3 specification which can be found in the file `assignment2_text.pdf`.


## Prerequisites

The only prerequisite is having Java installed in you machine.
Jflex and Cup dependencies are embedded in the project folder.

## Building

Run `make` to build the parser.

    $ make

If everything goes fine you should simply not see any error messages.

## Running

Run `make run in=filepath` to feed the input file to be parsed to the parser. You can try one of the test files, for example:

    $ make run in=test_ok/typecheck/1

If everything goes fine you will see the following as last line:

    Done executing. Look at output.txt for the outcome

Indeed, you can look into the file `output.txt` for the parsed input.

If you were to feed a bad program to the parser the makefile recipe will fail and any typing error is clearly reported. You can look into `output.txt` to have more info about where the compiler halted the execution.

## Understanding the output

The front end compiler gives one output per component if everything goes well during the compilation process:
- <b>Parsing</b> produces the first section which is a mere pretty printing of the input program
- <b>Static Checking</b> produces a first part in which the Class Descriptors are printed (order of classes is random because of the HashMap structure which holds them). That's the default environment for the whole program. Second part produces the type checking results for each class. Not that every component _Comp_ has been marked with the resulting Type in the following way: 
_[Comp -> Type]_
- <b>Code Generator</b> produces a first part with the program Data as specified by the formal description and right below all the functions code.

In case of errors at any stage, the compiling process will stop and the error will be reported in console. Make rule will obviously failed.
To have greater details about the error _DEBUG_ flag in Main class should be set to true, but that is meant for development purposes only.

## Tests

The parsers has been tested using the files in the folders test_ok/typecheck and test_ko/typecheck which in turn contain good syntax code and wrong written code.

## Notes

- methods overloading has been implement, allowing more methods with the same name to be declared on the same class, as long as the signature differ. Informally, the method signature is composed of the Method name + List of Types of params. This has not great implication on the code, as it still searches for a signature match when checking a function call is correctly made, being the code very clean and generic enough. The actual implication is in the Name Checking part at the beginning of the TypeCheckingVisitor class, where instead of just making sure method names are different, we check their signature, so is just a matter of changing the implementation of the equals method of the MethodDecl class.
- null type specification has not been formalised in details. I just treat it as a normal Type, but not assignable to a var or to anything else, it's not of great use really.
- while statement ambiguity has been patched by throwing a type error in case the while has no statement in its body.