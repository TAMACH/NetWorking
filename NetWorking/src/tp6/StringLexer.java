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
public class StringLexer implements Lexer {

    private final String jsonText;
    private int currentIndex;

    public StringLexer(String jsonText) {
        this.jsonText = jsonText;
        this.currentIndex = 0;
    }

    @Override
    public char current() {
        return jsonText.charAt(currentIndex);
    }

    @Override
    public char get() throws EOFException {
        char current = current();
        currentIndex++;
        return current;
    }

    @Override
    public void skipWhiteSpace() {
        if (Character.isWhitespace(current())) {
            currentIndex++;
        };
    }

    @Override
    public void next() throws EOFException {
        while (Character.isWhitespace(current())) {
            get();
        }
    }

    @Override
    public void check(char c) throws JsonParseException {
        if (current() != c) {
            throw new JsonParseException(currentIndex, current(), c);
        }
    }

    @Override
    public int position() {
        return currentIndex;
    }

    public String toString() {
        return jsonText.substring(currentIndex);
    }
}
