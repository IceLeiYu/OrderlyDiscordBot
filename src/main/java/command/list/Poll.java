package main.java.command.list;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static main.java.util.EmbedCreator.createEmbed;
import static main.java.util.EmojiUtil.dotEmojis;
import static main.java.util.PermissionERROR.hasPermission;
import static main.java.util.SlashCommandOption.QUESTION;

public class Poll {

    public void onCommand(@NotNull SlashCommandEvent event) {
        if (!hasPermission(Permission.MANAGE_CHANNEL, event, true))
            return;

        List<MessageEmbed.Field> fields = new ArrayList<>();
        for (int i = 1; i < event.getOptions().size(); i++) {
            fields.add(new MessageEmbed.Field(dotEmojis[i - 1].getAsMention() + event.getOptions().get(i).getAsString(), "", false));
        }
        event.getChannel().sendMessageEmbeds(createEmbed(
                Objects.requireNonNull(event.getOption(QUESTION)).getAsString(), null,
                "成員投票",
                Objects.requireNonNull(event.getMember()).getNickname() == null ? event.getUser().getAsTag() : event.getMember().getNickname(), event.getUser().getAvatarUrl(),
                fields,
                OffsetDateTime.now(), 0x87E5CF
        )).queue(m -> {
            for (int i = 0; i < event.getOptions().size() - 1; i++) {
                m.addReaction(dotEmojis[i]).queue();
            }
        });

        event.getHook().editOriginalEmbeds(createEmbed("創建成功", 0x9740b9)).queue();
    }
}