package be.heydari.ast;

import be.heydari.ResponseParser;
import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ParsingTest {
    private static Logger logger = Logger.getLogger(ParsingTest.class.getName());


    private ResponseParser responseParser;

    @Before
    public void setUp() throws Exception {
        responseParser = new ResponseParser();
    }

    @Test
    public void testAllCases() throws IOException {
        List<String> files = loadAllPolicyResponses();
        for (String file : files) {
            logger.info("\n\n\n--------------\n " + file + "\n--------------");

            InputStream is = stream(file);
            ResponseAST responseAST = responseParser.parse(is);

            assertNotNull(responseAST);
        }
    }

    private List<String> loadAllPolicyResponses() throws IOException {
        return IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("."), Charsets.UTF_8)
                .stream().filter(name -> name.contains(".json")).collect(Collectors.toList());
    }


    public static InputStream stream(String file) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
    }


}