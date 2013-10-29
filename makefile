#Exercicio Programa II - Batalha de Robos
#MAC0242 - Laboratorio de Programacao II
#Professor Marco Dimas Gubitoso

#Fellipe Souto Sampaio 
#Número USP: 7990422 e-mail: fellipe.sampaio@usp.com

#Gervásio Protásio dos Santos Neto 
#Número USP: 7990996 e-mail: gervasio.neto@usp.br

#Vinícius Jorge Vendramini
#Número USP: 7991103 e-mail: vinicius.vendramini@usp.br

#IME-USP
#2 semestre 2013

JCC = javac

# Define a makefile variable for compilation flags
# The -g flag compiles with debugging information
JFLAGS = -g

# typing 'make' will invoke the first target entry in the makefile
# (the default one in this case)
default: $(subst .java,.class,$(wildcard *.java))

# this target entry builds the Average class
# the Average.class file is dependent on the Average.java file
# and the rule associated with this entry gives the command to create it
#
%.class : %.java
	$(JCC) $(JFLAGS) $<

# To start over from scratch, type 'make clean'. 
# Removes all .class files, so that the next make rebuilds them
#
clean:
	$(RM) *.class