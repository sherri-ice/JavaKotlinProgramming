package by.sherriice.calc;

import by.sherriice.calc.expressions.ExpressionParseException;
import by.sherriice.calc.expressions.*;
import by.sherriice.calc.token.BinOpKind;

import java.util.ArrayList;
import java.util.Stack;

public class ParserImpl implements Parser {
    boolean hasVariable = false;

    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        parseTokens(input);
        Stack<Expression> expressions = new Stack<>();
        Stack<String> operations = new Stack<>();
        var tokens = parseTokens(input);
        for (String token : tokens) {
            if (Character.isDigit(token.charAt(0)) || Character.isLetter(token.charAt(0))) {
                expressions.push(new LiteralImpl(token));
            } else if (isOperation(token.charAt(0))) {
                if (token.equals("(")) {
                    operations.push(token);
                } else if (token.equals(")")) {
                    if (operations.isEmpty()) {
                        throw new ExpressionParseException("')' wasn't expected");
                    }
                    while (!operations.isEmpty() && !operations.peek().equals("(")) {
                        var operation = operations.pop();
                        var expr1 = expressions.pop();
                        var expr2 = expressions.pop();
                        expressions.push(new ParenthesisExpressionImpl(new BinaryExpressionImpl(BinOpKind.fromString(operation), expr2, expr1)));
                    }
                    if (!operations.isEmpty()) {
                        operations.pop(); // delete '('
                    }
                } else {
                    if (operations.isEmpty()) {
                        operations.push(token);
                    } else if (!operations.peek().equals("(") && operationsPriority.get(token) <= operationsPriority.get(operations.peek())) {
                        if (expressions.size() < 2) {
                            throw new ExpressionParseException("Invalid input");
                        }
                        var operation = operations.pop();
                        operations.push(token);
                        var expr1 = expressions.pop();
                        var expr2 = expressions.pop();
                        expressions.push(new BinaryExpressionImpl(BinOpKind.fromString(operation), expr2, expr1));
                    } else {
                        operations.push(token);
                    }
                }

            }
        }
        while (!operations.isEmpty()) {
            if (expressions.size() < 2) {
                throw new ExpressionParseException("Invalid input");
            }
            var expr1 = expressions.pop();
            var expr2 = expressions.pop();
            var operation = operations.pop();
            expressions.push(new BinaryExpressionImpl(BinOpKind.fromString(operation), expr2, expr1));
        }

        return expressions.peek();
    }

    private ArrayList<String> parseTokens(String input) throws ExpressionParseException {
        ArrayList<String> tokens = new ArrayList<>();
        for (int i = 0; i < input.length(); ++i) {
            StringBuilder next_token = new StringBuilder();
            if (Character.isWhitespace(input.charAt(i))) {
                continue;
            }
            if (isOperation(input.charAt(i))) {
                next_token.append(input.charAt(i));
            } else if (Character.isLetter(input.charAt(i)))  {
                next_token.append(input.charAt(i));
                hasVariable = true;
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

            tokens.add(next_token.toString());
        }
        return tokens;
    }
}
