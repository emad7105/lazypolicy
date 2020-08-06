package be.heydari.parser;

public interface Visitor {

    String visit(Conjunction conjunction);
    String visit(Disjunction disjunction);
    <L,R> String visit(BooleanPredicate<L,R> predicate);
    <T> String visit(GenericExpression<T> expression);
    /*String visit(BooleanExpression booleanExpression);
    String visit(StringExpression stringExpression);
    String visit(NumericExpression numericExpression);
    String visit(Table table);
    String visit(Column column);*/
}
