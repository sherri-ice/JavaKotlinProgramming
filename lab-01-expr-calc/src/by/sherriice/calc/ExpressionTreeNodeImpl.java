package by.sherriice.calc;

import by.sherriice.calc.expressions.Expression;

public class ExpressionTreeNodeImpl implements ExpressionTreeNode {
    Expression expression;
    Expression left;
    Expression right;

    ExpressionTreeNodeImpl(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Expression getLeft() {
        return this.left;
    }

    @Override
    public Expression getRight() {
        return this.right;
    }

    @Override
    public Expression getExpression() {
        return this.expression;
    }
}
