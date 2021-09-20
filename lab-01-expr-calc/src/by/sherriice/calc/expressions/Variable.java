package by.sherriice.calc.expressions;

import java.util.Scanner;

public interface Variable extends Expression {
    default double inputVariable(String literal) {
        System.out.println("value for '" + literal + "':");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }
    char getSymbol();

}
