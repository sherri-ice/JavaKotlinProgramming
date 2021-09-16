package by.sherriice.calc.expressions;

public class LiteralImpl implements Literal{

    String literal;

    public LiteralImpl(String literal) {
        this.literal = literal;
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
