import by.sherriice.calc.Parser;
import by.sherriice.calc.ParserImpl;
import by.sherriice.calc.exceptions.ExpressionParseException;
import by.sherriice.calc.expressions.Expression;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        Scanner in = new Scanner(System.in);
        Parser parser = new ParserImpl();
        var expr = parser.parseExpression(in.nextLine());
        System.out.print(expr.debugRepresentation());
    }
}
