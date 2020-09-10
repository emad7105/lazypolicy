package be.heydari.parsers.protobuf;

import be.heydari.ast.ResponseAST;
import be.heydari.parsers.ResponseParser;
import be.heydari.parsers.protobuf.generated.PDisjunction;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import static be.heydari.ast.ParsingTest.stream;
import static java.lang.String.format;
import static org.junit.Assert.*;

public class ProtobufWalkerTest {
    private static Logger logger = Logger.getLogger(ProtobufWalkerTest.class.getName());
    private static String PROTOBUFS_PATH = "protobufs";

    private ResponseParser responseParser;

    @BeforeClass
    public static void init() throws IOException, URISyntaxException {
        File f = new File(PROTOBUFS_PATH);
        FileUtils.deleteDirectory(f);
        FileUtils.forceMkdir(f);
    }

    @Before
    public void setUp() throws Exception {
        responseParser = new ResponseParser();
    }

    @Test
    public void processAstEq() throws IOException {
        String file = "1-eq.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        logger.info(pDisjunction.toByteString().toStringUtf8());
        writeToFile(file, pDisjunction);

        /*String expected = "\n" +
                "(\u0012&\n" +
                "\u001A\n" +
                "\n" +
                "accountStates\u0012\tbroker.id\u0012\bbroker23";

        assertEquals(expected, pDisjunction.toByteString().toStringUtf8());*/
    }

    @Test
    public void processAstEqSpecificRow() throws IOException {
        String file = "1-eq-specific-row.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }

    @Test
    public void processAstGt() throws IOException {
        String file = "2-gt.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }

    @Test
    public void processAstGtSpecificRow() throws IOException {
        String file = "2-gt-specific-row.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }

    @Test
    public void processAstGte() throws IOException {
        String file = "3-gte.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }

    @Test
    public void processAstGteSpecificRow() throws IOException {
        String file = "3-gte-specific-row.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }

    @Test
    public void processAstLt() throws IOException {
        String file = "4-lt.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }

    @Test
    public void processAstLtSpecificRow() throws IOException, URISyntaxException {
        String file = "4-lt-specific-row.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }

    @Test
    public void processAstLte() throws IOException, URISyntaxException {
        String file = "5-lte.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }

    @Test
    public void processAstLteSpecificRow() throws IOException, URISyntaxException {
        String file = "5-lte-specific-row.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }

    @Test
    public void processAstNeq() throws IOException, URISyntaxException {
        String file = "6-neq.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }

    @Test
    public void processAstAnd() throws IOException, URISyntaxException {
        String file = "8-and.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }

    @Test
    public void processAstOr() throws IOException, URISyntaxException {
        String file = "15-or.json";
        PDisjunction pDisjunction = parseAst(file);
        assertNotNull(pDisjunction);
        logger.info(pDisjunction.toString());
        writeToFile(file, pDisjunction);
    }


    private PDisjunction parseAst(String file) throws IOException {
        ResponseAST responseAST = responseParser.parse(stream(file));

        ProtobufWalker protobufWalker = new ProtobufWalker();
        return protobufWalker.processAst(responseAST, "AccountState");
    }

    private void writeToFile(String fileName, PDisjunction pDisjunction) throws IOException {
        FileOutputStream fos = new FileOutputStream(format("%s/%s.protobytes",PROTOBUFS_PATH, fileName));
        pDisjunction.writeTo(fos);
    }
}