package com.nolancarbin.RestaurantFinderClient.view;

import com.nolancarbin.RestaurantFinderClient.model.Restaurant;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

//Class referenced from Tech Elevator's course material
public class ConsoleService {

    private PrintWriter out;
    private Scanner in;

    //Used to get search query string
    private static final String[] OPTIONS_FOR_MILES = {"5", "10", "15", "20"};
    private static final String[] OPTIONS_FOR_CATEGORIES = {
            "American", "AsianFusion", "Barbeque", "Breakfast_Brunch",
            "Chinese", "Indian", "Italian", "Japanese", "Korean", "Mediterranean", "Mexican",
            "MidEastern", "Noodles", "Pizza", "Sandwiches", "Seafood", "SoulFood",
            "Sushi", "Taiwanese", "Tex-Mex", "Thai", "Vegan", "Vegetarian"
    };
    private static final String[] PRICE_OPTIONS = {"$", "$$", "$$$", "$$$$"};

    public ConsoleService(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output, true);
        this.in = new Scanner(input);
    }

    public String promptUserForSearchQuery() {
        String location = getUserInput("Please enter a city");
        String radius = (String) getChoiceFromOptions(OPTIONS_FOR_MILES, "Within how many miles?");
        String categoriesCSV = getCSVFromOptions(OPTIONS_FOR_CATEGORIES, "Select which categories to search from:");
        String priceTag = (String) getChoiceFromOptions(PRICE_OPTIONS, "What price range?");
        String price = getPrice(PRICE_OPTIONS, priceTag);
        String searchQuery = String.format("location=%s&radius=%s&categories=%s&price=%s",
                location, radius, categoriesCSV, price);
        return searchQuery;
    }


    //Create a csv from options selected
    public String getCSVFromOptions(Object[] options, String prompt) {
        //while loop until the user clicks something that indicates they are done
        Object choice = null;
        String csv = "";
        while (true) {
            out.println();
            out.println(prompt);
            displayMenuOptions(options, "Enter 0 to be finished");
            choice = getChoiceFromUserInput(options);
            //short circuiting condition:
            if(choice != null && choice.equals("0")) {
                break;
            }
            if(csv.length() == 0 && choice != null) {
                csv += choice.toString();
            } else if (choice != null){
                csv += "," + choice.toString();
            }
        }
        return csv.toLowerCase();
    }
    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            //Don't need a exitMessage for getting only one choice.
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }


    public Object getChoiceFromOptions(Object[] options, String prompt) {
        Object choice = null;
        while (choice == null) {
            out.println(prompt);
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        if(userInput.equals("0")) {
            choice = userInput;
            return choice;
        }
        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return choice;
    }

    private void displayMenuOptions(Object[] options, String exitMessage) {
        for (int i = 0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        if(!exitMessage.equals("")) {
            out.println(exitMessage);
        }
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }

    private void displayMenuOptions(Object[] options) {
        for (int i = 0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }

    public String getUserInput(String prompt) {
        out.print(prompt + ": ");
        out.flush();
        return in.nextLine();
    }

    public Integer getUserInputInteger(String prompt) {
        Integer result = null;
        do {
            out.print(prompt+": ");
            out.flush();
            String userInput = in.nextLine();
            try {
                result = Integer.parseInt(userInput);
            } catch(NumberFormatException e) {
                out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
            }
        } while(result == null);
        return result;
    }

    public String getPrice(String[] prices, String price) {
        for (int i = 0; i < prices.length; i++) {
            if(price.equals(prices[i])) {
                return Integer.toString(i + 1);
            }
        }
        return null;
    }

    public void waitForEnterInput() {
        out.println("Press Enter to continue...");
        in.nextLine();
        out.flush();
    }

    public boolean getConfirmation() {
        out.print("Please enter Y/N to confirm: ");
        out.flush();
        String confirmation = in.nextLine();
        if(confirmation.equalsIgnoreCase("Y")) {
            out.println("Processing...");
            return true;
        } else {
            out.println("NOT CONFIRMED");
            return false;
        }
    }
}
