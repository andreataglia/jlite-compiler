JAVA=java
JAVAC=javac -cp .:java-cup-11b.jar
JFLEX=jflex-1.6.1/bin/jflex
CUP=$(JAVA) -cp .:java-cup-11b.jar java_cup.Main <

all: Main.class

Main.class: Main.java Lexer.java parser.java asm/*.java jnodes/*.java utils/*.java concrete_nodes/*.java concrete_nodes/expressions/*.java nodes3/*.java

%.class: %.java
	@$(JAVAC) $^

Lexer.java: lexspec.flex
	$(JFLEX) lexspec.flex

parser.java: cupsepc.cup
	$(CUP) cupsepc.cup

run: Main.class
	@echo "compiling $(in)..."
	@$(JAVA) -cp .:java-cup-11b-runtime.jar Main $(in) > output.txt
	@echo "Done compiling into IR3. Look at output.txt for the outcome (cat output.txt)"
	@docker exec test-gem5 arm-linux-gnueabi-gcc-5 -o /mnt/asmout.bin /mnt/asmout.s -static
	@docker exec test-gem5 gem5.opt /usr/local/share/gem5/configs/example/se.py -c /mnt/asmout.bin
	@echo "Done running the generated executable"

run_asm:
	@echo "compiling $(in)..."
	@$(JAVA) -cp .:java-cup-11b-runtime.jar Main $(in) > output.txt
	@echo "Done compiling into assembly code. The output code is located in file asmout.s. Look at output.txt for the outcome of the previous steps."

test: 
	@echo "compiling test.j..."
	@$(JAVA) -cp .:java-cup-11b-runtime.jar Main test_ok/test.j > output.txt
	@echo "Done executing. Look at output.txt for the code until IR3, and asmout.s for the assembly"
	docker exec test-gem5 arm-linux-gnueabi-gcc-5 -o /mnt/asmout.bin /mnt/asmout.s -static
	docker exec test-gem5 gem5.opt /usr/local/share/gem5/configs/example/se.py -c /mnt/asmout.bin

runasm:
	docker exec test-gem5 arm-linux-gnueabi-gcc-5 -o /mnt/asmout.bin /mnt/asmout.s -static
	docker exec test-gem5 gem5.opt /usr/local/share/gem5/configs/example/se.py -c /mnt/asmout.bin

execbin:
	docker exec test-gem5 gem5.opt /usr/local/share/gem5/configs/example/se.py -c /mnt/asmout.bin

compileasm:
	docker exec test-gem5 arm-linux-gnueabi-gcc-5 -o /mnt/asmout.bin /mnt/asmout.s -static

dockerinit:
	docker run --rm --name test-gem5 -v $(pwd):/mnt -td tpxp/cs4212 bash

dockerstop:
	docker stop -t 0 test-gem5

compile:
    $(JAVAC) jnode/*.java

clean:
	rm -f parser.java Lexer.java sym.java output.txt *.class jnodes/*.class *~ utils/*.class concrete_nodes/*.class concrete_nodes/expressions/*.class nodes3/*.class asm/*.class asmout.bin asmout.s output.txt
