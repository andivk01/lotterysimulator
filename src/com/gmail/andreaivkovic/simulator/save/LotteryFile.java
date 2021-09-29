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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

import com.gmail.andreaivkovic.simulator.Lottery;

/**
 * @author Ivkovic Andrea
 */

public class LotteryFile {

	private Lottery lottery;

	public LotteryFile() {}

	public LotteryFile(Lottery lottery) {
		this.lottery = lottery;
	}

	public boolean write(String destination, String extension){
		if(lottery == null) return false;
		LocalDateTime date = LocalDateTime.now();
		destination += " " + date.getHour() + "-" + date.getMinute() + "-" + date.getSecond();

		try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(destination + extension))){
			writer.writeObject(lottery);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public Lottery read(File source){
		Lottery lottery = null;
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(source))){
			lottery = (Lottery) reader.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lottery;
	}

}
