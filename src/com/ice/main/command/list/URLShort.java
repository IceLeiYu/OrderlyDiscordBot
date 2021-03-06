package com.ice.main.command.list;

import com.ice.main.util.UrlDataGetter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static com.ice.main.lang.LangKey.URLSHORT_FAIL;
import static com.ice.main.lang.LangKey.URLSHORT_SUCCESS;
import static com.ice.main.util.EmbedCreator.createEmbed;

public class URLShort {
    String v2Cookie;

    public URLShort() {
        try {
            Map<String, List<String>> map = new URL("https://reurl.cc/main/tw").openConnection().getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                if (entry.getKey() != null && entry.getKey().equals("Set-Cookie"))
                    for (String value : entry.getValue())
                        if (value.startsWith("clientIdV2")) {
                            int endIndex = value.indexOf(';');
                            v2Cookie = value.substring(0, endIndex);
                        }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void onCommand(SlashCommandInteractionEvent event, boolean convert, String url, List<String> lang) {
        String result;
        if (!convert)
            url = event.getOption("url").getAsString();

        result = UrlDataGetter.postCookie("https://reurl.cc/webapi/shorten/v2",
                "{\"url\" : \"" + url + "\"}", v2Cookie);

        if (result != null) {
            JSONObject data = new JSONObject(result);
            if (data.has("url") && !(data.has("status") && data.getInt("status") > 399)) {
                event.getHook().editOriginalEmbeds(createEmbed(
                        lang.get(URLSHORT_SUCCESS),
                        "https://reurl.cc/" + data.getString("url"),
                        data.getString("title"),
                        "https://api.qrserver.com/v1/create-qr-code/?data=https://reurl.cc/" + data.getString("url") + "&size=128x128",
                        0x00FFFF)
                ).queue();
                return;
            } else if (data.has("msg") && !convert) {
                event.getHook().editOriginalEmbeds(createEmbed(0xFF0000, data.getString("msg"))).queue();
                return;
            } else if (convert) {
                event.getHook().editOriginalEmbeds(createEmbed(
                        lang.get(URLSHORT_SUCCESS),
                        url,
                        data.getString("title"),
                        "https://api.qrserver.com/v1/create-qr-code/?data=" + url + "&size=128x128",
                        0x00FFFF)
                ).queue();
                return;
            }
        }
        event.getHook().editOriginalEmbeds(createEmbed(lang.get(URLSHORT_FAIL), 0xFF0000)).queue();
    }
}