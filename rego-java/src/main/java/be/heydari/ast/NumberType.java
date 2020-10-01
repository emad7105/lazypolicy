package be.heydari.ast;

import be.heydari.lib.expressions.ExpressionType;

/**
 * @author Emad Heydari Beni
 */
public enum NumberType {
    INT {
        @Override
        public String asLowerCase() {
            return "i";
        }
    },
    LONG {
        @Override
        public String asLowerCase() {
            return "l";
        }
    },
    FLOAT {
        @Override
        public String asLowerCase() {
            return "f";
        }
    },
    DOUBLE {
        @Override
        public String asLowerCase() {
            return "d";
        }
    };

    public abstract String asLowerCase();

    public static NumberType from(String operator) {
        switch (operator) {
            case "i":
                return NumberType.INT;
            case "l":
                return NumberType.LONG;
            case "f":
                return NumberType.FLOAT;
            case "d":
                return NumberType.DOUBLE;
            default:
                return null;
        }
    }

    public static ExpressionType toExpressionType(NumberType numberType) {
        switch (numberType) {
            case INT:
                return ExpressionType.INT;
            case LONG:
                return ExpressionType.LONG;
            case FLOAT:
                return ExpressionType.FLOAT;
            case DOUBLE:
                return ExpressionType.DOUBLE;
            default:
                return null;
        }
    }
}

