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

import com.gmail.andreaivkovic.main.Language;

/**
 * @author Mellini Tancredi
 */

public enum PlayerType {
	T1(Language.get("player_type1"), Language.get("playLateComerNumber")),
	T2(Language.get("player_type2"), Language.get("playCasualNumber")),
	T3(Language.get("player_type3"), Language.get("playAlwaysSameNumber")),
	T4(Language.get("player_type4"), Language.get("playPrecedentNumber")),
	T5(Language.get("player_type5"), Language.get("playFrequentNumber"));

	private String title;
	private String description;

	private PlayerType(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
}
