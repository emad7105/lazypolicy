package be.heydari.lib.expressions;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Conjunction {

    private List<BoolPredicate> predicates;

    public Conjunction() {
    }

    public Conjunction(List<BoolPredicate> predicates) {
        this.predicates = predicates;
    }

    public boolean hasBooleanPredicates() {
        return predicates != null && predicates.size() > 0;
    }
}
