package by.sherriice.calc.expressions;

import by.sherriice.calc.token.BinOpKind;

public interface BinaryExpression extends Expression {
    Expression getLeft();
    Expression getRight();
    String getOperation();
}
