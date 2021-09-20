package by.sherriice.calc.expressions;

public interface ExpressionVisitor {
    String visitBinaryExpression(BinaryExpression expr);
    String visitLiteral(Literal expr);
    String visitParenthesis(ParenthesisExpression expr);
}
