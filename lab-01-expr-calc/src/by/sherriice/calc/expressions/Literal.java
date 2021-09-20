package by.sherriice.calc.expressions;

import by.sherriice.calc.expressions.Expression;

import java.util.Scanner;

public interface Literal extends Expression {
    double getValue();
    default double inputVariable(String literal) {
        System.out.println("value for '" + literal + "':");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }
}
