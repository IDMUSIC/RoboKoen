package RoboKoen;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.EnumSet;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class RoboKoenDiscordBot extends ListenerAdapter {

    //Holy shit this is so unsafe, I gotta figure out how to do this correctly later.
    private static String accessKey = "MTA4MDYwMDkwNjEyODY5MTMzMg.GC-cJW.js6aV06hrSmvo0TbNqAxFqMpJ06HN2U1VV6dr8";
    public static void main(String[] args) throws LoginException {
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


        commands.queue();
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
