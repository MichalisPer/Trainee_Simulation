package com.sparta.skeleton.utilities.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

//Create and configure the Custom file handler
public class CustomFileHandler {
	public static FileHandler getFileHandler() {
		try {
			FileHandler fileHandler = new FileHandler("src/main/resources/TraineeSimulator.log", false);
			fileHandler.setLevel(Level.ALL);
			fileHandler.setFormatter(new CustomFormatter());
			return fileHandler;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
