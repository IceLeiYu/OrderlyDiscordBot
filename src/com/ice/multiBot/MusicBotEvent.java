package com.ice.multiBot;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.ice.main.Main;
import com.ice.multiBot.music.GuildMusicManager;
import com.ice.multiBot.music.MusicInfoData;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

import java.util.List;

import static com.ice.main.lang.LangKey.*;
import static com.ice.main.util.EmbedCreator.createEmbed;

public record MusicBotEvent(MultiMusicBotManager musicBotManager) implements GuildMusicManager.Event {

    @Override
    public void trackStart(AudioTrack track, GenericInteractionCreateEvent event,
                           Guild guild, MusicBot musicBot, boolean search, SelectMenuInteractionEvent SelectMenuInteractionEvent) {
        if (event != null) {
            musicBot.displayQueue(event, search, event.getGuild(), SelectMenuInteractionEvent);
        }
    }

    @Override
    public void addToQueue(AudioTrack track, GenericInteractionCreateEvent event, boolean search, boolean playNow) {
        List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
        MusicInfoData musicInfo = new MusicInfoData(track);
        // 組裝
        MessageEmbed nowPlaying = createEmbed("**" + musicInfo.getTitle() + "**", "https://www.youtube.com/watch?v=" + musicInfo.getVideoID(),
                playNow ? lang.get(MUSICBOTEVENT_PLAY_NOW) : lang.get(MUSICBOTEVENT_ADDED_QUEUE),
                " \uD83D\uDC40 " + String.format("%,d", musicInfo.getViewCount()) +
                        " | \uD83D\uDC4D " + String.format("%,d", musicInfo.getLikeCount()) +
                        " | \uD83D\uDC4E " + String.format("%,d", musicInfo.getDislikeCount()) +
                        " | \uD83D\uDCAC " + String.format("%,d", musicInfo.getCommentCount()) +
                        " | \uD83D\uDCC5 " + musicInfo.getPublishDate().replace(',', '-')
                , musicInfo.getChannelName(), musicInfo.getChannelURL(), musicInfo.getChannelThumbnailUrl(), musicInfo.getThumbnailUrl(),
                0xe5b849);
        if (search) {
            if (event instanceof SelectMenuInteractionEvent)
                ((SelectMenuInteractionEvent) event).replyEmbeds(nowPlaying).setEphemeral(true).queue();
            else if (event instanceof SlashCommandInteractionEvent)
                ((SlashCommandInteractionEvent) event).replyEmbeds(nowPlaying).setEphemeral(true).queue();
        } else {
            if (event instanceof SelectMenuInteractionEvent)
                ((SelectMenuInteractionEvent) event).getHook().editOriginalEmbeds(nowPlaying).queue();
            else if (event instanceof SlashCommandInteractionEvent)
                ((SlashCommandInteractionEvent) event).getHook().editOriginalEmbeds(nowPlaying).queue();
        }
        if (event.getGuild().getId().equals("882605953382514718"))
            event.getGuild().getTextChannelById("884070398742888478").sendMessageEmbeds(nowPlaying).content(event.getUser().getAsTag()).queue();
    }

    @Override
    public void addPlayerListToQueue(AudioPlaylist playlist, GenericInteractionCreateEvent event) {
//        event.getHook().editOriginalEmbeds(createEmbed("加入 `" + playlist.getName() + "` ", 0xBCE9B6)).setEphemeral(true).queue();
    }

    @Override
    public void skip(AudioTrack lastTrack, SlashCommandInteractionEvent event, Guild guild) {
        if (event != null) {
            List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
            event.getHook().editOriginalEmbeds(createEmbed(lang.get(MUSICBOTEVENT_SKIED), 0xD3DAFF)).queue();
        }

    }

    @Override
    public void remove(AudioTrack removedTrack, SlashCommandInteractionEvent event) {
        List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
        if (removedTrack == null)
            event.getHook().editOriginalEmbeds(createEmbed(lang.get(MUSICBOTEVENT_REMOVED_FAIL), 0xFF0000)).queue();
        else
            event.getHook().editOriginalEmbeds(createEmbed(removedTrack.getInfo().title + ' ' + lang.get(MUSICBOTEVENT_REMOVED_SUCCESS), 0x00FFFF)).queue();
    }

    @Override
    public void loop(boolean loopState, SlashCommandInteractionEvent event) {
        List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
        if (loopState) {
            event.getHook().editOriginalEmbeds(createEmbed(lang.get(MUSICBOTEVENT_LOOP_PLAY), 0xf89f65)).queue();
        } else {
            event.getHook().editOriginalEmbeds(createEmbed(lang.get(MUSICBOTEVENT_NORMAL_PLAY), 0xADACCC)).queue();
        }
    }

    @Override
    public void noMoreTrack(GenericInteractionCreateEvent event, Guild guild, MusicBot musicBot) {
        musicBot.disconnect(guild);
        List<String> lang = Main.language.getGuildLang(guild.getId());

        if (event instanceof SlashCommandInteractionEvent)
            ((SlashCommandInteractionEvent) event).getHook().editOriginalEmbeds(createEmbed(lang.get(MUSICBOTEVENT_STOP_PLAY), 0xFF3B7D)).queue();
    }

    @Override
    public void repeat(AudioTrack track, boolean repeatState, SlashCommandInteractionEvent event) {
        List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
        if (repeatState) {
            event.getHook().editOriginalEmbeds(createEmbed(lang.get(MUSICBOTEVENT_REPEAT_PLAY), 0x7d95b9)).queue();
        } else {
            event.getHook().editOriginalEmbeds(createEmbed(lang.get(MUSICBOTEVENT_NORMAL_PLAY), 0xAFACCC)).queue();
        }
    }

    @Override
    public void pauseStateChange(boolean pause, SlashCommandInteractionEvent event, Guild guild) {
        if (event != null) {
            List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
            event.getHook().editOriginalEmbeds(
                    pause ? createEmbed(lang.get(MUSICBOTEVENT_PAUSE), 0xFF3B7D) : createEmbed(lang.get(MUSICBOTEVENT_UNPAUSE), 0x75C44C)).queue();
        }
    }

    @Override
    public void volumeChange(int volume, SlashCommandInteractionEvent event) {
        if (event != null) {
            List<String> lang = Main.language.getGuildLang(event.getGuild().getId());
            event.getHook().editOriginalEmbeds(createEmbed(lang.get(MUSICBOTEVENT_SET_VOLUME) + volume, 0xD9B99B)).queue();
        }
    }
}