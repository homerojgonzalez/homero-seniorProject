/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParameterSelection;

import DataObjects.CurrentDataObjectSet;
import EnvironmentalWidgets.EnvironmentalWidget;
import EnvironmentalWidgets.LaunchWeightWidget;
import Communications.MessagePipeline;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import Communications.Observer;
import DataObjects.CurrentLaunchInformation;
import EnvironmentalWidgets.AvgWindSpeedWidget;
import EnvironmentalWidgets.CrossWindComponentWidget;
import EnvironmentalWidgets.CurrentWidgetDataSet;
import EnvironmentalWidgets.DensityAltitudeWidget;
import EnvironmentalWidgets.GustWindSpeedWidget;
import EnvironmentalWidgets.HeadWindComponentWidget;
import EnvironmentalWidgets.HumidityWidget;
import EnvironmentalWidgets.PressureWidget;
import EnvironmentalWidgets.RunDirectionWidget;
import EnvironmentalWidgets.RunLengthWidget;
import EnvironmentalWidgets.RunSlopeWidget;
import EnvironmentalWidgets.TemperatureWidget;
import EnvironmentalWidgets.WindDirectionWidget;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Jacob Troxel
 */
public class EnvironmentalWindow extends javax.swing.JPanel implements Observer {

    private MessagePipeline pipe;
    private ArrayList<EnvironmentalWidget> widgets;
    private CurrentDataObjectSet data;
    private CurrentLaunchInformation info;
    
    public EnvironmentalWindow() {
        data = CurrentDataObjectSet.getCurrentDataObjectSet();
        data.attach(this);
        info = CurrentLaunchInformation.getCurrentLaunchInformation();
        info.attach(this);
        pipe = MessagePipeline.getInstance();
        widgets = new ArrayList<>();
        addWidget(new LaunchWeightWidget());
        addWidget(new RunLengthWidget());
        addWidget(new RunDirectionWidget());
        addWidget(new RunSlopeWidget());
        addWidget(new WindDirectionWidget());
        addWidget(new AvgWindSpeedWidget());
        addWidget(new GustWindSpeedWidget());
        addWidget(new HeadWindComponentWidget());
        addWidget(new CrossWindComponentWidget());
        addWidget(new DensityAltitudeWidget());
        addWidget(new TemperatureWidget());
        addWidget(new HumidityWidget());
        addWidget(new PressureWidget());
        add(new JLabel ("Press enter to update fields."));
        //setLayout(new FlowLayout());
        init();
    }
    
    private void addWidget(EnvironmentalWidget ew)
    {
        widgets.add(ew);
    }
    
    private void init()
    {
        for(EnvironmentalWidget ew : widgets)
        {
            add(ew);
        }
    }

    @Override
    public void update()
    {
        for(EnvironmentalWidget ew : widgets)
        {
            ew.update();
            ew.setupUnits();
        }
    }

    @Override
    public void update(String msg) {
    }
}
