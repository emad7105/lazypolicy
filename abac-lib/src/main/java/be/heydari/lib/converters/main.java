package be.heydari.lib.converters;

import be.heydari.lib.converters.protobuf.ProtobufUtils;
import be.heydari.lib.converters.protobuf.generated.PDisjunction;
import be.heydari.lib.converters.querydsl.QueryDslUtils;
import be.heydari.lib.expressions.Disjunction;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Base64;

public class main {
    public static void main(String[] args) throws Exception{
        for (int i = 0; i != 10; i++) {
            long time = System.currentTimeMillis();
            String abacCtxBooleanSlowStuff = "ClMKJgoeCgxhY2NvdW50U3RhdGUSDnNlbGVjdGl2aXR5MTAwEAEaAmVxEikKGgoMYWNjb3VudFN0YXRlEgpicm9rZXJOYW1lEgdicm9rZXIwGgJlcQ==";
            byte[] abacContextProtobytes = Base64.getDecoder().decode(abacCtxBooleanSlowStuff);
            PDisjunction pDisjunction = ((PDisjunction.Builder)PDisjunction.newBuilder().mergeFrom(abacContextProtobytes)).build();
            Disjunction disjunction = ProtobufUtils.to(pDisjunction, "");
            long time2 = System.currentTimeMillis();

            System.out.println(time2-time);
        }

//        BooleanExpression abacExpr = QueryDslUtils.from(abacContext, entityPath, EntityContext.getCurrentEntityContext().getJavaType());
    }
}
