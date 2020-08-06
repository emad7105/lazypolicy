package be.heydari.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Rule[body]
 * @author Emad Heydari Beni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expression implements ASTNode {
    private static Logger logger = Logger.getLogger(Expression.class.getName());


    private Integer index;
    // (eq (Belgium))  (data accountStates 25 location)
    private List<Term> terms;

    // body
    public static Expression fromData(JsonNode expression) {
        Integer index = Integer.valueOf(expression.get("index").asInt());
        JsonNode terms = expression.get("terms");

        // normally terms are array for all expressions
        List<Term> termList = new ArrayList<>();
        if (terms.isArray()) {
            return buildExpFromArrayTerms(index, terms, termList);
        } else if (terms.isObject()) {
            return buildExpFromObjectTerm(index, terms, termList);
        } else {
            logger.warning("The expression, body[terms], is neither of type Array nor Object: "  + terms.toPrettyString());
            return Expression.builder().build();
        }
    }

    private static Expression buildExpFromObjectTerm(Integer index, JsonNode terms, List<Term> termList) {
        termList.add(Term.fromData(terms));
        return Expression.builder()
                .index(index)
                .terms(termList)
                .build();
    }

    private static Expression buildExpFromArrayTerms(Integer index, JsonNode terms, List<Term> termList) {
        for (JsonNode term : terms) {
            String type = term.get("type").asText();
            termList.add(Term.fromData(term));
        }

        return Expression.builder()
                .index(index)
                .terms(termList)
                .build();
    }
}
