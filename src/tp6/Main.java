/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp6;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author tamac
 */
public class Main {

    public static void main(String[] args) throws Exception {
        StringLexer lexer = null;
        String jsonText = new String(Files.readAllBytes(Paths.get("C:\\Users\\tamac\\OneDrive\\Desktop\\json.json")));

        lexer = new StringLexer(jsonText);
        JsonValue parseValue = JsonObject.parseValue(lexer);

    }
}
