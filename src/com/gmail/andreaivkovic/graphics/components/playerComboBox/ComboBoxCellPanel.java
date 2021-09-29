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
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.simulator.players.PlayerType;

/**
 * @author Ivkovic Andrea
 */

public class ComboBoxCellPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private PlayerType type;
	private JLabel typeNameLbl;
	private JTextArea typeDescTxtArea;
	private GroupLayout groupLayout;

	public ComboBoxCellPanel(PlayerType type) {
		this(type.getTitle(), type.getDescription());
		this.type = type;
	}

	public ComboBoxCellPanel(String typeName, String typeDesc) {
		Color foreground = (Color) AppUIManager.get("comboBoxCellPanel.foreground", Color.class);
		typeNameLbl = new JLabel(typeName);
		typeNameLbl.setFont((Font) AppUIManager.get("comboBoxCellPanel.font", Font.class));
		typeNameLbl.setForeground(foreground);
		typeDescTxtArea = new JTextArea(typeDesc);
		typeDescTxtArea.setLineWrap(true);
		typeDescTxtArea.setWrapStyleWord(true);
		typeDescTxtArea.setForeground(foreground);
		typeDescTxtArea.setOpaque(false);

		setBackground((Color) AppUIManager.get("comboBoxCellPanel.color", Color.class));
		initLayout();
		setLayout(groupLayout);
	}

	@Override
	public String getName(){
		return typeNameLbl.getText();
	}

	public PlayerType getType(){
		return type;
	}

	private void initLayout() {
		groupLayout= new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(typeDescTxtArea, Alignment.LEADING, 10, 311, Short.MAX_VALUE)
						.addComponent(typeNameLbl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(typeNameLbl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(typeDescTxtArea, GroupLayout.PREFERRED_SIZE, 45, Short.MAX_VALUE)
					.addContainerGap())
		);
	}
}
