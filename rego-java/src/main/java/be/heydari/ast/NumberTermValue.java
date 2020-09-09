package be.heydari.ast;

import be.heydari.parsers.jpql.JPQLVisitor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * @author Emad Heydari Beni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumberTermValue implements TermValue<NumberValue> {
    private static Logger logger = Logger.getLogger(JPQLVisitor.class.getName());

    private NumberValue value;

    public static NumberTermValue fromData(JsonNode numberValueTerm) {
        JsonParser.NumberType type = numberValueTerm.numberType();
        NumberTermValue numberTermValue;
        switch (type) {
            case INT:
                numberTermValue = NumberTermValue.builder()
                        .value(new NumberValue<Integer>(numberValueTerm.asInt(), NumberType.INT))
                        .build();
                break;
            case LONG:
                numberTermValue = NumberTermValue.builder()
                        .value(new NumberValue<Long>(numberValueTerm.asLong(), NumberType.LONG))
                        .build();
                break;
            case FLOAT:
                numberTermValue = NumberTermValue.builder()
                        .value(new NumberValue<Float>(((Double)numberValueTerm.asDouble()).floatValue(), NumberType.FLOAT))
                        .build();
                break;
            case DOUBLE:
                numberTermValue = NumberTermValue.builder()
                        .value(new NumberValue<Double>(numberValueTerm.asDouble(), NumberType.DOUBLE))
                        .build();
                break;
            default:
                logger.warning(format("Type(%s) is not supported in the conversion of JSON number to Java: NumberTermValue", type.toString()));
                numberTermValue = NumberTermValue.builder()
                        .value(new NumberValue<String>(numberValueTerm.asText(), null))
                        .build();
                break;
        }
        return numberTermValue;
    }

    /*public static NumberTermValue newNumberTermValue(TermValue value) {
        return NumberTermValue.builder()
                .value((Long) value.getValue())
                .build();
    }*/
}
