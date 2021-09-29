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
package com.gmail.andreaivkovic.graphics.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.gmail.andreaivkovic.graphics.components.CustomTextField;
import com.gmail.andreaivkovic.graphics.components.textsComboBox.TextComboBox;
import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.main.Language.LangId;
import com.gmail.andreaivkovic.simulator.Lottery;

/**
 * @author Ivkovic Andrea
 */

public class SettingsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private StartupFrame parentFrame;

	private JLabel settingsTitleLbl;
	private JLabel simulationRangeLbl;
	private CustomTextField simRange2Field;
	private CustomTextField simRange1Field;
	private JLabel simRangeDivisorLbl;
	private JLabel playersBetLbl;
	private JLabel playersWinLbl;
	private JLabel playersLoseLbl;
	private JLabel langLbl;
	private GroupLayout groupLayout;

	private TextComboBox playersBetField;

	private CustomTextField playersWinField;

	private TextComboBox langField;

	private CustomTextField playersLoseField;
	private JButton startSimulationBtn;
	private JLabel saveStatusEach;

	private CustomTextField saveEachField;

	public SettingsPanel(StartupFrame parentFrame) {
		this.parentFrame = parentFrame;
		Color foreground = (Color) AppUIManager.get("settings.foreground", Color.class);
		Font font = (Font) AppUIManager.get("settings.font", Font.class);
		setBackground((Color) AppUIManager.get("settings.color", Color.class));
		settingsTitleLbl = new JLabel(Language.get("settings"));
		settingsTitleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		settingsTitleLbl.setFont((Font) AppUIManager.get("settings.settingsTitleLbl.font", Font.class));
		settingsTitleLbl.setForeground(foreground);

		simulationRangeLbl = new JLabel(Language.get("rangeOfSimulation"));
		simulationRangeLbl.setForeground(foreground);
		simulationRangeLbl.setFont(font);
		simulationRangeLbl.setVisible(false);

		simRange1Field = new CustomTextField("1");
		simRange1Field.setForeground(foreground);
		simRange1Field.setEditable(false);
		simRange1Field.setHorizontalAlignment(SwingConstants.CENTER);
		simRange1Field.setVisible(false);


		simRange2Field = new CustomTextField("90 (n<90)");
		simRange2Field.setForeground(foreground);
		simRange2Field.setHorizontalAlignment(SwingConstants.CENTER);
		simRange2Field.setVisible(false);

		simRangeDivisorLbl = new JLabel("-");
		simRangeDivisorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		simRangeDivisorLbl.setForeground(foreground);
		simRangeDivisorLbl.setFont(font);
		simRangeDivisorLbl.setVisible(false);

		playersBetLbl = new JLabel(Language.get("thePlayersBet"));
		playersBetLbl.setForeground(foreground);
		playersBetLbl.setFont(font);

		playersWinLbl = new JLabel(Language.get("thePlayersWin"));
		playersWinLbl.setForeground(foreground);
		playersWinLbl.setFont(font);

		playersLoseLbl = new JLabel(Language.get("thePlayersLose"));
		playersLoseLbl.setForeground(foreground);
		playersLoseLbl.setFont(font);

		langLbl = new JLabel(Language.get("language"));
		langLbl.setForeground(foreground);
		langLbl.setFont(font);

		playersBetField = new TextComboBox();
		playersBetField.add("1 " + Language.get("number"));
		playersBetField.add("2 " + Language.get("numbers"));
		playersBetField.add("3 " + Language.get("numbers"));
		playersBetField.add("4 " + Language.get("numbers"));
		playersBetField.add("5 " + Language.get("numbers"));

		playersWinField = new CustomTextField("18");
		playersWinField.setHorizontalAlignment(SwingConstants.CENTER);

		playersLoseField = new CustomTextField("1");
		playersLoseField.setHorizontalAlignment(SwingConstants.CENTER);

		langField = new TextComboBox();
		langField.add(Language.get("italian"));
		langField.add(Language.get("english"));
		langField.add(Language.get("spanish"));
		langField.add(Language.get("german"));
		langField.add(Language.get("french"));

		saveStatusEach = new JLabel(Language.get("savePlayerEach"));
		saveStatusEach.setForeground(foreground);
		saveStatusEach.setFont(font);

		saveEachField = new CustomTextField(Language.get("saveStatusEachTenPlay"));
		saveEachField.setHorizontalAlignment(SwingConstants.CENTER);

		startSimulationBtn = new JButton("START");
		startSimulationBtn.setForeground((Color) AppUIManager.get("simExtractionsBtn.foreground", Color.class));
		startSimulationBtn.setBackground((Color) AppUIManager.get("simExtractionsBtn.background", Color.class));
		startSimulationBtn.setFont((Font) AppUIManager.get("simExtractionsBtn.font", Font.class));
		startSimulationBtn.setFocusPainted(false);
		startSimulationBtn.setBorderPainted(false);

		initListeners();
		initLayout();
		setLayout(groupLayout);
	}

	private void initListeners() {
		startSimulationBtn.addActionListener((e)->{
			String langCode = langField.getSelectedString().trim();
			if(langCode.isEmpty()) langCode = "en";
			langCode = langCode.substring(langCode.length()-2);
			LangId langId = LangId.getByDesc(langCode);
			if(langId == null) langId = LangId.EN;
			Language.init(langId);

			String playersBet = playersBetField.getSelectedString().trim();
			if(playersBet.isEmpty()) playersBet = "1";
			playersBet = playersBet.substring(0, 1);

			int max = 90;
			double win = 17;
			double lose = 1;
			int nBet = 1;
			int saveStatusEach = 10;
			try{
				if(!simRange2Field.isOnlyHint()) {
					max = Integer.parseInt(simRange2Field.getText());
				}
				if(max > 90 || max < 1) max = 90;
				win = Double.parseDouble(playersWinField.getText());
				lose = Double.parseDouble(playersLoseField.getText());
				nBet = Integer.parseInt(playersBet);
				if(!saveEachField.isOnlyHint()){
					saveStatusEach = Integer.parseInt(saveEachField.getText());
				}
				Lottery lottery = new Lottery(5, max, win, lose, nBet);
				lottery.setSaveStoryOfPlayerEach(saveStatusEach);
				MainFrame appFrame = new MainFrame(lottery);
				appFrame.setVisible(true);
				appFrame.pack();
				appFrame.setMinimumSize(new Dimension(1110, 690));
				parentFrame.dispose();
			}catch(Exception exc){exc.printStackTrace();}
		});
	}

	private void initLayout() {
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(45)
							.addComponent(settingsTitleLbl, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
							.addGap(30))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(96)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(playersBetLbl, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
								.addComponent(langLbl, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
								.addComponent(simulationRangeLbl, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
								.addComponent(playersWinLbl, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(playersLoseLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(32))
								.addComponent(saveStatusEach, GroupLayout.PREFERRED_SIZE, 87, Short.MAX_VALUE))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(29)
									.addComponent(simRange1Field, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(simRangeDivisorLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(simRange2Field, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
									.addGap(24))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(saveEachField, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
										.addComponent(playersLoseField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(playersBetField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(langField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(playersWinField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
							.addGap(100)))
					.addGap(0))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(148)
					.addComponent(startSimulationBtn, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
					.addGap(152))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(settingsTitleLbl)
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(langField, 0, 0, Short.MAX_VALUE)
						.addComponent(langLbl))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(simulationRangeLbl)
						.addComponent(simRange1Field, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(simRangeDivisorLbl)
						.addComponent(simRange2Field, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(playersBetField, 0, 0, Short.MAX_VALUE)
						.addComponent(playersBetLbl))
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(playersWinLbl)
						.addComponent(playersWinField, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(playersLoseLbl)
						.addComponent(playersLoseField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(saveEachField, 0, 0, Short.MAX_VALUE)
						.addComponent(saveStatusEach, GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addComponent(startSimulationBtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(45))
		);
	}
}
