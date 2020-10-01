package be.heydari.lib.converters.protobuf;

import be.heydari.lib.expressions.*;
import be.heydari.lib.converters.Converter;
import be.heydari.lib.converters.protobuf.generated.*;

import java.util.logging.Logger;

import static java.lang.String.format;

public class ProtobufConverter implements Converter<String, PDisjunction, PConjunction, Object, PRefExpression> {
    private static Logger logger = Logger.getLogger(ProtobufConverter.class.getName());


    @Override
    public PDisjunction convert(Disjunction disjunction, String entity) {
        PDisjunction.Builder pDisjunctionBuilder = PDisjunction.newBuilder();
        if (disjunction.hasConjunctivePredicates()) {
            for (Conjunction conjunction : disjunction.getConjunctivePredicates()) {
                pDisjunctionBuilder.addConjunctions(convert(conjunction, entity));
            }
        }
        return pDisjunctionBuilder.build();
    }

    @Override
    public PConjunction convert(Conjunction conjunction, String entity) {
        PConjunction.Builder pConjunctionBuilder = PConjunction.newBuilder();

        if (conjunction.hasBooleanPredicates()) {
            for (BoolPredicate predicate : conjunction.getPredicates()) {
                Object p = convert(predicate, entity);
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
    public <TValueType> Object convert(BoolPredicate<TValueType> predicate, String entity) {
        RefExpression left = predicate.getLeft();
        GenericExpression<TValueType> right = predicate.getRight();
        ComparisonOperator operator = predicate.getOperator();

        PRefExpression pRefExpression = convert(left, entity);

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
    public PRefExpression convert(RefExpression refExpression, String entity) {
        return PRefExpression.newBuilder()
                .setTable(refExpression.getTable())
                .setColumn(refExpression.getColumn())
                .build();
    }
}
