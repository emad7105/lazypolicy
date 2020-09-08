package be.heydari.parsers;

import be.heydari.ast.*;
import be.heydari.ast.Expression;
import be.heydari.expressions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static be.heydari.ast.TermType.*;
import static be.heydari.ast.TermType.VAR;
import static be.heydari.expressions.ComparisonOperator.from;
import static java.lang.String.format;

public abstract class Walker<TBoolExpression, TEntity> {
    public abstract TBoolExpression processAst(ResponseAST ast, TEntity entity);
    public abstract Logger getLogger();


    protected Disjunction walk(ResponseAST ast) {
        List<Support> supports = ast.getSupports();
        if (supports != null) {
            // there is only one support
            for (Support support : supports) {
                List<Rule> rules = support.getRules();
                return processDisjunctions(rules);
            }
        }
        return Disjunction.builder().conjunctivePredicates(Collections.emptyList()).build();
    }

    private Disjunction processDisjunctions(List<Rule> rules) {
        List<Conjunction> conjunctions = new ArrayList<>();
        if (rules != null) {
            // TODO: drop the default value => important to fix!! => assumed it's always false!
            List<Rule> rulesWithoutLastOne = rules.stream().limit(rules.size() - 1).collect(Collectors.toList());
            for (Rule rule : rulesWithoutLastOne) {
                Conjunction conjunction = processConjunction(rule);
                conjunctions.add(conjunction);
            }
            return Disjunction.builder().conjunctivePredicates(conjunctions).build();
        }
        return Disjunction.builder().conjunctivePredicates(Collections.emptyList()).build();
    }

    private Conjunction processConjunction(Rule rule) {
        List<Expression> expressions = rule.getExpressions();
        List<BoolPredicate> boolPredicates = new ArrayList<>();
        if (expressions != null) {
            for (Expression expression : expressions) {
                BoolPredicate boolPredicate = processBoolPredicate(expression);
                boolPredicates.add(boolPredicate);
            }
            return Conjunction.builder().predicates(boolPredicates).build();
        }
        return Conjunction.builder().predicates(Collections.emptyList()).build();
    }

    private BoolPredicate processBoolPredicate(Expression expression) {
        BoolPredicate boolPredicate = new BoolPredicate();
        List<Term> terms = expression.getTerms();
        if (terms != null && terms.size() > 0) {

            RefExpression left = new RefExpression();
            GenericExpression right = new GenericExpression();

            for (Term term : terms) {
                switch (term.getType()) {
                    case REF:
                        // setLeft(left) & setOperator(op)
                        processRef(boolPredicate, left, term);
                        break;
                    case STRING:
                        right.setType(ExpressionType.STRING);
                        right.setValue(term.getValue().getValue());
                        break;
                    case NUMBER:
                        right.setType(ExpressionType.NUMERIC);
                        right.setValue(((NumberValue)term.getValue().getValue()).getValue());
                        break;
                    case BOOLEAN:
                        right.setType(ExpressionType.BOOLEAN);
                        right.setValue(term.getValue().getValue());
                        break;
                    default:
                        getLogger().warning(format("Type not supported: %s", term.getType()));
                        break;
                }
            }
            boolPredicate.setRight(right);
            return boolPredicate;
        }
        return null;
    }

    private void processRef(BoolPredicate boolPredicate, RefExpression left, Term term) {
        List<RefTermValuePart> refTermValueParts = ((RefTermValue) term.getValue()).getValue();
        if (refTermValueParts != null) {
            if (refTermValueParts.size() == 1) {
                // operator, e.g. (=)
                RefTermValuePart refTermValuePart = refTermValueParts.get(0);
                String refTermType = refTermValuePart.getType();
                if (VAR.equals(refTermType)) {
                    ComparisonOperator op = from(refTermValuePart.getValue());
                    if (op == null){
                        getLogger().severe("operator is null");
                    }
                    boolPredicate.setOperator(op);
                }
            } else if (refTermValueParts.size() >= 3) {
                // data.accountState.brokerId
                // data.accountState.broker.id
                // data.accountState[25].brokerId
                // data.accountState[25].broker.id
                left.setTable(refTermValueParts.get(1).getValue());

                // todo
                left.setEntityId(null);

                // broker.id or 25.broker.id
                String column = refTermValueParts.stream().skip(2)
                        .map(rp -> rp.getValue().toString())
                        .collect(Collectors.joining("."));
                left.setColumn(column);
            }
        }
        boolPredicate.setLeft(left);
    }

    /*public static String fetchEntityId() {

    }*/

}
