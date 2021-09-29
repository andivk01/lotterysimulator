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
package com.gmail.andreaivkovic.graphics.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.simulator.players.AbstractPlayer;

/**
 * @author Ivkovic Andrea
 */

public class PlayersTable extends JTable {
	private static final long serialVersionUID = 1L;
	private static final int COL0_WIDTH = 10;
	private static final int COL1_WIDTH = 10;
	private static final int COL2_WIDTH = 20;
	private static final int COL3_WIDTH = 70;
	private static final int COL4_WIDTH = 10;
	private static final int COL5_WIDTH = 10;
	private static final int COL6_WIDTH = 10;
	private static final int COL7_WIDTH = 10;
	private static final int ROWS_HEIGHT = 25;

	private PlayersTableModel model;
	private JTableHeader header;

	public PlayersTable(){
		header = getTableHeader();
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		header.setBackground((Color) AppUIManager.get("playersTable.header.background", Color.class));
		header.setForeground((Color) AppUIManager.get("playersTable.header.foreground", Color.class));
		header.setFont((Font) AppUIManager.get("playersTable.header.font", Font.class));
		model = new PlayersTableModel();
		setModel(model);

		model.addColumn(Language.get("name"));
		model.addColumn(Language.get("surname"));
		model.addColumn(Language.get("quantityOfBets"));
		model.addColumn(Language.get("currentBet"));
		model.addColumn(Language.get("gain"));
		model.addColumn(Language.get("loss"));
		model.addColumn(Language.get("profit"));
		model.addColumn(Language.get("enabled"));
		getColumn(Language.get("name")).setPreferredWidth(COL0_WIDTH);
		getColumn(Language.get("surname")).setPreferredWidth(COL1_WIDTH);
		getColumn(Language.get("quantityOfBets")).setPreferredWidth(COL2_WIDTH);
		getColumn(Language.get("currentBet")).setPreferredWidth(COL3_WIDTH);
		getColumn(Language.get("gain")).setPreferredWidth(COL4_WIDTH);
		getColumn(Language.get("loss")).setPreferredWidth(COL5_WIDTH);
		getColumn(Language.get("profit")).setPreferredWidth(COL6_WIDTH);
		getColumn(Language.get("enabled")).setPreferredWidth(COL7_WIDTH);

		setRowHeight(ROWS_HEIGHT);
		setDefaultRenderer(Object.class, new PlayersTableRenderer());
		setShowGrid(false);
	}

	public void fireTableDataChanged(){
		model.fireTableDataChanged();
	}

	public void addPlayer(AbstractPlayer player){
		model.addRow(new AbstractPlayer[] {player, player, player, player, player, player, player, player});
	}

	public void removeRow(int idx){
		model.removeRow(idx);
	}

	public void removeSelectedRows(){
		while(getSelectedRows().length > 0){
			removeRow(getSelectedRows()[0]);
		}
	}

}

class PlayersTableRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		JLabel retValue = new JLabel("");
		if(isSelected) {
			retValue.setBackground((Color) AppUIManager.get("playersTable.content.background.selected", Color.class));

		}else{
			retValue.setBackground((Color) AppUIManager.get("playersTable.content.background", Color.class));
		}
		retValue.setOpaque(true);
		retValue.setHorizontalAlignment(SwingConstants.CENTER);
		if(value instanceof String){
			String cell = (String) value;
			retValue.setText(cell);
		}

		if(value instanceof AbstractPlayer){
			AbstractPlayer player = (AbstractPlayer) value;
			String isEnabled = player.isEnabled() ? Language.get("yes") : Language.get("no");
			String lastBet = "";
			for(int bet : player.getLastBetReal()){
				lastBet += bet + " ";
			}

			switch(column){
				case 0:
					retValue.setText(player.getName());
					break;
				case 1:
					retValue.setText(player.getSurname());
					break;
				case 2:
					retValue.setText(player.getQuantityBets() + "");
					break;
				case 3:
					retValue.setText(lastBet);
					break;
				case 4:
					retValue.setText(player.getGain() + "");
					break;
				case 5:
					retValue.setText(player.getLoss() + "");
					break;
				case 6:
					retValue.setText(player.getProfit() + "");
					break;
				case 7:
					retValue.setText(isEnabled);
					break;
			}
		}
		retValue.setFont((Font) AppUIManager.get("playersTable.content.font", Font.class));
		retValue.setForeground((Color) AppUIManager.get("playersTable.content.foreground", Color.class));
		return retValue;
	}

}

class PlayersTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

}
