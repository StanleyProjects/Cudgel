package stan.cudgel;

public interface R
{
    interface colors
    {
        String TRANSPARENT = "#00000000";
        String BLACK = "#000000";
        String WHITE = "#ffffff";
        String GRAY = "#E0E0E0";
        String GRAY_DARK = "#BDBDBD";
        String BLUE = "#2196F3";
        String RED = "#F44336";

//        String PRIMARY = BLUE;
        String PRIMARY = RED;
        String ACCENT = WHITE;
    }

    interface images
    {
        String LAUNCHER = "launcher.png";
        String MIC_OFF = "cudgel/mic/mic_off_m.png";
        String MUSIC = "cudgel/music/music_xh.png";
        String SETTINGS = "cudgel/settings/settings_white_48dp_m.png";
        String CLOSE_WHITE = "actions/close/close_white_48dp_m.png";
        String CLOSE_BLACK = "actions/close/close_black_48dp_m.png";
        String CAMERA = "media/screenshot/photo_camera_white_48dp_m.png";
        String DOWNLOAD = "media/files/file_download_white_48dp_m.png";
        String TELEGRAM = "telegram/telegram.png";
    }

    interface strings
    {
        String APP_NAME = "Cudgel";
        String SETTINGS_TITLE = "Settings";
    }
}