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
public class RefTermValuePart {

    private String type;
    private String value;

    public static RefTermValuePart fromData(JsonNode refTermValuePart) {
        String type = refTermValuePart.get("type").asText();
        String value = refTermValuePart.get("value").asText();

        return RefTermValuePart.builder()
                .type(type)
                .value(value)
                .build();
    }
}
