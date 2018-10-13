JAVA=java
JAVAC=javac -cp .:java-cup-11b.jar
JFLEX=jflex-1.6.1/bin/jflex
CUP=$(JAVA) -cp .:java-cup-11b.jar java_cup.Main <

all: Main.class

Main.class: Main.java Lexer.java parser.java jnodes/*.java utils/*.java concrete_nodes/*.java concrete_nodes/expressions/*.java nodes3/*.java

%.class: %.java
	@$(JAVAC) $^

Lexer.java: lexspec.flex
	$(JFLEX) lexspec.flex

parser.java: cupsepc.cup
	$(CUP) cupsepc.cup

run: Main.class
	@echo "compiling $(in)..."
	@$(JAVA) -cp .:java-cup-11b-runtime.jar Main $(in) > output.txt
	@echo "Done executing. Look at output.txt for the outcome (cat output.txt)"

test: 
	@echo "compiling test.j..."
	@$(JAVA) -cp .:java-cup-11b-runtime.jar Main test_ok/test.j > output.txt
	@echo "Done executing. Look at output.txt for the outcome"
	cat output.txt

compile:
    $(JAVAC) jnode/*.java


clean:
	rm -f parser.java Lexer.java sym.java output.txt *.class jnodes/*.class *~ utils/*.class concrete_nodes/*.class concrete_nodes/expressions/*.class nodes3/*.class
