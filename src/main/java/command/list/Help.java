package main.java.command.list;

import main.java.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static main.java.lang.LangKey.HELP_USAGE;
import static main.java.lang.LangKey.LANG_CREATE_SUCCESS;
import static main.java.util.EmbedCreator.createEmbed;
import static main.java.util.PermissionERROR.hasPermission;

public class Help {


    public void onAnnouncementCommand(@NotNull SlashCommandEvent event) {
        List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
        if (hasPermission(Permission.ADMINISTRATOR, event, true)) {
            event.getChannel().sendMessageEmbeds(createEmbed(lang.get(HELP_USAGE), "", "", "", "", summonAnnouncementFields(), OffsetDateTime.now(), 0x00FFFF)).queue();
            event.getHook().editOriginalEmbeds(createEmbed(lang.get(LANG_CREATE_SUCCESS), 0x0FFFF)).queue();
        }
    }

    public void onMemberCommand(@NotNull SlashCommandEvent event) {
        List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
        event.getHook().editOriginalEmbeds(createEmbed(lang.get(HELP_USAGE), "", "", "", "", summonMemberFields(event.getMember(), false), OffsetDateTime.now(), 0x00FFFF)).queue();
    }

    public List<MessageEmbed.Field> summonMemberFields(Member member, boolean showALL) {

        /**
         * Help Fields
         */

        List<MessageEmbed.Field> helpFields = new ArrayList<>();

        helpFields.add(new MessageEmbed.Field("",

                """
                        **__指令__ | __Commands__**

                        ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                        ┃　**音樂 | Music**
                        ┃
                        ┃　　`/p {URL | NAME}` | 播放網址或查詢音樂
                        ┃　　`/s` | 播放下一首音樂
                        ┃　　`/q` | 顯示播放數據或列表
                        ┃　　`/pause` | 暫停播放
                        ┃　　`/loop` | 切換循環模式
                        ┃　　`/repeat` | 切換單曲循環模式
                        ┃　　`/volume [COUNT]` | 更改音樂音量 (1-100)
                        ┗
                        """
                , false));


        helpFields.add(new MessageEmbed.Field("", "" +
                "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "┃　 ** 通用 | General **\n" +
                "┃\n" +
                "┃　　`/help` | 顯示通用幫助列表\n" +
                "┃　　`/info` | 顯示伺服器資訊\n" +
                (showALL || (member.hasPermission(Permission.KICK_MEMBERS)) ? "┃　　`/helpannouncement` | 將幫助列表以公告呈現\n" : "") +
                (showALL || (member.hasPermission(Permission.KICK_MEMBERS)) ? "┃　　`/ban <@>` | 封鎖成員\n┃　　`/unban <ID>` | 解除封鎖成員\n" : "") +
                (showALL || (member.hasPermission(Permission.BAN_MEMBERS)) ? "┃　　`/kick <@>` | 踢出成員\n" : "") +
                (showALL || (member.hasPermission(Permission.MESSAGE_MANAGE)) ? "┃　　`/poll <Q> [A] ...` | 發起投票\n┃　　`/clear <COUNT>` | 刪除訊息\n" : "") +
                "┃　　`/support` | 傳送問題回報\n" +
                "┃　　`/botinfo` | 顯示機器人訊息\n" +
                "┃　　`/ping` | 延遲測試\n" +
                "┃　　`/surl` | 縮短網址\n" +
                "┃　　`/popcat` | 查看 PopCat 排行榜\n" +
                "┃　　`/mp4togif` | 將影片連結轉換為 Gif\n" +
                "┗"
                , false));

        helpFields.add(new MessageEmbed.Field("",
                """
                        ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                        ┃　 ** 進階 | Advance **
                        ┃
                        ┃　　`/setting help` | 顯示進階幫助列表
                        ┗
                        """
                , false));

        return helpFields;
    }

    private @NotNull List<MessageEmbed.Field> summonAnnouncementFields() {

        /**
         * Help Fields
         */

        List<MessageEmbed.Field> helpFields = new ArrayList<>();

        helpFields.add(new MessageEmbed.Field("",

                """
                        **__指令__ | __Commands__**

                        ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                        ┃　**音樂 | Music**
                        ┃
                        ┃　　`/p {URL | NAME}` | 播放網址或查詢音樂
                        ┃　　`/s` | 播放下一首音樂
                        ┃　　`/q` | 顯示播放數據或列表
                        ┃　　`/pause` | 暫停播放
                        ┃　　`/loop` | 切換循環模式
                        ┃　　`/repeat` | 切換單曲循環模式
                        ┃　　`/volume [COUNT]` | 更改音樂音量 (1-100)
                        ┗
                        """
                , false));


        helpFields.add(new MessageEmbed.Field("",
                """
                        ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                        ┃　 ** 通用 | General **
                        ┃
                        ┃　　`/help` | 顯示幫助列表
                        ┃　　`/support` | 傳送問題回報
                        ┃　　`/botinfo` | 顯示機器人訊息
                        ┃　　`/ping` | 延遲測試
                        ┗
                        """
                , false));

        return helpFields;
    }

}
