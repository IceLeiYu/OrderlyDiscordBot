package main.java.automatic;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static main.java.util.GuildUtil.guild;

public class InformationReaction extends ListenerAdapter {

    public static Role logRole;
    public static Role internalRole;

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        // 若公告改變 需更改
        if (!event.getMessageId().equals("864033587446415410")) return;
        Member member = event.getMember();
        switch (event.getReactionEmote().getEmote().getName()) {
            case "Bot":
                guild.addRoleToMember(member, logRole).queue();
                break;
            case "MinecraftGrassBlock":
                guild.addRoleToMember(member, internalRole).queue();
                break;
//            case "RightArrow":
//
//                List<MessageEmbed.Field> fields = new ArrayList<>();
//                fields.add(new MessageEmbed.Field("", "", false));
//
//                fields.add(new MessageEmbed.Field("", emoji.borderLeftTop.getAsMention() + emoji.borderTop.getAsMention().repeat(7) + "\n" +
//                        emoji.borderLeft.getAsMention() + " **簡介 | Introduction**\n" +
//                        emoji.borderLeft.getAsMention() + " \n" +
//                        emoji.borderLeft.getAsMention() + " 　　此地是以秩序與紀律為核心發展的伺服器\n" +
//                        emoji.borderLeft.getAsMention() + " 　　希望每位成員可以共同帶動伺服器發展\n" +
//                        emoji.borderLeft.getAsMention() + " 　　正常情況下，管理員不會干涉玩家的任何事件\n" +
//                        emoji.borderLeft.getAsMention() + " 　　不會限制玩家的所有作為\n" +
//                        emoji.borderLeft.getAsMention() + " 　　一切由機器人判斷為基準\n" +
//                        emoji.borderLeftBottom.getAsMention() + emoji.borderBottom.getAsMention().repeat(7)
//                        , false));
//
//                fields.add(new MessageEmbed.Field("", emoji.borderLeftTop.getAsMention() + emoji.borderTop.getAsMention().repeat(7) + "\n" +
//                        emoji.borderLeft.getAsMention() + " **指令 | Commands**\n" +
//                        emoji.borderLeft.getAsMention() + "\n" +
//                        emoji.borderLeft.getAsMention() + " 　　**關於房間 | About Rooms**\n" +
//                        emoji.borderLeft.getAsMention() + "\n" +
//                        emoji.borderLeft.getAsMention() + " 　　`/vinvite <@user>`\t| 給予成員權限\n" +
//                        emoji.borderLeft.getAsMention() + " 　　`/vkick <@user>`\t| 移除成員權限\n" +
//                        emoji.borderLeft.getAsMention() + " 　　`/public`\t| 將你所在的頻道改為公開\n" +
//                        emoji.borderLeft.getAsMention() + " 　　`/private`\t| 將你所在的頻道改為私人\n" +
//                        emoji.borderLeft.getAsMention() + " 　　`/vinfo (#channel)`\t| 列出頻道數據\n" +
//                        emoji.borderLeft.getAsMention() + " 　　`/vpromote <@user>`\t| 提拔成員\n" +
//                        emoji.borderLeft.getAsMention() + " 　　`/vunpromote <@user>`\t| 移除管理員\n" +
//                        emoji.borderLeftBottom.getAsMention() + emoji.borderBottom.getAsMention().repeat(7)
//                        , true));
//
//                fields.add(new MessageEmbed.Field("", emoji.borderTop.getAsMention().repeat(8) + "\n\n" +
//                        "\n" +
//                        " 　　**關於音樂 | About Music**\n" +
//                        "\n" +
//                        " 　　`/play <url>`\t| 加入播放音樂\n" +
//                        " 　　`/skip`\t| 切換至下一首\n" +
//                        " 　　`/repeat`\t| 切換單曲循環\n"
//                        , true));
//
//
//                fields.add(new MessageEmbed.Field("", emoji.borderLeftTop.getAsMention() + emoji.borderTop.getAsMention().repeat(7) + "\n" +
//                        emoji.borderLeft.getAsMention() + " **頻道 | Channels**\n" +
//                        emoji.borderLeft.getAsMention() + "\n" +
//                        emoji.borderLeft.getAsMention() + " 　　" + emoji.bot.getAsMention() + " 《\uD83D\uDCBE》紀錄 | 檢視伺服器紀錄\n" +
//                        emoji.borderLeft.getAsMention() + " 　　" + emoji.minecraftGrassBlock.getAsMention() + " 《\uD83D\uDCC4》生存內頻道 | Discord 與 Minecraft 對話通道\n" +
//                        emoji.borderLeft.getAsMention() + "\n" +
//                        emoji.borderLeft.getAsMention() + "\n" +
//                        emoji.borderLeft.getAsMention() + " Copyright © 2021 ORDERLY SERVER\n" +
//                        emoji.borderLeftBottom.getAsMention() + emoji.borderBottom.getAsMention().repeat(7)
//                        , false));
//
//                guild.getTextChannelById("858672865444626439").sendMessage(createEmbed(
//                        "原之序 | ORDERLY SERVER", null,
//                        "",
//                        "", "",
//                        fields,
//                        null, 0xFFD1DC
//                )).queue();
//
//                event.getChannel().addReactionById(event.getMessageId(), emoji.bot).queue();
//                event.getChannel().addReactionById(event.getMessageId(), emoji.minecraftGrassBlock).queue();
//                break;
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        // 若公告改變 需更改
        if (!event.getMessageId().equals("864033587446415410")) return;
        Member member = event.getMember();
        switch (event.getReactionEmote().getEmote().getName()) {
            case "Bot":
                guild.removeRoleFromMember(member, logRole).queue();
                break;
            case "MinecraftGrassBlock":
                guild.removeRoleFromMember(member, internalRole).queue();
                break;
        }
    }
}