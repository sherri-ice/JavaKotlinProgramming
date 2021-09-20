package by.sherriice.calc.expressions;

public class ComputeExpressionVisitor implements ExpressionVisitor {
    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        Double first = (Double) expr.getRight().accept(this);
        Double second = (Double) expr.getLeft().accept(this);

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
