package main.java.lang;

import main.java.command.list.Language;

import java.lang.reflect.Field;

public class LangKey {

    /**
     * {@link main.java.command.list.BotInfo}
     */
    public static int BOTINFO_GUILD_TOTAL_COUNT;
    public static int BOTINFO_MEMBER_TOTAL_COUNT;
    public static int BOTINFO_INFORMATION;


    /**
     * {@link main.java.command.list.URLShort}
     */
    public static int URLSHORT_SUCCESS;
    public static int URLSHORT_FAIL;


    /**
     * {@link main.java.command.list.UnBan}
     */
    public static int UNBAN_BOT_NO_PERMISSION;
    public static int UNBAN_PERMISSION_ERROR;
    public static int UNBAN_SUCCESS;
    public static int UNBAN_UNKNOWN_ERROR;
    public static int UNBAN_CAN_NOTE_FOUND_MEMBER;


    /**
     * {@link main.java.command.list.Ticket}
     */
    public static int TICKET_ALREADY_CLICKED;
    public static int TICKET_SIZE_LIMIT;
    public static int TICKET_LOCK;
    public static int TICKET_DELETE;
    public static int TICKET_UNLOCK;


    /**
     * {@link main.java.command.list.Support}
     */
    public static int SUPPORT_SENDING;
    public static int SUPPORT_FAIL;
    public static int SUPPORT_SUCCESS;


    /**
     * {@link main.java.command.list.Reload}
     */
    public static int RELOAD_SUCCESS;


    /**
     * {@link main.java.command.list.Poll}
     */
    public static int POLL_MEMBER_POLL;
    public static int POLL_SUCCESS;


    /**
     * {@link main.java.util.file.PopCatHelper}
     */
    public static int POPCATHELPER_PREVIEWS;
    public static int POPCATHELPER_NEXT;
    public static int POPCATHELPER_DETAIL_INFORMATION;


    /**
     * {@link main.java.command.list.Setting.SettingVCC}
     */
    public static int SETTINGVCC_LONG_OVER_100;
    public static int SETTINGVCC_ERROR_REPORT;
    public static int SETTINGVCC_DETECT_CATEGORY;
    public static int SETTINGVCC_DETECT_NAME;
    public static int SETTINGVCC_SETTING_SUCCESS;
    public static int SETTINGVCC_REMOVE_SUCCESS;


    /**
     * {@link main.java.command.list.Setting.SettingTicket}
     */
    public static int SETTINGTICKET_MESSAGE_CATEGORY_ERROR;
    public static int SETTINGTICKET_BUTTON_COLOR_ERROR;
    public static int SETTINGTICKET_BUTTON_NAME_AND_EMOJI_AT_LEAST_ONCE;
    public static int SETTINGTICKET_CAN_NOT_GET_EMOJI;
    public static int SETTINGTICKET_CAN_NOT_FOUND_MESSAGE_BY_ID;
    public static int SETTINGTICKET_MESSAGE_HAS_NOT_NEW_BUTTON;
    public static int SETTINGTICKET_MESSAGE_TOO_MANY_BUTTONS;
    public static int SETTINGTICKET_ERROR_REPORT;
    public static int SETTINGTICKET_MESSAGE_ID;
    public static int SETTINGTICKET_MESSAGE_CHANNEL;
    public static int SETTINGTICKET_ENTERED_MESSAGE;
    public static int SETTINGTICKET_TICKET_TEXT_CHANNEL_NAME;
    public static int SETTINGTICKET_TICKET_TEXT_CHANNEL_CATEGORY;
    public static int SETTINGTICKET_TICKET_VOICE_CHANNEL;
    public static int SETTINGTICKET_TICKET_VOICE_HAVE;
    public static int SETTINGTICKET_TICKET_VOICE_NOT_HAVE;
    public static int SETTINGTICKET_TICKET_VOICE_NAME;
    public static int SETTINGTICKET_TICKET_VOICE_CATEGORY;
    public static int SETTINGTICKET_TICKET_BUTTON_NAME;
    public static int SETTINGTICKET_TICKET_BUTTON_EMOJI;
    public static int SETTINGTICKET_TICKET_BUTTON_COLOR;
    public static int SETTINGTICKET_TICKET_ALLOW_ROLE;
    public static int SETTINGTICKET_TICKET_ALLOW_TAG_ROLE;
    public static int SETTINGTICKET_TICKET_ONLY_ONE;
    public static int SETTINGTICKET_TICKET_YES;
    public static int SETTINGTICKET_TICKET_NO;
    public static int SETTINGTICKET_TICKET_SETTING_SUCCESS;
    public static int SETTINGTICKET_TICKET_REMOVE_FAIL_BY_CHANNEL;
    public static int SETTINGTICKET_TICKET_REMOVE_FAIL_BY_MESSAGE;
    public static int SETTINGTICKET_TICKET_REMOVE_FAIL_BY_USING;
    public static int SETTINGTICKET_TICKET_REMOVE_BUTTON_FAIL_BY_POS;
    public static int SETTINGTICKET_TICKET_REMOVE_BUTTON_FAIL_BY_MESSAGE;
    public static int SETTINGTICKET_TICKET_REMOVE_BUTTON_FAIL_BY_CHANNEL;


