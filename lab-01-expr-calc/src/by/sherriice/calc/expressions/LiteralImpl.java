package by.sherriice.calc.expressions;

public class LiteralImpl implements Literal{

    String literal;
    String unaryOperator;

    public LiteralImpl(String literal) {
        this.literal = literal;
    }

    public LiteralImpl(String literal, String unaryOperator) {
        this.literal = literal;
        this.unaryOperator = unaryOperator;
    }

    @Override
    public String debugRepresentation() {
        return '\'' + literal + '\'';
    }

    @Override
    public double getValue() {
        return Double.parseDouble(literal);
    }
}
