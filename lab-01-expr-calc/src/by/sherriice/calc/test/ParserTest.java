package by.sherriice.calc.test;

import by.sherriice.calc.Parser;
import by.sherriice.calc.ParserImpl;
import by.sherriice.calc.expressions.*;
import by.sherriice.calc.token.BinOpKind;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private final Parser parser = new ParserImpl();
    private final DebugRepresentationExpressionVisitor debugVisitor = DebugRepresentationExpressionVisitor.INSTANCE;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    public class PrimitiveTests {
        @ParameterizedTest(name = "Parsing {0}")
        @ValueSource(strings = {"3", "4", "0", "33"})
        void literalParse(String input) throws ExpressionParseException {
            var expr = (LiteralImpl) parser.parseExpression(input);
            assertEquals(expr.getValue(), Double.parseDouble(input));
        }

        @ParameterizedTest(name = "Parsing {0}")
        @ValueSource(strings = {"x", "y", "e", "X", "Y"})
        void variableParse(String input) throws ExpressionParseException {
            var expr = (VariableImpl) parser.parseExpression(input);
            assertEquals(expr.getSymbol(), input.charAt(0));
        }

    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    public class ExceptionTests {
        @ParameterizedTest(name = "Parsing braces {0}")
        @ValueSource(strings = {"(", ")", "((", "(()", "(()()()", "3+4 * (", "(3+(4)))"})
        void bracesParse(String input) {
            Exception exception = assertThrows(ExpressionParseException.class, () ->
                    parser.parseExpression(input));

        }

        @ParameterizedTest(name = "Parsing braces {0}")
        @ValueSource(strings = {"++", "--", "3 *", "(3 + 4) *", "/", "3+4 / ", "+ 2"})
        void operatorParse(String input) {
            Exception exception = assertThrows(ExpressionParseException.class, () ->
                    parser.parseExpression(input));

        }

        @Test
        @DisplayName("Unary operations are not supported")
        void unaryOperation() {
            Exception exception = assertThrows(ExpressionParseException.class, () ->
                    parser.parseExpression("+"));
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class ComplexExpressionTests {
        @Test
        @DisplayName("Summary of 3 + 4")
        void simpleSum() throws ExpressionParseException {
            String input = "3+4";
            Expression output = new BinaryExpressionImpl(BinOpKind.SUM, new LiteralImpl("3"), new LiteralImpl("4"));
            var expr = parser.parseExpression(input);
            assertEquals(expr.accept(debugVisitor), output.accept(debugVisitor));
        }

        @Test
        @DisplayName("Summary of (3 + 4)*5")
        void complex1() throws ExpressionParseException {
            String input = "(3+4)*5";
            Expression output = new BinaryExpressionImpl(BinOpKind.MUL, new ParenthesisExpressionImpl(
                    new BinaryExpressionImpl(BinOpKind.SUM, new LiteralImpl("3"), new LiteralImpl("4"))),
                    new LiteralImpl("5"));
            var expr = parser.parseExpression(input);
            assertEquals(expr.accept(debugVisitor), output.accept(debugVisitor));
        }

        @Test
        @DisplayName("Summary of (5 * 6 / 1) / ((4 - 0) - 5)")
        void complex2() throws ExpressionParseException {
            String input = "(5 * 6 / 1) / ((4 - 0) - 5)";
            Expression output = new BinaryExpressionImpl(BinOpKind.DIV, new ParenthesisExpressionImpl(
                    new BinaryExpressionImpl(BinOpKind.DIV, new BinaryExpressionImpl(BinOpKind.MUL, new LiteralImpl("5"),
                            new LiteralImpl("6")), new LiteralImpl("1"))), new ParenthesisExpressionImpl(
                                    new BinaryExpressionImpl(BinOpKind.SUB, new ParenthesisExpressionImpl(new BinaryExpressionImpl(BinOpKind.SUB,
                                            new LiteralImpl("4"), new LiteralImpl("0"))), new LiteralImpl("5"))));
            var expr = parser.parseExpression(input);
            assertEquals(expr.accept(debugVisitor), output.accept(debugVisitor));
        }
    }


}