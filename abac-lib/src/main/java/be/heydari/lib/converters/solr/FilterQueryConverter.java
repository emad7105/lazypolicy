package be.heydari.lib.converters.solr;

import be.heydari.lib.converters.Converter;
import be.heydari.lib.converters.jpql.JPQLConverter;
import be.heydari.lib.expressions.*;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

public class FilterQueryConverter implements Converter<String,StringBuilder,StringBuilder,StringBuilder,String> {
    private static Logger logger = Logger.getLogger(JPQLConverter.class.getName());

    @Override
    public StringBuilder convert(Disjunction disjunction, String entityType) {
        StringBuilder jpqlDisjunctions = new StringBuilder();

        if (disjunction.hasConjunctivePredicates()) {
            List<Conjunction> conjunctions = disjunction.getConjunctivePredicates();

            Iterator<Conjunction> it = conjunctions.iterator();
            while (it.hasNext()) {
                Conjunction conjunction = it.next();

                StringBuilder jpqlConjunction = convert(conjunction, entityType);
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
    public StringBuilder convert(Conjunction conjunction, String entityType) {
        StringBuilder jpqlConjunctions = new StringBuilder();

        if (conjunction.hasBooleanPredicates()) {
            List<BoolPredicate> predicates = conjunction.getPredicates();

            Iterator<BoolPredicate> it = predicates.iterator();
            while (it.hasNext()) {
                BoolPredicate boolPredicate = it.next();

                StringBuilder jpqlPredicate = convert(boolPredicate, entityType);
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
    public <TValueType> StringBuilder convert(BoolPredicate<TValueType> predicate, String entityType) {
        RefExpression left = predicate.getLeft();
        GenericExpression<TValueType> right = predicate.getRight();
        ComparisonOperator operator = predicate.getOperator();

        String leftStr = convert(left, entityType);
        StringBuilder jpqlPredicate = new StringBuilder();

        switch (operator) {
            case EQ:
                return jpqlPredicate.append(format("%s:%s", leftStr, right.getValue()));
            case NEQ:
                return jpqlPredicate.append(format("!%s:%s", leftStr, right.getValue()));
            case GTE:
                return jpqlPredicate.append(format("%s:[%s TO *]", leftStr, right.getValue()));
            case GT:
                logger.warning("The operation (GT) is not implemented for Solr.FilterQuery; it falls back on GTE");
                return jpqlPredicate.append(format("%s:[%s TO *]", leftStr, right.getValue()));
            case LTE:
                return jpqlPredicate.append(format("%s:[* TO %s]", leftStr, right.getValue()));
            case LT:
                logger.warning("The operation (LT) is not implemented for Solr.FilterQuery; it falls back on LTE");
                return jpqlPredicate.append(format("%s:[* TO %s]", leftStr, right.getValue()));
            default:
                logger.warning("Unknown operation!");
                break;
        }
        return null;
    }

    @Override
    public String convert(RefExpression refExpression, String entityType) {
        String table = refExpression.getTable();
        String column = refExpression.getColumn();
        //return format("%s_%s",table,column);
        return format("%s",column.replace('.','_'));
    }
}
