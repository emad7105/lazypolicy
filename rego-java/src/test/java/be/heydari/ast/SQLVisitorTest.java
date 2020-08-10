package be.heydari.ast;

import be.heydari.parsers.ResponseParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.logging.Logger;

public class SQLVisitorTest {
    private static Logger logger = Logger.getLogger(ParsingTest.class.getName());
    private ResponseParser responseParser;

    @Before
    public void setUp() throws Exception {
        responseParser = new ResponseParser();
    }

    @Test
    public void testEq() throws IOException {
        ResponseAST ast = getAST("1-eq.json");
    }


    private ResponseAST getAST(String file) throws IOException {
        return responseParser.parse(ParsingTest.stream(file));
    }
}
