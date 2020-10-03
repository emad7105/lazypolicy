package be.heydari.lib.converters.querydsl;

import be.heydari.lib.converters.protobuf.generated.PDisjunction;
import be.heydari.lib.expressions.Disjunction;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;

public class QueryDslUtils {

    private static ProtobufToQueryDslConverter protobufToQueryDslConverter = new ProtobufToQueryDslConverter();
    private static QueryDslConverter queryDslConverter = new QueryDslConverter();

    public static BooleanExpression from(PDisjunction pDisjunction, PathBuilder entityPath, Class entityType) {
        return protobufToQueryDslConverter.convert(pDisjunction, entityPath, entityType);
    }

    public static BooleanExpression from(Disjunction disjunction, PathBuilder entityPath, Class entityType) {
        return queryDslConverter.convert(disjunction, entityPath, entityType);
    }
}
