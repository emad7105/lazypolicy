package be.heydari.expressions;

/**
 * operators:
 * - eq =
 * - neq <>
 * - gte >=
 * - gt >
 * - lt <
 * - lte <=
 *
 * @author Emad Heydari Beni
 */
public enum ComparisonOperator {
    EQ {
        @Override
        public String asLowerCase() {
            return EQ.toString().toLowerCase();
        }
    },
    NEQ {
        @Override
        public String asLowerCase() {
            return NEQ.toString().toLowerCase();
        }
    },
    GTE {
        @Override
        public String asLowerCase() {
            return GTE.toString().toLowerCase();
        }
    },
    GT {
        @Override
        public String asLowerCase() {
            return GT.toString().toLowerCase();
        }
    },
    LTE {
        @Override
        public String asLowerCase() {
            return LTE.toString().toLowerCase();
        }
    },
    LT {
        @Override
        public String asLowerCase() {
            return LT.toString().toLowerCase();
        }
    };

    public abstract String asLowerCase();

    public static ComparisonOperator from(String operator) {
        switch (operator) {
            case "eq"://"=":
                return ComparisonOperator.EQ;
            case "neq"://"!=":
                return ComparisonOperator.NEQ;
            case "lt"://"<":
                return ComparisonOperator.LT;
            case "gt"://">":
                return ComparisonOperator.GT;
            case "lte"://"<=":
                return ComparisonOperator.LTE;
            case "gte"://">=":
                return ComparisonOperator.GTE;
            default:
                return null;
        }
    }
}

