package stan.cudgel.modules.settings;

import stan.cudgel.contracts.SettingsContract;
import stan.cudgel.cores.SettingsCore;
import stan.cudgel.units.mvp.ModelPresenter;

class SettingsPresenter
    extends ModelPresenter<SettingsContract.View, SettingsContract.Model>
    implements SettingsContract.Presenter
{
    SettingsPresenter(SettingsContract.View v, SettingsContract.Model m)
    {
        super(v, m);
    }

    public void update()
    {
        onNewThread(() ->
        {
            getView().update(getModel().getMedia());
        });
    }

    public void save(SettingsCore.Media media)
    {
        onNewThread(() ->
        {
            getModel().saveMedia(media);
        });
    }
}