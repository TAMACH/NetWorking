/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp6;

import java.io.EOFException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Maroine
 */
public class JsonObject extends JsonValue {

    private final Map<String, JsonValue> map;

    public JsonObject() {
        map = new HashMap<>();
    }

    @Override
    public void parse(Lexer lexer) throws EOFException, JsonParseException {
        if (lexer.current() != '{') {
            throw new JsonParseException(lexer.position(), lexer.current(), '}');
        }
        do {
            JsonString key = new JsonString();
            key.parse(lexer);
            lexer.check(':');
            JsonValue val = JsonValue.parseValue(lexer);
            map.put(key.getString(), val);
        } while (lexer.get() == ',');
        lexer.check('}');
    }

    public String toString() {
        return "Object(" + map.toString() + ")";
    }

}
