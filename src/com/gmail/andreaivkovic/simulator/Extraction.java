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

/**
 * @author Mellini Tancredi
 */

public class Extraction implements Serializable {
	private static final long serialVersionUID = 1L;

	private int[] extraction;
	private int id;

	public Extraction(int[] extraction, int id) {
		this.extraction = extraction;
		this.id = id;
	}

	public int getId(){
		return id;
	}


	/**Ritorna l'Array coi numeri dell'estrazione*/
	public int[] getValues() {
		return extraction;
	}

	/**Restiuisce una Stringa coi valori dell'estrazione*/
	@Override
	public String toString() {
		String ret = "";
		for(int i : extraction)
			ret += ((i+1) + "\t ");
		return ret;
	}


}
