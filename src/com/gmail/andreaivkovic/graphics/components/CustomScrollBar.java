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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.gmail.andreaivkovic.main.AppUIManager;

/**
 * @author Ivkovic Andrea
 */

public class CustomScrollBar extends JScrollBar {
	private static final long serialVersionUID = 1L;

	public CustomScrollBar(){
		setUI(new CustomScrollBarUI());
		setUnitIncrement((int)AppUIManager.get("scrollbar.incrementUnit", int.class));
		setPreferredSize(new Dimension((int)AppUIManager.get("scrollbar.thickness", int.class), Integer.MAX_VALUE));
	}

}

class CustomScrollBarUI extends BasicScrollBarUI {

	public CustomScrollBarUI() {}



	@Override
	protected JButton createDecreaseButton(int orientation) {
		return new JButton() {
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension();
			}
		};
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return new JButton() {
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension();
			}
		};
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setPaint((Color)AppUIManager.get("scrollbar.colorTrack", Color.class));
		g2d.fillRect(r.x, r.y, r.width, r.height);
		g2d.dispose();
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		JScrollBar sb = (JScrollBar) c;
		if(!sb.isEnabled() || r.width > r.height){
			return;
		}
		g2d.setPaint((Color)AppUIManager.get("scrollbar.colorThumb", Color.class));
		g2d.fillRect(r.x, r.y, r.width, r.height);
		g2d.dispose();
	}
}
