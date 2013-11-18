#Exercicio Programa II - Batalha de Robos
#MAC0242 - Laboratorio de Programacao II
#Professor Marco Dimas Gubitoso

#Fellipe Souto Sampaio 
#Número USP: 7990422 e-mail: fellipe.sampaio@usp.br

#Gervásio Protásio dos Santos Neto 
#Número USP: 7990996 e-mail: gervasio.neto@usp.br

#Vinícius Jorge Vendramini
#Número USP: 7991103 e-mail: vinicius.vendramini@usp.br

#IME-USP
#2 semestre 2013

default:
	make clean
	javac Battlefield.java
	jar cvfe BF.jar Battlefield *.class *.png source* default*

clean:
	rm -f *.class
	rm -f *.jar
