package be.heydari.lib.converters.protobuf;

import be.heydari.lib.expressions.*;
import be.heydari.lib.converters.protobuf.generated.PDisjunction;

import java.util.logging.Logger;


public class ProtobufUtils {
    private static Logger logger = Logger.getLogger(ProtobufUtils.class.getName());

    private static ProtobufConverter protobufConverter = new ProtobufConverter();
    private static ProtobufToExpression protobufToExpression = new ProtobufToExpression();

    public static PDisjunction from(Disjunction disjunction, String entity) {
        return protobufConverter.convert(disjunction, entity);
    }

    public static Disjunction to(PDisjunction pDisjunction, String entity) {
        return protobufToExpression.convert(pDisjunction, entity);
    }
}
