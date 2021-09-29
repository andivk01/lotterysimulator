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
package com.gmail.andreaivkovic.graphics.popups;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import com.gmail.andreaivkovic.graphics.popups.panels.SaveDialogPanel;
import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.simulator.Lottery;

/**
 * @author Ivkovic Andrea
 */

public class SaveGameDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private SaveDialogPanel panel;

	private Lottery lottery;

	public SaveGameDialog(Lottery lottery){
		this.lottery = lottery;
		getContentPane().setBackground((Color) AppUIManager.get("addPlayerPanel.color", Color.class));
		setTitle(Language.get("softwareName") + " - " + Language.get("saveTheSimulation").toLowerCase());
		setIconImage(((ImageIcon)AppUIManager.get("software.icon", ImageIcon.class)).getImage());
		panel = new SaveDialogPanel(this);
		setLayout(new BorderLayout());
		add(panel, BorderLayout.NORTH);
		setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
		setResizable(false);
	}


	public void showAt(int x, int y){
		int width = 480;
		int height = 190;
		setBounds(x, y, width, height);
		setVisible(true);
	}


	public Lottery getLottery() {
		return lottery;
	}
}
