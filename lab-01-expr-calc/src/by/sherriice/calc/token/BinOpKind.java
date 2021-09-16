package by.sherriice.calc.token;

public enum BinOpKind {
    SUM() {
        @Override
        public String toString() {
            return "sum";
        }
    },
    SUB() {
        @Override
        public String toString() {
            return "sub";
        }
    },
    MUL() {
        @Override
        public String toString() {
            return "mul";
        }
    },
    DIV() {
        @Override
        public String toString() {
            return "div{}";
        }
    },
    UNKNOWN;

    public static BinOpKind convertOperation(String input) {
        if (input.equals("+")) {
            return BinOpKind.SUM;
        }
        if (input.equals("-")) {
            return BinOpKind.SUB;
        }
        if (input.equals("*")) {
            return BinOpKind.MUL;
        }
        if (input.equals("/")) {
            return BinOpKind.DIV;
        }
        return BinOpKind.UNKNOWN;
    }
}
