package by.sherriice.calc.expressions;

public interface Expression {
    Object accept(ExpressionVisitor visitor);
}
