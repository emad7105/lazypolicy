package be.heydari.lib.converters.solr;

import be.heydari.lib.expressions.Disjunction;

import java.util.logging.Logger;

public class SolrUtils {
    private static Logger LOGGER = Logger.getLogger(SolrUtils.class.getName());


    private static FilterQueryConverter filterQueryConverter = new FilterQueryConverter();

    public static String[] from(Disjunction disjunction, String entityType) {

        //String entity = "";
        StringBuilder fq = filterQueryConverter.convert(disjunction, entityType);
        return fq == null ?
                new String[]{""} :
                new String[]{fq.toString()};
    }
}
