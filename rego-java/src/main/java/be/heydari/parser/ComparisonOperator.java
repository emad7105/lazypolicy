package be.heydari.parser;

import lombok.Data;

/**
 * operators:
 *  - eq =
 *  - neq <>
 *  - gte >=
 *  - gt >
 *  - lt <
 *  - lte <=
 *
 * @author Emad Heydari Beni
 */
public enum ComparisonOperator {
    EQ{
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
}

