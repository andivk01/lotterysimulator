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

/**
 * @author Mellini Tancredi
 */

public class Type5 extends AbstractPlayer {
	private static final long serialVersionUID = 1L;

	public Type5(ExtractionsData ed){
		this("unknown", "", ed, 10);
	}

	public Type5(String name, String surname, ExtractionsData ed, int storyOfPlayerEach) {
		super(name, surname, ed, storyOfPlayerEach);
		this.type = PlayerType.T5;
	}

	public Type5(String name, double profit, double gain, double loss, int quantityBets) {
		super(name, profit, gain, loss, quantityBets);
	}

	@Override
	public void play() {
		this.bet = ed.getQuoted(bet.length);

		if(storyOfPlayerEach<=0) storyOfPlayerEach = 10;
		if((this.quantityBets % storyOfPlayerEach) == 0) {
			story.add(new Type5(name, this.profit, this.gain, this.loss, this.quantityBets));
		}

	}

}
