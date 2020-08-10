package be.heydari.ast;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author Emad Heydari Beni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StringTermValue implements TermValue<String> {

    private String value;

    public static StringTermValue fromData(JsonNode stringTermValue) {
        String value = stringTermValue.asText();

        return StringTermValue.builder()
                .value(value)
                .build();
    }

    public static StringTermValue newStringTermValue(TermValue value) {
        return StringTermValue.builder()
                .value((String) value.getValue())
                .build();
    }
}
