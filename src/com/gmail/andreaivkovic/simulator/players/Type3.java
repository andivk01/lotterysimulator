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

import com.gmail.andreaivkovic.simulator.ExtractionsData;
import com.gmail.andreaivkovic.utils.random.FilesRandom;

/**
 * @author Mellini Tancredi
 */

public class Type3 extends AbstractPlayer {
	private static final long serialVersionUID = 1L;

	private int[] fav;
	private FilesRandom filesRand;

	public Type3(ExtractionsData ed){
		this("unknown", "", ed, 10);
	}

	public Type3(String name, String surname, ExtractionsData ed, int storyOfPlayerEach) {
		super(name, surname, ed, storyOfPlayerEach);
		fav = new int[bet.length];
		filesRand = new FilesRandom(0, ed.getMax());

		this.type = PlayerType.T3;
	}

	public Type3(String name, double profit, double gain, double loss, int quantityBets) {
		super(name, profit, gain, loss, quantityBets);
	}

	/**Fa impostare i valori costante che il giocatore puntera*/
	public void setFav(int fav[]) {

		for(int i=0; i<bet.length; i++) {
			if(fav[i] < 0 || fav[i] >= 90) {
				this.fav[i] = filesRand.nextInt();
			}else{
				this.fav[i] = fav[i];
			}
		}

	}


	@Override
	public void play() {
		bet = fav;
		if(storyOfPlayerEach<=0) storyOfPlayerEach = 10;
		if((this.quantityBets % storyOfPlayerEach) == 0) {
			story.add(new Type3(name, this.profit, this.gain, this.loss, this.quantityBets));
		}
	}

}
