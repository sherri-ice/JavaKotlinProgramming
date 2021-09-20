package by.sherriice.calc.expressions;

public class DebugRepresentationExpressionVisitor implements ExpressionVisitor {
    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        return (expr.getOperation() + "(" + expr.getLeft().accept(this) + ", " +
                expr.getRight().accept(this) + ")");
    }

    @Override
    public Object visitLiteral(Literal expr) {
        if (expr.isVariable()) {
            return "var[" + expr.getValue() + "]";
        }
        return '\'' + String.valueOf(expr.getValue()) + '\'';
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return "paran-expr(" + expr.getExpr().accept(this) + ")";
    }
}
