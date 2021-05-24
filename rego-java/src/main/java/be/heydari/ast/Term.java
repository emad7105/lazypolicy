package be.heydari.ast;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.logging.Logger;

import static be.heydari.ast.NumberTermValue.isNumber;
import static be.heydari.ast.TermType.*;

/**
 * Expression: 'data.accountStates[34].location = Belgium' is composed of
 * <p>
 * - Ref term
 * - Value term
 * - Operator term
 *
 * @author Emad Heydari Beni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Term implements ASTNode {
    private static Logger logger = Logger.getLogger(Term.class.getName());

    // ref,string,number or boolean
    private String type;
    private TermValue value;

    public static Term fromData(JsonNode term) {
        String type = term.get("type").asText();
        JsonNode termValue = term.get("value");

        TermValue theTermValue = null;
        switch (type) {
            case REF:
                log(term, type);
                theTermValue = RefTermValue.fromData(termValue);
                break;
            case STRING:
                log(term, type);
                if (isNumber(termValue.asText())) {
                    theTermValue = NumberTermValue.fromData(termValue);
                    type = NUMBER;
                }
                else {
                    theTermValue = StringTermValue.fromData(termValue);
                }
                break;
            /*case NUMBER:
                log(term, type);
                theTermValue = NumberTermValue.fromData(termValue);
                break;*/
            case BOOLEAN:
                log(term, type);
                theTermValue = BooleanTermValue.fromData(termValue);
                break;
            default:
                warnNotSupported(type, term);
                break;
        }
        return Term.builder()
                .type(type)
                .value(theTermValue)
                .build();
    }


    private static void log(JsonNode term, String type) {
//        logger.info(type + " => " + term.toPrettyString());
    }


    private static void warnNotSupported(String type, JsonNode term) {
        logger.warning("Term Value not support: " +
                "'" + type + "' => " +
                term.toPrettyString());
    }
}
