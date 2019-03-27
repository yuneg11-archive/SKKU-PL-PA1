import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.Scanner;

public class ExprEvalApp {
    public static void main(String[] args) throws Exception {
        String text = "";

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            text += scanner.nextLine();
        }
        scanner.close();

        CodePointCharStream codePointCharStream = CharStreams.fromString(text);

        ExprLexer lexer = new ExprLexer(codePointCharStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(new ExprWalker(), parser.program());
    }
}

class ExprWalker extends ExprBaseListener {

    @Override
    public void enterProgram(ExprParser.ProgramContext ctx) {
        System.out.println("Entering Program");
    }

    @Override
    public void exitProgram(ExprParser.ProgramContext ctx) {
        System.out.println("Exiting Program");
    }

    @Override
    public void enterExpression(ExprParser.ExpressionContext ctx) {
        System.out.println("Entering Expression");
    }

    @Override
    public void exitExpression(ExprParser.ExpressionContext ctx) {
        System.out.println("Exiting Expression");
    }

    @Override
    public void enterAssign(ExprParser.AssignContext ctx) {
        System.out.println("Entering Assign");
    }

    @Override
    public void exitAssign(ExprParser.AssignContext ctx) {
        System.out.println("Exiting Assign");
    }

    @Override
    public void enterNumber(ExprParser.NumberContext ctx) {
        System.out.println("Entering Number");
    }

    @Override
    public void exitNumber(ExprParser.NumberContext ctx) {
        System.out.println("Exiting Number");
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        System.out.println("Entering Every Rule");
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        System.out.println("Exiting Every Rule");
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        System.out.println("Visiting Terminal");
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        System.out.println("Visiting Error Node");
    }
}
