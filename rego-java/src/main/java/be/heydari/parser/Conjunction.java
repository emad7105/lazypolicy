package be.heydari.parser;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Conjunction implements Visitable {

    private List<BooleanPredicate> predicates;

    public boolean hasPredicates() {
        return predicates.size() > 0;
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
