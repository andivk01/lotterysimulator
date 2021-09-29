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
package com.gmail.andreaivkovic.simulator.players;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gmail.andreaivkovic.simulator.ExtractionsData;

/**
 * @author Mellini Tancredi
 */

public abstract class AbstractPlayer implements Serializable {
	private static final long serialVersionUID = 1L;

	protected boolean visibleOnCharts;
	protected ExtractionsData ed;
	protected PlayerType type;
	protected String name;
	protected String surname;
	protected boolean enabled;
	protected int[] bet;
	protected int max;
	protected int quantityBets;
	protected double profit;
	protected double gain;
	protected double loss;
	protected Color color;
	protected List<AbstractPlayer> story;

	protected int storyOfPlayerEach;

	public AbstractPlayer(String name, double profit, double gain, double loss, int quantityBets) {
		this.name = name;
		this.profit = profit;
		this.gain = gain;
		this.loss = loss;
		this.quantityBets = quantityBets;
	}

	public AbstractPlayer(String name, String surname, ExtractionsData ed, int storyOfPlayerEach){
		this.name = name;
		this.surname = surname;
		this.enabled = false;
		this.bet = new int[ed.getnBet()];
		this.profit = 0;
		this.gain = 0;
		this.loss = 0;
		this.ed = ed;
		this.max = ed.getMax();
		this.quantityBets = 0;
		story = new ArrayList<>();
		this.visibleOnCharts = true;
		this.storyOfPlayerEach = storyOfPlayerEach;
		color = randomColor();
	}

	private Color randomColor(){
		Random rand = new Random();
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);

		return new Color(r,g,b);
	}

	public void changeColor(){
		color = randomColor();
	}

	public Color getColor(){
		if(color == null) color = randomColor();
		return color;
	}

	/**Fa eseguire la puntata al Giocatore*/
	public abstract void play();

	/**Confronta il Giocatore con un altro oggetto Giocatore*/
	public boolean equals(AbstractPlayer player){
		if(!this.name.equalsIgnoreCase(player.name) || !this.surname.equalsIgnoreCase(player.surname)) return false;
		return true;
	}

	/*Dato l'array dell'estrazione controlla l'eventuale vincita e aggiorna profitto, vincita e perdita*/
	public void checkResults(int[] e) {
		boolean result = false;    //variabile di controllo

		for (int element : bet) {
			boolean check = false;
			for (int element2 : e) {
				if(element == element2) {
					check = true;
					break;
				}
			}
			if(check)
				result = true;
			else {
				result = false;
				break;
			}
		}

		if(result){
			profit += ed.getWin()-ed.getLose();
			gain += ed.getWin();
			gain = Math.round(gain*100.0)/100.0;
			loss -= ed.getLose();
			loss = Math.round(loss*100.0)/100.0;
		}else{
			profit -= ed.getLose();
			loss -= ed.getLose();
			loss = Math.round(loss*100.0)/100.0;
		}
		profit = Math.round(profit*100.0)/100.0;


		quantityBets++;
	}

	/**Abilita il Giocatore*/
	public void enable() {
		enabled = true;
	}

	/**Disabilita il Giocatore*/
	public void disable() {
		enabled = false;
	}

	/**Ritorna lastBet in Int (-1)*/
	public int[] getLastBet(){return bet;}

	/**Ritorna il valore effettivamente puntato dall'utente (+1)*/
	public int[] getLastBetReal() {
		int[] ret = new int[bet.length];
		for(int i=0; i<ret.length; i++)
			ret[i] = (bet[i] + 1);
		return ret;
	}

	/**Ritorna una Stringa con il Nome del Giocatore*/
	public String getName(){return name;}

	/**Ritorna una Stringa con il Cognome del Giocatore*/
	public String getSurname(){return surname;}

	/**Ritorna True se il Giocatore � abilitato, False se il giocatore � disabilitato*/
	public boolean isEnabled(){return enabled;}

	/**Ritorna il numero di estrazioni effettuate dal Giocatore in Int*/
	public int getQuantityBets(){return quantityBets;}

	/**Ritorna il Profitto del Giocatore (guadagno+Perdita) in Int*/
	public double getProfit(){return profit;}

	/**Ritorna il Guadagno del Giocatore fino ad ora in Int*/
	public double getGain(){return gain;}

	/**Ritorna la Perdita del giocatore fino ad ora in Int*/
	public double getLoss(){return loss;}

	/**Ritorna l'enum tipo*/
	public PlayerType getType() {
		return type;
	}

	public ExtractionsData getExtractionsData() {
		return ed;
	}

	/**Ritorna il Tipo del Giocatore*/
	public String getTitle() {
		return this.type.getTitle();
	}

	public boolean isVisibleOnCharts() {
		return visibleOnCharts;
	}

	public void setVisibleOnCharts(boolean visibleOnCharts) {
		this.visibleOnCharts = visibleOnCharts;
	}

	/**Ritorna la Descrizione del Giocatore*/
	public String getDescription() {
		return type.getDescription();
	}

	public List<AbstractPlayer> getStory() {
		return story;
	}

	public int getStoryOfPlayerEach() {
		return storyOfPlayerEach;
	}

	@Override
	public String toString(){
		StringBuilder retValue = new StringBuilder();
		retValue.append(name);
		retValue.append("\t");
		retValue.append(gain);
		retValue.append("\t");
		retValue.append(loss);
		retValue.append("\t");
		retValue.append(profit);
		retValue.append("\t");
		retValue.append(quantityBets);
		return retValue.toString();
	}

}
