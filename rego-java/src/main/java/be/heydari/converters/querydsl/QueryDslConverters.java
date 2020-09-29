package be.heydari.converters.querydsl;

import be.heydari.ast.ResponseAST;
import be.heydari.converters.protobuf.generated.PDisjunction;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;

import java.util.logging.Logger;

public class QueryDslConverters {
    private static Logger logger = Logger.getLogger(QueryDslConverters.class.getName());
    private static QueryDslWalker walker = new QueryDslWalker();
    private static QueryDslGenerator protobufGenerator = new QueryDslGenerator();

    public static BooleanExpression from(ResponseAST ast, Class entityType) {
        return walker.processAst(ast, entityType);
    }

    public static BooleanExpression from(PDisjunction pDisjunction, PathBuilder entityPath, Class entityType) {
        return protobufGenerator.process(pDisjunction, entityPath, entityType);
    }
}
