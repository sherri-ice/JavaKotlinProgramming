package by.sherriice.calc;

import by.sherriice.calc.expressions.*;
import by.sherriice.calc.test.ParserTest;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Parser parser = new ParserImpl();
        System.out.println("Welcome to the calculator :) \n" +
                "Supported operations: +, -, *, /\n" +
                "Variables are also supported");

        while (true) {
            System.out.println("Enter your expression or 'E' if you finished:");
            String input = in.nextLine();
            if (Objects.equals(input, "E")) {
                break;
            }
            Expression expr = null;
            try {
                expr = parser.parseExpression(input);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());

            }
            if (expr != null) {
                System.out.println("Your expression: " + expr.accept(DebugRepresentationExpressionVisitor.INSTANCE));
                Map<Character, Double> variables = (Map<Character, Double>) expr.accept(VariableInputVisitor.INSTANCE);
                System.out.println("Depth: " + expr.accept(TreeDepthVisitor.INSTANCE));
                System.out.println("Result: " + expr.accept(new ComputeExpressionVisitor(variables)));
            }
        }

    }
}
