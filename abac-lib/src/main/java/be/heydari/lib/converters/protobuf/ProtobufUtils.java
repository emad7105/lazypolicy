package be.heydari.lib.converters.protobuf;

import be.heydari.lib.expressions.*;
import be.heydari.lib.converters.protobuf.generated.PDisjunction;

import java.util.logging.Logger;


public class ProtobufUtils {
    private static Logger logger = Logger.getLogger(ProtobufUtils.class.getName());

    private static ProtobufConverter protobufConverter = new ProtobufConverter();

    public static PDisjunction from(Disjunction disjunction, String entity) {
        return protobufConverter.convert(disjunction, entity);
    }
}
