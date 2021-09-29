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

import  java.util.Random;

import com.gmail.andreaivkovic.simulator.Extraction;
import com.gmail.andreaivkovic.simulator.ExtractionsData;
import com.gmail.andreaivkovic.utils.random.FilesRandom;

/**
 * @author Mellini Tancredi
 */

public class Type4 extends AbstractPlayer {
	private static final long serialVersionUID = 1L;
	private FilesRandom filesRand;

	public Type4(ExtractionsData ed){
		this("unknown", "", ed, 10);
	}

	public Type4(String name, String surname, ExtractionsData ed, int storyOfPlayerEach) {
		super(name, surname, ed, storyOfPlayerEach);
		this.type = PlayerType.T4;
		filesRand = new FilesRandom(0, ed.getMax());
	}


	public Type4(String name, double profit, double gain, double loss, int quantityBets) {
		super(name, profit, gain, loss, quantityBets);
	}

	public void play() {
		//int newBet = rand.nextInt(5);
		Extraction lastExt = ed.getLastExtraction();

		if(lastExt != null){
			Random r = new Random();
			int[] last = ed.getLastExtraction().getValues();
			int[] buffLast = new int[last.length];
			for(int i=0; i<last.length; i++){
				buffLast[i] = last[i];
			}

			for(int i=0; i<bet.length; i++) {
				int idx = r.nextInt(buffLast.length);

				while(buffLast[idx] == -1)
					idx = r.nextInt(buffLast.length);

				bet[i] = buffLast[idx];
				buffLast[idx] = -1;
			}
		}else{
			for(int i=0; i<bet.length; i++)
				bet[i] = filesRand.nextInt();
		}
		if(storyOfPlayerEach<=0) storyOfPlayerEach = 10;
		if((this.quantityBets % storyOfPlayerEach) == 0) {
			story.add(new Type4(name, this.profit, this.gain, this.loss, this.quantityBets));
		}
	}

}
