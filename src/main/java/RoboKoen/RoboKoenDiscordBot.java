package RoboKoen;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Properties;

public class RoboKoenDiscordBot extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        System.out.println("Is this thing on???");
        //Get Access Key from environment vars
//        String accessKey = System.getenv("DISCORD_BOT_KEY");
//        if (accessKey.isEmpty() && accessKey.isBlank()) {
//            accessKey = getDiscordBotKeyLocally();
//        }

        String accessKey = getDiscordBotKeyLocally();

        //Instantiate bot
        JDA jda = JDABuilder.createLight(accessKey, EnumSet.noneOf(GatewayIntent.class)) // slash commands don't need any intents
                .addEventListeners(new RoboKoenDiscordBot())
                .build();

        CommandListUpdateAction commands = jda.updateCommands();

        String testCommandID = "test";
        commands.addCommands(
                Commands.slash(testCommandID, "Test to see if bot works.")

                //.setGuildOnly(true) <- Makes it so command only can be called in servers, and not DMs
                //.setDefaultPermissions(DefaultMemberPermissions.DISABLED) <- Limits who can call the command
        );

        //add other commands here

        /**Command to roll for gatcha
         * Need to check user database to see if they already have a profile, if not, make one and then roll.
         * Add their spoils to their collection.
         * How many times can users roll?
         * Pity System???
         * probably will have a max limit of 99 copies of each koen card
         */

        /** Command for checking gatcha collection
         *  Shows how many Koen cards they have, their rarity, their art, and how many copies.
         */

        /** Trading would be cool, but not right now.
         *  That shit is fucking hard to pull off in a chat application, so later down the road.
         */



        commands.queue();
    }

    public static String getDiscordBotKeyLocally() {
        try (InputStream input = new FileInputStream("local.properties")) {
            Properties prop = new Properties();

            prop.load(input);

            String accessKey = prop.getProperty("DISCORD_BOT_KEY");

            System.out.println(accessKey);

            return accessKey;
        }
        catch (IOException ex) {
            System.out.println("Koen Failure...");
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * Listens to the command event passed in, and executes server side code for said command
     * @param event Triggered slash command
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        // Only accept commands from guilds
        if (event.getGuild() == null)
            return;
        switch (event.getName())
        {
            case "test":
                koen(event); // content is required so no null-check here
                break;
            default:
                //setEphemeral(true) makes it so only the user who called the command sees it.
                event.reply("I don't know what that command is :(").setEphemeral(true).queue();
        }
    }

    //Test command that prints "Koen!" into the channel.
    public void koen(SlashCommandInteractionEvent event) {
        event.reply("Koen!").setEphemeral(true).queue();
    }

}
