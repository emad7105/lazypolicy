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
public class BooleanTermValue implements TermValue {

    private Boolean value;

    public static BooleanTermValue fromData(JsonNode booleanTermValue) {
        Boolean value = Boolean.valueOf(booleanTermValue.get("value").asBoolean());

        return BooleanTermValue.builder()
                .value(value)
                .build();
    }
}
