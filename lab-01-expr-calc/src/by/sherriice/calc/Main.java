package by.sherriice.calc;

import by.sherriice.calc.expressions.*;
import by.sherriice.calc.test.ParserTest;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        Scanner in = new Scanner(System.in);
        Parser parser = new ParserImpl();

        var expr = parser.parseExpression(in.nextLine());
        System.out.println(expr.accept(DebugRepresentationExpressionVisitor.INSTANCE));
        Map<Character, Double> variables= (Map<Character, Double>) expr.accept(VariableInputVisitor.INSTANCE);
        System.out.println(expr.accept(new ComputeExpressionVisitor(variables)));
        System.out.println(expr.accept(TreeDepthVisitor.INSTANCE));

    }
}
