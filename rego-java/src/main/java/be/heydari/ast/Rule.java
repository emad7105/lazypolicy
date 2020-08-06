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
public class Rule implements ASTNode {

    // Rule[Body]
    private Head head = new Head();
    // a rule is made of one or many expressions
    // Rule[body]
    private List<Expression> expressions;

    public static Rule fromData(JsonNode rule) {
        JsonNode head = rule.get("head");
        JsonNode expressions = rule.get("body");

        List<Expression> expressionList = new ArrayList<>();
        for (JsonNode expression : expressions) {
            expressionList.add(Expression.fromData(expression));
        }

        return Rule.builder()
                .head(Head.fromData(head))
                .expressions(expressionList)
                .build();
    }
}
