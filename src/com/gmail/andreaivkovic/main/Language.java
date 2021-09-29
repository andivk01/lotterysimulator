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
package com.gmail.andreaivkovic.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @author Ivkovic Andrea
 */

public class Language {
	private static Properties values;

	public enum LangId{
		IT("it", "/com/gmail/andreaivkovic/graphics/config/lang_it"),
		EN("en", "/com/gmail/andreaivkovic/graphics/config/lang_en"),
		ES("es", "/com/gmail/andreaivkovic/graphics/config/lang_es"),
		FR("fr", "/com/gmail/andreaivkovic/graphics/config/lang_fr"),
		DE("de", "/com/gmail/andreaivkovic/graphics/config/lang_de");

		private String desc;
		private String filePath;

		private LangId(String desc, String filePath){
			this.desc = desc;
			this.filePath = filePath;
		}

		public static LangId getByDesc(String desc) {
			for(LangId item : LangId.values()){
				if(item.desc.equalsIgnoreCase(desc)) return item;
			}
			return null;
		}

	}

	public static void init(LangId lang){
		values = new Properties();
		InputStream is = AppUIManager.class.getResourceAsStream(lang.filePath);
		Reader reader = null;
		try {
			reader = new InputStreamReader(is, "UTF-8");
		} catch (UnsupportedEncodingException e1) {e1.printStackTrace();}
		try {
			values.load(reader);
		} catch (IOException e) {e.printStackTrace();}
		try {
			is.close();
		} catch (IOException e) {e.printStackTrace();}
	}

	public static void init(){
		init(LangId.EN);
	}

	public static String get(String key){
		return values.getProperty(key, "");
	}

}
