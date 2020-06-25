package be.heydari.parser;


import be.heydari.ast.ResponseAST;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

import java.io.IOException;
import java.io.InputStream;


public class ResponseParser {

    //private final JsonFactory factory;
    private final ObjectMapper mapper;

    public ResponseParser() {
        this.mapper = new ObjectMapper();
//        this.factory = new JsonFactory();
    }

    public ResponseAST parse(InputStream is) throws IOException {
        return parse(mapper.readTree(is));
    }

    public ResponseAST parse(String response) throws IOException {
        Preconditions.checkNotNull(response);
        return parse(mapper.readTree(response));
    }

    private ResponseAST parse(JsonNode obj) {
        // TODO null checking
        return ResponseAST.fromData(obj);
    }
}
