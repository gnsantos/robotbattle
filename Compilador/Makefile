LISTA=(*.class)
LIMPEZA= CalcConstants.java Calc.java ParseException.java SimpleCharStream.java \
	Token*.java CalcTokenManager.java

%.class: %.java

Calc.class: Calc.java *.java
	javac  *.java

Calc.java: Calc.jj
	javacc $<

run: Calc.class
	java Calc <hanoi
	java Calc <fatorial
	java Calc <quadrado
	java Calc <yara

clean:
	rm -f *.class *~ $(LIMPEZA)

pacote:
	tar czf Compilador.tgz Calc.jj   TabSim.java  Pilha.java \
							Computador.java Instrucao.java Simbolo.java \
							Empilhavel.java Makefile simples simples2 \
							fatorial hanoi quadrado yara
