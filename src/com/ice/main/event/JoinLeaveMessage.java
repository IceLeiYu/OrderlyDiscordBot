package com.ice.main.event;

import com.ice.main.util.file.GuildSettingHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import org.json.JSONObject;

import static com.ice.main.util.EmbedCreator.createEmbed;
import static com.ice.main.util.JsonKeys.*;

public record JoinLeaveMessage(GuildSettingHelper settingHelper) {

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        JSONObject data;
        if ((data = settingHelper.getSettingData(event.getGuild(), J_SETTING)) == null)
            return;

        Guild guild = event.getGuild();

        for (String i : data.keySet()) {
            TextChannel channel;

            if ((channel = guild.getTextChannelById(i)) == null) {
                data.remove(i);
                return;
            }

            for (Object j : data.getJSONObject(i).getJSONArray(J_JOIN_ROLE)) {
                Role role;
                if ((role = guild.getRoleById(String.valueOf(j))) == null) {
                    data.getJSONObject(i).remove(String.valueOf(j));
                    settingHelper.getGuildSettingManager(event.getGuild().getId()).saveFile();
                    continue;
                }
                guild.addRoleToMember(event.getMember(), role).queue();
            }

            if (data.has(J_JOIN_MESSAGE) && channel.canTalk())
                channel.sendMessageEmbeds(createEmbed(data.getString(J_JOIN_MESSAGE), 0xd6ff8e)).queue();
        }
    }


    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        JSONObject data;
        if ((data = settingHelper.getSettingData(event.getGuild(), L_SETTING)) == null)
            return;

        Guild guild = event.getGuild();

        for (String i : data.keySet()) {
            TextChannel channel;

            if ((channel = guild.getTextChannelById(i)) == null) {
                data.remove(i);
                return;
            }

            if (data.has(L_LEAVE_MESSAGE) && channel.canTalk())
                channel.sendMessageEmbeds(createEmbed(data.getString(L_LEAVE_MESSAGE), 0xef4565)).queue();
        }
    }
}