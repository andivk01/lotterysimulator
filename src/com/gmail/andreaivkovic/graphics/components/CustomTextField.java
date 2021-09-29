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
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import com.gmail.andreaivkovic.main.AppUIManager;

/**
 * @author Ivkovic Andrea
 */

public class CustomTextField extends JTextField {
	private static final long serialVersionUID = 1L;

	private String hint;

	public CustomTextField(String hint, int alignment){
		this(hint);
		setHorizontalAlignment(alignment);
	}

	public CustomTextField(String hint){
		this.hint = hint;
		Color background = (Color) AppUIManager.get("customTextField.color", Color.class);
		Color foreground = (Color) AppUIManager.get("customTextField.foreground", Color.class);

		setBackground(background);
		setForeground(foreground);
		setCaretColor(foreground);
		setBorder(null);

		setText(hint);
		setFont(new Font("Arial", Font.ITALIC, 12));

		initListeners();
	}

	private void initListeners(){
		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setText(getText().trim());
				if(getText().equals("")){
					setText(hint);
					setFont(new Font("Arial", Font.ITALIC, 12));
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if(getText().equals(hint)) setText("");
				setForeground((Color) AppUIManager.get("customTextField.foreground", Color.class));
				setFont(new Font("Arial", Font.PLAIN, 12));
			}
		});
	}

	public boolean isOnlyHint(){
		if(getText().equals(hint)) return true;
		return false;
	}
}
