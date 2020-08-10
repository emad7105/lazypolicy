package be.heydari.parsers.querydsl;

import be.heydari.expressions.*;
import be.heydari.ast.*;
import be.heydari.parsers.Walker;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.logging.Logger;


public class QueryDslWalker extends Walker<BooleanExpression,Class> {
    private static Logger logger = Logger.getLogger(QueryDslWalker.class.getName());

    @Override
    public BooleanExpression processAst(ResponseAST ast, Class entityType) {
        QueryDslVisitor visitor = new QueryDslVisitor();

        Disjunction disjunctiveQuery = walk(ast);
        return visitor.visit(disjunctiveQuery, entityType);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
