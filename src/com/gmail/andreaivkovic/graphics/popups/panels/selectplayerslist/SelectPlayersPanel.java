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
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import com.gmail.andreaivkovic.graphics.components.CustomScrollBar;
import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.simulator.players.AbstractPlayer;

/**
 * @author Ivkovic Andrea
 */

public class SelectPlayersPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GroupLayout groupLayout;
	private JList<AbstractPlayer> playersList;
	private DefaultListModel listModel;
	private JScrollPane scrollPane;
	private List<AbstractPlayer> players;

	private JButton enablePlayerBtn;

	private JButton disablePlayerBtn;

	public SelectPlayersPanel(List<AbstractPlayer> players) {
		this.players = players;
		playersList = new JList<>();
		playersList.setOpaque(false);
		playersList.setCellRenderer(new CustomCellListRenderer());
		playersList.setFixedCellHeight(50);
		listModel = new DefaultListModel<>();
		playersList.setModel(listModel);
		playersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane = new JScrollPane(playersList);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBar(new CustomScrollBar());
		scrollPane.setBorder(null);
		scrollPane.getViewport().setBackground((Color) AppUIManager.get("mainPanel.color", Color.class));
		addPlayers(players);

		enablePlayerBtn = new JButton();
		enablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("allPlayersPanelChartOn.icon", ImageIcon.class));
		enablePlayerBtn.setFocusPainted(false);
		enablePlayerBtn.setContentAreaFilled(false);
		enablePlayerBtn.setBorderPainted(false);

		disablePlayerBtn = new JButton();
		disablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("allPlayersPanelChartOff.icon", ImageIcon.class));
		disablePlayerBtn.setFocusPainted(false);
		disablePlayerBtn.setContentAreaFilled(false);
		disablePlayerBtn.setBorderPainted(false);

		initListeners();
		initLayout();
		setLayout(groupLayout);
	}

	public void addPlayer(AbstractPlayer player){
		listModel.addElement(player);
	}

	public void addPlayers(List<AbstractPlayer> players){
		for(AbstractPlayer item : players){
			addPlayer(item);
		}
	}

	@Override
	public void removeAll(){
		listModel.removeAllElements();
	}

	public void updatePanel(){
		removeAll();
		addPlayers(players);
		if(players.isEmpty()){
			enablePlayerBtn.setVisible(false);
			disablePlayerBtn.setVisible(false);
		}else{
			enablePlayerBtn.setVisible(true);
			disablePlayerBtn.setVisible(true);
		}
		playersList.repaint(10);
	}

	private void initListeners(){
		enablePlayerBtn.addMouseListener(new MouseAdapter() {
			long lastChangedIcon = 0;
			@Override
			public void mouseExited(MouseEvent e) {
				enablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("allPlayersPanelChartOn.icon", ImageIcon.class));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(System.currentTimeMillis() - lastChangedIcon < 100) return;
				enablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("allPlayersPanelChartOn.hover.icon", ImageIcon.class));
				lastChangedIcon = System.currentTimeMillis();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int idx : playersList.getSelectedIndices()){
					AbstractPlayer player;
					if(listModel.getElementAt(idx) instanceof AbstractPlayer){
						player = (AbstractPlayer) listModel.getElementAt(idx);
						player.setVisibleOnCharts(true);
					}
				}
				playersList.clearSelection();
				repaint();
			}
		});

		disablePlayerBtn.addMouseListener(new MouseAdapter() {
			long lastChangedIcon = 0;
			@Override
			public void mouseExited(MouseEvent e) {
				disablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("allPlayersPanelChartOff.icon", ImageIcon.class));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(System.currentTimeMillis() - lastChangedIcon < 100) return;
				disablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("allPlayersPanelChartOff.hover.icon", ImageIcon.class));
				lastChangedIcon = System.currentTimeMillis();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int idx : playersList.getSelectedIndices()){
					AbstractPlayer player;
					if(listModel.getElementAt(idx) instanceof AbstractPlayer){
						player = (AbstractPlayer) listModel.getElementAt(idx);
						player.setVisibleOnCharts(false);
					}
				}
				playersList.clearSelection();
				repaint();
			}
		});
	}

	public JList<AbstractPlayer> getPlayersList(){
		return playersList;
	}

	public JButton getEnablePlayerBtn() {
		return enablePlayerBtn;
	}

	public JButton getDisablePlayerBtn() {
		return disablePlayerBtn;
	}

	private void initLayout() {
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, 0, 254, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(enablePlayerBtn, 0, 0, Short.MAX_VALUE)
						.addComponent(disablePlayerBtn, GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(enablePlayerBtn, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(disablePlayerBtn, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(440, Short.MAX_VALUE))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
		);
	}

}

class CustomCellListRenderer implements ListCellRenderer{

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		AbstractPlayer player;
		if(value instanceof AbstractPlayer){
			player = (AbstractPlayer) value;
			PlayerCell retValue = new PlayerCell(player);
			if(player.isVisibleOnCharts()) {
				retValue.setBackground((Color)AppUIManager.get("playerVisibleOnChart.color", Color.class));
				retValue.setLblForeground((Color)AppUIManager.get("playerVisibleOnChart.foreground", Color.class));
			}else{
				retValue.setBackground((Color)AppUIManager.get("playerNotVisibleOnChart.color", Color.class));
				retValue.setLblForeground((Color)AppUIManager.get("playerNotVisibleOnChart.foreground", Color.class));
			}
			if(isSelected){
				retValue.setBackground((Color)AppUIManager.get("playerOnChartSelected.foreground", Color.class));
			}
			retValue.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
			retValue.getPlayerNameLbl().setForeground(player.getColor());
			return retValue;
		}

		return new JLabel("");
	}

}
