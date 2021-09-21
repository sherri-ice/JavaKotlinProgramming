package by.sherriice.calc.test;

import by.sherriice.calc.Parser;
import by.sherriice.calc.ParserImpl;
import by.sherriice.calc.expressions.ComputeExpressionVisitor;
import by.sherriice.calc.expressions.ExpressionParseException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ComputeTests {
    private final Parser parser = new ParserImpl();

    @Nested
    public class PrimitiveCases {

        @ParameterizedTest(name = "Computing {0}")
        @ValueSource(strings = {"1", "2", "333", "42", "20032003"})
        void test(String input) throws ExpressionParseException {
            var visitor = new ComputeExpressionVisitor(new HashMap<>());
            assertEquals(parser.parseExpression(input).accept(visitor), Double.parseDouble(input));
        }
    }
}
