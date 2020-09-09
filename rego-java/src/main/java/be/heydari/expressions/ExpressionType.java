package be.heydari.expressions;


import be.heydari.ast.NumberType;

public enum ExpressionType {
    BOOLEAN {
        @Override
        public String asLowerCase() {
            return "boolean";
        }
    },
    STRING {
        @Override
        public String asLowerCase() {
            return "string";
        }
    },

    /* numerics */
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

    public static ExpressionType fromNumerType(NumberType numberType) {
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

    public static ExpressionType from(String numType) {
        switch (numType) {
            case "i":
                return ExpressionType.INT;
            case "l":
                return ExpressionType.LONG;
            case "f":
                return ExpressionType.FLOAT;
            case "d":
                return ExpressionType.DOUBLE;
            default:
                return null;
        }
    }

}
