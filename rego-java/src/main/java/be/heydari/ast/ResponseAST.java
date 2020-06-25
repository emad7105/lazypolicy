package be.heydari.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emad Heydari Beni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseAST {


    private List<Support> support;

    public static ResponseAST fromData(JsonNode response) {
        // TODO null checks
        ArrayNode supports = (ArrayNode) response.get("result").get("support");

        // iterate through 'supports'
        List<Support> supportList = new ArrayList<>();
        for (JsonNode support : supports) {
            supportList.add(Support.fromData(support));
        }

        return ResponseAST.builder()
                .support(supportList)
                .build();
    }
}
