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
package com.gmail.andreaivkovic.graphics.popups.panels;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.gmail.andreaivkovic.graphics.components.CustomTextField;
import com.gmail.andreaivkovic.graphics.popups.SaveGameDialog;
import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.simulator.save.LotteryFile;
import com.gmail.andreaivkovic.simulator.save.LotteryTextFile;

/**
 * @author Ivkovic Andrea
 */

public class SaveDialogPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private SaveGameDialog parent;
	private GroupLayout groupLayout;
	private JLabel saveSimulationLbl;
	private JRadioButton fileGameBtn;
	private JRadioButton fileTxtBtn;
	private JLabel exportAsLbl;
	private JLabel destPathLbl;
	private CustomTextField pathDestField;
	private JLabel fileNameLbl;
	private JButton saveBtn;
	private CustomTextField nameFileField;

	public SaveDialogPanel(SaveGameDialog parent) {
		this.parent = parent;
		Color foreground = (Color) AppUIManager.get("saveSimulationPanel.foreground", Color.class);
		setBackground((Color) AppUIManager.get("saveSimulationPanel.color", Color.class));
		saveSimulationLbl = new JLabel(Language.get("saveTheSimulation"));
		saveSimulationLbl.setBackground(Color.GRAY);
		saveSimulationLbl.setOpaque(true);
		saveSimulationLbl.setHorizontalAlignment(SwingConstants.CENTER);

		fileGameBtn = new JRadioButton(Language.get("gameFile"));
		fileGameBtn.setOpaque(false);
		fileGameBtn.setForeground(foreground);
		fileGameBtn.setFocusPainted(false);

		fileTxtBtn = new JRadioButton(Language.get("textFile"));
		fileTxtBtn.setOpaque(false);
		fileTxtBtn.setForeground(foreground);
		fileTxtBtn.setFocusPainted(false);

		exportAsLbl = new JLabel(Language.get("exportAs"));
		exportAsLbl.setForeground(foreground);

		destPathLbl = new JLabel(Language.get("pathOfDestination"));
		destPathLbl.setForeground(foreground);

		fileNameLbl = new JLabel(Language.get("nameOfFile"));
		fileNameLbl.setForeground(foreground);
		pathDestField = new CustomTextField(System.getProperty("user.home") + File.separator + "Desktop");
		nameFileField = new CustomTextField(Language.get("softwareName"));

		saveBtn = new JButton(Language.get("save"));
		saveBtn.setFocusPainted(false);
		saveBtn.setBorderPainted(false);
		saveBtn.setForeground((Color) AppUIManager.get("simExtractionsBtn.foreground", Color.class));
		saveBtn.setBackground((Color) AppUIManager.get("simExtractionsBtn.background", Color.class));
		saveBtn.setFont((Font) AppUIManager.get("simExtractionsBtn.font", Font.class));

		initListeners();
		initLayout();
		setLayout(groupLayout);
	}

	private void initListeners() {
		saveBtn.addActionListener((e)->{
			String destFile = pathDestField.getText() + File.separator + nameFileField.getText();
			if(fileTxtBtn.isSelected()) {
				LotteryTextFile fileCreator = new LotteryTextFile(parent.getLottery());
				fileCreator.write(destFile, ".txt");
			}

			if(fileGameBtn.isSelected()){
				LotteryFile fileCreator = new LotteryFile(parent.getLottery());
				fileCreator.write(destFile, ".lottery");
			}

			parent.dispose();
		});
	}

	private void initLayout() {
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(saveSimulationLbl, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(exportAsLbl)
							.addPreferredGap(ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
							.addComponent(fileGameBtn)
							.addGap(18)
							.addComponent(fileTxtBtn))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(destPathLbl, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(pathDestField, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(fileNameLbl, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(nameFileField, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(188, Short.MAX_VALUE)
					.addComponent(saveBtn, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
					.addGap(182))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(saveSimulationLbl, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(exportAsLbl)
						.addComponent(fileTxtBtn)
						.addComponent(fileGameBtn))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(destPathLbl)
						.addComponent(pathDestField, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(fileNameLbl)
						.addComponent(nameFileField, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(saveBtn, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
					.addContainerGap())
		);
	}
}
