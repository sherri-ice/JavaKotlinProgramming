package by.sherriice.calc.expressions;

import by.sherriice.calc.Parser;
import by.sherriice.calc.ParserImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        Scanner in = new Scanner(System.in);
        Parser parser = new ParserImpl();

        var expr = parser.parseExpression(in.nextLine());
        System.out.println(expr.accept(DebugRepresentationExpressionVisitor.INSTANCE));
        System.out.print(expr.accept(ComputeExpressionVisitor.INSTANCE));

    }
}
