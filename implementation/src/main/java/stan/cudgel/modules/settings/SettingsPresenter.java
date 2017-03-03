package stan.cudgel.modules.settings;

import stan.cudgel.contracts.SettingsContract;
import stan.cudgel.units.mvp.ModelPresenter;

class SettingsPresenter
    extends ModelPresenter<SettingsContract.View, SettingsContract.Model>
    implements SettingsContract.Presenter
{
    SettingsPresenter(SettingsContract.View v, SettingsContract.Model m)
    {
        super(v, m);
    }
}