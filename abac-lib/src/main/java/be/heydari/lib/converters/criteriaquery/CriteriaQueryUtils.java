package be.heydari.lib.converters.criteriaquery;

import be.heydari.lib.expressions.Disjunction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CriteriaQueryUtils {

    private static CriteriaQueryConverter criteriaQueryConverter = new CriteriaQueryConverter();

    public static Predicate from(Disjunction disjunction, Root root, CriteriaBuilder cb) {
        return criteriaQueryConverter.convert(disjunction, root, cb);
    }
}
