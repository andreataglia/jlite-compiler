JAVA=java
JAVAC=javac -cp .:java-cup-11b.jar
JFLEX=jflex
CUP=$(JAVA) -cp .:java-cup-11b.jar java_cup.Main <

all: output.txt

# test: output.txt
# 	@(diff output.txt output.good && echo "Test OK!") || echo "Test failed!"

output.txt: Main.class test.txt
	echo "-------- running main... ------------"
	$(JAVA) -cp .:java-cup-11b-runtime.jar Main test.txt > output.txt

Main.class: Main.java Lexer.java parser.java

%.class: %.java
	$(JAVAC) $^

Lexer.java: lcalc.flex
	$(JFLEX) lcalc.flex

parser.java: ycalc.cup
	$(CUP) ycalc.cup

run: Main.class test.txt
	echo "-------- running main... ------------"
	$(JAVA) -cp .:java-cup-11b-runtime.jar Main test.txt > output.txt

clean:
	rm -f parser.java Lexer.java sym.java output.txt *.class *~
