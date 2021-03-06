package com.ice.main.command.list;

import com.ice.main.Main;
import com.ice.main.command.CommandRegister;
import com.ice.main.util.file.GuildSettingHelper;
import com.ice.main.util.file.JsonFileManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import org.json.JSONObject;

import java.util.List;

import static com.ice.main.lang.LangKey.LANG_CHOOSE;
import static com.ice.main.lang.LangKey.LANG_SUCCESS;
import static com.ice.main.util.CountryCodeToEmoji.countryCodeToEmoji;
import static com.ice.main.util.EmbedCreator.createEmbed;
import static com.ice.main.util.JsonKeys.GUILD_LANG;
import static com.ice.main.util.PermissionERROR.permissionCheck;

public record Language(GuildSettingHelper settingHelper) {
    public void onCommand(SlashCommandInteractionEvent event) {
        if (!permissionCheck(Permission.ADMINISTRATOR, event, true))
            return;


        List<String> lang = Main.language.getGuildLang(event.getGuild().getId());

        SelectMenu.Builder builder = SelectMenu.create("Lang:change:" + event.getUser().getId());

        for (String i : Main.language.languagesName)
            builder.addOption(i, i, Emoji.fromUnicode(countryCodeToEmoji(i.split("_")[1].toLowerCase())));

        event.getHook().editOriginalComponents().setEmbeds(createEmbed(lang.get(LANG_CHOOSE), 0xa3d7fe)).setActionRow(builder.build()).queue();

    }

    public void onSelect(SelectMenuInteractionEvent event, String[] args, CommandRegister register) {
        String guildID = event.getGuild().getId();
        if (!args[0].equals("Lang"))
            return;
        if (args[1].equals("change")) {
            JsonFileManager fileManager = settingHelper.getGuildSettingManager(guildID);
            JSONObject data = fileManager.data;
            String langStr;
            switch (event.getValues().get(0)) {
                case "zh_TW", "zh_HK", "zh_SG", "zh_MO" -> langStr = "zh_TW";
                case "zh_CN" -> langStr = "zh_CN";
                default -> langStr = "en_US";
            }
            data.put(GUILD_LANG, langStr);
            List<String> lang = Main.language.setGuildLang(guildID, langStr);
            register.addPublicSlashCommand(event.getGuild());
            fileManager.saveFile();
            event.deferReply(true).addEmbeds(createEmbed(lang.get(LANG_SUCCESS), 0x00FFFF)).queue();
        }
    }
}
