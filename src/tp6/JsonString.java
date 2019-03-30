/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp6;

import java.io.EOFException;

/**
 *
 * @author Maroine
 */
public class JsonString extends JsonValue {

    private String string;

    public JsonString() {

    }

    public JsonString(String string) {
        this.string = string;
    }

    public String toString() {
        return "String( " + string;
    }

    public String getString() {
        return string;
    }

    @Override
    public void parse(Lexer lexer) throws EOFException, JsonParseException {
        if (lexer.current() != '"') {
            throw new JsonParseException(0, lexer.current(), '"');
        }
        while (lexer.get() != '"') {
            string += lexer.current();
        }
    }
}
