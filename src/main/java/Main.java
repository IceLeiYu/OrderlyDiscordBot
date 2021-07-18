package main.java;

import main.java.automatic.InformationReaction;
import main.java.automatic.TicketChannel;
import main.java.command.BlockCommand;
import main.java.event.GeneralReplay;
import main.java.event.Join;
import main.java.event.Log;
import main.java.util.EmojiUtil;
import main.java.util.GuildUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static main.java.BotSetting.activityMessages;
import static main.java.BotSetting.botToken;


public class Main {
    public static String botID;
    public static String botNickname, botAvatarUrl;
    public static BotSetting setting;
    public static SelfUser self;
    public static EmojiUtil emoji = new EmojiUtil();
    private final String TAG = "[Main]";

    //interval
    private ScheduledExecutorService threadPool;
    private int currentIndex = 0;

    Main() throws LoginException {
        setting = new BotSetting(); // 讀取設定

        /**
         * init bot
         */
        JDABuilder builder = JDABuilder.createDefault(botToken)
                .disableCache(CacheFlag.MEMBER_OVERRIDES) // Disable parts of the cache
                .setBulkDeleteSplittingEnabled(false) // Enable the bulk delete event
                .setCompression(Compression.ZLIB) // Disable compression (not recommended)
                .setLargeThreshold(250);

        JDA jda = builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES).build();

        //註冊event
        jda.addEventListener(new Log());
        jda.addEventListener(new Join());
        jda.addEventListener(new GeneralReplay());
        jda.addEventListener(new TicketChannel());
        jda.addEventListener(new InformationReaction());
        jda.addEventListener(new BlockCommand());
        SlashCommandManager commandManager = new SlashCommandManager();
        jda.addEventListener(commandManager);


        //bot自己的資料
        botID = jda.getSelfUser().getId();
        botAvatarUrl = jda.getSelfUser().getAvatarUrl();
        self = jda.getSelfUser();
        //開始切換狀態
        startChangeActivity(jda);

        //啟動完畢
        System.out.println(TAG + " 已啟動");
        //平行處理 不然會卡住
        new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
//                System.out.println();
                String command = scanner.nextLine();
//                System.out.println(command);
                switch (command) {
                    case "stop":
                        jda.shutdown();
                        threadPool.shutdown();
                        System.out.println(TAG + " 已停止");
                        return;
                    case "reload":
                        //load new setting
                        setting.reloadConfig();
                        Guild guild = jda.getGuildById(GuildUtil.guildID);
                        if (guild == null) {
                            System.err.println(TAG + " 無法找到公會: " + GuildUtil.guildID);
                            break;
                        }
                        //get guild variable from setting
                        commandManager.getGuildVariable(guild);
                        //開始自動切換
                        startChangeActivity(jda);
                        System.out.println(TAG + " 重新載入完成");
                        break;
//                    case "test":
//                        for (String memberID : Join.memberData.keySet()) {
//                            Member member;
//                            try {
//                                member = GuildUtil.guild.retrieveMemberById(memberID).complete();
//                            } catch (Exception e) {
//                                continue;
//                            }
//                            JSONObject obj = Join.memberData.getJSONObject(member.getId());
//                            obj.put(JsonKeys.CHINESE_NICK, member.getNickname().substring(0,2));
//                            Join.memberData.put(member.getId(), obj);
//                            System.out.println(member.getNickname().substring(0,2));
//                        }
//                        Join.memberFile.saveFile();
//                        break;
                    default:
                        if (command.length() == 0)
                            setting.sendNoneToConsole();
                        else
                            System.out.println(TAG + " 不知名的指令");
                        break;
                }
            }
        }).start();
    }

    private void startChangeActivity(JDA jda) {
        if (threadPool != null && !threadPool.isShutdown())
            threadPool.shutdown();

        threadPool = Executors.newSingleThreadScheduledExecutor();
        //run thread
        threadPool.scheduleWithFixedDelay(() -> {
            String[] msg = activityMessages.get(currentIndex);
            try {
                if (msg.length < 2) {
                    System.err.println(TAG + " parameter not found");
                    threadPool.shutdown();
                    return;
                }

                Activity.ActivityType type = Activity.ActivityType.valueOf(msg[0]);
                if (msg[1].equals("STREAMING")) {
                    if (msg.length < 3) {
                        System.err.println(TAG + " url not found");
                        threadPool.shutdown();
                        return;
                    }
                    //name, url
                    jda.getPresence().setActivity(Activity.of(type, msg[1], msg[2]));
                } else {
                    jda.getPresence().setActivity(Activity.of(type, msg[1]));
                }
            } catch (IllegalArgumentException e) {
                System.err.println(TAG + " can not find type: " + msg[0]);
                threadPool.shutdown();
                return;
            }
            currentIndex = (currentIndex + 1) % activityMessages.size();
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) throws LoginException {
        new Main();
    }
}