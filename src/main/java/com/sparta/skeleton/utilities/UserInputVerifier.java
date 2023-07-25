package com.sparta.skeleton.utilities;

import com.sparta.skeleton.view.DisplayManager;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * <pre>
 * <b>File name:</b> UserInputVerifier.java <br>
 * <b>Purpose:</b> The UserInputVerifier class provides utility methods
 * to verify and retrieve user input from the standard input stream.
 * The methods in this class utilize regular expression patterns for
 * input validation and handle user input exceptions appropriately.
 * </pre>
 *
 * @author Perikleous Michalis
 * @version 1.0.0
 */
public class UserInputVerifier {

    /**
     * This method reads user input from the standard input stream
     * and retrieves either a number of years or a filename from the console.
     * The method uses a regular expression pattern to validate the input.
     *
     * @param in the Scanner object representing the standard input stream,
     *           used to read user input from the console.
     * @return a String representing either the number of years or the filename, as provided by the user.
     */
    public static String getYearOrFile(Scanner in) {
        String yearOrFile; // stores the filename or number of years given by the user
        do {
            Pattern pattern = Pattern.compile("[0-9]+|.*.txt");
            try {
                yearOrFile = in.next(pattern);
                break;
            } catch (InputMismatchException e) {
                DisplayManager.printWrongInputMessage(in.next());
                in.nextLine();
            }
        } while (true);
        return yearOrFile;
    }

    /**
     * This method prompts the user to select their preferred frequency
     * for displaying output and retrieves the user's choice from the standard input stream.
     * <p>
     * The user can choose one of the following options:
     * <p>
     * - 'y': Yearly Output: The output will be displayed on a yearly basis.<br>
     * - 'm': Monthly Output: The output will be displayed on a monthly basis.<br>
     * - 'f': Final Output Only: The output will be displayed only after the final result is computed.<br>
     * <p>
     * The method uses a regular expression pattern to validate the user's input
     * and ensure it matches one of the valid frequency options ('y', 'm', or 'f').
     *
     * @param in the Scanner object representing the standard input stream, used to read the user's choice from the console.
     * @return a String representing the user's preferred frequency for displaying output, which can be one of the following: 'y' for yearly output, 'm' for monthly output, or 'f' for final output only.
     */
    public static String getOutputFrequency(Scanner in) {
        String outputFrequency; // stores the preference of user for frequency of displaying output
        DisplayManager.displayOutputOptions();
        do { // Do-While loop to verify the user input for outputFrequency
            try {
                Pattern pattern = Pattern.compile("[ymf]");
                outputFrequency = in.next(pattern);
                break;
            } catch (InputMismatchException e) {
                DisplayManager.printWrongFrequencyInput(in.next());
                in.nextLine();
            }
        } while (true);
        return outputFrequency;
    }

    /**
     * This method checks whether the given input String represents a valid number of years or not.
     *
     * @param yearOrFile the input String to be checked, which can be either a number of years or a filename.
     * @return true if the input String represents a valid number of years, false otherwise.
     */
    public static boolean isNumOfYears(String yearOrFile) {
        boolean isNumOfYears; // checks if the user input is number of years (true) or filename (false)

        Pattern pattern = Pattern.compile("[0-9]+");
        isNumOfYears = pattern.matcher(yearOrFile).matches();
        return isNumOfYears;
    }
}
