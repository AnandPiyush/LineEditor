package com.editor.lineeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LineEditor {

	public static void main(String[] args) {
		String fileName = args[0];
		System.out.println("File Name: " + fileName);
		List<String> textFileDataList = new ArrayList<>();
		boolean isFileAvailableAndReadable = false;
		try {
			File myObj = new File(fileName);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				textFileDataList.add(data);
			}
			myReader.close();
			isFileAvailableAndReadable = true;
		} catch (FileNotFoundException e) {
			System.out.println("File Not found or not accessible");
			e.printStackTrace();
			isFileAvailableAndReadable = false;
		}
		if (isFileAvailableAndReadable) {
			boolean isUserInputRequired = true;
			while (isUserInputRequired) {
				Scanner userInputObject = new Scanner(System.in);
				System.out.println("Enter Command >>");
				String userInput = userInputObject.nextLine();
				if (userInput.equals("list")) {
					int i = 1;
					for (String data : textFileDataList) {
						System.out.println(i + ":" + data);
						i++;
					}
					userInput = userInput.trim();
				} else if (userInput.startsWith("ins") && userInput.split(" ")[0].equals("ins")) {
					String[] dataValues = userInput.split(" ");
					int lineNumber = Integer.valueOf(dataValues[1]);
					if (lineNumber > textFileDataList.size()) {
						System.out.println("File lines number are limited to " + textFileDataList.size());
					} else {
						textFileDataList.add(lineNumber - 1, "New Line added");
						System.out.println("Line has been added successfully, use command list to see data");
					}
				} else if (userInput.startsWith("del") && userInput.split(" ")[0].equals("del")) {
					String[] dataValues = userInput.split(" ");
					int lineNumber = Integer.valueOf(dataValues[1]);
					if (lineNumber > textFileDataList.size()) {
						System.out.println("File lines number are limited to " + textFileDataList.size());
					} else {
						textFileDataList.remove(lineNumber - 1);
						System.out.println("Line has been deleted successfully, use command list to see data");
					}
				} else if (userInput.equals("save")) {
					final File file = new File(fileName);
					try {
						file.delete();
						file.createNewFile();
						final FileWriter fileWriter = new FileWriter(file);
						textFileDataList.forEach(data -> {
							try {
								fileWriter.write(data + System.lineSeparator());
							} catch (IOException e) {
								System.out.println("File has not been saved successfully, please try again");
								e.printStackTrace();
							}
						});
						System.out.println("File Saved Successfully. use command list to see data");
						fileWriter.close();
					} catch (IOException e) {
						System.out.println("File has not been saved successfully, please try again");
						e.printStackTrace();
					}
				} else if (userInput.equals("quit")) {
					userInputObject.close();
					isUserInputRequired = false;
					System.out.println("Thank You For using the Editor");
				} else if (userInput.equals("help")) {
					StringBuilder builder = new StringBuilder();
					builder.append("list  - list each line in n:xxx format");
					builder.append("\ndel n - delete line at n");
					builder.append("\nins n - insert line at n");
					builder.append("\nsave - saves to disk");
					builder.append("\nquit - quits the editor and returns to the command line");
					System.out.println(builder.toString());
				} else {
					System.out.println("No Command Found with " + userInput
							+ " string,  use command help to see available commands");
				}
			}
		}
	}
}
