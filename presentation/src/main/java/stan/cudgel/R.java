package stan.cudgel;

public interface R
{
    interface colors
    {
        String TRANSPARENT = "#00000000";
        String BLACK = "#000000";
        String WHITE = "#ffffff";
        String GRAY_LIGHT = "#E0E0E0";
        String GRAY = "#9E9E9E";
        String GRAY_DARK = "#616161";
        String BLUE = "#2196F3";
        String RED = "#F44336";

        String PRIMARY = BLUE;
//        String PRIMARY = RED;
        String ACCENT = WHITE;
    }

    interface images
    {
        String LAUNCHER = "images/launcher.png";
        String MIC_OFF = "images/cudgel/mic/mic_off_m.png";
        String MUSIC = "images/cudgel/music/music_xh.png";
        String SETTINGS = "images/cudgel/settings/settings_white_48dp_m.png";
        String CLOSE_WHITE = "images/actions/close/close_white_48dp_m.png";
        String CLOSE_BLACK = "images/actions/close/close_black_48dp_m.png";
        String CAMERA = "images/media/screenshot/photo_camera_white_48dp_m.png";
        String DOWNLOAD = "images/media/files/file_download_white_48dp_m.png";
        String TELEGRAM = "images/telegram/telegram.png";
    }

    interface styles
    {
        String MAIN = "styles/main.css";
    }

    interface strings
    {
        String APP_LOCAL_PATH = "stan/cudgel";
        String APP_NAME = "Cudgel";
        String SETTINGS_TITLE = "Settings";
        String MEDIA_LABLE = "Media";
        String SCREENSHOTSPATH_SUBLABLE = "Screenshots path";
        String SCREENSHOTSPATH_HINT = "Write your screenshots path here";
        String SETTINGS_SAVE = "Save";
    }
}