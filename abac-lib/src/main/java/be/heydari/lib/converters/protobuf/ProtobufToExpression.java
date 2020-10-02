package be.heydari.lib.converters.protobuf;

import be.heydari.lib.converters.protobuf.generated.*;
import be.heydari.lib.expressions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProtobufToExpression{
    private static Logger logger = Logger.getLogger(ProtobufToExpression.class.getName());


    public Disjunction convert(PDisjunction pDisjunction, String entity) {
        Disjunction.DisjunctionBuilder builder = Disjunction.builder();

        List<Conjunction> conjunctions = new ArrayList<>();
        for (PConjunction pConjunction : pDisjunction.getConjunctionsList()) {
            Conjunction convert = convertConjunction(pConjunction, entity);
            conjunctions.add(convert);
        }

        return Disjunction.builder()
                .conjunctivePredicates(conjunctions)
                .build();
    }

    private Conjunction convertConjunction(PConjunction pConjunction, String entity) {
        List<BoolPredicate> predicates = new ArrayList<>();

        if (pConjunction.getBooleanPredicatesCount() > 0) {
            for (PBooleanPredicate pBooleanPredicate : pConjunction.getBooleanPredicatesList()) {
                predicates.add(convertBooleanPredicate(pBooleanPredicate, entity));
            }
        }

        if (pConjunction.getStringPredicatesCount() > 0) {
            for ( PStringPredicate pStringPredicate : pConjunction.getStringPredicatesList()) {
                predicates.add(convertStringPredicate(pStringPredicate, entity));
            }
        }

        if (pConjunction.getIntPredicatesCount() > 0) {
            for (PIntPredicate pIntPredicate : pConjunction.getIntPredicatesList()){
                predicates.add(convertIntPredicate(pIntPredicate, entity));
            }
        }

        if (pConjunction.getDoublePredicatesCount() > 0) {
            for (PDoublePredicate pDoublePredicate : pConjunction.getDoublePredicatesList()) {
                predicates.add(convertDoublePredicate(pDoublePredicate, entity));
            }
        }

        if (pConjunction.getFloatPredicatesCount() > 0) {
            for (PFloatPredicate pFloatPredicate : pConjunction.getFloatPredicatesList()) {
                predicates.add(convertFloatPredicate(pFloatPredicate, entity));
            }
        }

        if (pConjunction.getLongPredicatesCount() > 0) {
            for (PLongPredicate pLongPredicate : pConjunction.getLongPredicatesList()) {
                predicates.add(convertLongPredicate(pLongPredicate, entity));
            }
        }

        return Conjunction.builder()
                .predicates(predicates)
                .build();
    }

    private BoolPredicate convertLongPredicate(PLongPredicate pLongPredicate, String entity) {
        PRefExpression left = pLongPredicate.getLeft();
        ComparisonOperator operator = ComparisonOperator.from(pLongPredicate.getOperator());


        Long right = pLongPredicate.getRight();
        GenericExpression<Long> longExpression = new GenericExpression<>();
        longExpression.setValue(right);
        longExpression.setType(ExpressionType.LONG);

        assert operator != null;
        return BoolPredicate.<Long>builder()
                .left(convertRefExpression(left, entity))
                .operator(operator)
                .right(longExpression)
                .build();
    }

    private BoolPredicate convertFloatPredicate(PFloatPredicate pFloatPredicate, String entity) {
        PRefExpression left = pFloatPredicate.getLeft();
        ComparisonOperator operator = ComparisonOperator.from(pFloatPredicate.getOperator());


        Float right = pFloatPredicate.getRight();
        GenericExpression<Float> floatExpression = new GenericExpression<>();
        floatExpression.setValue(right);
        floatExpression.setType(ExpressionType.FLOAT);

        assert operator != null;
        return BoolPredicate.<Float>builder()
                .left(convertRefExpression(left, entity))
                .operator(operator)
                .right(floatExpression)
                .build();
    }

    private BoolPredicate convertDoublePredicate(PDoublePredicate pDoublePredicate, String entity) {
        PRefExpression left = pDoublePredicate.getLeft();
        ComparisonOperator operator = ComparisonOperator.from(pDoublePredicate.getOperator());


        Double right = pDoublePredicate.getRight();
        GenericExpression<Double> doubleExpression = new GenericExpression<>();
        doubleExpression.setValue(right);
        doubleExpression.setType(ExpressionType.DOUBLE);

        assert operator != null;
        return BoolPredicate.<Double>builder()
                .left(convertRefExpression(left, entity))
                .operator(operator)
                .right(doubleExpression)
                .build();
    }

    private BoolPredicate convertIntPredicate(PIntPredicate pIntPredicate, String entity) {
        PRefExpression left = pIntPredicate.getLeft();
        ComparisonOperator operator = ComparisonOperator.from(pIntPredicate.getOperator());


        Integer right = pIntPredicate.getRight();
        GenericExpression<Integer> integerExpression = new GenericExpression<>();
        integerExpression.setValue(right);
        integerExpression.setType(ExpressionType.INT);

        assert operator != null;
        return BoolPredicate.<Integer>builder()
                .left(convertRefExpression(left, entity))
                .operator(operator)
                .right(integerExpression)
                .build();
    }

    private BoolPredicate convertStringPredicate(PStringPredicate pStringPredicate, String entity) {
        PRefExpression left = pStringPredicate.getLeft();
        ComparisonOperator operator = ComparisonOperator.from(pStringPredicate.getOperator());

        String right = pStringPredicate.getRight();
        GenericExpression<String> stringExpression = new GenericExpression<>();
        stringExpression.setValue(right);
        stringExpression.setType(ExpressionType.STRING);

        assert operator != null;
        return BoolPredicate.<String>builder()
                .left(convertRefExpression(left, entity))
                .operator(operator)
                .right(stringExpression)
                .build();
    }

    private BoolPredicate convertBooleanPredicate(PBooleanPredicate pBooleanPredicate, String entity) {
        PRefExpression left = pBooleanPredicate.getLeft();
        ComparisonOperator operator = ComparisonOperator.from(pBooleanPredicate.getOperator());

        Boolean right = pBooleanPredicate.getRight();
        GenericExpression<Boolean> booleanExpression = new GenericExpression<>();
        booleanExpression.setValue(right);
        booleanExpression.setType(ExpressionType.BOOLEAN);

        assert operator != null;
        return BoolPredicate.<Boolean>builder()
                .left(convertRefExpression(left, entity))
                .operator(operator)
                .right(booleanExpression)
                .build();
    }

    private RefExpression convertRefExpression(PRefExpression left, String entity) {
        return RefExpression.builder()
                .table(left.getTable())
                .column(left.getColumn())
                .build();
    }

}
