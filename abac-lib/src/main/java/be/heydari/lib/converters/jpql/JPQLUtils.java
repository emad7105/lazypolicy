package be.heydari.lib.converters.jpql;


import be.heydari.lib.expressions.*;

import java.util.logging.Logger;

public class JPQLUtils {
    private static Logger LOGGER = Logger.getLogger(JPQLUtils.class.getName());

    private static JPQLConverter jpqlConverter = new JPQLConverter();

    public static String from(Disjunction disjunction, String aliasOrEntity) {

        //String entity = "";
        StringBuilder whereClause = jpqlConverter.convert(disjunction, aliasOrEntity);
        return whereClause == null ? "" : whereClause.toString();
    }
}
