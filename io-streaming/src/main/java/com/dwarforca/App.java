package com.dwarforca;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        try {
            String filePath = "src/main/resources/data.txt";
            Path filename = Path.of(filePath);

            String actual = Files.readString(filename, StandardCharsets.UTF_8);
            String content = new String ( Files.readAllBytes( Paths.get(filePath) ) );

            var file = new DataInputStream(
                    new FileInputStream("src/main/resources/data.txt")
                    );
            var text = new InputStreamReader(
                    new FileInputStream("src/main/resources/data.txt"), StandardCharsets.UTF_8
                    );
            System.out.printf("%s", "hello");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
