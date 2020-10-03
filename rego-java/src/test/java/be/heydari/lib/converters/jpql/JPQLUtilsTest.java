package be.heydari.lib.converters.jpql;

import be.heydari.AstWalker;
import be.heydari.ast.ResponseAST;
import be.heydari.ResponseParser;
import be.heydari.lib.expressions.Disjunction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.logging.Logger;

import static be.heydari.ast.ParsingTest.stream;
import static org.junit.Assert.*;

public class JPQLUtilsTest {
    private static Logger logger = Logger.getLogger(JPQLUtilsTest.class.getName());

    private ResponseParser responseParser;


    @Before
    public void setUp() throws Exception {
        responseParser = new ResponseParser();
    }

    @Test
    public void processAstEq() throws IOException {
        String jpqlWhereClauses = parseAst("1-eq.json");

        assertEquals(rmSpace("( ( accountStates.broker.id = 1  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstEqSpecificRow() throws IOException {
        String jpqlWhereClauses = parseAst("1-eq-specific-row.json");

        assertEquals(rmSpace("( ( accountStates.id = '1'   AND  accountStates.broker.id = 'broker23'  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstGt() throws IOException {
        String jpqlWhereClauses = parseAst("2-gt.json");

        assertEquals(rmSpace("( ( accountStates.issue > 2015  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstGtSpecificRow() throws IOException {
        String jpqlWhereClauses = parseAst("2-gt-specific-row.json");

        assertEquals(rmSpace("( ( accountStates.id = '1'   AND  accountStates.issue > 2015  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstGte() throws IOException {
        String jpqlWhereClauses = parseAst("3-gte.json");

        assertEquals(rmSpace("( ( accountStates.issue >= 2015  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstGteSpecificRow() throws IOException {
        String jpqlWhereClauses = parseAst("3-gte-specific-row.json");

        assertEquals(rmSpace("( ( accountStates.id = '1'   AND  accountStates.issue >= 2015  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstLt() throws IOException {
        String jpqlWhereClauses = parseAst("4-lt.json");

        assertEquals(rmSpace("( ( accountStates.issue < 2015  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstLtSpecificRow() throws IOException {
        String jpqlWhereClauses = parseAst("4-lt-specific-row.json");

        assertEquals(rmSpace("( ( accountStates.id = '1'   AND  accountStates.issue < 2015  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstLte() throws IOException {
        String jpqlWhereClauses = parseAst("5-lte.json");

        assertEquals(rmSpace("( ( accountStates.issue <= 2015  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstLteSpecificRow() throws IOException {
        String jpqlWhereClauses = parseAst("5-lte-specific-row.json");

        assertEquals(rmSpace("( ( accountStates.id = '1'   AND  accountStates.issue <= 2015  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstNeq() throws IOException {
        String jpqlWhereClauses = parseAst("6-neq.json");

        assertEquals(rmSpace("( ( accountStates.broker.id <> 'broker23'  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstAnd() throws IOException {
        String jpqlWhereClauses = parseAst("8-and.json");

        assertEquals(rmSpace("( ( accountStates.id = '1'   AND  accountStates.broker.Id = 'broker23'   AND  accountStates.location = 'Belgium'  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    @Test
    public void processAstOr() throws IOException {
        String jpqlWhereClauses = parseAst("15-or.json");

        assertEquals(rmSpace("( ( accountStates.broker.id <> 'broker23'   AND  accountStates.issue >= 2019  )  OR  ( accountStates.broker.id = 'broker23'   AND  accountStates.issue > 2015   AND  accountStates.location = 'Belgium'  )  OR  ( accountStates.broker.id = 'broker23'   AND  accountStates.issue <= 2015   AND  accountStates.location <> 'Belgium'  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    private String parseAst(String file) throws IOException {
        return parseAst(file, null); // if null, the table (entity) name is used
    }

    private String parseAst(String file, String alias) throws IOException {
        ResponseAST responseAST = responseParser.parse(stream(file));
        Disjunction disjunction = AstWalker.walk(responseAST);
        return JPQLUtils.from(disjunction, alias);
    }

    @Test
    public void processAstAndWithAlias() throws IOException {
        String jpqlWhereClauses = parseAst("8-and.json", "acc");

        assertEquals(rmSpace("( ( acc.id = '1'   AND  acc.broker.Id = 'broker23'   AND  acc.location = 'Belgium'  ) )"),
                rmSpace(jpqlWhereClauses));

        logger.info(jpqlWhereClauses);
    }

    private String rmSpace(String str) {
        return str.replaceAll(" ", "");
    }
}