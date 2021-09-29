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
package com.gmail.andreaivkovic.utils.random;

import java.io.BufferedInputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Ivkovic Andrea
 */

public class FilesRandom implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final int FILES_QUANTITY = 13;
	private static final String FILES_NAME = "/com/gmail/andreaivkovic/utils/random/files/rand";
	private static Random rand;
	private transient List<Integer> randomValues;
	private static List<Integer> filesAlreadyUsed;
	private int posIdx;
	private int min;
	private int max;
	private int currentFileNumber;

	/**min inclusive, max exclusive*/
	public FilesRandom(int min, int max){
		this.min = min;
		this.max = max;
		rand = new SecureRandom();
		randomValues = new ArrayList<>();
		if(filesAlreadyUsed == null) filesAlreadyUsed = new ArrayList<>();
		loadNextFile();
	}


	public int nextInt(){
		if(randomValues == null){
			randomValues = new ArrayList<>();
			loadNextFile();
		}
		int retValue = randomValues.get(posIdx);
		if(posIdx >= randomValues.size()-1){
			if(filesAlreadyUsed.contains(currentFileNumber)){
				filesAlreadyUsed.remove((Integer)currentFileNumber);
			}
			loadNextFile();
			posIdx = 0;
		}
		posIdx++;

		return retValue;
	}

	private void loadNextFile(){
		int nFile = rand.nextInt(FILES_QUANTITY)+1;
		while(countAllMatches(nFile) > 0 && filesAlreadyUsed.size() < FILES_QUANTITY){
			nFile = rand.nextInt(FILES_QUANTITY)+1;
		}
		currentFileNumber = nFile;
		filesAlreadyUsed.add(nFile);

		String absFileName = FILES_NAME + nFile;
		try(
			BufferedInputStream inStream = new BufferedInputStream(FilesRandom.class.getResourceAsStream(absFileName));
		){
			int buffer;
			randomValues.clear();
			while((buffer = inStream.read()) != -1){
				buffer = Math.abs(buffer);
				if(buffer >= min && buffer < max){
					randomValues.add(buffer);
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}

	private static int countAllMatches(int number){
		int count = 0;
		for(int item : filesAlreadyUsed){
			if(item == number) count++;
		}
		return count;
	}

}
