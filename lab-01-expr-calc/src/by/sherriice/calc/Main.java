package by.sherriice.calc;

import by.sherriice.calc.Parser;
import by.sherriice.calc.ParserImpl;
import by.sherriice.calc.expressions.ComputeExpressionVisitor;
import by.sherriice.calc.expressions.DebugRepresentationExpressionVisitor;
import by.sherriice.calc.expressions.ExpressionParseException;
import by.sherriice.calc.expressions.TreeDepthVisitor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        Scanner in = new Scanner(System.in);
        Parser parser = new ParserImpl();

        var expr = parser.parseExpression(in.nextLine());
        System.out.println(expr.accept(DebugRepresentationExpressionVisitor.INSTANCE));
        System.out.println(expr.accept(ComputeExpressionVisitor.INSTANCE));
        System.out.println(expr.accept(TreeDepthVisitor.INSTANCE));

    }
}
