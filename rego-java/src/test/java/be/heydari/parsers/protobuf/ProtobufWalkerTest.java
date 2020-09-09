package be.heydari.parsers.protobuf;

import be.heydari.ast.ResponseAST;
import be.heydari.parsers.ResponseParser;
import be.heydari.parsers.protobuf.generated.PDisjunction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.logging.Logger;

import static be.heydari.ast.ParsingTest.stream;
import static org.junit.Assert.*;

public class ProtobufWalkerTest {
    private static Logger logger = Logger.getLogger(ProtobufWalkerTest.class.getName());

    private ResponseParser responseParser;

    @Before
    public void setUp() throws Exception {
        responseParser = new ResponseParser();
    }

    @Test
    public void processAstEq() throws IOException {
        PDisjunction pDisjunction = parseAst("1-eq.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());

        /*String expected = "\n" +
                "(\u0012&\n" +
                "\u001A\n" +
                "\n" +
                "accountStates\u0012\tbroker.id\u0012\bbroker23";

        assertEquals(expected, pDisjunction.toByteString().toStringUtf8());*/
    }

    @Test
    public void processAstEqSpecificRow() throws IOException {
        PDisjunction pDisjunction = parseAst("1-eq-specific-row.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }

    @Test
    public void processAstGt() throws IOException {
        PDisjunction pDisjunction = parseAst("2-gt.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }

    @Test
    public void processAstGtSpecificRow() throws IOException {
        PDisjunction pDisjunction = parseAst("2-gt-specific-row.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }

    @Test
    public void processAstGte() throws IOException {
        PDisjunction pDisjunction = parseAst("3-gte.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }

    @Test
    public void processAstGteSpecificRow() throws IOException {
        PDisjunction pDisjunction = parseAst("3-gte-specific-row.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }

    @Test
    public void processAstLt() throws IOException {
        PDisjunction pDisjunction = parseAst("4-lt.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }

    @Test
    public void processAstLtSpecificRow() throws IOException {
        PDisjunction pDisjunction = parseAst("4-lt-specific-row.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }

    @Test
    public void processAstLte() throws IOException {
        PDisjunction pDisjunction = parseAst("5-lte.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }

    @Test
    public void processAstLteSpecificRow() throws IOException {
        PDisjunction pDisjunction = parseAst("5-lte-specific-row.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }

    @Test
    public void processAstNeq() throws IOException {
        PDisjunction pDisjunction = parseAst("6-neq.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }

    @Test
    public void processAstAnd() throws IOException {
        PDisjunction pDisjunction = parseAst("8-and.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }

    @Test
    public void processAstOr() throws IOException {
        PDisjunction pDisjunction = parseAst("15-or.json");
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
    }


    private PDisjunction parseAst(String file) throws IOException {
        ResponseAST responseAST = responseParser.parse(stream(file));

        ProtobufWalker protobufWalker = new ProtobufWalker();
        return protobufWalker.processAst(responseAST, "AccountState");
    }
}