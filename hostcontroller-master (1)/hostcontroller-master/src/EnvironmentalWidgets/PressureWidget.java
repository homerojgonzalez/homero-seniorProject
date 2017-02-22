/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvironmentalWidgets;

import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import java.awt.Color;
/**
 *
 * @author jtroxel
 */
public class PressureWidget extends EnvironmentalWidget {

    public PressureWidget() {
        super("Pressure", true, true);
    }

    @Override
    public void update() {
        field.setBackground(Color.WHITE);
        if (manualEntry())
        {
            try{
                float press = Float.parseFloat(field.getText()) / UnitConversionRate.convertPressureUnitIndexToFactor(unitId);
                CurrentWidgetDataSet.getInstance().setValue("pressure", String.valueOf(press));
            }catch (NumberFormatException e){
                field.setBackground(Color.PINK);
            }
        }
        else
        {
            if (CurrentWidgetDataSet.getInstance().getValue("pressure").equals(""))
            {
                field.setText("");
            }
            else
            {
                float press = (Float.parseFloat(CurrentWidgetDataSet.getInstance().getValue("pressure")) * UnitConversionRate.convertPressureUnitIndexToFactor(unitId));
                field.setText(String.format("%.2f", press));
            }
        }
    }

    @Override
    public void update(String msg) {
    }

    @Override
    public void setupUnits() {
        Operator temp = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile();
        unitId = temp.getUnitSetting("pressure");
        unit.setText(" " + UnitLabelUtilities.pressureUnitIndexToString(unitId));
    }
    
}
