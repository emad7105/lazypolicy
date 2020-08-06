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
public class Head implements ASTNode {

    // example: allow_partial
    private String name;
    private Boolean value;

    public static Head fromData(JsonNode head){
        String name = head.get("name").asText();
        Boolean value = head.get("value").get("value").asBoolean();

        return Head.builder()
                .name(name)
                .value(value)
                .build();
    }
}
