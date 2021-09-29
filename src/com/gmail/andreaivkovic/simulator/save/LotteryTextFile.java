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
package com.gmail.andreaivkovic.simulator.save;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import com.gmail.andreaivkovic.main.Language;
import com.gmail.andreaivkovic.simulator.Extraction;
import com.gmail.andreaivkovic.simulator.Lottery;
import com.gmail.andreaivkovic.simulator.players.AbstractPlayer;

/**
 * @author Ivkovic Andrea
 */

public class LotteryTextFile {

	private Lottery lottery;

	public LotteryTextFile(Lottery lottery) {
		this.lottery = lottery;
	}

	public boolean write(String destination, String extension){
		LocalDateTime date = LocalDateTime.now();
		destination += " " + date.getHour() + "-" + date.getMinute() + "-" + date.getSecond();
		StringBuilder outString = new StringBuilder("");

		outString.append(Language.get("playersState") + ":\n");
		outString.append(Language.get("name") + "\t" + Language.get("gain") + "\t" + Language.get("loss") + "\t" + Language.get("profit") + "\t");
		outString.append(Language.get("quantityOfBets") + "\n");

		boolean needNewLine = false;
		for(int i=0; i<lottery.getExtractionsData().getTotalExtractions(); i++){
			for(AbstractPlayer item : lottery.getPlayers()){
				if(item.getStory().size() > i) {
					outString.append(item.getStory().get(i).toString() + "\n");
					needNewLine = true;
				}else{
					needNewLine = false;
				}
			}
			if(needNewLine) outString.append("\n");
		}

		outString.append("\n" + Language.get("extractions").toUpperCase() + "\n");

		for(Extraction thisExtraction : lottery.getExtractionsData().getExtractions()){
			for(int thisValue : thisExtraction.getValues()){
				thisValue++;
				outString.append(thisValue + "\t");
			}
			outString.append("\n");
		}

		try(BufferedWriter writer = new BufferedWriter(new FileWriter(destination + extension, false))){
			writer.write(outString.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
