package by.sherriice.calc.expressions;

import by.sherriice.calc.token.BinOpKind;

public class BinaryExpressionImpl implements BinaryExpression {
    private final Expression left_expression;
    private final Expression right_expression;
    private final BinOpKind operation;

    public BinaryExpressionImpl(BinOpKind operation, Expression left_expression, Expression right_expression) {
        this.left_expression = left_expression;
        this.right_expression = right_expression;
        this.operation = operation;
    }

    @Override
    public Expression getLeft() {
        return this.left_expression;
    }

    @Override
    public Expression getRight() {
        return this.right_expression;
    }

    @Override
    public BinOpKind getOperation() {
        return this.operation;
    }


    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitBinaryExpression(this);
    }
}
