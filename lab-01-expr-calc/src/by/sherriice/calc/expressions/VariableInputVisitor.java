package by.sherriice.calc.expressions;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VariableInputVisitor implements ExpressionVisitor {

    private Map<Character, Double> variables = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    private VariableInputVisitor() {
    }

    public static final VariableInputVisitor INSTANCE = new VariableInputVisitor();

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        expr.getLeft().accept(this);
        expr.getRight().accept(this);
        return this.variables;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return this.variables;
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        expr.getExpr().accept(this);
        return this.variables;
    }

    @Override
    public Object visitVariable(Variable expr) {
        if (!this.variables.containsKey(expr.getSymbol())) {
            System.out.println("Input value for '" + expr.getSymbol() + "':");
            this.variables.put(expr.getSymbol(), scanner.nextDouble());
        }
        return this.variables;
    }
}
