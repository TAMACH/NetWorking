/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp6;

import java.io.EOFException;

/**
 *
 * @author tamac
 */
public class JsonFalse extends JsonValue {

    public JsonFalse() {
    }

    @Override
    public String toString() {
        return "false";
    }

    @Override
    public void parse(Lexer lexer) throws EOFException, JsonParseException {
        lexer.check('f');
        lexer.get();
        lexer.check('a');
        lexer.get();
        lexer.check('l');
        lexer.get();
        lexer.check('s');
        lexer.get();
        lexer.check('e');
    }

}
