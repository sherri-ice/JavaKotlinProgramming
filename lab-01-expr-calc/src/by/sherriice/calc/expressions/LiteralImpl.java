package by.sherriice.calc.expressions;

public class LiteralImpl implements Literal{

    private final String literal;

    public LiteralImpl(String literal) {
            this.literal = literal;
    }
    @Override
    public double getValue() {
        return Double.parseDouble(literal);
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitLiteral(this);
    }
}
