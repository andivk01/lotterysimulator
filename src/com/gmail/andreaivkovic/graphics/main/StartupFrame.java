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

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.gmail.andreaivkovic.main.AppUIManager;

/**
 * @author Ivkovic Andrea
 */

public class StartupFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public StartupFrame(){
		SettingsPanel panel = new SettingsPanel(this);
		setContentPane(panel);
		pack();
		setLocation(200, 200);
		setResizable(false);
		setIconImage(((ImageIcon)AppUIManager.get("software.icon", ImageIcon.class)).getImage());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
