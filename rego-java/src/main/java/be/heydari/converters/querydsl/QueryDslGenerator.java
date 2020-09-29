package be.heydari.converters.querydsl;

import be.heydari.converters.protobuf.generated.*;
import be.heydari.expressions.ComparisonOperator;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;

import java.util.logging.Logger;

import static java.lang.String.format;

public class QueryDslGenerator {
    private static Logger logger = Logger.getLogger(QueryDslGenerator.class.getName());


    public BooleanExpression process(PDisjunction pDisjunction, PathBuilder entityPath, Class entityType) {
        BooleanExpression agg = Expressions.FALSE;

        if (pDisjunction.getConjunctionsCount() > 0) {
            for (PConjunction pConjunction : pDisjunction.getConjunctionsList()) {
                agg = agg.or(processConjunction(pConjunction, entityPath, entityType));
            }
            return agg;
        }
        return Expressions.TRUE;
    }

    private Predicate processConjunction(PConjunction pConjunction, PathBuilder entityPath, Class entityType) {
        BooleanExpression agg = Expressions.TRUE;
        Boolean predicateExists = false;

        if (pConjunction.getBooleanPredicatesCount() > 0) {
            predicateExists = true;
            for (PBooleanPredicate pBooleanPredicate : pConjunction.getBooleanPredicatesList()) {
                agg = agg.and(processBooleanPredicate(pBooleanPredicate, entityPath, entityType));
            }
        }

        if (pConjunction.getStringPredicatesCount() > 0) {
            predicateExists = true;
            for ( PStringPredicate pStringPredicate : pConjunction.getStringPredicatesList()) {
                agg = agg.and(processStringPredicate(pStringPredicate, entityPath, entityType));
            }
        }

        if (pConjunction.getIntPredicatesCount() > 0) {
            predicateExists = true;
            for (PIntPredicate pIntPredicate : pConjunction.getIntPredicatesList()){
                BooleanExpression predicate = processIntPredicate(pIntPredicate, entityPath, entityType);
                agg = agg.and(predicate);
            }
        }

        if (pConjunction.getDoublePredicatesCount() > 0) {
            predicateExists = true;
            for (PDoublePredicate pDoublePredicate : pConjunction.getDoublePredicatesList()) {
                agg = agg.and(processDoublePredicate(pDoublePredicate, entityPath, entityType));
            }
        }

        if (pConjunction.getFloatPredicatesCount() > 0) {
            predicateExists = true;
            for (PFloatPredicate pFloatPredicate : pConjunction.getFloatPredicatesList()) {
                agg = agg.and(processFloatPredicate(pFloatPredicate, entityPath, entityType));
            }
        }

        if (pConjunction.getLongPredicatesCount() > 0) {
            predicateExists = true;
            for (PLongPredicate pLongPredicate : pConjunction.getLongPredicatesList()) {
                agg = agg.and(processLongPredicate(pLongPredicate, entityPath, entityType));
            }
        }


        return predicateExists ? agg : Expressions.FALSE;
    }

    private BooleanExpression processLongPredicate(PLongPredicate pLongPredicate, PathBuilder entityPath, Class entityType) {
        PRefExpression left = pLongPredicate.getLeft();
        Long right = pLongPredicate.getRight();
        ComparisonOperator operator = ComparisonOperator.from(pLongPredicate.getOperator());

        assert operator != null;
        return processPredicate(left, operator, right, entityPath, entityType);
    }

    private BooleanExpression processFloatPredicate(PFloatPredicate pFloatPredicate, PathBuilder entityPath, Class entityType) {
        PRefExpression left = pFloatPredicate.getLeft();
        Float right = pFloatPredicate.getRight();
        ComparisonOperator operator = ComparisonOperator.from(pFloatPredicate.getOperator());

        assert operator != null;
        return processPredicate(left, operator, right, entityPath, entityType);
    }

    private BooleanExpression processDoublePredicate(PDoublePredicate pDoublePredicate, PathBuilder entityPath, Class entityType) {
        PRefExpression left = pDoublePredicate.getLeft();
        Double right = pDoublePredicate.getRight();
        ComparisonOperator operator = ComparisonOperator.from(pDoublePredicate.getOperator());

        assert operator != null;
        return processPredicate(left, operator, right, entityPath, entityType);
    }

    private BooleanExpression processIntPredicate(PIntPredicate pIntPredicate, PathBuilder entityPath, Class entityType) {
        PRefExpression left = pIntPredicate.getLeft();
        Integer right = pIntPredicate.getRight();
        ComparisonOperator operator = ComparisonOperator.from(pIntPredicate.getOperator());

        assert operator != null;
        return processPredicate(left, operator, right, entityPath, entityType);
    }

    private BooleanExpression processStringPredicate(PStringPredicate pStringPredicate, PathBuilder entityPath, Class entityType) {
        PRefExpression left = pStringPredicate.getLeft();
        String right = pStringPredicate.getRight();
        ComparisonOperator operator = ComparisonOperator.from(pStringPredicate.getOperator());

        assert operator != null;
        return processPredicate(left, operator, right, entityPath, entityType);
    }

    private BooleanExpression processBooleanPredicate(PBooleanPredicate pBooleanPredicate, PathBuilder entityPath, Class entityType) {
        PRefExpression left = pBooleanPredicate.getLeft();
        Boolean right = pBooleanPredicate.getRight();
        ComparisonOperator operator = ComparisonOperator.from(pBooleanPredicate.getOperator());

        assert operator != null;
        return processPredicate(left, operator, right, entityPath, entityType);
    }

    private <T> BooleanExpression processPredicate(PRefExpression left, ComparisonOperator operator, T rightValue, PathBuilder entityPath, Class entityType) {
        ComparablePath refExpComparablePath = processRefExpression(left,  entityPath, entityType, rightValue.getClass());
        switch (operator) {
            case EQ:
                return refExpComparablePath.eq(rightValue);
            case NEQ:
                return refExpComparablePath.ne(rightValue);
            case GTE:
                return refExpComparablePath.goe(Expressions.constant(rightValue));
            case GT:
                return refExpComparablePath.gt(Expressions.constant(rightValue));
            case LTE:
                return refExpComparablePath.loe(Expressions.constant(rightValue));
            case LT:
                return refExpComparablePath.lt(Expressions.constant(rightValue));
            default:
                logger.warning(format("The operator %s is not supported!", operator.asLowerCase()));
                break;
        }
        return null;
    }

    private ComparablePath processRefExpression(PRefExpression left, PathBuilder entityPath, Class entityType, Class columnType) {
        String column = left.getColumn();
        String table = left.getTable();
        //PathBuilder entityPath = new PathBuilder(entityType, "entity");
        return entityPath.getComparable(column, columnType);
    }

}
