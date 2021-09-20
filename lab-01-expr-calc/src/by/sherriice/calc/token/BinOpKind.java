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
            return "div";
        }
    },
    UNKNOWN;

    public static BinOpKind fromString(String input) {
        if ("+".equals(input)) {
            return BinOpKind.SUM;
        }
        if ("-".equals(input)) {
            return BinOpKind.SUB;
        }
        if ("*".equals(input)) {
            return BinOpKind.MUL;
        }
        if ("/".equals(input)) {
            return BinOpKind.DIV;
        }
        return BinOpKind.UNKNOWN;
    }
}
