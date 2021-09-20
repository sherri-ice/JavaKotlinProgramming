package by.sherriice.calc.expressions;

public class ParenthesisExpressionImpl implements ParenthesisExpression{
    Expression expression;

    public ParenthesisExpressionImpl(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Expression getExpr() {
        return this.expression;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitParenthesis(this);
    }
}
