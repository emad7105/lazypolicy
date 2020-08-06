package be.heydari.ast;

import com.fasterxml.jackson.databind.JsonNode;
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
public class Support {

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