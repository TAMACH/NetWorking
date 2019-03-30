/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp6;

import java.io.EOFException;
import java.text.ParseException;

/**
 *
 * @author Maroine
 */
public abstract class JsonValue {

    public abstract void parse(Lexer lexer) throws EOFException, JsonParseException;

    public static JsonValue parseValue(Lexer l) throws EOFException, JsonParseException {
        JsonValue v = null;
        char c = l.current();
        switch (c) {
            case '"':
                v = new JsonString();
                break;
            case 'n':
                v = new JsonNull();
                break;
            case 't':
                v = new JsonTrue();
                break;
            case 'f':
                v = new JsonFalse();
                break;
            case '[':
                v = new JsonArray();
                break;
            case '{':
                v = new JsonObject();
                break;
            default: {
                if (c >= '0' && c <= '9') {
                    v = new JsonNumber();
                }
            }
        }
        v.parse(l);
        return v;
    }
}
