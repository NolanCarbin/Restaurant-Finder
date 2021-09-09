package com.nolancarbin.RestaurantFinderClient;

import com.nolancarbin.RestaurantFinderClient.model.Restaurant;
import com.nolancarbin.RestaurantFinderClient.services.RestaurantService;
import com.nolancarbin.RestaurantFinderClient.view.ConsoleService;

import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ApplicationCLI {

    private final ConsoleService console;
    private final RestaurantService restaurantService;
    private final RestTemplate restTemplate;

    private static final String MAIN_MENU_OPTION_1 = "Search for Restaurant";
    private static final String MAIN_MENU_OPTION_2 = "View saved Restaurants";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_1, MAIN_MENU_OPTION_2, MAIN_MENU_OPTION_EXIT};

    public ApplicationCLI(ConsoleService consoleService) {
        this.console = consoleService;
        this.restTemplate = new RestTemplate();
        this.restaurantService = new RestaurantService(this.restTemplate);
    }

    public static void main(String[] args) {
        ConsoleService consoleService = new ConsoleService(System.in, System.out);
        ApplicationCLI cli = new ApplicationCLI(consoleService);
        cli.run();
    }

    public void run() {
        while(true) {
            String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS, "Main Menu");
            if(choice.equals(MAIN_MENU_OPTION_1)) {
                String searchQuery = console.promptUserForSearchQuery();
                System.out.println(searchQuery);
                List<Restaurant> restaurants = restaurantService.searchForRestaurants(searchQuery);
                for(Restaurant restaurant : restaurants) {
                    restaurantService.displayRestaurant(restaurant);
                    String userInput = (String) console.getChoiceFromOptions(new Object[]{"NEXT", "SAVE", "EXIT"});
                    if(userInput.equalsIgnoreCase("next")) {
                        System.out.println("Printing Next...");;
                    } else if (userInput.equalsIgnoreCase("save")) {
                        boolean saved = restaurantService.saveRestaurant(restaurant);
                        if(saved) {
                            System.out.println(restaurant.getName() + " was SAVED to the Database!");
                        } else {
                            System.out.println(restaurant.getName() + " was NOT SAVED");
                        }
                    } else if(userInput.equalsIgnoreCase("exit")) {
                        break;
                    }
                }


            } else if(choice.equals(MAIN_MENU_OPTION_2)) {
                restaurantService.getSavedRestaurants();
                restaurantService.displaySavedRestaurantsIdsAndNames();
                String userInput = (String) console.getChoiceFromOptions(new Object[]{"Show details", "Delete saved restaurant", "Return to main menu"});
                if (userInput.equalsIgnoreCase("show details")) {
                    int selectedId = console.getUserInputInteger("Please enter the ID: ");
                    restaurantService.displaySavedRestaurantById(selectedId);
                    console.waitForEnterInput();
                } else if (userInput.equalsIgnoreCase("delete saved restaurant")) {
                    int selectedId = console.getUserInputInteger("Please enter the ID: ");
                    if(console.getConfirmation()) {
                        boolean removed = restaurantService.deleteSavedRestaurant(selectedId);
                        if(removed) {
                            System.out.println(restaurantService.getSavedRestaurantNameById(selectedId) + " was successfully removed");
                        } else {
                            System.out.println("Something went wrong, restaurant was not removed");
                        }
                    }
                }

            } else {
                System.out.println("Goodbye");
                break;
            }
        }
    }
}
