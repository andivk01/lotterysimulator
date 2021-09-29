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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.gmail.andreaivkovic.graphics.components.CustomTextField;
import com.gmail.andreaivkovic.graphics.components.IntegerGroupPanel;
import com.gmail.andreaivkovic.graphics.components.PlayersTable;
import com.gmail.andreaivkovic.graphics.components.playerComboBox.ComboBoxCellPanel;
import com.gmail.andreaivkovic.graphics.components.playerComboBox.CustomComboBox;
import com.gmail.andreaivkovic.graphics.popups.AddPlayerDialog;
import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.simulator.Lottery;
import com.gmail.andreaivkovic.simulator.players.AbstractPlayer;
import com.gmail.andreaivkovic.simulator.players.PlayerType;
import com.gmail.andreaivkovic.simulator.players.Type1;
import com.gmail.andreaivkovic.simulator.players.Type2;
import com.gmail.andreaivkovic.simulator.players.Type3;
import com.gmail.andreaivkovic.simulator.players.Type4;
import com.gmail.andreaivkovic.simulator.players.Type5;

/**
 * @author Ivkovic Andrea
 */

public class AddPlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private PlayersTable table;
	private AddPlayerDialog parent;
	private JLabel addPlayerLbl;
	private JLabel nameLbl;
	private JLabel surnameLbl;
	private JLabel enabledLbl;
	private JLabel typeLbl;
	private JButton addBtn;
	private CustomTextField nameTextField;
	private CustomTextField surnameTextField;
	private JRadioButton typologyRBtn;
	private CustomComboBox comboBox;
	private JLabel betLbl;
	private IntegerGroupPanel integerGroupPanel;
	private GroupLayout groupLayout;

	public AddPlayerPanel(AddPlayerDialog parent, PlayersTable table) {
		Color lblsColor = (Color) AppUIManager.get("addPlayerPanel.foreground", Color.class);
		String[] betFieldHint = ((String)AppUIManager.get("betFieldHint", String.class)).split(",");
		setBackground((Color) AppUIManager.get("addPlayerPanel.color", Color.class));
		this.parent = parent;
		this.table = table;

		addPlayerLbl = new JLabel(Language.get("addOnePlayer"));
		addPlayerLbl.setMinimumSize(new Dimension(5, 14));
		addPlayerLbl.setBackground(Color.gray);
		addPlayerLbl.setOpaque(true);
		addPlayerLbl.setForeground(Color.black);
		addPlayerLbl.setHorizontalAlignment(SwingConstants.CENTER);

		nameLbl = new JLabel(Language.get("name") + "*");
		nameLbl.setForeground(lblsColor);

		surnameLbl = new JLabel(Language.get("surname"));
		surnameLbl.setForeground(lblsColor);

		enabledLbl = new JLabel(Language.get("enabled") + "*");
		enabledLbl.setForeground(lblsColor);

		typeLbl = new JLabel(Language.get("typology") + "*");
		typeLbl.setForeground(lblsColor);

		addBtn = new JButton(Language.get("addPlayer"));
		addBtn.setBorderPainted(false);
		addBtn.setFocusPainted(false);
		addBtn.setForeground((Color) AppUIManager.get("simExtractionsBtn.foreground", Color.class));
		addBtn.setBackground((Color) AppUIManager.get("simExtractionsBtn.background", Color.class));
		addBtn.setFont((Font) AppUIManager.get("simExtractionsBtn.font", Font.class));

		nameTextField = new CustomTextField(Language.get("insertName"));

		surnameTextField = new CustomTextField(Language.get("insertSurname"));

		typologyRBtn = new JRadioButton(Language.get("clickForDisable"));
		typologyRBtn.setForeground(lblsColor);
		typologyRBtn.setSelected(true);
		typologyRBtn.setOpaque(false);
		typologyRBtn.setFocusPainted(false);

		comboBox = new CustomComboBox();

		betLbl = new JLabel(Language.get("bet"));
		betLbl.setForeground(lblsColor);
		betLbl.setVisible(false);
		integerGroupPanel = new IntegerGroupPanel(betFieldHint, SwingConstants.CENTER, 1, 91);
		integerGroupPanel.setVisible(false);

		initLayout();
		initListeners();
		setLayout(groupLayout);
	}

	private void initListeners() {
		addBtn.addActionListener((e)->{
			AbstractPlayer toAdd = null;

			String name = nameTextField.getText();
			String surname = surnameTextField.getText();
			if(name.equalsIgnoreCase("") || nameTextField.isOnlyHint()){
				nameTextField.setForeground((Color) AppUIManager.get("customTextField.color.error", Color.class));
			}else if(!integerGroupPanel.hasErrors()){
				ComboBoxCellPanel item = (ComboBoxCellPanel) comboBox.getSelectedItem();
				if(surnameTextField.isOnlyHint()) surname = "";
				Lottery lottery = parent.getLottery();
				switch(item.getType()) {
					case T1:
						toAdd = new Type1(name, surname, lottery.getExtractionsData(), lottery.getSaveStoryOfPlayerEach());
						break;
					case T2:
						toAdd = new Type2(name, surname, lottery.getExtractionsData(), lottery.getSaveStoryOfPlayerEach());
						break;
					case T3:
						toAdd = new Type3(name, surname, lottery.getExtractionsData(), lottery.getSaveStoryOfPlayerEach());
						((Type3)toAdd).setFav(integerGroupPanel.getValues());
						break;
					case T4:
						toAdd = new Type4(name, surname, lottery.getExtractionsData(), lottery.getSaveStoryOfPlayerEach());
						break;
					case T5:
						toAdd = new Type5(name, surname, lottery.getExtractionsData(), lottery.getSaveStoryOfPlayerEach());
						break;
				}
				if(typologyRBtn.isSelected()) toAdd.enable();
				table.addPlayer(toAdd);
				parent.getLottery().getPlayers().add(toAdd);
				parent.dispose();
			}
		});

		comboBox.addItemListener((e)->{
			if(e.getStateChange() == ItemEvent.SELECTED){
				ComboBoxCellPanel item = (ComboBoxCellPanel) e.getItem();
				if(item.getType() == PlayerType.T3) {
					betLbl.setVisible(true);
					integerGroupPanel.setVisible(true);
				}else{
					betLbl.setVisible(false);
					integerGroupPanel.setVisible(false);
				}
			}
		});

		typologyRBtn.addItemListener((e)->{
			if(typologyRBtn.isSelected()){
				typologyRBtn.setText(Language.get("clickForDisable"));
			}else{
				typologyRBtn.setText(Language.get("clickForEnable"));
			}
		});
	}

	private void initLayout() {
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(addPlayerLbl, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(surnameLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(nameLbl, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
							.addComponent(enabledLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(typeLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(betLbl))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(surnameTextField, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
								.addComponent(typologyRBtn, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
								.addComponent(comboBox, 0, 0, Short.MAX_VALUE)
								.addComponent(nameTextField, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(60)
							.addComponent(integerGroupPanel, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
							.addGap(56)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(154)
					.addComponent(addBtn, GroupLayout.PREFERRED_SIZE, 77, Short.MAX_VALUE)
					.addGap(144))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(addPlayerLbl, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameLbl)
						.addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(surnameLbl)
						.addComponent(surnameTextField, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(typologyRBtn, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(enabledLbl))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(typeLbl))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(betLbl)
						.addComponent(integerGroupPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(addBtn)
					.addGap(14))
		);
	}
}
