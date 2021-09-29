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
package com.gmail.andreaivkovic.simulator;
import java.io.Serializable;
import  java.util.ArrayList;
import  java.util.List;

import com.gmail.andreaivkovic.simulator.players.AbstractPlayer;

/**
 * @author Mellini Tancredi
 */

public class Lottery implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<AbstractPlayer> players;
	private ExtractionsData ed;
	private int saveStoryOfPlayerEach;

	public Lottery() {
		this(5, 90, 17, 1, 1);
	}

	public Lottery(int n, int max, double win, double lose, int nBet) {
		players = new ArrayList<>();
		ed = new ExtractionsData(n, max, win, lose, nBet);
		this.saveStoryOfPlayerEach = 10;
	}

	/**Genera fa puntare i giocatori abilitati, genera l'estrazione e controlla i relativi risultati*/
	public void extract() {
		for(AbstractPlayer thisPlayer : players) {
			if(thisPlayer.isEnabled()) {
				thisPlayer.play();
			}
		}

		//estrazione dei numeri
		ed.addExtraction();

		//confronto per le vincite
		for(AbstractPlayer thisPlayer : players) {
			if(thisPlayer.isEnabled()) {
				thisPlayer.checkResults(ed.getLastExtraction().getValues());
			}
		}
	}

	public double getProbability() {
		int n = (ed.getMax()-ed.getnBet());
		int k = ed.getN() - ed.getnBet();
		int fav = Utils.combination(n, k, false).intValue();
		int poss = Utils.combination(ed.getMax(), ed.getN(), false).intValue();
		double ret = ((double)fav)/((double)poss);
		return ret;
	}

	public boolean isFair() {
		double fairWin = 0;
		fairWin = (ed.getLose()/getProbability());
		double diff = Math.abs(fairWin - ed.getWin());
		if(diff < 0.5)
			return true;
		else
			return false;
	}

	/**Ritorna l'arrayList coi giocatori*/
	public List<AbstractPlayer> getPlayers() {
		return players;
	}

	public AbstractPlayer getPlayerHighestProfit(){
		double max = Integer.MIN_VALUE;
		AbstractPlayer retValue = null;
		for(AbstractPlayer item : players){
			if(item.getProfit() > max){
				max = item.getProfit();
				retValue = item;
			}
		}
		return retValue;
	}

	public AbstractPlayer getPlayerLesserProfit(){
		double min = Integer.MAX_VALUE;
		AbstractPlayer retValue = null;
		for(AbstractPlayer item : players){
			if(item.getProfit() < min){
				min = item.getProfit();
				retValue = item;
			}
		}
		return retValue;
	}

	public double maxProfitDifference(){
		return getPlayerHighestProfit().getProfit() - getPlayerLesserProfit().getProfit();
	}

	/**Ritorna l'oggetto ExtractionsData*/
	public ExtractionsData getExtractionsData() {
		return ed;
	}

	public int getSaveStoryOfPlayerEach() {
		return saveStoryOfPlayerEach;
	}

	public void setSaveStoryOfPlayerEach(int saveStoryOfPlayerEach) {
		this.saveStoryOfPlayerEach = saveStoryOfPlayerEach;
	}

}
