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
import com.gmail.andreaivkovic.simulator.Extraction;

/**
 * @author Ivkovic Andrea
 */

public class ExtractionsTable extends JTable {
	private static final long serialVersionUID = 1L;
	private static final int ROWS_HEIGHT = 25;

	private ExtractionsTableModel model;
	private JTableHeader header;

	public ExtractionsTable(){
		header = getTableHeader();
		header.setReorderingAllowed(false);
		header.setBackground((Color) AppUIManager.get("extractionsTable.header.background", Color.class));
		header.setForeground((Color) AppUIManager.get("extractionsTable.header.foreground", Color.class));
		header.setFont((Font) AppUIManager.get("extractionsTable.header.font", Font.class));
		model = new ExtractionsTableModel();
		setModel(model);

		model.addColumn(Language.get("numberExtraction"));
		model.addColumn(Language.get("extractedNumbers"));
		setAutoResizeMode(AUTO_RESIZE_LAST_COLUMN);
		setRowHeight(ROWS_HEIGHT);
		setDefaultRenderer(Object.class, new ExtractionsTableRenderer());
		setShowGrid(false);
	}

	public void fireTableDataChanged(){
		model.fireTableDataChanged();
	}

	public void addExtraction(Extraction extraction){
		model.addRow(new Extraction[] {extraction, extraction, extraction, extraction, extraction, extraction, extraction, extraction});
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

class ExtractionsTableRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		JLabel retValue = new JLabel("");
		if(isSelected) {
			retValue.setBackground((Color) AppUIManager.get("extractionsTable.content.background.selected", Color.class));

		}else{
			retValue.setBackground((Color) AppUIManager.get("extractionsTable.content.background", Color.class));
		}
		retValue.setOpaque(true);
		retValue.setHorizontalAlignment(SwingConstants.CENTER);
		if(value instanceof String){
			String cell = (String) value;
			retValue.setText(cell);
		}

		if(value instanceof Extraction){
			Extraction extraction = (Extraction) value;
			String id = extraction.getId() + "";
			String nums = extraction.toString();
			switch(column){
			case 0:
				retValue.setText(id);
				break;
			case 1:
				retValue.setText(nums);
				break;
			}
		}
		retValue.setFont((Font) AppUIManager.get("extractionsTable.content.font", Font.class));
		retValue.setForeground((Color) AppUIManager.get("extractionsTable.content.foreground", Color.class));
		return retValue;
	}

}

class ExtractionsTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

}