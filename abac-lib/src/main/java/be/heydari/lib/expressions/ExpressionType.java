package be.heydari.lib.expressions;



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

        @Override
        public Boolean isNumber() {
            return false;
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

        @Override
        public Boolean isNumber() {
            return false;
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

        @Override
        public Boolean isNumber() {
            return true;
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

        @Override
        public Boolean isNumber() {
            return true;
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

        @Override
        public Boolean isNumber() {
            return true;
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

        @Override
        public Boolean isNumber() {
            return true;
        }
    };

    public abstract String asLowerCase();
    public abstract Class getJavaClass();
    public abstract Boolean isNumber();


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
