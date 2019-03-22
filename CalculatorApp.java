import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.Stack;
import java.util.HashMap;
import java.util.Scanner;

public class CalculatorApp {
    public static void main(String[] args) throws Exception {
        String text = "";

        Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			text += scanner.nextLine();
		}
		scanner.close();

        CodePointCharStream codePointCharStream = CharStreams.fromString(text);

        CalculatorLexer lexer = new CalculatorLexer(codePointCharStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalculatorParser parser = new CalculatorParser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(new CalculatorWalker(), parser.program());
    }
}

class CalculatorWalker extends CalculatorBaseListener {
    HashMap<String, Double> variables = new HashMap<String, Double>();
    Stack<Double> evaluation = new Stack<Double>();

    @Override
    public void enterProgram(CalculatorParser.ProgramContext ctx) {
        //System.out.println("Entering Program");
    }

    @Override
    public void exitProgram(CalculatorParser.ProgramContext ctx) {
        //System.out.println("Exiting Program");
    }

    @Override
    public void enterExpression(CalculatorParser.ExpressionContext ctx) {
        //System.out.println("Entering Expression");
    }

    @Override
    public void exitExpression(CalculatorParser.ExpressionContext ctx) {
        //System.out.println("Exiting Expression");
    }

    @Override
    public void enterAssign(CalculatorParser.AssignContext ctx) {
        //System.out.println("Entering Assign");
    }
    
    @Override
    public void exitAssign(CalculatorParser.AssignContext ctx) {
        if(ctx.ID() != null && ctx.number() != null) {
            try {
                double value = Double.parseDouble(ctx.number().getChild(0).getText());
                variables.put(ctx.ID().getText(), value);
            } catch(Exception e) { }
        }
    }

    @Override
    public void enterNumber(CalculatorParser.NumberContext ctx) {
        //System.out.println("Entering Number");
    }

    @Override
    public void exitNumber(CalculatorParser.NumberContext ctx) {
        //System.out.println("Exiting Number");
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        //System.out.println("Entering Every Rule");
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        //System.out.println("Exiting Every Rule");
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        //System.out.println("Visiting Terminal");
    }
    
    @Override
    public void visitErrorNode(ErrorNode node) {
        //System.out.println("Visiting Error Node");
    }
}
