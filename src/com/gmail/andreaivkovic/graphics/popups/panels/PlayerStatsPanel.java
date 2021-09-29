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
package com.gmail.andreaivkovic.graphics.popups.panels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.markers.SeriesMarkers;

import com.gmail.andreaivkovic.graphics.popups.PlayerStatsDialog;
import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.simulator.players.AbstractPlayer;

/**
 * @author Ivkovic Andrea
 */

public class PlayerStatsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel titleLbl;
	private XChartPanel<XYChart> chartPanel;
	private GroupLayout groupLayout;
	private AbstractPlayer player;
	private XYChart chart;

	private JLabel subtitleLbl;

	public PlayerStatsPanel(PlayerStatsDialog parent, AbstractPlayer player) {
		this.player = player;
		setBackground((Color)AppUIManager.get("mainPanel.color", Color.class));
		titleLbl = new JLabel();
		titleLbl.setForeground((Color)AppUIManager.get("mainPanel.foreground", Color.class));
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		chart = new XYChartBuilder().width(800).height(600).theme(ChartTheme.Matlab).title("Andamento profitto").build();
		chart.getStyler().setAxisTickLabelsColor(Color.WHITE);
		chart.getStyler().setChartBackgroundColor((Color)AppUIManager.get("mainPanel.color", Color.class));
		chart.getStyler().setChartPadding(5);
		chart.getStyler().setPlotGridVerticalLinesVisible(false);
		chart.getStyler().setToolTipsAlwaysVisible(true);
		chart.getStyler().setChartFontColor(Color.LIGHT_GRAY);
		chart.getStyler().setLegendVisible(false);

		subtitleLbl = new JLabel();
		subtitleLbl.setForeground((Color)AppUIManager.get("mainPanel.foreground", Color.class));
		subtitleLbl.setHorizontalAlignment(SwingConstants.CENTER);

		chartPanel = new XChartPanel<>(chart);
		update();

		initLayout();
		setLayout(groupLayout);
	}

	public void update(){
		titleLbl.setText(
			player.getName() + " " +
			player.getSurname() + " (" +
			player.getType().getTitle() + ") " +
			Language.get("hasPlayed") + " " +
			player.getQuantityBets() + " " +
			Language.get("times")
		);
		String betString = ": [";
		for(int item : player.getLastBetReal()){
			betString += " " + item;
		}
		betString += " ]";
		subtitleLbl.setText(
			Language.get("gain") + ": " +
			player.getGain() + "      " +
			Language.get("loss") + ": " +
			player.getLoss() + "      " +
			Language.get("profit") + ": " +
			player.getProfit() + "      " +
			Language.get("currentBet") + betString
		);

		updateChart();
	}

	private void updateChart(){
		synchronized (chart) {
			List<Double> yData = new ArrayList<>();
			List<Double> xData = new ArrayList<>();
			double i = 0;
			for(AbstractPlayer item : player.getStory()){
				yData.add(item.getProfit());
				xData.add(i);
				i+=player.getStoryOfPlayerEach();
			}
			yData.add(player.getProfit());
			xData.add((double) player.getQuantityBets());

			if(yData.isEmpty()) return;
			chart.removeSeries("f");
			chart.addSeries("f", xData, yData).setMarker(SeriesMarkers.NONE).setLineColor(Color.CYAN);
			chartPanel.repaint();
		}
	}

	private void initLayout() {
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(titleLbl, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
				.addComponent(subtitleLbl, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
				.addComponent(chartPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(titleLbl, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(subtitleLbl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chartPanel, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
		);
	}

}
