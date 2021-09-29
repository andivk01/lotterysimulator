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
package com.gmail.andreaivkovic.graphics.popups.panels.selectplayerslist;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.simulator.players.AbstractPlayer;

/**
 * @author Ivkovic Andrea
 */

public class PlayerCell extends JPanel {
	private static final long serialVersionUID = 1L;
	private GroupLayout groupLayout;
	private JLabel playerNameLbl;
	private JLabel profitLbl;
	private JLabel playerType;

	public PlayerCell(AbstractPlayer player) {

		playerNameLbl = new JLabel(player.getName() + " " + player.getSurname());

		profitLbl = new JLabel(Language.get("profit").toLowerCase() + ": " + player.getProfit());

		playerType = new JLabel(player.getType().getTitle());

		initLayout();

		setLayout(groupLayout);

	}

	public JLabel getPlayerNameLbl(){
		return playerNameLbl;
	}

	public void setLblForeground(Color color){
		playerNameLbl.setForeground(color);
		profitLbl.setForeground(color);
		playerType.setForeground(color);
	}

	private void initLayout() {
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(playerNameLbl, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(profitLbl, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
						.addComponent(playerType, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(40, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(playerNameLbl)
						.addComponent(playerType))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(profitLbl)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
	}

}