    /**
     * {@link main.java.command.list.Setting.SettingRoom}
     */
    public static int SETTINGROOM_TEXT_CHANNEL_NAME_SPACE_ERROR;
    public static int SETTINGROOM_VOICE_CHANNEL_NAME_ERROR;
    public static int SETTINGROOM_TEXT_CHANNEL_NAME_ERROR;
    public static int SETTINGROOM_BITRATE_ERROR;
    public static int SETTINGROOM_MEMBER_LIMIT_ERROR;
    public static int SETTINGROOM_ERROR_REPORT;
    public static int SETTINGROOM_DETECT_VOICE_CHANNEL;
    public static int SETTINGROOM_VOICE_CATEGORY;
    public static int SETTINGROOM_TEXT_CATEGORY;
    public static int SETTINGROOM_VOICE_NAME;
    public static int SETTINGROOM_TEXT_NAME;
    public static int SETTINGROOM_MEMBER_LIMIT;
    public static int SETTINGROOM_BITRATE;
    public static int SETTINGROOM_SETTING_SUCCESS;
    public static int SETTINGROOM_REMOVE_SUCCESS;
    public static int SETTINGROOM_NONE;


    /**
     * {@link main.java.command.list.FileConvert}
     */
    public static int FILECONVERT_CONVERTING;
    public static int FILECONVERT_WAIT_TIME;
    public static int FILECONVERT_FAIL;


    /**
     * {@link main.java.command.list.Setting.SettingChannelStatus}
     */
    public static int SETTINGCHANNELSTATUS_FAIL;
    public static int SETTINGCHANNELSTATUS_SETTING_SUCCESS;
    public static int SETTINGCHANNELSTATUS_REMOVE_SUCCESS;


    /**
     * {@link main.java.command.list.Setting.SettingHelp}
     */
    public static int SETTINGHELP_ADVANCE_HELP;


    /**
     * {@link main.java.command.list.Setting.SettingJoinLeave}
     */
    public static int SETTINGJOINLEAVE_CAN_NOT_FOUND_TEXT_CHANNEL;
    public static int SETTINGJOINLEAVE_SETTING_FAIL;
    public static int SETTINGJOINLEAVE_NOTICE_CHANNEL;
    public static int SETTINGJOINLEAVE_NOTICE_MESSAGE;
    public static int SETTINGJOINLEAVE_DM_MESSAGE;
    public static int SETTINGJOINLEAVE_ROLE_ADD;
    public static int SETTINGJOINLEAVE_SETTING_SUCCESS;


