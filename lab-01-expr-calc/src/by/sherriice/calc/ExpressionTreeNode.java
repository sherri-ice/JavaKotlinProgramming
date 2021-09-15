package by.sherriice.calc;

import by.sherriice.calc.expressions.Expression;

public interface ExpressionTreeNode {
    Expression getLeft();
    Expression getRight();
    Expression getExpression();
}
