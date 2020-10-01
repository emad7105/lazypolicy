package be.heydari.lib.expressions;

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
            return "eq";
        }
    },
    NEQ {
        @Override
        public String asLowerCase() {
            return "neq";
        }
    },
    GTE {
        @Override
        public String asLowerCase() {
            return "gte";
        }
    },
    GT {
        @Override
        public String asLowerCase() {
            return "gt";
        }
    },
    LTE {
        @Override
        public String asLowerCase() {
            return "lte";
        }
    },
    LT {
        @Override
        public String asLowerCase() {
            return "lt";
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

