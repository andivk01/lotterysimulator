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

import java.math.BigInteger;

/**
 * @author Ivkovic Andrea
 */

public abstract class Utils {

    public static BigInteger combination(int num, int k, boolean rip){
        if(rip){
        	num += k-1;
        }

        BigInteger numeratore = fact(BigInteger.valueOf(num));

        BigInteger den1 = fact(BigInteger.valueOf(k));
        BigInteger den2 = fact(BigInteger.valueOf(num-k));


        BigInteger denominatore = den1.multiply(den2);

        return numeratore.divide(denominatore);
    }


    public static BigInteger esegDisp(int num, int k, boolean rip){
        if(rip){
            BigInteger numeratore = BigInteger.valueOf(num);
            return numeratore.pow(k);
        }

        BigInteger numeratore = fact(BigInteger.valueOf(num));

        BigInteger denominatore = fact(BigInteger.valueOf(num-k));

        return numeratore.divide(denominatore);
    }

    public static BigInteger esegPerm(int num, int k, boolean rip){

        if(rip){
            BigInteger numeratore = fact(BigInteger.valueOf(num));
            BigInteger denominatore = fact(BigInteger.valueOf(k));

            return numeratore.divide(denominatore);
        }

        return fact(BigInteger.valueOf(num));
    }

    public static BigInteger fact(BigInteger num){
        BigInteger ris = BigInteger.ONE;

        if(num.compareTo(BigInteger.ZERO)==0) return BigInteger.ONE;

        for(BigInteger i = BigInteger.ONE; i.compareTo(num)!=0; i=i.add(BigInteger.ONE)){
            ris = ris.multiply(i);
        }

        ris = ris.multiply(num);

        return ris;
    }

}

