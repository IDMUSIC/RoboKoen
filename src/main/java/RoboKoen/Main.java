package RoboKoen;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        System.out.println("Init Koen...");
        try (InputStream input = new FileInputStream("src/main/resources/local.properties")) {
            //
            Properties prop = new Properties();

            prop.load(input);

            System.out.println(prop.getProperty("DISCORD_BOT_KEY"));

            //RoboKoenDiscordBot bot = new RoboKoenDiscordBot();
        }
        catch (IOException ex) {
            System.out.println("Koen Failure...");
            ex.printStackTrace();
        }
    }
}