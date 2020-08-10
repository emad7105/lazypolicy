package be.heydari.ast;

import com.fasterxml.jackson.databind.JsonNode;
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
public class Support implements ASTNode {

    private List<Rule> rules;

    public static Support fromData(JsonNode support) {

        JsonNode rules = support.get("rules");

        // iterate over rules
        List<Rule> ruleList = new ArrayList<>();
        for (JsonNode rule : rules) {
            ruleList.add(Rule.fromData(rule));
        }

        return Support.builder()
                .rules(ruleList)
                .build();
    }
}
