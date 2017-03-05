package stan.cudgel.contracts;

import stan.cudgel.cores.SettingsCore;

public interface SettingsContract
{
    interface Model
    {
        SettingsCore.Media getMedia();
        void saveMedia(SettingsCore.Media media);
    }
    interface View
    {
        void update(SettingsCore.Media media);
    }
    interface Presenter
    {
        void update();
        void save(SettingsCore.Media media);
    }

    interface Behaviour
    {
        void close();
    }
}