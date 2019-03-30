/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp6;

import java.io.EOFException;
import java.text.ParseException;
import java.util.regex.Pattern;

/**
 *
 * @author tamac
 */
public class JsonNull extends JsonValue {

    public JsonNull() {
    }

    @Override
    public void parse(Lexer lexer) throws JsonParseException, EOFException {
        lexer.check('n');
        lexer.get();
        lexer.check('u');
        lexer.get();
        lexer.check('l');
        lexer.get();
        lexer.check('l');
    }

    @Override
    public String toString() {
        return "null";
    }

}
