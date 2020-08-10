package be.heydari.expressions;

import be.heydari.parsers.querydsl.Visitable;
import be.heydari.parsers.querydsl.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Disjunction {

    private List<Conjunction> conjunctivePredicates;

    public Disjunction() {
    }

    public Disjunction(List<Conjunction> conjunctivePredicates) {
        this.conjunctivePredicates = conjunctivePredicates;
    }

    public boolean hasConjunctivePredicates() {
        return conjunctivePredicates != null && conjunctivePredicates.size() > 0;
    }
}
