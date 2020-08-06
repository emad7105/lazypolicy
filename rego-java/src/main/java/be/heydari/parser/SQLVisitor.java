package be.heydari.parser;

import be.heydari.ast.Term;

import java.util.Iterator;
import java.util.logging.Logger;


/**
 * @author Emad Heydari Beni
 */
public class SQLVisitor implements Visitor {
    private static Logger logger = Logger.getLogger(SQLVisitor.class.getName());
    private static String EMPTY = "";

    @Override
    public String visit(Conjunction conjunction) {
        log("conjunction");

        if (conjunction.hasPredicates()) {
            iterateBooleanPredicates(conjunction.getPredicates().iterator(), "AND");
        }
        return EMPTY;
    }

    @Override
    public String visit(Disjunction disjunction) {
        log("disjunction");

        if (disjunction.hasPredicates()) {
            return iterateBooleanPredicates(disjunction.getPredicates().iterator(), "OR");
        }
        return EMPTY;
    }

    @Override
    public String visit(BooleanPredicate predicate) {
        log("boolean predicate");

        String left = visit(predicate.getLeft());
        String right = visit(predicate.getRight());

        return "(" + left + predicate.getOperator().asLowerCase() +  right + ")";
    }

    @Override
    public <T> String visit(GenericExpression<T> expression) {
        return null;
    }

    /*@Override
    public String visit(BooleanExpression booleanExpression) {
        return booleanExpression.getValue() ? "TRUE" : "FALSE";
    }

    @Override
    public String visit(StringExpression stringExpression) {
        return "'" + stringExpression.getValue() + "'";
    }

    @Override
    public String visit(NumericExpression numericExpression) {
        return String.valueOf(numericExpression.getValue());
    }

    @Override
    public String visit(Table table) {
        return table.getName();
    }

    @Override
    public String visit(Column column) {
        return column.getName();
    }*/

    private String iterateBooleanPredicates(Iterator<BooleanPredicate> it, String operator) {
        StringBuilder c = new StringBuilder();

        c.append("(");
        while (it.hasNext()) {
            c.append(visit(it.next()));
            if (it.hasNext()) {
                c.append(operator);
            }
        }
        c.append(")");

        return c.toString();
    }


    private static void log(String msg) {
        logger.info("Visited: " + msg);
    }
}
