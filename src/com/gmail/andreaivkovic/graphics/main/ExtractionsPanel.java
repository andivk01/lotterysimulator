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

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;

import com.gmail.andreaivkovic.graphics.components.CustomScrollBar;
import com.gmail.andreaivkovic.graphics.components.ExtractionsTable;
import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.simulator.Extraction;
import com.gmail.andreaivkovic.simulator.ExtractionsData;

/**
 * @author Ivkovic Andrea
 */

public class ExtractionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private ExtractionsData extrData;
	private JScrollPane tableScrollPane;

	private CategoryChart chart;

	private ExtractionsTable extractionsTable;

	private XChartPanel<CategoryChart> chartPanel;

	private GroupLayout groupLayout;

	private JSplitPane splitPane;

	public ExtractionsPanel(ExtractionsData extrData) {
		this.extrData = extrData;
		chart = new CategoryChartBuilder().title(Language.get("frequencyOfNumbers")).build();
		chart.getStyler().setChartBackgroundColor((Color)AppUIManager.get("mainPanel.color", Color.class));
		chart.getStyler().setChartPadding(5);
		chart.getStyler().setAxisTickLabelsColor(Color.WHITE);
		chart.getStyler().setToolTipsEnabled(true);
		chart.getStyler().setToolTipBackgroundColor(Color.DARK_GRAY);
		chart.getStyler().setPlotGridVerticalLinesVisible(false);
		chart.getStyler().setChartFontColor(Color.LIGHT_GRAY);
		chart.getStyler().setLegendVisible(false);
		extractionsTable = new ExtractionsTable();
		chartPanel = new XChartPanel<>(chart);
		this.tableScrollPane = new JScrollPane(extractionsTable);
		tableScrollPane.setVerticalScrollBar(new CustomScrollBar());
		splitPane = new JSplitPane();
		splitPane.setDividerLocation(300);
		splitPane.setLeftComponent(tableScrollPane);
		splitPane.setRightComponent(chartPanel);
		splitPane.setBorder(null);
		splitPane.setDividerSize(6);
		splitPane.setUI(new BasicSplitPaneUI() {
			@Override
			public BasicSplitPaneDivider createDefaultDivider() {
				return new BasicSplitPaneDivider(this) {
					private static final long serialVersionUID = 1L;
					@Override
					public void setBorder(Border b) {}
					@Override
					public void paint(Graphics g) {
						g.setColor(Color.BLACK);
						g.fillRect(0, 0, getSize().width, getSize().height);
						super.paint(g);
					}
				};
			}
        });

        splitPane.setBorder(null);

		for(Extraction thisExt : extrData.getExtractions()){
			extractionsTable.addExtraction(thisExt);
		}

		update();

		initLayout();
		setLayout(groupLayout);
	}

	private void initLayout() {
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
		);
	}

	public void update(){
		extractionsTable.fireTableDataChanged();

		int[] yData = extrData.getNumbersQuote();
		int[] xData = new int[yData.length];
		for(int i=0; i<xData.length; i++){
			xData[i] = xData[i]+i+1;
		}
		chart.removeSeries("f");
		chart.addSeries("f", xData, yData);
		chartPanel.repaint();
	}

	public ExtractionsTable getExtractionsTable() {
		return extractionsTable;
	}
}
