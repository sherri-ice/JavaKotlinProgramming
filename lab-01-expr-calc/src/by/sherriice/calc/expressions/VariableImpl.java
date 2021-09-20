package by.sherriice.calc.expressions;

public class VariableImpl implements Variable{

    private final char symbol;

    public VariableImpl(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitVariable(this);
    }

    @Override
    public char getSymbol() {
        return this.symbol;
    }
}
