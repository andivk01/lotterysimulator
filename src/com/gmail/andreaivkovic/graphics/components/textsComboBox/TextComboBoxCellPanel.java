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
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.gmail.andreaivkovic.main.AppUIManager;

/**
 * @author Ivkovic Andrea
 */

public class TextComboBoxCellPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel typeNameLbl;
	private GroupLayout groupLayout;

	public TextComboBoxCellPanel(String typeName) {
		Color foreground = (Color) AppUIManager.get("betQuantityCellPanel.foreground", Color.class);
		typeNameLbl = new JLabel(typeName);
		typeNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		typeNameLbl.setFont((Font) AppUIManager.get("betQuantityCellPanel.font", Font.class));
		typeNameLbl.setForeground(foreground);

		setBackground((Color) AppUIManager.get("betQuantityCellPanel.color", Color.class));
		initLayout();
		setLayout(groupLayout);
	}

	private void initLayout() {
		groupLayout= new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(typeNameLbl, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(typeNameLbl, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
		);
	}

	@Override
	public String getName(){
		return typeNameLbl.getText();
	}

}
