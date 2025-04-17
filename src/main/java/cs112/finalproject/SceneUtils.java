package cs112.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.Scanner;

public class SceneUtils {
    public static final double PREFERRED_LABELED_WIDTH = 150;
    public static final double PREFERRED_LABELED_HEIGHT = 30;

    public static void standardise(Labeled labelOrButton) {
        labelOrButton.setPrefSize(PREFERRED_LABELED_WIDTH, PREFERRED_LABELED_HEIGHT);
        labelOrButton.setAlignment(Pos.CENTER);
    }

    private static void initButton(Button button, EventHandler<ActionEvent> event) {
        standardise(button);
        button.setOnAction(event);
    }

    public static Button newButton(String label, EventHandler<ActionEvent> event) {
        Button retval = new Button(label);
        retval.setOnAction(event);
        standardise(retval);
        return retval;
    }

    public static Scanner scanner = new Scanner(System.in); // for user input
    public static final int FIXED_STR_LENGTH = 41; // standardised length for menus

    /**
     * Shorthand for calling Scanner.nextLine() with a prompt beforehand
     * @return String representing user console input
     */
    public static String getInput(String prompt)
    {
        System.out.print(prompt);
        return getInput();
    }

    /**
     * Shorthand for calling Scanner.nextLine()
     * @return String representing user console input
     */
    public static String getInput()
    {
        return scanner.nextLine();
    }

    /**
     * Formats the given String to contain padding at the front and end in order to achieve a String matching the given length.
     * Uses a predetermined length, use centreString(String, int) to specify own length.
     * @param str String to centre
     * @return String of given length formatted to appear centred 
     */
    public static String centreString(String str)
    {
        return centreString(str, FIXED_STR_LENGTH);
    }

    /**
     * Formats the given String to contain padding at the front and end in order to achieve a String matching the given length
     * @param str String to centre
     * @param length int representing how long the formatted String should be
     * @return String of given length formatted to appear centred 
     */
    public static String centreString(String str, int length)
    {
        int leftPadding = (length - str.length()) / 2;
        int rightPadding = (length - str.length()) - leftPadding;
        String leftCode = "%";
        String rightCode = "%";
        if (leftPadding > 0)
        {
            leftCode += leftPadding;
        }
        if (rightPadding > 0)
        {
            rightCode += rightPadding;
        }
        leftCode += "s";
        rightCode += "s";
        return String.format(leftCode + "%s" + rightCode, "", str, "");
    }

    /**
     * Formats the given String to be centred and of a given length then prints the result to the console.
     * Uses a predetermined length, use printCentred(String, int) to specify own length.
     * @param str String to format then print
     */
    public static void printCentred(String str)
    {
        printCentred(str, FIXED_STR_LENGTH);
    }

    /**
     * Formats the given String to be centred and of a given length then prints the result to the console
     * @param str String to format then print
     * @param strLength how long the final String should be
     */
    public static void printCentred(String str, int strLength)
    {
        System.out.println(centreString(str, strLength));
    }
    
    /**
     * Formats then prints the given String to appear as centred text between two '|' characters.
     * Uses a predetermined length, use printCentredBox(String, int) to specify own length.
     * @param str String to format and print
     */
    public static void printCentredBox(String str)
    {
        printCentredBox(str, FIXED_STR_LENGTH);
    }

    /**
     * Formats then prints the given String to appear as centred text between two '|' characters.
     * @param str String to format and print
     * @param strLength int representing how long the final String should be
     */
    public static void printCentredBox(String str, int strLength)
    {
        System.out.println("|" + centreString(str, strLength - 2) + "|");
    }

    /**
     * Prints a String composed entirely of a single character to the console.
     * Uses a predetermined length, use printLine(String, int) to specify own length.
     * @param key char the String should be composed
     */
    public static void printLine(char key)
    {
        printLine(key, FIXED_STR_LENGTH);
    }

    /**
     * Prints a String composed entirely of a single character to the console
     * @param key char the String should be composed
     * @param length how long the String should be
     */
    public static void printLine(char key, int length)
    {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            build.append(key);
        }
        System.out.println(build.toString());
    }

    /**
     * Tries to parse the given String to an int. Will loop until a valid String is given.
     * @param str String to parse
     * @param minVal int representing min acceptable value, exclusive
     * @param maxVal int representing max acceptable value, inclusive
     * @return int from the given String
     */
//    public static int parseInt(String str, int minVal, int maxVal)
//    {
//        int retval;
//        try
//        {
//            retval = Integer.parseInt(str);
//            if (retval < minVal || retval > maxVal)
//            {
//                System.out.println(String.format("Value is out of bounds: Acceptable range is: [%d,%d] inclusive", minVal, maxVal));
//                return parseInt(ConsoleUtils.getInput(">> "), minVal, maxVal);
//            }
//            return retval;
//        }
//        catch (NumberFormatException e)
//        {
//            System.out.println("Input cannot be parsed. Please type an integer.");
//            return parseInt(ConsoleUtils.getInput(">> "), minVal, maxVal);
//        }
//    }

    /**
     * Gets user console input as an integer between [min, max]. Repeats until getting valid input.
     * @param prompt String to print before getting user input
     * @param min lowest acceptable value
     * @param max highest acceptable value
     * @return user input as an int
     */
//    public static int getInt(String prompt, int min, int max)
//    {
//        String str = getInput(prompt);
//        return parseInt(str, min, max);
//    }

    /**
     * Gets user console input as an integer between [min, max]. Repeats until getting valid input.
     * @param min lowest acceptable value
     * @param max highest acceptable value
     * @return user input as an int
     */
//    public static int getInt(int min, int max)
//    {
//        String str = getInput();
//        return parseInt(str, min, max);
//    }
}
