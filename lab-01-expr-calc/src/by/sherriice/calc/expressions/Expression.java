package by.sherriice.calc.expressions;

public interface Expression {
    String accept(ExpressionVisitor visitor);
}
