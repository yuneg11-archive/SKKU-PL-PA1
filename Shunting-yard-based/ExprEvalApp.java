import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.EmptyStackException;

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
    HashMap<String, Double> variables = new HashMap<String, Double>();
    Stack<String> operators = new Stack<String>();
    Stack<Double> values = new Stack<Double>();

    Boolean error;

    @Override
    public void enterProgram(ExprParser.ProgramContext ctx) {
        //System.out.println("Entering Program");
    }

    @Override
    public void exitProgram(ExprParser.ProgramContext ctx) {
        //System.out.println("Exiting Program");
    }

    @Override
    public void enterExpression(ExprParser.ExpressionContext ctx) {
        if(ctx.parent.getRuleIndex() == ExprParser.RULE_program) {
            error = false;
        }
    }

    @Override
    public void exitExpression(ExprParser.ExpressionContext ctx) {
        if(ctx.parent.getRuleIndex() == ExprParser.RULE_program) {
            if(!error) {
                try {
                    while(!operators.isEmpty()) {
                        popOperator();
                    }
                    if(values.size() == 1) {
                        System.out.println(values.pop());
                    } else {
                        throw new Exception();
                    }
                } catch(Exception e) {
                    System.out.println("Expression Error");
                }

                values.clear();
                operators.clear();
            } else {
                System.out.println("Expression Error");
            }
        }
    }

    @Override
    public void enterAssign(ExprParser.AssignContext ctx) {
        error = false;
    }

    @Override
    public void exitAssign(ExprParser.AssignContext ctx) {
        if(!error) {
            if(ctx.id() != null && ctx.number() != null) {
                try {
                    double value = Double.parseDouble(ctx.number().getText());
                    variables.put(ctx.id().getText(), value);
                } catch(Exception e) { }
            }
        }
    }

    @Override
    public void enterParenthesis(ExprParser.ParenthesisContext ctx) {
        //System.out.println("Entering Parenthesis");
    }

    @Override
    public void exitParenthesis(ExprParser.ParenthesisContext ctx) {
        if(ctx.getText().equals("(")) {
            operators.push("(");
        } else if(ctx.getText().equals(")")) {
            try {
                while(!operators.peek().equals("(")) {
                    popOperator();
                }
                operators.pop();
            } catch(Exception e) {
                error = true;
            }
        } else {
            error = true;
        }
    }

    @Override
    public void enterOperator(ExprParser.OperatorContext ctx) {
        //System.out.println("Entering Operator");
    }

    @Override
    public void exitOperator(ExprParser.OperatorContext ctx) {
        try {
            while(getOperatorPriority(operators.peek()) >= getOperatorPriority(ctx.getText())) {
                popOperator();
            }
        } catch(EmptyStackException e) {
        } catch(Exception e) {
            error = true;
        } finally {
            operators.push(ctx.getText());
        }
    }

    @Override
    public void enterId(ExprParser.IdContext ctx) {
        //System.out.println("Entering Id");
    }

    @Override
    public void exitId(ExprParser.IdContext ctx) {
        if(ctx.parent.getRuleIndex() == ExprParser.RULE_expression) {
            if(variables.get(ctx.getText()) != null) {
                values.push(variables.get(ctx.getText()));
            } else {
                error = true;
            }
        }
    }

    @Override
    public void enterNumber(ExprParser.NumberContext ctx) {
        //System.out.println("Entering Number");
    }

    @Override
    public void exitNumber(ExprParser.NumberContext ctx) {
        if(ctx.parent.getRuleIndex() == ExprParser.RULE_expression) {
            try {
                values.push(Double.parseDouble(ctx.getText()));
            } catch(Exception e) {
                error = true;
            }
        }
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
        error = true;
    }

    private int getOperatorPriority(String str) throws Exception {
        String[][] operator = { { "(" }, { "+", "-" }, { "*", "/" }};

        for(int i = 0; i < operator.length; i++) {
            for(String op : operator[i]) {
                if(str.equals(op)) {
                    return i;
                }
            }
        }

        throw new Exception();
    }

    private void popOperator() throws Exception {
        String operator = operators.pop();
        try {
            Double operand2 = values.pop();
            Double operand1 = values.pop();
            Double result = 0.0;

            if(operator.equals("*")) {
                result = operand1 * operand2;
            } else if(operator.equals("/")) {
                result = operand1 / operand2;
            } else if(operator.equals("+")) {
                result = operand1 + operand2;
            } else if(operator.equals("-")) {
                result = operand1 - operand2;
            }

            values.push(result);
        } catch(Exception e) {
            error = true;
            throw new Exception();
        }
    }
}
