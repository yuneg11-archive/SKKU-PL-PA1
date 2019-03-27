import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.tree.pattern.TokenTagToken;
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
        String value;

        if(ctx.ID() != null) {
            value = variables.get(ctx.ID().getText()).toString();
        } else if(ctx.number() != null) {
            value = ctx.number().getText();
        } else if(ctx.getChild(0).getText().equals("(") && ctx.getChild(2).getText().equals(")")) {
            value = getExpressionValue(ctx.getChild(3));
        } else {
            Double operand1 = Double.parseDouble(getExpressionValue(ctx.getChild(3)));
            Double operand2 = Double.parseDouble(getExpressionValue(ctx.getChild(4)));
            Double result = 0.0;
            if(ctx.getChild(1).getText().equals("*")) {
                result = operand1 * operand2;
            } else if(ctx.getChild(1).getText().equals("/")) {
                result = operand1 / operand2;
            } else if(ctx.getChild(1).getText().equals("+")) {
                result = operand1 + operand2;
            } else if(ctx.getChild(1).getText().equals("-")) {
                result = operand1 - operand2;
            }
            value = result.toString();
        }

        if(ctx.parent.getRuleIndex() == CalculatorParser.RULE_program) {
            System.out.println(value);
        } else {
            ctx.getParent().addChild(new TerminalNodeImpl(new TokenTagToken(value, -1)));
        }
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

    private String getExpressionValue(ParseTree child) {
        try {
            String str = child.getText();
            return str.substring(1, str.length() - 1);
        } catch(Exception e) {
            return null;
        }
    }
}
