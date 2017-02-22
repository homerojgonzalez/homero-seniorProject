/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class CrossWindComponentWidget extends EnvironmentalWidget {

    public CrossWindComponentWidget() {
        super("Crosswind Component", true, false);
    }

    @Override
    public void update() {
        float speed = (CurrentLaunchInformation.getCurrentLaunchInformation().getCrosswindComponent()) * UnitConversionRate.convertSpeedUnitIndexToFactor(unitId);
        field.setText(String.format("%.2f", speed));
    }

    @Override
    public void update(String msg) {
    }

    @Override
    public void setupUnits() {
        Operator temp = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile();
        unitId = temp.getUnitSetting("crosswind");
        unit.setText(" " + UnitLabelUtilities.velocityUnitIndexToString(unitId));
    }
    
}
