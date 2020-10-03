package be.heydari.lib.converters.jpql;

import be.heydari.lib.expressions.*;
import be.heydari.lib.converters.Converter;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

public class JPQLConverter implements Converter<String, StringBuilder, StringBuilder, StringBuilder, StringBuilder> {
    private static Logger logger = Logger.getLogger(JPQLConverter.class.getName());

    @Override
    public StringBuilder convert(Disjunction disjunction, String alias) {
        StringBuilder jpqlDisjunctions = new StringBuilder();

        if (disjunction.hasConjunctivePredicates()) {
            List<Conjunction> conjunctions = disjunction.getConjunctivePredicates();

            Iterator<Conjunction> it = conjunctions.iterator();
            while (it.hasNext()) {
                Conjunction conjunction = it.next();

                StringBuilder jpqlConjunction = convert(conjunction, alias);
                jpqlDisjunctions.append(format(" %s ", jpqlConjunction));

                if (it.hasNext()) {
                    jpqlDisjunctions.append(" OR ");
                }
            }

            // brackets
            jpqlDisjunctions.insert(0, "(");
            jpqlDisjunctions.append(")");
        }

        return jpqlDisjunctions;
    }

    @Override
    public StringBuilder convert(Conjunction conjunction, String alias) {
        StringBuilder jpqlConjunctions = new StringBuilder();

        if (conjunction.hasBooleanPredicates()) {
            List<BoolPredicate> predicates = conjunction.getPredicates();

            Iterator<BoolPredicate> it = predicates.iterator();
            while (it.hasNext()) {
                BoolPredicate boolPredicate = it.next();

                StringBuilder jpqlPredicate = convert(boolPredicate, alias);
                jpqlConjunctions.append(format(" %s ", jpqlPredicate));

                if (it.hasNext()) {
                    jpqlConjunctions.append(" AND ");
                }
            }

            // brackets
            jpqlConjunctions.insert(0, "(");
            jpqlConjunctions.append(")");
        }

        return jpqlConjunctions;
    }

    @Override
    public <TValueType> StringBuilder convert(BoolPredicate<TValueType> predicate, String alias) {
        RefExpression left = predicate.getLeft();
        GenericExpression<TValueType> right = predicate.getRight();
        ComparisonOperator operator = predicate.getOperator();

        StringBuilder jpqlPredicate = convert(left, alias);

        switch (operator) {
            case EQ:
                return jpqlPredicate.append(format(" = %s ", String.valueOf(right.getValue())));
            case NEQ:
                return jpqlPredicate.append(format(" <> %s ", String.valueOf(right.getValue())));
            case GTE:
                return jpqlPredicate.append(format(" >= %s ", String.valueOf(right.getValue())));
            case GT:
                return jpqlPredicate.append(format(" > %s ", String.valueOf(right.getValue())));
            case LTE:
                return jpqlPredicate.append(format(" <= %s ", String.valueOf(right.getValue())));
            case LT:
                return jpqlPredicate.append(format(" < %s ", String.valueOf(right.getValue())));
            default:
                logger.warning("Unknown operation!");
                break;
        }
        return null;

    }

    @Override
    public StringBuilder convert(RefExpression refExpression, String alias) {
        return new StringBuilder(format("%s.%s",
                alias == null ? refExpression.getTable() : alias,
                refExpression.getColumn()));
    }
}
