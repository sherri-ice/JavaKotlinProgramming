package by.sherriice.calc.expressions;

public interface ExpressionVisitor {
    Object visitBinaryExpression(BinaryExpression expr);
    Object visitLiteral(Literal expr);
    Object visitParenthesis(ParenthesisExpression expr);
    Object visitVariable(Variable expr);
}
