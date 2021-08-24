package main.java.command.list;

import main.java.Main;
import main.java.event.Log;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static main.java.Main.emoji;
import static main.java.Main.language;
import static main.java.event.Log.ignoreMessageID;
import static main.java.event.Log.logChannel;
import static main.java.lang.LangKey.*;
import static main.java.util.EmbedCreator.createEmbed;
import static main.java.util.PermissionERROR.hasPermission;
import static main.java.util.SlashCommandOption.COUNT;

public class Clear {


    /**
     * clear 指令
     */

    public void onCommand(@NotNull SlashCommandEvent event) {
        List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
        if (!hasPermission(Permission.MESSAGE_MANAGE, event, true))
            return;
        OptionMapping amountOption = event.getOption(COUNT);
        int amount = amountOption == null
                ? 100 // default 100
                : (int) Math.min(200, Math.max(2, amountOption.getAsLong())); // set number to 2~200
        // arg
        String userId = event.getUser().getId();
        event.getHook().editOriginalEmbeds(createEmbed("確定刪除 " + amount + " 則訊息?", 0xd0effe))
                .setActionRow(Button.danger("Clear:delete:" + userId + ':' + amount, "Yes!"))
                .queue();
    }

    public void onButton(@NotNull ButtonClickEvent event, String @NotNull [] args) {
        List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
        if (!args[0].equals("Clear"))
            return;
        if (args[1].equals("delete")) {
            event.deferEdit().setEmbeds(createEmbed("已刪除", 0x9740b9)).setActionRows().queue();
            int amount = Integer.parseInt(args[3]);
            try {
                List<Message> deleteMessage = event.getChannel().getIterableHistory()
                        .skipTo(event.getMessageIdLong())
                        .takeAsync(amount)
                        .get();
                deleteMessage.forEach(i -> ignoreMessageID.add(i.getId())); // 取得訊息
                event.getChannel().purgeMessages(deleteMessage);

                /**
                 * 紀錄刪除內容
                 */
                LinkedList<MessageEmbed.Field> fields = new LinkedList<>();
                int textLength = 0;
                /**
                 * 存放 i 至最大限制容量
                 */
                for (int i = 0; i < deleteMessage.size(); i++) {
                    Message thisMessage = deleteMessage.get(i);
                    String messageContent = thisMessage.getContentRaw();
                    String memberNickName;
                    if (thisMessage.getMember() == null)
                        memberNickName = "找無成員";
                    else if (thisMessage.getMember().getNickname() == null)
                        memberNickName = "名字找無";
                    else
                        memberNickName = "無";

                    if (messageContent.length() == 0)
                        messageContent = "找無";
                    int thisMessageLength = messageContent.length() + memberNickName.length();

                    if (textLength + thisMessageLength > 5000 || i == deleteMessage.size() - 1) {
                        if (i == deleteMessage.size() - 1) {
                            if (messageContent.length() > 1024)
                                messageContent = messageContent.substring(0, 1021) + "...";
                            fields.add(new MessageEmbed.Field(memberNickName, messageContent, false)); // 加入最後一個訊息
                        }
                        logChannel.sendMessageEmbeds(createEmbed(
                                "刪除: ", null,
                                "刪除訊息",
                                Main.botNickname, Main.botAvatarUrl,
                                fields,
                                OffsetDateTime.now(), 0x1ABC9C)
                        ).completeAfter(50, TimeUnit.MILLISECONDS);
                        fields.clear();
                        textLength = 0;
                    } else {
                        if (messageContent.length() > 1024)
                            messageContent = messageContent.substring(0, 1021) + "...";
                        fields.add(new MessageEmbed.Field(memberNickName, messageContent, false));
                        textLength += thisMessageLength;
                    }
                }

                event.getChannel().sendMessageEmbeds(
                        createEmbed(emoji.yesEmoji.getAsMention() + ' ' + amount + " 則文字已刪除！", 0xe3c6d6)
                ).queue(m -> Log.deleteNoLog(m, 2));
            } catch (InterruptedException | ExecutionException e) {
                logChannel.sendMessageEmbeds(createEmbed(
                        "錯誤: ", null,
                        e.getMessage(),
                        Main.botNickname, Main.botAvatarUrl,
                        OffsetDateTime.now(), 0x00FFFF)).queue();
            }
        }
    }
}
