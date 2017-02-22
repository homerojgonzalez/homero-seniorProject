/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvironmentalWidgets;

import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Communications.Observer;
import DataObjects.CurrentLaunchInformation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author jtroxel
 */
public abstract class EnvironmentalWidget extends JPanel implements Observer {
    private JLabel title;
    protected JTextField field;
    protected JLabel unit;
    private JCheckBox isEditable;
    protected int unitId;
    
    public EnvironmentalWidget(String titleIn, boolean hasField, boolean canEdit)
    {
        setBackground(Color.WHITE);
        JPanel fieldPanel = new JPanel();
        JPanel titlePanel = new JPanel();
        fieldPanel.setBackground(Color.WHITE);
        fieldPanel.setLayout(new BorderLayout());     
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setLayout(new BorderLayout());
        title = new JLabel(titleIn);
        unit = new JLabel();
        field = new JTextField();
        field.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        field.setEditable(false);
        field.setBackground(Color.WHITE);
        field.setPreferredSize(new Dimension(80, 20));
        field.setHorizontalAlignment(JTextField.RIGHT);
        field.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    update();
                    CurrentLaunchInformation.getCurrentLaunchInformation().update("Manual Entry");
        	}
        });
        field.setHorizontalAlignment(JTextField.RIGHT);
        isEditable = new JCheckBox();
        isEditable.setBackground(Color.WHITE);
        isEditable.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    field.setEditable(true);
                    field.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
                }
                else
                {
                    field.setEditable(false);
                    field.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
                    update();
                }
            }
        });
        setLayout(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(185,20));
        fieldPanel.setPreferredSize(new Dimension(185,20));
        //setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        //setPreferredSize(new Dimension(185, 37));
        titlePanel.add(title, BorderLayout.LINE_START);
        fieldPanel.add(field, BorderLayout.LINE_START);
        fieldPanel.add(unit, BorderLayout.CENTER);
        if(canEdit) titlePanel.add(isEditable, BorderLayout.LINE_END);
        add(titlePanel, BorderLayout.PAGE_START);
        if(hasField)
        {
            add(fieldPanel, BorderLayout.PAGE_END);
        }
    }
    
    public String getFieldValue()
    {
        return field.getText();
    }
    
    public boolean manualEntry(){
        return isEditable.isSelected();
    }

    @Override
    public abstract void update();

    @Override
    public abstract void update(String msg);
    
    public abstract void setupUnits();
}
