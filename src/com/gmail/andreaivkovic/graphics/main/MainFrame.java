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
import com.gmail.andreaivkovic.simulator.Lottery;

/**
 * @author Ivkovic Andrea
 */

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private Lottery lottery;
	private MainPanel mainPanel;

	public MainFrame(Lottery lottery){
		mainPanel = new MainPanel(this, lottery);
		setIconImage(((ImageIcon)AppUIManager.get("software.icon", ImageIcon.class)).getImage());
		setTitle("LotterySimulator - v4.2");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(mainPanel);
	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}


	public void setLottery(Lottery lottery){
		this.lottery = lottery;
	}

	public void updatePanels(){
		mainPanel = new MainPanel(this, lottery);
		setContentPane(mainPanel);
		revalidate();
	}

}