    /**
     * {@link main.java.command.CommandRegister}
     */
    public static int COMMANDREGISTER_WARN;
    public static int COMMANDREGISTER_WARN_MEMBER_YOU_CHOOSE;
    public static int COMMANDREGISTER_WARN_REASON;
    public static int COMMANDREGISTER_REMOVE_WARM;
    public static int COMMANDREGISTER_REMOVE_MEMBER_WARN_YOU_CHOOSE;
    public static int COMMANDREGISTER_KICK;
    public static int COMMANDREGISTER_KICK_MEMBER_YOU_CHOOSE;
    public static int COMMANDREGISTER_BAN;
    public static int COMMANDREGISTER_BAN_MEMBER_YOU_CHOOSE;
    public static int COMMANDREGISTER_BAN_TIME;
    public static int COMMANDREGISTER_BAN_REASON;
    public static int COMMANDREGISTER_UNBAN;
    public static int COMMANDREGISTER_UNBAN_MEMBER_YOU_CHOOSE;
    public static int COMMANDREGISTER_CLEAR;
    public static int COMMANDREGISTER_CLEAR_BETWEEN_2_TO_200;
    public static int COMMANDREGISTER_POLL;
    public static int COMMANDREGISTER_POLL_QUESTION;
    public static int COMMANDREGISTER_POLL_OPTION_A;
    public static int COMMANDREGISTER_POLL_OPTION_B;
    public static int COMMANDREGISTER_POLL_OPTION_C;
    public static int COMMANDREGISTER_POLL_OPTION_D;
    public static int COMMANDREGISTER_POLL_OPTION_E;
    public static int COMMANDREGISTER_POLL_OPTION_F;
    public static int COMMANDREGISTER_POLL_OPTION_G;
    public static int COMMANDREGISTER_POLL_OPTION_H;
    public static int COMMANDREGISTER_POLL_OPTION_I;
    public static int COMMANDREGISTER_POLL_OPTION_J;
    public static int COMMANDREGISTER_HELP;
    public static int COMMANDREGISTER_HELP_ANNOUNCEMENT;
    public static int COMMANDREGISTER_PLAY;
    public static int COMMANDREGISTER_PLAY_NAME_OR_URL;
    public static int COMMANDREGISTER_PLAYNOW;
    public static int COMMANDREGISTER_PN_NAME_OR_URL;
    public static int COMMANDREGISTER_QUEUE;
    public static int COMMANDREGISTER_SKIP;
    public static int COMMANDREGISTER_REMOVE;
    public static int COMMANDREGISTER_REMOVE_POS;
    public static int COMMANDREGISTER_VOLUME;
    public static int COMMANDREGISTER_VOLUME_COUNT;
    public static int COMMANDREGISTER_LOOP;
    public static int COMMANDREGISTER_REPEAT;
    public static int COMMANDREGISTER_PAUSE;
    public static int COMMANDREGISTER_LEAVE;
    public static int COMMANDREGISTER_SURL;
    public static int COMMANDREGISTER_SURL_URL;
    public static int COMMANDREGISTER_POP_SPEED;
    public static int COMMANDREGISTER_POP_TOP;
    public static int COMMANDREGISTER_GIVEAWAY;
    public static int COMMANDREGISTER_GIVEAWAY_NAME;
    public static int COMMANDREGISTER_GIVEAWAY_WIN_COUNT;
    public static int COMMANDREGISTER_GIVEAWAY_EMOJI;
    public static int COMMANDREGISTER_GIVEAWAY_MONTH;
    public static int COMMANDREGISTER_GIVEAWAY_WEEK;
    public static int COMMANDREGISTER_GIVEAWAY_DAY;
    public static int COMMANDREGISTER_GIVEAWAY_HOUR;
    public static int COMMANDREGISTER_GIVEAWAY_MINUTE;
    public static int COMMANDREGISTER_GIVEAWAY_SECOND;
    public static int COMMANDREGISTER_GIVEAWAY_TIME;
    public static int COMMANDREGISTER_MP4_TO_GIF;
    public static int COMMANDREGISTER_MTG_URL;
    public static int COMMANDREGISTER_MTG_OUT_PUT_NAME;
    public static int COMMANDREGISTER_MTG_FPS;
    public static int COMMANDREGISTER_SETTING;
    public static int COMMANDREGISTER_S_HELP;
    public static int COMMANDREGISTER_S_NEW_ROOM;
    public static int COMMANDREGISTER_S_NR_DETECT_CHANNEL;
    public static int COMMANDREGISTER_S_NR_VOICE_NAME;
    public static int COMMANDREGISTER_S_NR_TEXT_NAME;
    public static int COMMANDREGISTER_S_NR_VOICE_CATEGORY;
    public static int COMMANDREGISTER_S_NR_TEXT_CATEGORY;
    public static int COMMANDREGISTER_S_NR_VOICE_BITRATE;
    public static int COMMANDREGISTER_S_NR_MEMBER_LIMIT;
    public static int COMMANDREGISTER_S_REMOVE_ROOM;
    public static int COMMANDREGISTER_S_RR_DETECT_CHANNEL;
    public static int COMMANDREGISTER_S_NEW_AUTO_VC;
    public static int COMMANDREGISTER_S_NAVC_DETECT_CATEGORY;
    public static int COMMANDREGISTER_S_NAVC_VOICE_NAME;
    public static int COMMANDREGISTER_S_REMOVE_AUTO_VC;
    public static int COMMANDREGISTER_S_RAVC_DETECT_CATEGORY;
    public static int COMMANDREGISTER_S_NEW_TICKET;
    public static int COMMANDREGISTER_S_NT_MESSAGE_ID;
    public static int COMMANDREGISTER_S_NT_MESSAGE_CHANNEL;
    public static int COMMANDREGISTER_S_NT_ALLOW_ROLE;
    public static int COMMANDREGISTER_S_NT_ALLOW_TAG_ROLE;
    public static int COMMANDREGISTER_S_NT_ENTERED_MESSAGE;
    public static int COMMANDREGISTER_S_NT_TEXT_NAME;
    public static int COMMANDREGISTER_S_NT_TEXT_CATEGORY;
    public static int COMMANDREGISTER_S_NT_HAS_VOICE_CHANNEL;
    public static int COMMANDREGISTER_S_NT_VOICE_NAME;
    public static int COMMANDREGISTER_S_NT_VOICE_CATEGORY;
    public static int COMMANDREGISTER_S_NT_BUTTON_NAME;
    public static int COMMANDREGISTER_S_NT_BUTTON_COLOR;
    public static int COMMANDREGISTER_S_NT_BUTTON_EMOJI;
    public static int COMMANDREGISTER_S_NT_ONLY_ONE;
    public static int COMMANDREGISTER_S_ADD_TICKET;
    public static int COMMANDREGISTER_S_AT_MESSAGE_ID;
    public static int COMMANDREGISTER_S_AT_MESSAGE_CHANNEL;
    public static int COMMANDREGISTER_S_AT_ALLOW_ROLE;
    public static int COMMANDREGISTER_S_AT_ALLOW_TAG_ROLE;
    public static int COMMANDREGISTER_S_AT_ENTERED_MESSAGE;
    public static int COMMANDREGISTER_S_AT_TEXT_NAME;
    public static int COMMANDREGISTER_S_AT_TEXT_CATEGORY;
    public static int COMMANDREGISTER_S_AT_HAS_VOICE_CHANNEL;
    public static int COMMANDREGISTER_S_AT_VOICE_NAME;
    public static int COMMANDREGISTER_S_AT_VOICE_CATEGORY;
    public static int COMMANDREGISTER_S_AT_BUTTON_NAME;
    public static int COMMANDREGISTER_S_AT_BUTTON_COLOR;
    public static int COMMANDREGISTER_S_AT_BUTTON_EMOJI;
    public static int COMMANDREGISTER_S_AT_ONLY_ONE;
    public static int COMMANDREGISTER_S_REMOVE_TICKET;
    public static int COMMANDREGISTER_S_RT_MESSAGE_ID;
    public static int COMMANDREGISTER_S_RT_MESSAGE_CHANNEL;
    public static int COMMANDREGISTER_S_RT_POSITION;
    public static int COMMANDREGISTER_S_NEW_CHANNEL_STATUS;
    public static int COMMANDREGISTER_S_NCS_CHANNEL;
    public static int COMMANDREGISTER_S_NCS_CHANNEL_NAME;
    public static int COMMANDREGISTER_S_NCS_FORMAT;
    public static int COMMANDREGISTER_S_REMOVE_CHANNEL_STATUS;
    public static int COMMANDREGISTER_S_RSC_CHANNEL;
    public static int COMMANDREGISTER_S_NEW_JOIN;
    public static int COMMANDREGISTER_S_NJ_CHANNEL;
    public static int COMMANDREGISTER_S_NJ_MESSAGE;
    public static int COMMANDREGISTER_S_NJ_DM;
    public static int COMMANDREGISTER_S_NJ_ROLE1;
    public static int COMMANDREGISTER_S_NJ_ROLE2;
    public static int COMMANDREGISTER_S_NJ_ROLE3;
    public static int COMMANDREGISTER_S_NJ_ROLE4;
    public static int COMMANDREGISTER_S_NJ_ROLE5;
    public static int COMMANDREGISTER_S_NJ_ROLE6;
    public static int COMMANDREGISTER_S_NJ_ROLE7;
    public static int COMMANDREGISTER_S_NJ_ROLE8;
    public static int COMMANDREGISTER_S_NJ_ROLE9;
    public static int COMMANDREGISTER_S_NJ_ROLE10;
    public static int COMMANDREGISTER_S_REMOVE_JOIN;
    public static int COMMANDREGISTER_S_RJ_CHANNEL;
    public static int COMMANDREGISTER_S_NEW_LEAVE;
    public static int COMMANDREGISTER_S_NL_CHANNEL;
    public static int COMMANDREGISTER_S_NL_MESSAGE;
    public static int COMMANDREGISTER_S_REMOVE_LEAVE;
    public static int COMMANDREGISTER_S_RL_CHANNEL;
    public static int COMMANDREGISTER_RELOAD;
    public static int COMMANDREGISTER_NICK;
    public static int COMMANDREGISTER_INVITE;
    public static int COMMANDREGISTER_INVITE_MEMBER_YOU_CHOOSE;


