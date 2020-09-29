package be.heydari.converters.querydsl;

import be.heydari.converters.protobuf.generated.*;
import be.heydari.expressions.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.*;
import lombok.Data;

import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * @author Emad Heydari Beni
 */
@Data
public class QueryDslVisitor2 implements be.heydari.converters.Visitor<Class, BooleanExpression, BooleanExpression, BooleanExpression, ComparablePath> {
    private static Logger logger = Logger.getLogger(QueryDslVisitor2.class.getName());


    @Override
    public BooleanExpression visit(Disjunction disjunction, Class entityType) {
        List<Conjunction> conjunctivePredicates = disjunction.getConjunctivePredicates();

        if (disjunction.hasConjunctivePredicates()) {
            return conjunctivePredicates.stream().reduce(Expressions.FALSE,
                    (agg, conjunctivePredicate) -> agg.or(visit(conjunctivePredicate, entityType)),
                    BooleanExpression::or
            );
        } else {
            return Expressions.TRUE;
        }
    }

    @Override
    public BooleanExpression visit(Conjunction conjunction, Class entityType) {
        List<BoolPredicate> predicates = conjunction.getPredicates();

        if (conjunction.hasBooleanPredicates()) {
            return predicates.stream().reduce(Expressions.TRUE,
                    (agg, predicate) -> agg.and(visit(predicate, entityType)),
                    BooleanExpression::and
            );
        } else {
            return Expressions.FALSE;
        }
    }

    @Override
    public <TValueType> BooleanExpression visit(BoolPredicate<TValueType> predicate, Class entityType) {
        RefExpression left = predicate.getLeft();
        GenericExpression<TValueType> right = predicate.getRight();
        ComparisonOperator operator = predicate.getOperator();

        ComparablePath refExpComparablePath = visit(left, entityType, right.getType().getJavaClass());
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

    @Override
    public ComparablePath visit(RefExpression refExpression, Class aClass) {
        throw new IllegalArgumentException("There is no default column type; this method is not supported for the BooleanExpression converter!");
    }

    public ComparablePath visit(RefExpression refExpression, Class entityType, Class columnType) {
        PathBuilder entityPath = new PathBuilder(entityType, "entity");
        return entityPath.getComparable(refExpression.getColumn(), columnType);
    }
}
