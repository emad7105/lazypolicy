package be.heydari.converters.jpql;

import be.heydari.expressions.*;
import be.heydari.converters.Visitor;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

public class JPQLVisitor implements Visitor<String, StringBuilder, StringBuilder, StringBuilder, StringBuilder> {
    private static Logger logger = Logger.getLogger(JPQLVisitor.class.getName());

    @Override
    public StringBuilder visit(Disjunction disjunction, String entity) {
        StringBuilder jpqlDisjunctions = new StringBuilder();

        if (disjunction.hasConjunctivePredicates()) {
            List<Conjunction> conjunctions = disjunction.getConjunctivePredicates();

            Iterator<Conjunction> it = conjunctions.iterator();
            while (it.hasNext()) {
                Conjunction conjunction = it.next();

                StringBuilder jpqlConjunction = visit(conjunction, entity);
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
    public StringBuilder visit(Conjunction conjunction, String entity) {
        StringBuilder jpqlConjunctions = new StringBuilder();

        if (conjunction.hasBooleanPredicates()) {
            List<BoolPredicate> predicates = conjunction.getPredicates();

            Iterator<BoolPredicate> it = predicates.iterator();
            while (it.hasNext()) {
                BoolPredicate boolPredicate = it.next();

                StringBuilder jpqlPredicate = visit(boolPredicate, entity);
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
    public <TValueType> StringBuilder visit(BoolPredicate<TValueType> predicate, String entity) {
        RefExpression left = predicate.getLeft();
        GenericExpression<TValueType> right = predicate.getRight();
        ComparisonOperator operator = predicate.getOperator();

        StringBuilder jpqlPredicate = visit(left, entity);

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
    public StringBuilder visit(RefExpression refExpression, String entity) {
        return new StringBuilder(format("%s.%s",refExpression.getTable(),refExpression.getColumn()));
    }
}
