package main.java.event;

import main.java.command.CommandRegister;
import main.java.command.list.Help;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

import static main.java.util.EmbedCreator.createEmbed;

public class NewGuild {
    public void onCommand(@NotNull GuildJoinEvent event, @NotNull CommandRegister commandRegister) {
        commandRegister.addPublicSlashCommand(event.getGuild());
        event.getGuild().retrieveOwner().complete().getUser().openPrivateChannel().queue(i ->
                i.sendMessageEmbeds(createEmbed("您已邀請 <**" +
                        event.getGuild().getSelfMember().getUser().getAsTag() +
                        "**> 進入 <**" + event.getGuild().getName() + "**>\n輸入 `/help` 顯示幫助列表\n" +
                        "You have invited <**" + event.getGuild().getSelfMember().getUser().getAsTag() +
                        "**> join <**" + event.getGuild().getName() +
                        "**> Discord Server\nType `/help` to show helps", "", "", "", "", new Help().summonMemberFields(null, true), OffsetDateTime.now(), 0x00FFFF)).queue());
    }
}