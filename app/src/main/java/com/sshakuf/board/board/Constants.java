package com.sshakuf.board.board;

public interface Constants {


    public static final String SharedPrefrencesKey = "MyPREFERENCES";
    public static final String sp_BTDeviceID_Key = "BTDEVICE_ID";
    public static final String sp_ShouldAnimateFall = "AnimateFall";

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";


    public static final int COLOR_NONE=0;
    public static final int COLOR_YELLOW=1;
    public static final int COLOR_GREEN=2;
    public static final int COLOR_LIGHTGREEN=3;
    public static final int COLOR_CYAN=4;
    public static final int COLOR_BLUE=5;
    public static final int COLOR_PURPLE=6;
    public static final int COLOR_PINK=7;
    public static final int COLOR_RED=8;


}
