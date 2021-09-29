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

import com.gmail.andreaivkovic.graphics.popups.panels.PlayerStatsPanel;
import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.simulator.Lottery;
import com.gmail.andreaivkovic.simulator.players.AbstractPlayer;

/**
 * @author Ivkovic Andrea
 */

public class PlayerStatsDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private PlayerStatsPanel panel;
	private Lottery lottery;

	public PlayerStatsDialog(AbstractPlayer player){
		getContentPane().setBackground((Color) AppUIManager.get("addPlayerPanel.color", Color.class));
		setTitle(Language.get("softwareName") + " - " + Language.get("statistics").toLowerCase());
		setIconImage(((ImageIcon)AppUIManager.get("software.icon", ImageIcon.class)).getImage());
		panel = new PlayerStatsPanel(this, player);
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		setModalityType(Dialog.ModalityType.MODELESS);
	}

	public void updatePanel(){
		panel.update();
	}

	public void showAt(int x, int y){
		int width = 600;
		int height = 300;
		setBounds(x, y, width, height);
		setVisible(true);
	}

	public Lottery getLottery(){
		return lottery;
	}
}
