package by.sherriice.calc.expressions;

import com.sun.source.tree.Tree;

import static java.lang.Math.max;

public class TreeDepthVisitor implements ExpressionVisitor {
    private TreeDepthVisitor() {
    }

    public static final TreeDepthVisitor INSTANCE = new TreeDepthVisitor();

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        return max((Integer) expr.getRight().accept(this), (Integer) expr.getLeft().accept(this)) + 1;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return 1;
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expr) {
        return (Integer) expr.getExpr().accept(this) + 1;
    }
}
