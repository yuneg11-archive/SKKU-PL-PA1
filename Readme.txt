To run the program, follow instructions below.

1. Set up ANTLR environment
   - Visit https://www.antlr.org and follow Quick Start guide.

2. Build the ANTLR grammer file
   - Open command prompt (terminal).
   - Move to the project directory.
   - Enter the following command.
     $ antlr4 Expr.g4

3. Build the program
   - Enter the following command.
     $ javac Expr*.java

4. Prepare the input file
   - Make input text file (input.txt) to the project directory.
   example of content) a = 100; a + 2.1 * 34; 10 * (5 / 2);

4. Run the program
   - Enter the following command (you can change the input file name).
     $ java ExprEvalApp input.txt