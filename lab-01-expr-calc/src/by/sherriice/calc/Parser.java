package by.sherriice.calc;

import by.sherriice.calc.expressions.ExpressionParseException;
import by.sherriice.calc.expressions.Expression;

import java.util.HashMap;
import java.util.Map;

public interface Parser {
    Map<String, Integer> operationsPriority = new HashMap<>() {{
        put("+", 1);
        put("-", 1);
        put("*", 2);
        put("/", 2);
        put("(", 3);
        put(")", 3);
    }};

    Expression parseExpression(String input) throws ExpressionParseException;
    default boolean isOperation(char ch) {
        switch (ch) {
            case '+', '-', '*', '/', '(', ')' -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}
