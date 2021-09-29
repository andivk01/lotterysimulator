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
package com.gmail.andreaivkovic.graphics.components.textsComboBox;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.gmail.andreaivkovic.main.AppUIManager;

/**
 * @author Ivkovic Andrea
 */

public class TextComboBox extends JComboBox<Object> {
	private static final long serialVersionUID = 1L;

	private DefaultComboBoxModel<Object> model;

	public TextComboBox() {
		super();
		model = new DefaultComboBoxModel<>();
		setModel(model);
		add("");
		setBackground((Color) AppUIManager.get("betQuantityCellPanel.color", Color.class));
		setForeground((Color) AppUIManager.get("betQuantityCellPanel.foreground", Color.class));
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
		setRenderer(new TextComboBoxCellRenderer());
	}

	public void add(String toAdd){
		model.addElement(new TextComboBoxCellPanel(toAdd));
	}

	public String getSelectedString(){
		TextComboBoxCellPanel pnl = (TextComboBoxCellPanel) getSelectedItem();
		return pnl.getName();
	}

}

class TextComboBoxCellRenderer implements ListCellRenderer<Object> {

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		list.setSelectionBackground((Color) AppUIManager.get("betQuantityCellPanel.color", Color.class));

		if(value instanceof TextComboBoxCellPanel){
			TextComboBoxCellPanel cell = (TextComboBoxCellPanel) value;
			if(isSelected){
				cell.setBackground((Color) AppUIManager.get("betQuantityCellPanel.selectedColor", Color.class));
			}else{
				cell.setBackground((Color) AppUIManager.get("betQuantityCellPanel.color", Color.class));
			}
			return cell;
		}

		return null;
	}

}