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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.gmail.andreaivkovic.graphics.components.CustomTextField;
import com.gmail.andreaivkovic.graphics.components.PlayersTable;
import com.gmail.andreaivkovic.graphics.popups.AddPlayerDialog;
import com.gmail.andreaivkovic.graphics.popups.SaveGameDialog;
import com.gmail.andreaivkovic.graphics.popups.panels.AllPlayersStatsPanel;
import com.gmail.andreaivkovic.main.AppUIManager;
import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.simulator.Extraction;
import com.gmail.andreaivkovic.simulator.Lottery;
import com.gmail.andreaivkovic.simulator.players.AbstractPlayer;
import com.gmail.andreaivkovic.simulator.save.LotteryFile;

/**
 * @author Ivkovic Andrea
 */

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private CustomTextField quantitySimulateField;

	private MainFrame parentFrame;
	private Lottery lottery;

	private Timer simTimer;
	private JPanel innerPanel;
	private ExtractionsPanel extrPanel;
	private PlayersPanel playersPanel;
	private AllPlayersStatsPanel playersStatsPanel;
	private JButton simulateExtBtn;
	private JLabel quantitySimulateLbl;
	private JButton addPlayerBtn;
	private JButton remPlayerBtn;
	private JButton enablePlayerBtn;
	private JLabel totalExtractionsLbl;
	private JButton saveBtn;
	private JButton loadSimulationBtn;
	private JButton disablePlayerBtn;
	private GroupLayout groupLayout;
	private JButton extractionsBtn;
	private JButton playersBtn;

	private JLabel lastExtLbl;

	private JLabel lastExtNumsLbl;

	private JButton showStatsBtn;
	private JSlider simulationSlider;
	private JLabel infoDevelopedByLbl;

	public MainPanel(MainFrame parentFrame, Lottery lottery) {
		this.parentFrame = parentFrame;
		this.lottery = lottery;
		this.simTimer = new Timer(true);
		Color backgroundColor = (Color) AppUIManager.get("mainPanel.color", Color.class);
		Color foreground = (Color) AppUIManager.get("simExtractionsBtn.foreground", Color.class);
		setBackground(backgroundColor);
		quantitySimulateField = new CustomTextField("1", SwingConstants.CENTER);

		extractionsBtn = new JButton(Language.get("extractions").toUpperCase());
		extractionsBtn.setToolTipText(Language.get("extractions").toUpperCase());
		extractionsBtn.setFocusPainted(false);
		extractionsBtn.setBorderPainted(false);
		extractionsBtn.setForeground((Color) AppUIManager.get("simExtractionsBtn.foreground", Color.class));
		extractionsBtn.setBackground((Color) AppUIManager.get("simExtractionsBtn.background", Color.class));
		playersBtn = new JButton(Language.get("players").toUpperCase());
		playersBtn.setToolTipText(Language.get("players").toUpperCase());
		playersBtn.setFocusPainted(false);
		playersBtn.setBorderPainted(false);
		playersBtn.setForeground((Color) AppUIManager.get("simExtractionsBtn.foreground", Color.class));
		playersBtn.setBackground((Color) AppUIManager.get("simExtractionsBtn.background", Color.class));



		addPlayerBtn = new JButton();
		addPlayerBtn.setContentAreaFilled(false);
		addPlayerBtn.setIcon((ImageIcon) AppUIManager.get("addPlayerBtn.icon", ImageIcon.class));
		addPlayerBtn.setFocusPainted(false);
		addPlayerBtn.setBorderPainted(false);

		remPlayerBtn = new JButton();
		remPlayerBtn.setIcon((ImageIcon) AppUIManager.get("remPlayerBtn.icon", ImageIcon.class));
		remPlayerBtn.setFocusPainted(false);
		remPlayerBtn.setContentAreaFilled(false);
		remPlayerBtn.setBorderPainted(false);

		enablePlayerBtn = new JButton();
		enablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("enablePlayerBtn.icon", ImageIcon.class));
		enablePlayerBtn.setFocusPainted(false);
		enablePlayerBtn.setContentAreaFilled(false);
		enablePlayerBtn.setBorderPainted(false);

		disablePlayerBtn = new JButton();
		disablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("disablePlayerBtn.icon", ImageIcon.class));
		disablePlayerBtn.setFocusPainted(false);
		disablePlayerBtn.setContentAreaFilled(false);
		disablePlayerBtn.setBorderPainted(false);

		saveBtn = new JButton();
		saveBtn.setIcon((ImageIcon) AppUIManager.get("saveBtn.icon", ImageIcon.class));
		saveBtn.setFocusPainted(false);
		saveBtn.setContentAreaFilled(false);
		saveBtn.setBorderPainted(false);


		loadSimulationBtn = new JButton();
		loadSimulationBtn.setIcon((ImageIcon) AppUIManager.get("loadSimulationBtn.icon", ImageIcon.class));
		loadSimulationBtn.setFocusPainted(false);
		loadSimulationBtn.setContentAreaFilled(false);
		loadSimulationBtn.setBorderPainted(false);

		quantitySimulateLbl = new JLabel(Language.get("quantity"));
		quantitySimulateLbl.setForeground((Color) AppUIManager.get("quantityLbl.foreground", Color.class));


		simulateExtBtn = new JButton(Language.get("simulateExtractions"));
		simulateExtBtn.setForeground((Color) AppUIManager.get("simExtractionsBtn.foreground", Color.class));
		simulateExtBtn.setBackground((Color) AppUIManager.get("simExtractionsBtn.background", Color.class));
		simulateExtBtn.setFont((Font) AppUIManager.get("simExtractionsBtn.font", Font.class));
		simulateExtBtn.setBorderPainted(false);
		simulateExtBtn.setFocusPainted(false);

		totalExtractionsLbl = new JLabel(Language.get("haveBeenSimulated") + " " + lottery.getExtractionsData().getTotalExtractions() + " " + Language.get("extractions"));
		totalExtractionsLbl.setForeground(foreground);

		innerPanel = new JPanel();
		playersPanel = new PlayersPanel(lottery);
		extrPanel = new ExtractionsPanel(lottery.getExtractionsData());
		playersStatsPanel = new AllPlayersStatsPanel(lottery);
		innerPanel.setLayout(new BorderLayout());
		innerPanel.add(playersPanel, BorderLayout.CENTER);
		repaint();
		revalidate();

		lastExtLbl = new JLabel(Language.get("lastExtraction"));
		lastExtLbl.setFont((Font) AppUIManager.get("lastExtractionLbl.font", Font.class));
		lastExtLbl.setForeground(foreground);
		lastExtLbl.setHorizontalAlignment(SwingConstants.CENTER);

		lastExtNumsLbl = new JLabel("0 0 0 0 0");
		Extraction lastExt = lottery.getExtractionsData().getLastExtraction();
		if(lastExt != null) {
			lastExtNumsLbl.setText(lastExt.toString());
		}
		lastExtNumsLbl.setFont((Font) AppUIManager.get("lastExtractionNumsLbl.font", Font.class));
		lastExtNumsLbl.setForeground(foreground);
		lastExtNumsLbl.setHorizontalAlignment(SwingConstants.CENTER);


		showStatsBtn = new JButton(Language.get("statistics").toUpperCase());
		showStatsBtn.setForeground((Color) AppUIManager.get("mainPanel.color", Color.class));
		showStatsBtn.setFocusPainted(false);
		showStatsBtn.setBorderPainted(false);
		showStatsBtn.setContentAreaFilled(false);

		simulationSlider = new JSlider();
		simulationSlider.setOpaque(false);
		simulationSlider.setMaximum(60);
		simulationSlider.setMajorTickSpacing(5);
		simulationSlider.setMinorTickSpacing(1);
		simulationSlider.setPaintTicks(true);
		simulationSlider.setPaintLabels(true);
		simulationSlider.setValue(0);
		simulationSlider.setFont(new Font("Arial", Font.PLAIN, 12));
		simulationSlider.setForeground((Color) AppUIManager.get("mainPanel.color", Color.class));
		if(lottery.getExtractionsData().getTotalExtractions() > 0) enableShowStatsBtn();

		infoDevelopedByLbl = new JLabel("?");
		infoDevelopedByLbl.setFont(new Font("Arial", Font.BOLD, 20));
		infoDevelopedByLbl.setForeground(foreground);
		infoDevelopedByLbl.setToolTipText(
			"<html>Developed by <b>Ivkovic Andrea, Mellini Tancredi, Peinkhofer Leo</b><br>"
			+ "For more informations: <b>andrea.ivkovic01@gmail.com</b><br>"
			+ "Software released under <b>GPLv3.0 license</b>,<br>"
			+ "source code can be find on <b>github.com/andivk01/lotterysimulator</b>"
		);

		initListeners();
		initLayout();
		setLayout(groupLayout);
	}

	private void enableShowStatsBtn(){
		showStatsBtn.setContentAreaFilled(true);
		showStatsBtn.setToolTipText(Language.get("statistics").toUpperCase());
		showStatsBtn.setForeground((Color) AppUIManager.get("simExtractionsBtn.foreground", Color.class));
		showStatsBtn.setBackground((Color) AppUIManager.get("simExtractionsBtn.background", Color.class));
		showStatsBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				innerPanel.removeAll();
				innerPanel.add(playersStatsPanel, BorderLayout.CENTER);
				playersStatsPanel.updatePanel();
				repaint();
				revalidate();
			}
		});
	}

	private void initListeners(){

		playersBtn.addActionListener((e)->{
			innerPanel.removeAll();
			innerPanel.add(playersPanel, BorderLayout.CENTER);
			repaint();
			revalidate();
		});

		extractionsBtn.addActionListener((e)->{
			innerPanel.removeAll();
			innerPanel.add(extrPanel, BorderLayout.CENTER);
			repaint();
			revalidate();
		});

		simulateExtBtn.addActionListener((e)->{
			Integer quantityToSim = 1;
			try{
				quantityToSim = Integer.parseInt(quantitySimulateField.getText());
			}catch(NumberFormatException exc){
				quantitySimulateField.setText("1");
			}

			for(int i=0; i<quantityToSim; i++){
				lottery.extract();
				extrPanel.getExtractionsTable().addExtraction(lottery.getExtractionsData().getLastExtraction());
			}
			lastExtNumsLbl.setText(lottery.getExtractionsData().getLastExtraction().toString());
			playersPanel.update();
			extrPanel.update();
			playersStatsPanel.updatePanel();
			totalExtractionsLbl.setText(Language.get("haveBeenSimulated") + " " + lottery.getExtractionsData().getTotalExtractions() + " " + Language.get("extractions"));
			enableShowStatsBtn();
		});

		quantitySimulateField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							simulateExtBtn.doClick();
						}
					});
				}
			}

		});

		addPlayerBtn.addMouseListener(new MouseAdapter() {
			long lastChangedIcon = 0;
			@Override
			public void mouseEntered(MouseEvent e) {
				if(System.currentTimeMillis() - lastChangedIcon < 400) return;
				addPlayerBtn.setIcon((ImageIcon) AppUIManager.get("addPlayerBtn.icon", ImageIcon.class));
				lastChangedIcon = System.currentTimeMillis();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				int xPos = addPlayerBtn.getLocationOnScreen().x + addPlayerBtn.getWidth();
				int yPos = addPlayerBtn.getLocationOnScreen().y;
				new AddPlayerDialog(playersPanel.getPlayersTable(), lottery).showAt(xPos, yPos);
			}
		});

		remPlayerBtn.addMouseListener(new MouseAdapter() {
			long lastChangedIcon = 0;
			@Override
			public void mouseEntered(MouseEvent e) {
				if(System.currentTimeMillis() - lastChangedIcon < 400) return;
				remPlayerBtn.setIcon((ImageIcon) AppUIManager.get("remPlayerBtn.icon", ImageIcon.class));
				lastChangedIcon = System.currentTimeMillis();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayersTable playersTable = playersPanel.getPlayersTable();
				for(int row : playersTable.getSelectedRows()){
					AbstractPlayer player = (AbstractPlayer) playersTable.getValueAt(row, 0);
					lottery.getPlayers().remove(player);
				}
				playersTable.removeSelectedRows();
			}
		});

		enablePlayerBtn.addMouseListener(new MouseAdapter() {
			long lastChangedIcon = 0;
			@Override
			public void mouseExited(MouseEvent e) {
				enablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("enablePlayerBtn.icon", ImageIcon.class));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(System.currentTimeMillis() - lastChangedIcon < 400) return;
				enablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("enablePlayerBtn.hover.icon", ImageIcon.class));
				lastChangedIcon = System.currentTimeMillis();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayersTable playersTable = playersPanel.getPlayersTable();
				for(int row : playersTable.getSelectedRows()){
					AbstractPlayer player = (AbstractPlayer) playersTable.getValueAt(row, 0); //get name of the player
					player.enable();
				}
				playersTable.fireTableDataChanged();
			}
		});

		disablePlayerBtn.addMouseListener(new MouseAdapter() {
			long lastChangedIcon = 0;
			@Override
			public void mouseExited(MouseEvent e) {
				disablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("disablePlayerBtn.icon", ImageIcon.class));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(System.currentTimeMillis() - lastChangedIcon < 400) return;
				disablePlayerBtn.setIcon((ImageIcon) AppUIManager.get("disablePlayerBtn.hover.icon", ImageIcon.class));
				lastChangedIcon = System.currentTimeMillis();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayersTable playersTable = playersPanel.getPlayersTable();
				for(int row : playersTable.getSelectedRows()){
					AbstractPlayer player = (AbstractPlayer) playersTable.getValueAt(row, 0); //get name of the player
					player.disable();
				}
				playersTable.fireTableDataChanged();
			}
		});

		saveBtn.addMouseListener(new MouseAdapter() {
			long lastChangedIcon = 0;
			@Override
			public void mouseExited(MouseEvent e) {
				saveBtn.setIcon((ImageIcon) AppUIManager.get("saveBtn.icon", ImageIcon.class));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(System.currentTimeMillis() - lastChangedIcon < 400) return;
				saveBtn.setIcon((ImageIcon) AppUIManager.get("saveBtn.hover.icon", ImageIcon.class));
				lastChangedIcon = System.currentTimeMillis();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				int xPos = addPlayerBtn.getLocationOnScreen().x + addPlayerBtn.getWidth();
				int yPos = addPlayerBtn.getLocationOnScreen().y;
				new SaveGameDialog(lottery).showAt(xPos, yPos);
			}
		});

		loadSimulationBtn.addMouseListener(new MouseAdapter() {
			long lastChangedIcon = 0;
			@Override
			public void mouseExited(MouseEvent e) {
				loadSimulationBtn.setIcon((ImageIcon) AppUIManager.get("loadSimulationBtn.icon", ImageIcon.class));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(System.currentTimeMillis() - lastChangedIcon < 400) return;
				loadSimulationBtn.setIcon((ImageIcon) AppUIManager.get("loadSimulationBtn.hover.icon", ImageIcon.class));
				lastChangedIcon = System.currentTimeMillis();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				parentFrame.setVisible(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("File .lottery", "lottery");
				UIManager.put("FileChooser.readOnly", Boolean.TRUE);
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog(parentFrame);
				if(fileChooser.getSelectedFile() != null && fileChooser.getSelectedFile().isFile()) {
					Lottery lottery = new LotteryFile().read(fileChooser.getSelectedFile());
					if(lottery != null){
						parentFrame.setLottery(lottery);
						parentFrame.updatePanels();
					}
				}
				parentFrame.setVisible(true);
				parentFrame.repaint();
			}
		});
		simulationSlider.addChangeListener((e)->{
			if(simulationSlider.getValue() == 0){
				simTimer.cancel();
				simulationSlider.setForeground((Color) AppUIManager.get("mainPanel.color", Color.class));
			}else{
				simulationSlider.setForeground((Color) AppUIManager.get("mainPanel.foreground", Color.class));
				simTimer.cancel();
				simTimer = new Timer(true);
				TimerTask timertask = new TimerTask() {

					@Override
					public void run() {
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								simulateExtBtn.doClick();
							}
						});
					}
				};
				simTimer.schedule(timertask, simulationSlider.getValue()*1000 +1, simulationSlider.getValue()*1000 +1);
			}
		});
	}

	private void initLayout(){
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(saveBtn, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(loadSimulationBtn, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(remPlayerBtn, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(addPlayerBtn, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(enablePlayerBtn, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(disablePlayerBtn, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(totalExtractionsLbl)
									.addGap(218)
									.addComponent(simulationSlider, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(simulateExtBtn, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(quantitySimulateLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(quantitySimulateField, 0, 0, Short.MAX_VALUE)))
								.addComponent(innerPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(infoDevelopedByLbl, Alignment.TRAILING))
							.addGap(21))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(playersBtn, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(extractionsBtn, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(showStatsBtn, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(lastExtNumsLbl, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
							.addGap(433))))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(176)
					.addComponent(lastExtLbl, GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
					.addGap(175))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lastExtLbl, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(infoDevelopedByLbl))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(27)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(playersBtn, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(showStatsBtn, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addComponent(extractionsBtn, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lastExtNumsLbl, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(addPlayerBtn, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(remPlayerBtn, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(enablePlayerBtn, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(disablePlayerBtn, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 255, Short.MAX_VALUE)
							.addComponent(saveBtn, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addComponent(innerPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(quantitySimulateLbl)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(quantitySimulateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(simulationSlider, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(simulateExtBtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
									.addComponent(totalExtractionsLbl))))
						.addComponent(loadSimulationBtn, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
	}
}
