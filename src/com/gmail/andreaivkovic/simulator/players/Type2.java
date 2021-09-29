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

public class Type2 extends AbstractPlayer {
	private static final long serialVersionUID = 1L;

	private FilesRandom filesRand;

	public Type2(ExtractionsData ed){
		this("unknown", "", ed, 10);
	}

	public Type2(String name, String surname, ExtractionsData ed, int storyOfPlayerEach) {
		super(name, surname, ed, storyOfPlayerEach);
		this.type = PlayerType.T2;
		filesRand = new FilesRandom(0, ed.getMax());
	}

	public Type2(String name, double profit, double gain, double loss, int quantityBets) {
		super(name, profit, gain, loss, quantityBets);
	}

	@Override
	public void play() {
		for(int i=0; i<bet.length; i++) {    //ciclo per generare i numeri estratti
			int random = 0;
			boolean check = true;
			while(check) {
				random = filesRand.nextInt();
				//ciclo per controllare gli altri valori dell'estrazione per evitare doppioni
				for(int j=0; j<bet.length; j++) {
					if(bet[j] == random) {
						check = true;
						break;
					}
					else {
						check = false;
					}
				}
			}
			bet[i] = random;
		}
		if(storyOfPlayerEach<=0) storyOfPlayerEach = 10;
		if((this.quantityBets % storyOfPlayerEach) == 0) {
			story.add(new Type2(name, this.profit, this.gain, this.loss, this.quantityBets));
		}
	}

}
