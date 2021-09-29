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

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.ImageIcon;

import com.gmail.andreaivkovic.graphics.main.MainPanel;

/**
 * @author Ivkovic Andrea
 */

public class AppUIManager {
	private static Properties props;
	private static final String CONFIG_FILE = "/com/gmail/andreaivkovic/graphics/config/configuration";
	private static HashMap<String, ImageIcon> imageIcons;

	public static void init(){
		props = new Properties();
		imageIcons = new HashMap<>();
		InputStream is = AppUIManager.class.getResourceAsStream(CONFIG_FILE);
		try {
			props.load(is);
		} catch (IOException e) {e.printStackTrace();}
		try {
			is.close();
		} catch (IOException e) {e.printStackTrace();}

	}

	public static Object get(String key, Class<?> retClass){
		Object retValue = null;
		if(retClass.equals(Color.class)){
			String[] value = props.getProperty(key, "0,0,0").split(",");
			retValue = new Color(
				Integer.parseInt(value[0]),
				Integer.parseInt(value[1]),
				Integer.parseInt(value[2])
			);
		}
		if(retClass.equals(String.class)){
			return props.getProperty(key, "");
		}

		if(retClass.equals(Font.class)) {
			String[] value = props.getProperty(key, "0,0,0").split(",");
			retValue = new Font(
				value[0],
				Integer.parseInt(value[1]),
				Integer.parseInt(value[2])
			);
		}

		if(retClass.equals(ImageIcon.class)){
			if(imageIcons.containsKey(key)) {
				retValue = imageIcons.get(key);
			}else{
				String value = props.getProperty(key, "0");
				Image image = Toolkit.getDefaultToolkit().createImage(MainPanel.class.getResource(value));
				retValue = new ImageIcon(image);
				imageIcons.put(key, (ImageIcon)retValue);
			}

		}

		if(retClass.equals(int.class)){
			String value = props.getProperty(key, "0");
			try{
				retValue = new Integer(value);
			}catch(NumberFormatException exc){
				exc.printStackTrace();
			}
		}

		return retValue;
	}

}
