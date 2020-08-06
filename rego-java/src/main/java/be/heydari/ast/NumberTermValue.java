package be.heydari.ast;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

/**
 * @author Emad Heydari Beni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumberTermValue implements TermValue<Double> {

    private Double value;

    public static NumberTermValue fromData(JsonNode numberValueTerm) {
        Double value = Double.valueOf(numberValueTerm.asDouble());

        return NumberTermValue.builder()
                .value(value)
                .build();
    }

    public static NumberTermValue newNumberTermValue(TermValue value) {
        return NumberTermValue.builder()
                .value((Double) value.getValue())
                .build();
    }
}
