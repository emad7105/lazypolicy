package be.heydari.converters.querydsl;

import be.heydari.expressions.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.Data;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Emad Heydari Beni
 */
@Data
public class QueryDslVisitor implements Visitor {
    private static Logger logger = Logger.getLogger(QueryDslVisitor.class.getName());

    @Override
    public <E> BooleanExpression visit(Disjunction disjunction, Class<E> entityType) {
        List<Conjunction> predicates = disjunction.getConjunctivePredicates();

        return predicates.stream().reduce(Expressions.FALSE,
                (agg, predicate) -> agg.or(visit(predicate, entityType)),
                BooleanExpression::or
        );
    }

    @Override
    public <E> BooleanExpression visit(Conjunction conjunction, Class<E> entityType) {
        List<BoolPredicate> predicates = conjunction.getPredicates();

        return predicates.stream().reduce(Expressions.TRUE,
                (agg, predicate) -> agg.and(visit(predicate, entityType)),
                BooleanExpression::and
        );
    }

    @Override
    public <T,E> BooleanExpression visit(BoolPredicate<T> predicate, Class<E> entityType) {
        RefExpression left = predicate.getLeft();
        GenericExpression<T> right = predicate.getRight();
        ComparisonOperator operator = predicate.getOperator();

        PathBuilder path = visit(left, entityType);

        switch (operator) {
            case EQ:
                return path.eq(right.getValue());
            case NEQ:
                return path.ne(right.getValue());
            case GTE:
                // todo: implement
                break;
            case GT:
                // todo: implement
                break;
            case LTE:
                // todo: implement
                break;
            case LT:
                // todo: implement
                break;
            default:
                logger.warning("Unknown operation!");
                break;
        }
        return null;
    }

    @Override
    public <E> PathBuilder visit(RefExpression refExpression, Class<E> entityType) {
        // TODO: only Long
        PathBuilder entityPath = new PathBuilder(entityType, "entity");
        return entityPath.get(refExpression.getColumn(), Long.class);
    }

}
