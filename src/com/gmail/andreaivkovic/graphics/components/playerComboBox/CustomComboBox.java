/*
    Copyright (C) 2019 Ivkovic Andrea, Mellini Tancredi, Peinkhofer Leo

	This file is part of LotterySimulator.

    LotterySimulator is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    LotterySimulator is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with LotterySimulator.  If not, see <https://www.gnu.org/licenses/>.
*/
package com.gmail.andreaivkovic.graphics.components.playerComboBox;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.simulator.players.PlayerType;

/**
 * @author Ivkovic Andrea
 */

public class CustomComboBox extends JComboBox<Object> {
	private static final long serialVersionUID = 1L;

	private DefaultComboBoxModel<Object> model;

	public CustomComboBox() {
		super();
		model = new DefaultComboBoxModel<>();
		setModel(model);
		for(PlayerType item : PlayerType.values()){
			model.addElement(new ComboBoxCellPanel(item));
		}

		setBackground((Color) AppUIManager.get("comboBoxCellPanel.color", Color.class));
		setForeground((Color) AppUIManager.get("comboBoxCellPanel.foreground", Color.class));
        setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
                return new JButton(){
					private static final long serialVersionUID = 1L;

					@Override
					public int getWidth() { return 0; }
                };
            }
        });

		setRenderer(new CustomComboBoxCellRenderer());
	}


}

class CustomComboBoxCellRenderer implements ListCellRenderer<Object> {

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		list.setSelectionBackground((Color) AppUIManager.get("comboBoxCellPanel.color", Color.class));

		if(value instanceof ComboBoxCellPanel){
			ComboBoxCellPanel cell = (ComboBoxCellPanel) value;
			if(isSelected){
				cell.setBackground((Color) AppUIManager.get("comboBoxCellPanel.selectedColor", Color.class));
			}else{
				cell.setBackground((Color) AppUIManager.get("comboBoxCellPanel.color", Color.class));
			}
			return cell;
		}

		return null;
	}

}