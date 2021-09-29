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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.markers.SeriesMarkers;

import com.gmail.andreaivkovic.graphics.popups.panels.selectplayerslist.SelectPlayersPanel;
import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.simulator.Lottery;
import com.gmail.andreaivkovic.simulator.players.AbstractPlayer;

/**
 * @author Ivkovic Andrea
 */

public class AllPlayersStatsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GroupLayout groupLayout;
	private XChartPanel chartPanel;
	private JLabel isAFairGameLbl;
	private JLabel nExtractedMoreTimesLbl;
	private JLabel playerHighestProfitLbl;
	private JLabel totalExtractionsLbl;
	private JLabel nExtractedLessTimeLbl;
	private JLabel maxProfitDiffLbl;
	private SelectPlayersPanel selectPlayersPnl;
	private JLabel nExtractedMoreRisLbl;
	private JLabel nExtractedLessRisLbl;
	private JLabel totalExtractionsRisLbl;
	private JLabel highestProfitRisLbl;
	private JLabel maxDiffProfitRisLbl;
	private JLabel playersEnabledLbl;
	private Lottery lottery;
	private XYChart chart;
	private int seriesName;

	public AllPlayersStatsPanel(Lottery lottery) {
		Color backgroundColor = (Color) AppUIManager.get("mainPanel.color", Color.class);
		Color foreground = (Color) AppUIManager.get("simExtractionsBtn.foreground", Color.class);
		setBackground(backgroundColor);
		chart = new XYChartBuilder().width(800).height(600).theme(ChartTheme.Matlab).title("Andamento profitto").build();
		chart.getStyler().setAxisTickLabelsColor(Color.WHITE);
		chart.getStyler().setChartBackgroundColor((Color)AppUIManager.get("mainPanel.color", Color.class));
		chart.getStyler().setChartPadding(5);
		chart.getStyler().setPlotGridVerticalLinesVisible(false);
		chart.getStyler().setToolTipsAlwaysVisible(true);
		chart.getStyler().setChartFontColor(Color.LIGHT_GRAY);
		chart.getStyler().setLegendVisible(false);
		chartPanel = new XChartPanel<>(chart);

		this.lottery = lottery;
		List<AbstractPlayer> players = lottery.getPlayers();

		String isFairGame = "";
		if(lottery.isFair()){
			isFairGame = Language.get("isAFairGame");
		}else{
			isFairGame = Language.get("isNotAFairGame");
		}
		isAFairGameLbl = new JLabel(isFairGame);
		isAFairGameLbl.setToolTipText(isFairGame);
		isAFairGameLbl.setForeground(foreground);

		nExtractedMoreTimesLbl = new JLabel(Language.get("numberExtractedMoreTimes") + ":");
		nExtractedMoreTimesLbl.setToolTipText(Language.get("numberExtractedMoreTimes"));
		nExtractedMoreTimesLbl.setForeground(foreground);

		playerHighestProfitLbl = new JLabel(Language.get("playerHighestProfit") + ":");
		playerHighestProfitLbl.setToolTipText(Language.get("playerHighestProfit"));
		playerHighestProfitLbl.setForeground(foreground);

		totalExtractionsLbl = new JLabel(Language.get("extractionQuantity") + ":");
		totalExtractionsLbl.setToolTipText(Language.get("extractionQuantity"));
		totalExtractionsLbl.setForeground(foreground);

		nExtractedLessTimeLbl = new JLabel(Language.get("numberExtractedLessTimes") + ":");
		nExtractedLessTimeLbl.setToolTipText(Language.get("numberExtractedLessTimes"));
		nExtractedLessTimeLbl.setForeground(foreground);

		maxProfitDiffLbl = new JLabel(Language.get("maxProfitDifference") + ":");
		maxProfitDiffLbl.setToolTipText(Language.get("maxProfitDifference"));
		maxProfitDiffLbl.setForeground(foreground);

		playersEnabledLbl = new JLabel(Language.get("playersEnabledInChart") + ":");
		playersEnabledLbl.setToolTipText(Language.get("playersEnabledInChart"));
		playersEnabledLbl.setForeground(foreground);

		selectPlayersPnl = new SelectPlayersPanel(players);
		selectPlayersPnl.setBackground(backgroundColor);

		nExtractedMoreRisLbl = new JLabel();
		nExtractedMoreRisLbl.setForeground(foreground);

		nExtractedLessRisLbl = new JLabel();
		nExtractedLessRisLbl.setForeground(foreground);

		totalExtractionsRisLbl = new JLabel();
		totalExtractionsRisLbl.setForeground(foreground);

		highestProfitRisLbl = new JLabel();
		highestProfitRisLbl.setForeground(foreground);

		maxDiffProfitRisLbl = new JLabel();
		maxDiffProfitRisLbl.setForeground(foreground);
		updateChart();
		initListeners();
		initLayout();
		setLayout(groupLayout);
	}

	private void initListeners() {
		selectPlayersPnl.getEnablePlayerBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						updateChart();
					}
				});
			}
		});
		selectPlayersPnl.getDisablePlayerBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						updateChart();
					}
				});
			}
		});
		selectPlayersPnl.getPlayersList().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				JList<AbstractPlayer> list = selectPlayersPnl.getPlayersList();
				if(e.getClickCount() >= 2){
					AbstractPlayer player = list.getModel().getElementAt(list.locationToIndex(e.getPoint()));
					player.changeColor();
				}
				list.repaint();
				updateChart();
			}
		});
	}

	private void updateChart(){
		for(int i=0; i<seriesName; i++){
			chart.removeSeries(i+"");
		}
		seriesName = 0;
		synchronized (chart) {
			for(AbstractPlayer player : lottery.getPlayers()) {
				if(!player.isVisibleOnCharts()) continue;
				List<Double> yData = new ArrayList<>();
				List<Double> xData = new ArrayList<>();
				double i = 0;
				for(AbstractPlayer story : player.getStory()){
					yData.add(story.getProfit());
					xData.add(i);
					i+=player.getStoryOfPlayerEach();
				}

				yData.add(player.getProfit());
				xData.add((double) player.getQuantityBets());
				if(yData.isEmpty()) continue;
				chart.addSeries(seriesName+"", xData, yData).setMarker(SeriesMarkers.NONE).setLineColor(player.getColor());
				seriesName++;
			}
			chartPanel.repaint();
		}
	}

	public void removeSeries(String name){
		chart.removeSeries(name);
	}



	public void updatePanel() {
		nExtractedMoreRisLbl.setText(lottery.getExtractionsData().getNumberExtrMoreTimes() + "");
		nExtractedLessRisLbl.setText(lottery.getExtractionsData().getNumberExtrLessTimes() + "");
		totalExtractionsRisLbl.setText(lottery.getExtractionsData().getExtractions().size() + "");
		if(!lottery.getPlayers().isEmpty()) {
			highestProfitRisLbl.setText(lottery.getPlayerHighestProfit().getName());
			maxDiffProfitRisLbl.setText(lottery.maxProfitDifference() + "");
		}
		selectPlayersPnl.updatePanel();
		updateChart();
	}

	private void initLayout() {
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(nExtractedMoreTimesLbl, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
								.addComponent(nExtractedLessTimeLbl, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
								.addComponent(totalExtractionsLbl, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
								.addComponent(playerHighestProfitLbl, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
								.addComponent(maxProfitDiffLbl, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(maxDiffProfitRisLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(highestProfitRisLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(totalExtractionsRisLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(nExtractedLessRisLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(nExtractedMoreRisLbl, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)))
						.addComponent(isAFairGameLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(playersEnabledLbl, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
						.addComponent(selectPlayersPnl, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chartPanel, GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
					.addGap(9))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(chartPanel, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(nExtractedMoreTimesLbl)
								.addComponent(nExtractedMoreRisLbl))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(nExtractedLessTimeLbl)
								.addComponent(nExtractedLessRisLbl))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(totalExtractionsLbl)
								.addComponent(totalExtractionsRisLbl))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(playerHighestProfitLbl)
								.addComponent(highestProfitRisLbl))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(maxProfitDiffLbl)
								.addComponent(maxDiffProfitRisLbl))
							.addGap(11)
							.addComponent(isAFairGameLbl, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(playersEnabledLbl)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(selectPlayersPnl, GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)))
					.addContainerGap())
		);
	}
}
