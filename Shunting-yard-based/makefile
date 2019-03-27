ANTLR_PATH=/usr/local/lib/antlr-4.7.2-complete.jar
ANTLR_TEST=org.antlr.v4.gui.TestRig
PROG=Expr
ROOT=program

all:
	java -jar $(ANTLR_PATH) $(PROG).g4
	javac $(PROG)*.java
	java $(PROG)EvalApp

grammer:
	java -jar $(ANTLR_PATH) $(PROG).g4

$(ROOT):
	javac $(PROG)*.java
	java $(PROG)EvalApp

grun:
	java -jar $(ANTLR_PATH) $(PROG).g4
	javac $(PROG)*.java
	java $(ANTLR_TEST) $(PROG) $(ROOT) -gui

clean:
	rm -rf .antlr
	rm -f *.interp *.tokens *.class
	rm -f $(PROG)BaseListener.java $(PROG)Lexer.java $(PROG)Listener.java $(PROG)Parser.java
