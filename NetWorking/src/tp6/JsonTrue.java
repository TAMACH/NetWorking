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
public class JsonTrue extends JsonValue {

    public JsonTrue() {
    }

    @Override
    public void parse(Lexer lexer) throws JsonParseException, EOFException {
        lexer.check('t');
        lexer.get();
        lexer.check('r');
        lexer.get();
        lexer.check('u');
        lexer.get();
        lexer.check('e');
    }

    public String toString() {
        return "true";
    }

}
