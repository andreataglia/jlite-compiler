# jLite Front-End Compiler 


This is a compiler for Jlite programs. It is fully written in Java and includes both a frontend and a backend.

The compiler frontedn aims at isolating in the best possible the way all the programming language details so as to isolate from the backend compiler work. This allows any backend compilers to be used as it can just take care of the machine code details. This way other backend or frontend compilers can be plugged in instead of the one being currently used.

## Prerequisites

The only prerequisite is having Java installed in you machine.
Jflex and Cup dependencies are embedded in the project folder.

## Building

Run `make` to build the parser.

    $ make

If everything goes fine you should simply not see any error messages.

## Running

Run `make run_asm in=filepath` to feed the input file to be compiled. You can try one of the test files, for example:

    $ make run_asm in=testcaes/test_ok/asm/1.j

If everything goes fine you will something like this:

    Done compiling into assembly code.

The output assembly code will be inside `asmout.s`.
You can look into the file `output.txt` to see the output of the frontend process (up to the IR3 code).

If you were to feed a bad program to the parser the makefile recipe will fail and any typing error is clearly reported. You can look into `output.txt` to have more info about where the compiler halted the execution.

## Understanding the frontend output

The front end compiler gives one output per component if everything goes well during the compilation process:
- <b>Parsing</b> produces the first section which is a mere pretty printing of the input program
- <b>Static Checking</b> produces a first part in which the Class Descriptors are printed (order of classes is random because of the HashMap structure which holds them). That's the default environment for the whole program. Second part produces the type checking results for each class. Not that every component _Comp_ has been marked with the resulting Type in the following way: 
_[Comp -> Type]_
- <b>Code Generator</b> produces a first part with the program Data as specified by the formal description and right below all the functions code.

In case of errors at any stage, the compiling process will stop and the error will be reported in console. Make rule will obviously failed.
To have greater details about the error _DEBUG_ flag in Main class should be set to true, but that is meant for development purposes only.

## Tests

The compiler has been tested using the files in the folders `testcases/test_ok/asm` and `testcases/test_ko/typecheck` which in turn contain good syntax code and wrong written code.

The folder `testcases/test_ok/asm` contains 5 samples which have been used for testing the backend part of the compiler. The expected output for each file is in `testcases/test_ok/asm/<test_id>_out.txt` where `<test_id>` can be a number between 1 and 5.  
