package be.heydari.ast;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

public class MappingTest {

    @Before
    public void setUp() throws Exception {
    }

    @Ignore
    public void testSampleResponse() throws IOException {
        CompileApiAST compileApiAST = parse("SampleOPACompileResponse.json");

        assertNotNull(compileApiAST);
    }


    private InputStream stream(String file) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleOPACompileResponse.json");
        //JsonParser jsonParser = new JsonFactory().createParser(is);
    }

    private CompileApiAST parse(String file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stream(file), CompileApiAST.class);
    }


}