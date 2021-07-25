package multiBot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import multiBot.MusicBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
    private final Guild guild;
    private final AudioPlayer player;
    private final List<AudioTrack> queue;
    private int lastIndex = 0;
    private int index = 0;

    private GuildMusicManager.Event event;
    // now playing
    public AudioTrack playingTrack;
    public long startPlayTime;

    // setting
    public boolean repeat;
    public boolean loop;
    private final int defaultVolume = 20;

    public int loopStatus = 0;

    /**
     * @param player The audio player this scheduler uses
     */
    public TrackScheduler(AudioPlayer player, Guild guild) {
        this.guild = guild;
        this.player = player;
        this.queue = new ArrayList<>();
    }

    public void addPlayListToQueue(AudioPlaylist playlist, GenericInteractionCreateEvent event, MusicBot musicBot) {
        List<AudioTrack> trackList = playlist.getTracks();
        if (trackList.size() == 0)
            return;

        // 加入序列
        for (int i = 1; i < trackList.size(); i++) {
            queue.add(trackList.get(i));
        }
        // 嘗試播放
        queue(trackList.get(0), event, musicBot, 0);

        this.event.addPlayerListToQueue(playlist, event);
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track    The track to play or add to queue.
     * @param musicBot
     */
    public void queue(AudioTrack track, GenericInteractionCreateEvent event, MusicBot musicBot, int position) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (position != -1)
            queue.add(position, track);
        else
            queue.add(track);
        if (!player.startTrack(track, true)) {
            // 加入序列
            this.event.addToQueue(track, event);
        } else {
            // 開始撥放
            playingTrack = track;
            startPlayTime = System.currentTimeMillis();
            calculateNormalized(track, defaultVolume);
            this.event.playStart(track, event, guild, musicBot);
        }
    }

    private boolean playTrack() {
        AudioTrack track;
        if (repeat) {
            index = lastIndex;
            track = playingTrack.makeClone();
        } else {
            if (queue.size() == 0)
                return false;
            if (index < 0) {
                index = lastIndex;
                return false;
            }

            if (index == queue.size()) {
                if (loop)
                    index = 0;
                else
                    return false;
            }

            // 取得歌曲
            track = queue.get(index);
            if (index < lastIndex)
                track = track.makeClone();
        }

        if (player.startTrack(track, false)) {
            playingTrack = track;
            startPlayTime = System.currentTimeMillis();
            calculateNormalized(track, defaultVolume);
            return true;
        }
        return false;
    }

    public void nextTrack(SlashCommandEvent event) {
        lastIndex = index;
        index++;
        if (playTrack()) {
            this.event.skip(playingTrack, event, guild);
            this.event.playStart(playingTrack, event, guild, null);
        } else {
            stopPlay(event);
        }
//        nextTrack(true, event);
    }

    public void previousTrack(SlashCommandEvent event) {
        lastIndex = index;
        index--;
        if (playTrack())
            this.event.playStart(playingTrack, event, guild, null);
        else
            stopPlay(event);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext) {
            lastIndex = index;
            index++;
            if (!playTrack())
                stopPlay(null);
        }
    }

    public void stopPlay(SlashCommandEvent event) {
        playingTrack = null;
        musicPause = false;
        player.setPaused(false);
        player.stopTrack();
        this.event.noMoreTrack(event, guild);
    }

    public List<AudioTrack> getQueue() {
        return new ArrayList<>(queue).subList(index, queue.size());
    }

    public void toggleRepeat(SlashCommandEvent slashCommandEvent) {
        repeat = !repeat;
        event.repeat(playingTrack, repeat, slashCommandEvent);
    }

    /**
     * pause
     */
    public boolean musicPause = false;
    private long pauseStart;

    public void pause(SlashCommandEvent event, boolean play) {
        if (play) {
            if (musicPause) {
                startPlayTime += System.currentTimeMillis() - pauseStart;
                player.setPaused(false);
                musicPause = false;
            }
        } else {
            player.setPaused(musicPause = !musicPause);
            if (musicPause)
                pauseStart = System.currentTimeMillis();
            else
                startPlayTime += System.currentTimeMillis() - pauseStart;

            this.event.pause(musicPause, event, guild);
        }
    }

    /**
     * volume control
     */
    private double percent = -1;
    public float loudness = 0f;

    public void changeVolume(Integer targetVolume, SlashCommandEvent event) {

        if (targetVolume == null)
            targetVolume = defaultVolume;

        if (percent > -1) {
            player.setVolume((int) Math.round(percent * targetVolume));
        } else
            player.setVolume(targetVolume);

        this.event.volumeChange(targetVolume, event);
    }

    private void calculateNormalized(AudioTrack audioTrack, int defaultVolume) {
        String videoID = audioTrack.getInfo().identifier;

        // get video info
        String url = "https://youtubei.googleapis.com/youtubei/v1/player?key=AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8";
        String payload = "{\"videoId\":\"" + videoID + "\",\"context\":{\"client\":{\"hl\":\"zh\",\"gl\":\"TW\",\"clientName\":\"ANDROID\",\"clientVersion\":\"16.02\"}}}";
        String result = getUrl(url, payload);

        if (result == null) {
            player.setVolume(defaultVolume);
            return;
        }

        JSONObject playerResponse = new JSONObject(result).getJSONObject("playerConfig").getJSONObject("audioConfig");
        if (playerResponse.has("loudnessDb"))
            loudness = playerResponse.getFloat("loudnessDb");
        percent = ((95 + -7.22 * loudness) / 100);
        player.setVolume((int) Math.round(percent * defaultVolume) + 2);
    }

    public String getUrl(String input, String payload) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(input).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            //post
            OutputStream payloadOut = connection.getOutputStream();
            payloadOut.write(payload.getBytes(StandardCharsets.UTF_8));
            payloadOut.flush();
            //get
            InputStream in;
            if (connection.getResponseCode() > 200)
                in = connection.getErrorStream();
            else
                in = connection.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int length;
            while ((length = in.read(buff)) > 0) {
                out.write(buff, 0, length);
            }
            return out.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static String getUrlData(String urlStr) {
        URL url;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            return null;
        }
        //get result
        try {
            InputStream in = url.openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int length;
            while ((length = in.read(buff)) > 0) {
                out.write(buff, 0, length);
            }
            return out.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    public void setManagerEvent(GuildMusicManager.Event event) {
        this.event = event;
    }
}