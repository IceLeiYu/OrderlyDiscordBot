package main.java.event;

import main.java.util.StringCalculate;
import main.java.util.file.GuildSettingHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static main.java.util.JsonKeys.*;

public class StatusListener {
    private ScheduledExecutorService threadPool;

    //                guildID    typeName  value
    private final Map<String, Map<String, Integer>> guildsMemberStatus = new HashMap<>();

    //                guildID   channelID timeLeft
    private final Map<String, Map<String, Long>> guildsChannelTimer = new HashMap<>();

    private GuildSettingHelper settingHelper;

    public void startListen(JDA jda, GuildSettingHelper settingHelper) {
        this.settingHelper = settingHelper;
        if (threadPool != null && !threadPool.isShutdown())
            threadPool.shutdown();
        threadPool = Executors.newScheduledThreadPool(1);

        // run thread
        threadPool.scheduleWithFixedDelay(() ->
                        jda.getGuilds().forEach(this::updateGuild)
                , 0, 6, TimeUnit.MINUTES);
    }


    public void updateGuild(@NotNull Guild guild) {
        guild.loadMembers()
                .onError(error -> System.err.println(error.getMessage()))
                .onSuccess(members -> {
                    Map<String, Integer> memberStatus = new HashMap<>();
                    memberStatus.put("member", 0);
                    memberStatus.put("member_bot", members.size());
                    memberStatus.put("online", 0);
                    memberStatus.put("online_bot", 0);
                    memberStatus.put("offline", 0);
                    memberStatus.put("offline_bot", 0);
                    memberStatus.put("idle", 0);
                    memberStatus.put("idle_bot", 0);
                    memberStatus.put("dnd", 0);
                    memberStatus.put("dnd_bot", 0);
                    memberStatus.put("inVoiceChannel", 0);
                    memberStatus.put("inVoiceChannel_bot", 0);
                    memberStatus.put("stream", 0);
                    memberStatus.put("camera", 0);
                    memberStatus.put("play_minecraft", 0);
                    members.forEach(member -> {
                        if (member.getUser().isBot()) { // 包含機器人
                            // 線上狀態
                            memberStatus.put(member.getOnlineStatus().getKey() + "_bot", memberStatus.get(member.getOnlineStatus().getKey() + "_bot") + 1);

                            // 通話狀態
                            if (member.getVoiceState().inVoiceChannel())
                                memberStatus.put("inVoiceChannel_bot", memberStatus.get("inVoiceChannel_bot") + 1);

                        } else { // 不包含機器人
                            memberStatus.put("member", memberStatus.get("member") + 1);

                            // 線上狀態
                            memberStatus.put(member.getOnlineStatus().getKey(), memberStatus.get(member.getOnlineStatus().getKey()) + 1);
                            memberStatus.put(member.getOnlineStatus().getKey() + "_bot", memberStatus.get(member.getOnlineStatus().getKey() + "_bot") + 1);

                            // 通話狀態
                            if (member.getVoiceState().inVoiceChannel()) {
                                memberStatus.put("inVoiceChannel", memberStatus.get("inVoiceChannel") + 1);
                            }

                            if (member.getVoiceState().isStream())
                                memberStatus.put("stream", memberStatus.get("stream") + 1);

                            if (member.getVoiceState().isSendingVideo())
                                memberStatus.put("camera", memberStatus.get("camera") + 1);

                            // Play Minecraft
                            for (Activity activity : member.getActivities()) {
                                if (activity.getName().equals("Minecraft"))
                                    memberStatus.put("play_minecraft", memberStatus.get("play_minecraft") + 1);
                                else if (activity.getName().equals("LabyMod"))
                                    memberStatus.put("play_minecraft", memberStatus.get("play_minecraft") + 1);
                                else if (activity.getName().equals("Lunar Client"))
                                    memberStatus.put("play_minecraft", memberStatus.get("play_minecraft") + 1);
                                else if (activity.getName().equals("Badlion Client"))
                                    memberStatus.put("play_minecraft", memberStatus.get("play_minecraft") + 1);
                                else if (activity.getName().equals("Impact"))
                                    memberStatus.put("play_minecraft", memberStatus.get("play_minecraft") + 1);
                                else if (activity.getName().equals("Aristois"))
                                    memberStatus.put("play_minecraft", memberStatus.get("play_minecraft") + 1);
                                else if (activity.getName().equals("Skyblock"))
                                    memberStatus.put("play_minecraft", memberStatus.get("play_minecraft") + 1);
                            }
                        }
                    });
                    Map<String, Integer> lastMemberStatus;
                    int change = 0;
                    if ((lastMemberStatus = guildsMemberStatus.get(guild.getId())) != null) {
                        if ((int) lastMemberStatus.get("member") != memberStatus.get("member"))
                            change += 1;
                        if ((int) lastMemberStatus.get("member_bot") != memberStatus.get("member_bot"))
                            change += 1 << 1;
                        if ((int) lastMemberStatus.get("online") != memberStatus.get("online"))
                            change += 1 << 2;
                        if ((int) lastMemberStatus.get("online_bot") != memberStatus.get("online_bot"))
                            change += 1 << 3;
                        if ((int) lastMemberStatus.get("offline") != memberStatus.get("offline"))
                            change += 1 << 4;
                        if ((int) lastMemberStatus.get("offline_bot") != memberStatus.get("offline_bot"))
                            change += 1 << 5;
                        if ((int) lastMemberStatus.get("idle") != memberStatus.get("idle"))
                            change += 1 << 6;
                        if ((int) lastMemberStatus.get("idle_bot") != memberStatus.get("idle_bot"))
                            change += 1 << 7;
                        if ((int) lastMemberStatus.get("dnd") != memberStatus.get("dnd"))
                            change += 1 << 8;
                        if ((int) lastMemberStatus.get("dnd_bot") != memberStatus.get("dnd_bot"))
                            change += 1 << 9;
                        if ((int) lastMemberStatus.get("inVoiceChannel") != memberStatus.get("inVoiceChannel"))
                            change += 1 << 10;
                        if ((int) lastMemberStatus.get("inVoiceChannel_bot") != memberStatus.get("inVoiceChannel_bot"))
                            change += 1 << 11;
                        if ((int) lastMemberStatus.get("member") - lastMemberStatus.get("inVoiceChannel") != memberStatus.get("member") - memberStatus.get("inVoiceChannel"))
                            change += 1 << 12;
                        if ((int) lastMemberStatus.get("member_bot") - lastMemberStatus.get("inVoiceChannel_bot") != memberStatus.get("member_bot") - memberStatus.get("inVoiceChannel_bot"))
                            change += 1 << 13;
                        if ((int) lastMemberStatus.get("stream") != memberStatus.get("stream"))
                            change += 1 << 14;
                        if ((int) lastMemberStatus.get("camera") != memberStatus.get("camera"))
                            change += 1 << 15;
                        if ((int) lastMemberStatus.get("play_minecraft") != memberStatus.get("play_minecraft"))
                            change += 1 << 16;
                    } else {
                        change = 65535;
                    }
                    guildsMemberStatus.put(guild.getId(), memberStatus);
                    updateChannelStatus(guild, change, memberStatus);
                });
    }