    /**
     * {@link multiBot.MusicBotEvent}
     */
    public static int MUSICBOTEVENT_PLAY_NOW;
    public static int MUSICBOTEVENT_ADDED_QUEUE;
    public static int MUSICBOTEVENT_SKIED;
    public static int MUSICBOTEVENT_REMOVED_FAIL;
    public static int MUSICBOTEVENT_REMOVED_SUCCESS;
    public static int MUSICBOTEVENT_LOOP_PLAY;
    public static int MUSICBOTEVENT_NORMAL_PLAY;
    public static int MUSICBOTEVENT_STOP_PLAY;
    public static int MUSICBOTEVENT_REPEAT_PLAY;
    public static int MUSICBOTEVENT_PAUSE;
    public static int MUSICBOTEVENT_UNPAUSE;
    public static int MUSICBOTEVENT_SET_VOLUME;


    /**
     * {@link multiBot.MusicBot}
     */
    public static int MUSICBOT_NOT_SUPPORT_PLAYLIST;
    public static int MUSICBOT_URL_NOT_FOUND;
    public static int MUSICBOT_CANT_PLAY_URL;
    public static int MUSICBOT_NO_MUSIC;
    public static int MUSICBOT_NO_CONNECT_PERMISSION;
    public static int MUSICBOT_VOLUME;
    public static int MUSICBOT_NORMAL_PLAY;
    public static int MUSICBOT_LOOP_PLAY;
    public static int MUSICBOT_REPEAT_PLAY;
    public static int MUSICBOT_NO_PLAYING;
    public static int MUSICBOT_NONE;
    public static int MUSICBOT_WAITING_PLAYLIST;


    /**
     * {@link main.java.ListenerManager}
     */
    public static int LISTENERMANAGER_CANT_DO_THIS;
    public static int LISTENERMANAGER_WRONG_CHANNEL;
    public static int LISTENERMANAGER_NOT_YOUR_BUTTON;


    /**
     * {@link Language}
     */
    public static int LANG_CHANGE;
    public static int LANG_CHOOSE;
    public static int LANG_SUCCESS;


    public static void loadKey() {
        int n = 0;
        for (Field field : LangKey.class.getFields())
            try {
                field.set(LangKey.class, n++);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
    }
}