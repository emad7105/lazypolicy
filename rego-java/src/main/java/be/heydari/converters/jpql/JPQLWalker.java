package be.heydari.converters.jpql;


import be.heydari.ast.*;
import be.heydari.expressions.*;
import be.heydari.converters.Walker;

import java.util.logging.Logger;

public class JPQLWalker extends Walker<String,String> {
    private static Logger logger = Logger.getLogger(JPQLWalker.class.getName());


    @Override
    public String processAst(ResponseAST ast, String entity) {
        JPQLVisitor visitor = new JPQLVisitor();

        Disjunction disjunctiveQuery = walk(ast);
        //String entity = "";
        StringBuilder whereClause = visitor.visit(disjunctiveQuery, entity);
        return whereClause == null ? "" : whereClause.toString();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
