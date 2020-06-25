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
public class NumberTermValue implements TermValue {

    private Double value;

    public static NumberTermValue fromData(JsonNode numberValueTerm) {
        Double value = Double.valueOf(numberValueTerm.get("value").asDouble());

        return NumberTermValue.builder()
                .value(value)
                .build();
    }
}
