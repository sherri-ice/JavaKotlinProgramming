package by.sherriice.calc.expressions;

public class ExpressionVisitorImpl implements ExpressionVisitor {
    @Override
    public String visitBinaryExpression(BinaryExpression expr) {
        return (expr.getOperation() + "( " + expr.getLeft().accept(this) + ", " +
                expr.getRight().accept(this) + ")");
    }

    @Override
    public String visitLiteral(Literal expr) {
        if (expr.isVariable()) {
            return "var[" + expr.getValue() + "]";
        }
        return '\'' + String.valueOf(expr.getValue()) + '\'';
    }

    @Override
    public String visitParenthesis(ParenthesisExpression expr) {
        return "paran-expr(" + expr.getExpr().accept(this) + ")";
    }
}
