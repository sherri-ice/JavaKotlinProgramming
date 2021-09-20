package by.sherriice.calc.expressions;

public class ComputeExpressionVisitor implements ExpressionVisitor {
    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        Double first = (Double) expr.getLeft().accept(this);
        Double second = (Double) expr.getRight().accept(this);

        switch (expr.getOperation()) {
            case SUM -> {
                return first + second;
            }
            case SUB -> {
                return first - second;
            }
            case MUL -> {
                return first * second;
            }
            case DIV -> {
                if (second == 0.) {
                    throw new ArithmeticException("Division by zero!");
                }
                 return first / second;
            }
            default -> {
            }
        }
        return null;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.getValue();
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return expr.getExpr().accept(this);
    }
}
