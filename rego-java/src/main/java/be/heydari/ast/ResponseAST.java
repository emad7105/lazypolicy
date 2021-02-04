package be.heydari.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emad Heydari Beni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseAST implements ASTNode {


    private List<Support> supports;

    public static ResponseAST fromData(JsonNode response) {
        // TODO null checks
        ArrayNode supports = (ArrayNode) response.get("result").get("support");

        // iterate through 'supports'
        List<Support> supportList = new ArrayList<>();
        if (supports != null) {
            for (JsonNode support : supports) {
                supportList.add(Support.fromData(support));
            }
        }

        return ResponseAST.builder()
                .supports(supportList)
                .build();
    }
}
