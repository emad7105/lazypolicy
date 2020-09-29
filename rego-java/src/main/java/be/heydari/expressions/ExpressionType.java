package be.heydari.expressions;


import be.heydari.ast.NumberType;

public enum ExpressionType {
    BOOLEAN {
        @Override
        public String asLowerCase() {
            return "boolean";
        }

        @Override
        public Class getJavaClass() {
            return Boolean.class;
        }
    },
    STRING {
        @Override
        public String asLowerCase() {
            return "string";
        }

        @Override
        public Class getJavaClass() {
            return String.class;
        }
    },

    /* numerics */
    INT {
        @Override
        public String asLowerCase() {
            return "i";
        }

        @Override
        public Class getJavaClass() {
            return Integer.class;
        }
    },
    LONG {
        @Override
        public String asLowerCase() {
            return "l";
        }

        @Override
        public Class getJavaClass() {
            return Long.class;
        }
    },
    FLOAT {
        @Override
        public String asLowerCase() {
            return "f";
        }

        @Override
        public Class getJavaClass() {
            return Float.class;
        }
    },
    DOUBLE {
        @Override
        public String asLowerCase() {
            return "d";
        }

        @Override
        public Class getJavaClass() {
            return Float.class;
        }
    };

    public abstract String asLowerCase();
    public abstract Class getJavaClass();

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
