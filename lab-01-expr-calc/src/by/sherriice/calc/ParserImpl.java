package by.sherriice.calc;

import by.sherriice.calc.exceptions.ExpressionParseException;
import by.sherriice.calc.expressions.*;

import java.util.ArrayList;
import java.util.Stack;

public class ParserImpl implements Parser {
    int start_pos = 0;
    ArrayList<String> tokens = new ArrayList<>();

    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        ParseTokens(input);
        Stack<Expression> expressions = new Stack<>();
        Stack<String> operations = new Stack<>();
        for (String token : tokens) {
            if (Character.isDigit(token.charAt(0))) {
                expressions.push(new LiteralImpl(token));
            } else if (isOperation(token.charAt(0))) {
                if (token.equals("(")) {
                    operations.push(token);
                } else if (token.equals(")")) {
                    while (!operations.isEmpty() && !operations.peek().equals("(")) {
                        var operation = operations.pop();
                        var expr1 = expressions.pop();
                        var expr2 = expressions.pop();
                        expressions.push(new ParenthesisExpressionImpl(new BinaryExpressionImpl(operation, expr2, expr1)));
                    }
                    if (!operations.isEmpty()) {
                        operations.pop(); // delete '('
                    }
                } else {
                    if (operations.isEmpty()) {
                        operations.push(token);
                    } else if (operationsPriority.get(token) <= operationsPriority.get(operations.peek())) {
                        var operation = operations.pop();
                        operations.push(token);
                        var expr1 = expressions.pop();
                        var expr2 = expressions.pop();
                        expressions.push(new BinaryExpressionImpl(operation, expr2, expr1));
                    } else {
                        operations.push(token);
                    }
                }

            }
        }
        while (!operations.isEmpty()) {
            var expr1 = expressions.pop();
            var expr2 = expressions.pop();
            var operation = operations.pop();
            expressions.push(new BinaryExpressionImpl(operation, expr2, expr1));
        }
        return expressions.peek();
    }

    private void ParseTokens(String input) throws ExpressionParseException {
        for (int i = 0; i < input.length(); ++i) {
            StringBuilder next_token = new StringBuilder();
            if (isOperation(input.charAt(i)) || Character.isLetter(input.charAt(i))) {
                next_token.append(input.charAt(i));
            } else if (Character.isDigit(input.charAt(i))) {
                while (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.') {
                    next_token.append(input.charAt(i));
                    ++i;
                    if (i >= input.length()) {
                        break;
                    }
                }
                --i;
            } else {
                throw new ExpressionParseException("Invalid input!");
            }

            this.tokens.add(next_token.toString());
        }
    }
}
