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
public interface Lexer {

    public char current();

    public char get() throws EOFException;

    public void skipWhiteSpace();

    public void next() throws EOFException;

    public void check(char c) throws JsonParseException;

    public int position();

}
