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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;

import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.main.Language;

/**
 * @author Ivkovic Andrea
 */

public class IntegerGroupPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private boolean hasErrors;
	private int minValue;
	private int maxValue;

	private CustomTextField firstField;

	private CustomTextField fifthField;

	private CustomTextField fourthField;

	private CustomTextField thirdField;

	private CustomTextField secondField;

	private GroupLayout groupLayout;

	public IntegerGroupPanel(String[] hints, int alignment, int minValue, int maxValue) {
		this.hasErrors = false;
		this.minValue = minValue;
		this.maxValue = maxValue;
		String hintPrevix = Language.get("example") + ": ";

		firstField = new CustomTextField(hintPrevix + hints[0], alignment);

		secondField = new CustomTextField(hintPrevix + hints[1], alignment);

		thirdField = new CustomTextField(hintPrevix + hints[2], alignment);

		fourthField = new CustomTextField(hintPrevix + hints[3], alignment);

		fifthField = new CustomTextField(hintPrevix + hints[4], alignment);

		initLayout();
		initListeners();
		setLayout(groupLayout);
	}

	private void initLayout() {
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(firstField, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addGap(1)
					.addComponent(secondField, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addGap(1)
					.addComponent(thirdField, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addGap(1)
					.addComponent(fourthField, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addGap(1)
					.addComponent(fifthField, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(firstField, GroupLayout.PREFERRED_SIZE, 13, Short.MAX_VALUE)
						.addComponent(secondField, GroupLayout.PREFERRED_SIZE, 13, Short.MAX_VALUE)
						.addComponent(thirdField, GroupLayout.PREFERRED_SIZE, 13, Short.MAX_VALUE)
						.addComponent(fourthField, GroupLayout.PREFERRED_SIZE, 13, Short.MAX_VALUE)
						.addComponent(fifthField, GroupLayout.PREFERRED_SIZE, 13, Short.MAX_VALUE))
					.addGap(0))
		);
	}

	private void initListeners() {
		FocusListener errorListener = new FocusListener(){
			@Override
			public void focusLost(FocusEvent e) {
				CustomTextField textField = (CustomTextField) e.getSource();
				Color error = (Color)AppUIManager.get("customTextField.color.error", Color.class);
				int value = 0;
				try {
					value = Integer.parseInt(textField.getText().trim());
				}catch(NumberFormatException exc){
					if(!textField.isOnlyHint() && !textField.getText().equalsIgnoreCase("ND")) textField.setForeground(error);
				}
				if(value >= maxValue || value < minValue) textField.setForeground(error);
				if(!textField.isOnlyHint() && isDuplicated(textField.getText())) textField.setForeground(error);
			}
			@Override
			public void focusGained(FocusEvent e) {}

			public boolean isDuplicated(String searched){
				int count = 0;
				if(firstField.getText().equals(searched) && !firstField.getText().equalsIgnoreCase("ND")) count++;
				if(secondField.getText().equals(searched) && !secondField.getText().equalsIgnoreCase("ND")) count++;
				if(thirdField.getText().equals(searched) && !thirdField.getText().equalsIgnoreCase("ND")) count++;
				if(fourthField.getText().equals(searched) && !fourthField.getText().equalsIgnoreCase("ND")) count++;
				if(fifthField.getText().equals(searched) && !fifthField.getText().equalsIgnoreCase("ND")) count++;
				if(count > 1){ hasErrors = true; }else{ hasErrors = false; }
				if(count > 1){ return true; }else{ return false; }
			}
		};

		firstField.addFocusListener(errorListener);
		secondField.addFocusListener(errorListener);
		thirdField.addFocusListener(errorListener);
		fourthField.addFocusListener(errorListener);
		fifthField.addFocusListener(errorListener);

	}

	public boolean hasErrors(){
		return hasErrors;
	}

	public int[] getValues(){
		int[] retValue = new int[5];
		try{
			retValue[0] = Integer.parseInt(firstField.getText());
		}catch(NumberFormatException exc){}

		try{
			retValue[1] = Integer.parseInt(secondField.getText());
		}catch(NumberFormatException exc){}

		try{
			retValue[2] = Integer.parseInt(thirdField.getText());
		}catch(NumberFormatException exc){}

		try{
			retValue[3] = Integer.parseInt(fourthField.getText());
		}catch(NumberFormatException exc){}

		try{
			retValue[4] = Integer.parseInt(fifthField.getText());
		}catch(NumberFormatException exc){}

		for(int i=0; i<retValue.length; i++){
			retValue[i]--;
		}

		return retValue;
	}
}
