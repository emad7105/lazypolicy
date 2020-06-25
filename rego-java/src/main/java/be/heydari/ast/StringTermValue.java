package be.heydari.ast;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

/*
 * @author Emad Heydari Beni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StringTermValue implements TermValue {

    private String value;

    public static StringTermValue fromData(JsonNode stringTermValue) {
        String value = stringTermValue.asText();

        return StringTermValue.builder()
                .value(value)
                .build();
    }
}
