JAVA=java
JAVAC=javac -cp .:java-cup-11b.jar
JFLEX=jflex-1.6.1/bin/jflex
CUP=$(JAVA) -cp .:java-cup-11b.jar java_cup.Main <

all: Main.class

Main.class: Main.java Lexer.java parser.java jnodes/*.java utils/*.java

%.class: %.java
	$(JAVAC) $^

Lexer.java: lexspec.flex
	$(JFLEX) lexspec.flex

parser.java: cupsepc.cup
	$(CUP) cupsepc.cup

run: Main.class
	@echo "-------- Parsing file: $(in) ------------"
	@$(JAVA) -cp .:java-cup-11b-runtime.jar Main $(in) > output.txt
	@echo "Successfully parse! Look at output.txt for the outcome"

test: 
	@echo "-------- Parsing file: e1.j ------------"
	@$(JAVA) -cp .:java-cup-11b-runtime.jar Main test_ok/e1.j > output.txt
	@echo "Successfully parse! Look at output.txt for the outcome"
	cat output.txt

compile:
    $(JAVAC) jnode/*.java


clean:
	rm -f parser.java Lexer.java sym.java output.txt *.class jnodes/*.class *~
