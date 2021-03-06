package com.ice.main.command.list;

import com.ice.main.Main;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.List;

import static com.ice.main.lang.LangKey.INFO_SERVER_COUNT;

public class Info {

    @SuppressWarnings("ALL")
    public void onCommand(SlashCommandInteractionEvent event) {
        List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
        List<MessageEmbed.Field> fields = new ArrayList<>();
        fields.add(new MessageEmbed.Field(lang.get(INFO_SERVER_COUNT), String.valueOf((long) event.getJDA().getGuilds().size()), false));

//        event.getHook().editOriginalEmbeds(createEmbed());

    }
}
