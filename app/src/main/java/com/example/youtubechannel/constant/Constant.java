package com.example.youtubechannel.constant;

public interface Constant {

    int FAIL_CONNECT_CODE = -1;
    int JSON_PARSER_CODE = -10;
    int OTHER_CODE = -20;

    String GENERIC_ERROR = "General error, please try again later";
    String SERVER_ERROR = "Fail to connect to server";

    String HOST_SCHEMA = "https://";
    String DOMAIN_NAME = "www.googleapis.com";
    String HOST = HOST_SCHEMA + DOMAIN_NAME + "/youtube/v3/";

    String PAGE_FACEBOOK = "fb://page/tincoder";
    String LINK_FACEBOOK = "https://www.facebook.com/tincoder";
    String LINK_YOUTUBE = "https://www.youtube.com/channel/UCu3DXfXYgygIYXN8TduNcNQ";
    String PHONE_NUMBER = "+84392229676";
    String GMAIL = "mixproduction29932@gmail.com";
    String SKYPE_ID = "dangtin29932";
    String ZALO_LINK = "https://zalo.me/0392229676";

    String VALUE_CHANNEL_ID = "UCu3DXfXYgygIYXN8TduNcNQ";
    String VALUE_KEY = "AIzaSyBGx4TkkZCuBb54ZmRGeiP0p4_aGOJmuSk";
    int VALUE_MAX_RESULTS_PLAYLIST = 8;
    int VALUE_MAX_RESULTS_LATEST_VIDEO = 8;
    int VALUE_MAX_RESULTS_COMMENT = 8;

    // Key Intent
    String KEY_PLAYLIST = "KEY_PLAYLIST";
    String KEY_TYPE_ORDER = "KEY_TYPE_ORDER";
    String KEY_VIDEO = "KEY_VIDEO";
    String KEY_VIDEO_ITEM = "KEY_VIDEO_ITEM";
    String KEY_VIDEO_ID = "KEY_VIDEO_ID";
    String KEY_VIDEO_TITLE = "KEY_VIDEO_TITLE";
}