    private void updateChannelStatus(Guild guild, int change, Map<String, Integer> memberStatus) {
        JSONObject data;
        if ((data = getSettingData(guild, settingHelper)) == null)
            return;


        data.keySet().forEach(channelID -> {
            if (guild.getGuildChannelById(channelID) == null)
                data.remove(channelID);
            else {
                String originalName = data.getJSONObject(channelID).getString(CS_NAME);
                String newName = data.getJSONObject(channelID).getString(CS_NAME);

                if (originalName.contains("%member%") && (change & 1) > 0)
                    newName = newName.replace("%member%", memberStatus.get("member").toString());

                if (originalName.contains("%member_bot%") && (change & (1 << 1)) > 0)
                    newName = newName.replace("%member_bot%", memberStatus.get("member_bot").toString());

                if (originalName.contains("%online_member%") && (change & (1 << 2)) > 0)
                    newName = newName.replace("%online_member%", memberStatus.get("online").toString());

                if (originalName.contains("%online_member_bot%") && (change & (1 << 3)) > 0)
                    newName = newName.replace("%online_member_bot%", memberStatus.get("online_bot").toString());

                if (originalName.contains("%offline_member%") && (change & (1 << 4)) > 0)
                    newName = newName.replace("%offline_member%", memberStatus.get("offline").toString());

                if (originalName.contains("%offline_member_bot%") && (change & (1 << 5)) > 0)
                    newName = newName.replace("%offline_member_bot%", memberStatus.get("offline_bot").toString());

                if (originalName.contains("%afk_member%") && (change & (1 << 6)) > 0)
                    newName = newName.replace("%afk_member%", memberStatus.get("idle").toString());

                if (originalName.contains("%afk_member_bot%") && (change & (1 << 7)) > 0)
                    newName = newName.replace("%afk_member_bot%", memberStatus.get("idle_bot").toString());

                if (originalName.contains("%working_member%") && (change & (1 << 8)) > 0)
                    newName = newName.replace("%dnd_member%", memberStatus.get("dnd").toString());

                if (originalName.contains("%working_member_bot%") && (change & (1 << 9)) > 0)
                    newName = newName.replace("%dnd_member_bot%", memberStatus.get("dnd_bot").toString());

                if (originalName.contains("%in_voicechannel%") && (change & (1 << 10)) > 0)
                    newName = newName.replace("%in_voicechannel%", memberStatus.get("inVoiceChannel").toString());

                if (originalName.contains("%in_voicechannel_bot%") && (change & (1 << 11)) > 0)
                    newName = newName.replace("%in_voicechannel_bot%", memberStatus.get("inVoiceChannel_bot").toString());

                if (originalName.contains("%not_voicechannel%") && (change & (1 << 12)) > 0)
                    newName = newName.replace("%not_voicechannel%", String.valueOf(memberStatus.get("member") - memberStatus.get("inVoiceChannel")));

                if (originalName.contains("%not_voicechannel_bot%") && (change & (1 << 13)) > 0)
                    newName = newName.replace("%not_voicechannel_bot%", String.valueOf(memberStatus.get("member_bot") - memberStatus.get("inVoiceChannel_bot")));

                if (originalName.contains("%stream%") && (change & (1 << 14)) > 0)
                    newName = newName.replace("%stream%", memberStatus.get("stream").toString());

                if (originalName.contains("%camera%") && (change & (1 << 15)) > 0)
                    newName = newName.replace("%camera%", memberStatus.get("camera").toString());

                if (originalName.contains("%play_minecraft%") && (change & (1 << 16)) > 0)
                    newName = newName.replace("%play_minecraft%", memberStatus.get("play_minecraft").toString());


                if (originalName.contains("${")) {
                    StringCalculate calculate = new StringCalculate();
                    newName = calculate.processes(newName, data.getJSONObject(channelID).getString(CS_FORMAT));
                    if (calculate.haveError())
                        return;
                }

                guildsChannelTimer.get(guild.getId()).get(channelID);
                GuildChannel channel = guild.getGuildChannelById(channelID);
                if (!channel.getName().equals(newName))
                    channel.getManager().setName(newName).queue();
            }
        });
    }

    private @Nullable JSONObject getSettingData(@NotNull Guild guild, @NotNull GuildSettingHelper settingHelper) {
        if (settingHelper.getGuildSettingManager(guild.getId()).data.has(CS_SETTING))
            return settingHelper.getGuildSettingManager(guild.getId()).data.getJSONObject(CS_SETTING);
        else {
            return null;
        }
    }

    /**
     * ONLINE("online"),
     * IDLE("idle"),
     * DO_NOT_DISTURB("dnd"),
     * INVISIBLE("invisible"),
     * OFFLINE("offline"),
     * UNKNOWN("");
     */
}