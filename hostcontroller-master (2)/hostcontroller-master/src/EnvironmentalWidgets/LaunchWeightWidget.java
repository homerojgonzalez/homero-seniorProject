package EnvironmentalWidgets;

import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.CurrentLaunchInformation;
import DataObjects.Operator;

/**
 *
 * @author jtroxel
 */
public class LaunchWeightWidget extends EnvironmentalWidget {

    public LaunchWeightWidget()
    {
        super("Launch Weight", true, false);
    }

    @Override
    public void update()
    {
        float weight = (CurrentLaunchInformation.getCurrentLaunchInformation().getGliderLaunchMass()) * UnitConversionRate.convertWeightUnitIndexToFactor(unitId);
        field.setText(String.format("%.2f", weight));
    }

    @Override
    public void update(String msg) {}

    @Override
    public void setupUnits() {
        Operator temp = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile();
        unitId = temp.getUnitSetting("launchweight");
        unit.setText(" " + UnitLabelUtilities.weightUnitIndexToString(unitId));
    }
    
}
