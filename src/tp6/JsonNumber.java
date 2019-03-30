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
public class JsonNumber extends JsonValue {

    private double number;

    public JsonNumber() {
    }

    public JsonNumber(double number) {
        this.number = number;
    }

    public String toString() {
        return "Number(" + number + ")";
    }

    @Override
    public void parse(Lexer lexer) throws EOFException, JsonParseException {
        char c = lexer.current();
        if (c < '0' || c > '9') {
            throw new JsonParseException(0, '0', '0');
        }
        String num = "";
        do {
            lexer.get();
            num += lexer.current();
        } while (lexer.current() == '-'
                || lexer.current() == 'e'
                || lexer.current() == 'E'
                || lexer.current() == '.'
                || (lexer.current() >= '0' && lexer.current() <= '9'));
        try {
            number = Double.parseDouble(num);
        } catch (Exception e) {
            throw new JsonParseException(0, '0', '0');
        }
    }

}
