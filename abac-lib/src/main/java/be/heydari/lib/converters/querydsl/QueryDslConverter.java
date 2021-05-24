package be.heydari.lib.converters.querydsl;

import be.heydari.lib.converters.Converter;
import be.heydari.lib.expressions.*;
import com.querydsl.core.types.*;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author Emad Heydari Beni
 */
@Data
public class QueryDslConverter {
    private static Logger logger = Logger.getLogger(QueryDslConverter.class.getName());


    public BooleanExpression convert(Disjunction disjunction, PathBuilder entityPath, Class entityType) {
        List<Conjunction> conjunctivePredicates = disjunction.getConjunctivePredicates();

        if (disjunction.hasConjunctivePredicates()) {
            BooleanExpression mixin = null;
            for (Conjunction conjunctivePredicate : conjunctivePredicates) {
                BooleanExpression right = convert(conjunctivePredicate, entityPath, entityType);
                if (mixin == null) {
                    mixin = right;
                } else {
                    mixin = Expressions.booleanOperation(Ops.OR, mixin, right);
                }
            }
            return mixin;
            /*List<BooleanExpression> conjunctiveExpressions = new ArrayList<>();
            for(Conjunction conjunctivePredicate : conjunctivePredicates) {
                conjunctiveExpressions.add(convert(conjunctivePredicate, entityPath, entityType));
            }
            return Expressions.booleanOperation(Ops.OR, conjunctiveExpressions.stream().toArray(BooleanExpression[]::new));*/
            /*return conjunctivePredicates.stream().reduce(Expressions.FALSE,
                    (agg, conjunctivePredicate) -> agg.or(convert(conjunctivePredicate, entityPath, entityType)),
                    BooleanExpression::or
            );*/
        } else {
            return Expressions.TRUE;
        }
    }

    public BooleanExpression convert(Conjunction conjunction, PathBuilder entityPath, Class entityType) {
        List<BoolPredicate> predicates = conjunction.getPredicates();

        if (conjunction.hasBooleanPredicates()) {
            BooleanExpression mixin = null;
            for(BoolPredicate boolPredicate : predicates) {
                BooleanExpression right = convert(boolPredicate, entityPath, entityType);
                if (mixin == null) {
                    mixin = right;
                } else {
                    mixin = Expressions.booleanOperation(Ops.AND, mixin, right);
                }
            }
            return mixin;
            /*List<BooleanExpression> expressions = new ArrayList<>();
            for(BoolPredicate boolPredicate : predicates) {
                expressions.add(convert(boolPredicate, entityPath, entityType));
            }
            return Expressions.booleanOperation(Ops.AND, expressions.stream().toArray(BooleanExpression[]::new));*/
            /*return predicates.stream().reduce(Expressions.TRUE,
                    (agg, predicate) -> agg.and(convert(predicate, entityPath, entityType)),
                    BooleanExpression::and
            );*/
        } else {
            return Expressions.FALSE;
        }
    }

    public <TValueType> BooleanExpression convert(BoolPredicate<TValueType> predicate, PathBuilder entityPath, Class entityType) {
        RefExpression left = predicate.getLeft();
        GenericExpression<TValueType> right = predicate.getRight();
        ComparisonOperator operator = predicate.getOperator();

        ComparablePath refExpComparablePath = convert(left, entityPath, entityType, right.getType().getJavaClass());
        switch (operator) {
            case EQ:
                return refExpComparablePath.eq(right.getValue());
            case NEQ:
                return refExpComparablePath.ne(right.getValue());
            case GTE:
                return refExpComparablePath.goe(Expressions.constant(right.getValue()));
            case GT:
                return refExpComparablePath.gt(Expressions.constant(right.getValue()));
            case LTE:
                return refExpComparablePath.loe(Expressions.constant(right.getValue()));
            case LT:
                return refExpComparablePath.lt(Expressions.constant(right.getValue()));
            default:
                logger.warning(format("The operator %s is not supported!", operator.asLowerCase()));
                break;
        }

        return null;
    }

    public ComparablePath convert(RefExpression refExpression, PathBuilder entityPath, Class entityType, Class columnType) {
        return entityPath.getComparable(refExpression.getColumn(), columnType);
    }
}
