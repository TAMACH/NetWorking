/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp6;

import java.io.EOFException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tamac
 */
public class JsonArray extends JsonValue {

    private final List<JsonValue> elements;

    public JsonArray() {
        elements = new ArrayList<>();
    }

    @Override
    public void parse(Lexer lexer) throws EOFException, JsonParseException {
        if (lexer.current() != '[') {
            throw new JsonParseException(0, lexer.current(), '[');
        }
        do {
            lexer.next();
            elements.add(JsonValue.parseValue(lexer));
            lexer.next();
        } while (lexer.current() == ',');
        lexer.next();
        lexer.check(']');
    }

    @Override
    public String toString() {
        return "Object(" + elements.toString() + ")";
    }
}
