package by.sherriice.calc.expressions;

public class LiteralImpl implements Literal{

    private final String literal;
    private final boolean isVariable;

    public LiteralImpl(String literal) {
        this.isVariable = Character.isLetter(literal.charAt(0));
        if (isVariable) {
            this.literal = String.valueOf(inputVariable(literal));
        } else {
            this.literal = literal;
        }
    }
    @Override
    public String debugRepresentation() {
        if (isVariable) {
            return "var[" + literal + "]";
        }
        return '\'' + literal + '\'';
    }

    @Override
    public double getValue() {
        return Double.parseDouble(literal);
    }
}
