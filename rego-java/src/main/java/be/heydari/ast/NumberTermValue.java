package be.heydari.ast;

import be.heydari.lib.converters.jpql.JPQLConverter;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * f or F: Float
 * d or D: Double
 * i or I: Integer
 * l or L: Long
 * nothing just number : Integer
 *
 * @author Emad Heydari Beni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumberTermValue implements TermValue<NumberValue> {
    private static Logger logger = Logger.getLogger(JPQLConverter.class.getName());

    private NumberValue value;

    public static NumberTermValue fromData(JsonNode numberValueWithLiteralsTerm) {
        //JsonParser.NumberType type = numberValueTerm.numberType();
        String numberWithLiterals = numberValueWithLiteralsTerm.asText();
        NumberType numberType = typeFromConstant(numberWithLiterals);
        NumberTermValue numberTermValue;
        switch (numberType) {
            case INT:
                Integer intNum = Integer.parseInt(numberWithLiterals.toLowerCase().replace("i", ""));
                numberTermValue = NumberTermValue.builder()
                        .value(new NumberValue<Integer>(intNum, NumberType.INT))
                        .build();
                break;
            case LONG:
                Long longNum = Long.parseLong(numberWithLiterals.toLowerCase().replace("l", ""));
                numberTermValue = NumberTermValue.builder()
                        .value(new NumberValue<Long>(longNum, NumberType.LONG))
                        .build();
                break;
            case FLOAT:
                Float floatNum = Float.parseFloat(numberWithLiterals.toLowerCase().replace("f", ""));
                numberTermValue = NumberTermValue.builder()
                        .value(new NumberValue<Float>(floatNum, NumberType.FLOAT))
                        .build();
                break;
            case DOUBLE:
                Double doubleNum = Double.parseDouble(numberWithLiterals.toLowerCase().replace("d", ""));
                numberTermValue = NumberTermValue.builder()
                        .value(new NumberValue<Double>(doubleNum, NumberType.DOUBLE))
                        .build();
                break;
            default:
                logger.warning(format("Type(%s) is not supported in the conversion of JSON number to Java: NumberTermValue", numberWithLiterals));
                numberTermValue = NumberTermValue.builder()
                        .value(new NumberValue<String>(numberWithLiterals, null))
                        .build();
                break;
        }
        return numberTermValue;
    }

    public static boolean isNumber(String numberWithLiterals) {
        Pattern numberWithLiteralPattern = Pattern.compile("\\d+[lLfFdDiI]$");
        Matcher numberWithLiteralMatcher = numberWithLiteralPattern.matcher(numberWithLiterals);
        return numberWithLiteralMatcher.find();
    }

    public static NumberType typeFromConstant(String value) {
        if (value.endsWith("L") || value.endsWith("l")) {
            return NumberType.LONG;
        } else if (value.endsWith("F") || value.endsWith("f")) {
            return NumberType.FLOAT;
        } else if (value.endsWith("D") || value.endsWith("d")) {
            return NumberType.DOUBLE;
        } else if (value.endsWith("I") || value.endsWith("i")) {
            return NumberType.INT;
        } else {
            throw new IllegalArgumentException(format("Number %s must end with one of these literals: f/F/l/L/i/I/d/D",value));
        }
    }


    /*public static NumberTermValue newNumberTermValue(TermValue value) {
        return NumberTermValue.builder()
                .value((Long) value.getValue())
                .build();
    }*/
}
