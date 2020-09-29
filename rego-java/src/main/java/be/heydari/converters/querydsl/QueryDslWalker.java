package be.heydari.converters.querydsl;

import be.heydari.converters.protobuf.generated.PDisjunction;
import be.heydari.expressions.*;
import be.heydari.ast.*;
import be.heydari.converters.Walker;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.logging.Logger;


public class QueryDslWalker extends Walker<BooleanExpression,Class> {
    private static Logger logger = Logger.getLogger(QueryDslWalker.class.getName());

    @Override
    public BooleanExpression processAst(ResponseAST ast, Class entityType) {
        QueryDslVisitor2 visitor = new QueryDslVisitor2();

        Disjunction disjunctiveQuery = walk(ast);
        return visitor.visit(disjunctiveQuery, entityType);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
