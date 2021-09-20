package by.sherriice.calc.expressions;

import by.sherriice.calc.Parser;
import by.sherriice.calc.ParserImpl;
import by.sherriice.calc.expressions.ExpressionParseException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        Scanner in = new Scanner(System.in);
        Parser parser = new ParserImpl();
        var expr = parser.parseExpression(in.nextLine());
        ExpressionVisitor visitor = new ExpressionVisitorImpl();
        System.out.print(expr.accept(visitor));

    }
}
