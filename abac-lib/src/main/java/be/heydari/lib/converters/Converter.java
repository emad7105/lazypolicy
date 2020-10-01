package be.heydari.lib.converters;

import be.heydari.lib.expressions.BoolPredicate;
import be.heydari.lib.expressions.Conjunction;
import be.heydari.lib.expressions.Disjunction;
import be.heydari.lib.expressions.RefExpression;

/**
 * @author Emad Heydari Beni
 */
public interface Converter<TEntity, TDisjunction, TConjunction, TPredicate, TReference> {

    TDisjunction convert(Disjunction disjunction, TEntity entity);

    TConjunction convert(Conjunction conjunction, TEntity entity);

    <TValueType> TPredicate convert(BoolPredicate<TValueType> predicate, TEntity entity);

    TReference convert(RefExpression refExpression, TEntity entity);
}
