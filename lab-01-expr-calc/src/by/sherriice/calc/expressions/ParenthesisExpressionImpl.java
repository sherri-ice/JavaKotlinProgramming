package by.sherriice.calc.expressions;

public class ParenthesisExpressionImpl implements ParenthesisExpression{
    Expression expression;

    public ParenthesisExpressionImpl(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String debugRepresentation() {
        return "paran-expr(" + this.expression.debugRepresentation() + ")";
    }

    @Override
    public Expression getExpr() {
        return this.expression;
    }
}
