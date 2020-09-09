package be.heydari.parsers;


import be.heydari.ast.ResponseAST;
import be.heydari.parsers.protobuf.generated.PBooleanExpression;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

import java.io.IOException;
import java.io.InputStream;

/**
 * Developed and tested against OPA:
 *      Version: 0.20.5
 *      Build Commit: 64dd76e1
 *      Build Timestamp: 2020-06-01T18:34:57Z
 *      Build Hostname: 8f7822bb4c39
 *
 *
 * @author Emad Heydari Beni
 */
public class ResponseParser {

    private final ObjectMapper mapper;

    public ResponseParser() {
        this.mapper = new ObjectMapper();
    }

    public ResponseAST parse(InputStream is) throws IOException {
        return parse(mapper.readTree(is));
    }

    public ResponseAST parse(String response) throws IOException {
        Preconditions.checkNotNull(response);
        return parse(mapper.readTree(response));
    }

    private ResponseAST parse(JsonNode obj) {
        // TODO null checks
        return ResponseAST.fromData(obj);
    }
}
