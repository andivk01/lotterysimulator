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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.gmail.andreaivkovic.graphics.components.PlayersTable;
import com.gmail.andreaivkovic.graphics.popups.PlayerStatsDialog;
import com.gmail.andreaivkovic.simulator.Lottery;
import com.gmail.andreaivkovic.simulator.players.AbstractPlayer;

/**
 * @author Ivkovic Andrea
 */

public class PlayersPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private List<PlayerStatsDialog> dialogsToUpdate;
	private PlayersTable playersTable;
	private GroupLayout groupLayout;
	private JScrollPane scrollPane;

	public PlayersPanel(Lottery lottery) {
		dialogsToUpdate = new ArrayList<>();
		scrollPane = new JScrollPane();
		playersTable = new PlayersTable();
		for(AbstractPlayer thisPlayer : lottery.getPlayers()){
			playersTable.addPlayer(thisPlayer);
		}
		playersTable.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					int row = playersTable.rowAtPoint(e.getPoint());
					int col = playersTable.columnAtPoint(e.getPoint());
					if (row >= 0 && col >= 0) {
						AbstractPlayer player = (AbstractPlayer) playersTable.getValueAt(row, col);
						PlayerStatsDialog dialog = new PlayerStatsDialog(player);
						dialog.showAt(0, 0);
						dialogsToUpdate.add(dialog);
					}
				}
			}
		});
		scrollPane.setViewportView(playersTable);
		initLayout();
		setLayout(groupLayout);
	}

	public void update(){
		playersTable.fireTableDataChanged();
		for(PlayerStatsDialog item : dialogsToUpdate){
			item.updatePanel();
		}
	}

	private void initLayout() {
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
		);
	}

	public PlayersTable getPlayersTable() {
		return playersTable;
	}

}
