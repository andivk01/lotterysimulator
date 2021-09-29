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
import java.util.ArrayList;
import java.util.List;

import com.gmail.andreaivkovic.utils.random.FilesRandom;

/**
 * @author Mellini Tancredi
 */

public class ExtractionsData implements Serializable {
	private static final long serialVersionUID = 1L;

	private int[] numbersHistory;		//vettore con le estrazioni rispetto ai numeri
	private int[] numbersQuote;			//vettore con la frequenza delle uscite dei numeri
	private int totalExtractions = 0;	//contatore delle estrazioni effettuate
	private List<Extraction> extractions = new ArrayList<>();
	private int n;   //numeri estratti es 5
	private int max; //numeri estraibili es 1-90
	private double win;
	private double lose;
	private int nBet;

	private FilesRandom filesRand;

	public ExtractionsData(int n, int max, double win, double lose, int nBet) {
		numbersHistory = new int[max];
		numbersQuote = new int[max];
		this.n = n;
		this.max = max;
		this.win = win;
		this.lose = lose;
		this.nBet = nBet;
		filesRand = new FilesRandom(0, max);
	}

	public void addExtraction() {
		extractions.add(new Extraction(generate(), extractions.size()+1));
		totalExtractions++;
		updateHistory();
	}

	//genera una nuova estrazione
	private int[] generate() {
		int[] currentEx = new int[n];

		for(int i=0; i<n; i++)      //mette tutto il vettore a -1 in modo da avere numeri non estraibili
			currentEx[i] = -1;

		for(int i=0; i<n; i++) {    //ciclo per generare i numeri estratti
			int random = 0;
			boolean check = true;
			while(check) {
				random = filesRand.nextInt();
				//ciclo per controllare gli altri valori dell'estrazione per evitare doppioni
				for(int j=0; j<n; j++) {
					if(currentEx[j] == random) {
						check = true;
						break;
					}
					else {
						check = false;
					}
				}
			}
			currentEx[i] = random;
		}
		return currentEx;
	}

	//aggiorna i dati per il numero ritardatario
	private void updateHistory() {
		for(int i=0; i<n; i++) {
			int num = extractions.get(extractions.size()-1).getValues()[i];
			numbersHistory[num] = totalExtractions;
			numbersQuote[num]++;
		}
	}

	/**Ritorna l'ultima Estrazione*/
	public Extraction getLastExtraction() {
		if(extractions.isEmpty()) return null;
		return extractions.get(extractions.size()-1);
	}

	public int[] getRetarded(int n){
		int[] buff = new int[numbersHistory.length];

		for(int i=0; i<numbersHistory.length; i++)
			buff[i] = numbersHistory[i];

		int[] ret = new int[n];

		for(int j=0; j<n; j++) {

			int min = Integer.MAX_VALUE;
			for(int i=0; i<buff.length; i++) {
				if((buff[i] < min) && (buff[i] >= 1)) {
					min = buff[i];
					ret[j] = i;
					buff[i] = Integer.MAX_VALUE;
				}
			}
		}
		return ret;
	}

	/**Ritorna il numero uscito + volte*/
	public int[] getQuoted(int n){
		int[] buff = new int[numbersQuote.length];

		for(int i=0; i<numbersQuote.length; i++)
			buff[i] = numbersQuote[i];

		int[] ret = new int[n];

		for(int j=0; j<n; j++) {

			int max = -1;
			for(int i=0; i<buff.length; i++) {
				if(buff[i] > max) {
					max = buff[i];
					ret[j] = i;
					buff[i] = -1;
				}
			}
		}
		return ret;
	}

	public int[] getNumbersQuote() {
		return numbersQuote;
	}

	public int getNumberExtrLessTimes(){
		int min = Integer.MAX_VALUE;
		int idx = 0;
		for(int i=0; i<numbersQuote.length; i++){
			if(min>numbersQuote[i] && numbersQuote[i]!=0) {
				min = numbersQuote[i];
				idx = i;
			}
		}
		return idx+1;
	}

	public int getNumberExtrMoreTimes(){
		int max = Integer.MIN_VALUE;
		int idx = 0;
		for(int i=0; i<numbersQuote.length; i++){
			if(max<numbersQuote[i]) {
				max = numbersQuote[i];
				idx = i;
			}
		}
		return idx+1;
	}

	/**Ritorna Max, il valore massimo puntabile*/
	public int getMax() {
		return max;
	}

	/**Ritorna N, il numero che definisce quanti numeri vengono estratti*/
	public int getN() {
		return n;
	}

	/**Ritorna il numero totale di estrazioni effettuate in Int*/
	public int getTotalExtractions() {
		return totalExtractions;
	}

	/**Ritorna l'arrayList con le estrazioni*/
	public List<Extraction> getExtractions() {
		return extractions;
	}

	public double getWin() {
		return win;
	}

	public double getLose() {
		return lose;
	}

	public int getnBet() {
		return nBet;
	}


}
