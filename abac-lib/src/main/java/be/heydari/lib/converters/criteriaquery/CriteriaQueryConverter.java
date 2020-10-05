package be.heydari.lib.converters.criteriaquery;

import be.heydari.lib.expressions.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CriteriaQueryConverter {
    private static Logger logger = Logger.getLogger(CriteriaQueryConverter.class.getName());


    public Predicate convert(Disjunction disjunction, Root root, CriteriaBuilder cb) {
        return null;
    }

    public Predicate convert(Conjunction conjunction, Root root, CriteriaBuilder cb) {
        List<BoolPredicate> predicates = conjunction.getPredicates();

        Predicate conjunctionResult = null;

        if (conjunction.hasBooleanPredicates()) {
            List<Predicate> cbPredicates = predicates.stream().map(p -> convert(p, root, cb)).collect(Collectors.toList());
            conjunctionResult = cb.and(cbPredicates.toArray(Predicate[]::new));
        }

        return conjunctionResult;
    }

    public <TValueType> Predicate convert(BoolPredicate<TValueType> predicate, Root root, CriteriaBuilder cb) {
        RefExpression left = predicate.getLeft();
        GenericExpression<TValueType> right = predicate.getRight();
        ComparisonOperator operator = predicate.getOperator();

        Path path = convert(left, root);
        switch (operator) {
            case EQ:
                return cb.equal(path, right.getValue());
            case NEQ:
                return cb.notEqual(path, right.getValue());
            case GTE:
                if (right.getType().isNumber()) {
                    return cb.greaterThanOrEqualTo(path, (Comparable) right.getValue());
                } else {
                    logger.warning(format("The operator %s supports only numbers! %s is not a number.", operator.asLowerCase(), right.getValue()));
                    return null;
                }
            case GT:
                if (right.getType().isNumber()) {
                    return cb.greaterThan(path, (Comparable) right.getValue());
                } else {
                    logger.warning(format("The operator %s supports only numbers! %s is not a number.", operator.asLowerCase(), right.getValue()));
                    return null;
                }
            case LTE:
                if (right.getType().isNumber()) {
                    return cb.lessThanOrEqualTo(path, (Comparable) right.getValue());
                } else {
                    logger.warning(format("The operator %s supports only numbers! %s is not a number.", operator.asLowerCase(), right.getValue()));
                    return null;
                }
            case LT:
                if (right.getType().isNumber()) {
                    return cb.lessThan(path, (Comparable) right.getValue());
                } else {
                    logger.warning(format("The operator %s supports only numbers! %s is not a number.", operator.asLowerCase(), right.getValue()));
                    return null;
                }
            default:
                logger.warning(format("The operator %s is not supported!", operator.asLowerCase()));
                break;
        }

        return null;
    }

    public Path convert(RefExpression refExpression, Root root) {
        String entity = refExpression.getTable();
        String field = refExpression.getColumn();

        String[] fieldSegments = field.split("\\.");
        From f = root;

        // inner join of attributes, if need be
        int i = 0;
        for (; i < fieldSegments.length - 1; i++) {
            f = f.join(fieldSegments[i]);
        }

        return f.get(fieldSegments[fieldSegments.length - 1]);
    }
}
