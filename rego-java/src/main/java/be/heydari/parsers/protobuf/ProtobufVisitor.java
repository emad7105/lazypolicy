package be.heydari.parsers.protobuf;

import be.heydari.expressions.*;
import be.heydari.parsers.Visitor;
import be.heydari.parsers.jpql.JPQLVisitor;
import be.heydari.parsers.protobuf.generated.*;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

public class ProtobufVisitor implements Visitor<String, PDisjunction, PConjunction, Object, PRefExpression> {
    private static Logger logger = Logger.getLogger(ProtobufVisitor.class.getName());


    @Override
    public PDisjunction visit(Disjunction disjunction, String entity) {
        PDisjunction.Builder pDisjunctionBuilder = PDisjunction.newBuilder();
        if (disjunction.hasConjunctivePredicates()) {
            for (Conjunction conjunction : disjunction.getConjunctivePredicates()) {
                pDisjunctionBuilder.addConjunctions(visit(conjunction, entity));
            }
        }
        return pDisjunctionBuilder.build();
    }

    @Override
    public PConjunction visit(Conjunction conjunction, String entity) {
        PConjunction.Builder pConjunctionBuilder = PConjunction.newBuilder();

        if (conjunction.hasBooleanPredicates()) {
            for (BoolPredicate predicate : conjunction.getPredicates()) {
                Object p = visit(predicate, entity);
                if (p instanceof PStringPredicate) {
                    pConjunctionBuilder.addStringPredicates((PStringPredicate) p);
                } else if (p instanceof PBooleanPredicate) {
                    pConjunctionBuilder.addBooleanPredicates((PBooleanPredicate) p);
                } else if (p instanceof PIntPredicate) {
                    pConjunctionBuilder.addIntPredicates((PIntPredicate) p);
                } else if (p instanceof PLongPredicate) {
                    pConjunctionBuilder.addLongPredicates((PLongPredicate) p);
                } else if (p instanceof PFloatPredicate) {
                    pConjunctionBuilder.addFloatPredicates((PFloatPredicate) p);
                } else if (p instanceof PDoublePredicate) {
                    pConjunctionBuilder.addDoublePredicates((PDoublePredicate) p);
                }
            }
        }

        return pConjunctionBuilder.build();
    }

    @Override
    public <TValueType> Object visit(BoolPredicate<TValueType> predicate, String entity) {
        RefExpression left = predicate.getLeft();
        GenericExpression<TValueType> right = predicate.getRight();
        ComparisonOperator operator = predicate.getOperator();

        PRefExpression pRefExpression = visit(left, entity);

        switch (right.getType()) {
            case BOOLEAN:
                return PBooleanPredicate.newBuilder()
                        .setLeft(pRefExpression)
                        .setRight((Boolean) right.getValue())
                        .setOperator(operator.asLowerCase())
                        .build();
            case STRING:
                return PStringPredicate.newBuilder()
                        .setLeft(pRefExpression)
                        .setRight((String) right.getValue())
                        .setOperator(operator.asLowerCase())
                        .build();
            case INT:
                return PIntPredicate.newBuilder()
                        .setLeft(pRefExpression)
                        .setRight((Integer) right.getValue())
                        .setOperator(operator.asLowerCase())
                        .build();
            case LONG:
                return PLongPredicate.newBuilder()
                        .setLeft(pRefExpression)
                        .setRight((Long) right.getValue())
                        .setOperator(operator.asLowerCase())
                        .build();
            case FLOAT:
                return PFloatPredicate.newBuilder()
                        .setLeft(pRefExpression)
                        .setRight((Float) right.getValue())
                        .setOperator(operator.asLowerCase())
                        .build();
            case DOUBLE:
                return PDoublePredicate.newBuilder()
                        .setLeft(pRefExpression)
                        .setRight((Double) right.getValue())
                        .setOperator(operator.asLowerCase())
                        .build();
            default:
                logger.warning(String.format("Type %s not supporter", right.getType().toString()));
                return null;
        }
    }

    @Override
    public PRefExpression visit(RefExpression refExpression, String entity) {
        return PRefExpression.newBuilder()
                .setTable(refExpression.getTable())
                .setColumn(refExpression.getColumn())
                .build();
    }
}
