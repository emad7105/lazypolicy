package be.heydari.ast;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Emad Heydari Beni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumberTermValue implements TermValue<Long> {

    private Long value;

    public static NumberTermValue fromData(JsonNode numberValueTerm) {
        Long value = Long.valueOf(numberValueTerm.asLong());

        return NumberTermValue.builder()
                .value(value)
                .build();
    }

    public static NumberTermValue newNumberTermValue(TermValue value) {
        return NumberTermValue.builder()
                .value((Long) value.getValue())
                .build();
    }
}
