import java.util.*;
import javax.swing.*;


public class StaticDrinkServing extends DynamicDrinkServing {
    static Scanner scanner = new Scanner(System.in);
    static RestrictedSpot[] spots = new RestrictedSpot[5];

    //To check if robot is maintaing safe distance from its surroundings
    static void proceedDynamicCheck(String robotId, String name, RestrictedSpot spot) {
        try {  //using exception to ensure all the inputs are numeric
            double left = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter distance from LEFT (meters):"));
            double right = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter distance from RIGHT (meters):"));
            double front = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter distance from FRONT (meters):"));
            double back = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter distance from BACK (meters):"));

            DynamicDrinkServing checker = new DynamicDrinkServing();
            boolean result = checker.checkDistance(left, right, front, back);

            JOptionPane.showMessageDialog(null, result);

            if (!result) {
                String report = "\n ALERT: You are in CASUAL CONTACT!\n\n" +
                        "Robot ID     : " + robotId + "\n" +
                        "Full Name    : " + name + "\n" +
                        "Spot         : [" + spot.spotId + "] " + spot.spotName + "\n" +
                        "Date         : " + java.time.LocalDate.now() + "\n" +
                        "Time         : " + java.time.LocalTime.now().withNano(0) + "\n" +
                        "Contact Status: Casual Contact";
                JOptionPane.showMessageDialog(null, report);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, " Invalid input. Please enter valid numbers.");
        }
    }

    //It simulates a robot getting closer to customer (reducing the distance percentage over time) and notifies when it finally delivers a drink.
    static void robotdistance() {
        
            Random rand = new Random();
            int percentage = 100;
            StringBuilder status = new StringBuilder();

            while (percentage > 0) {
                int decrease = rand.nextInt(30) + 1;
                percentage -= decrease;
                if (percentage < 0) percentage = 0;
                status.append("The robot is ").append(percentage).append("% from you\n");
            }

            status.append("The robot is ").append(percentage).append("% away. Here is your drink. Enjoy!");
            JOptionPane.showMessageDialog(null, status.toString());
        
    }
  
    //Initialisation spots array
    static void initSpots() {
        spots[0] = new RestrictedSpot("1", "Dining Foyer", 30.0, 5);
        spots[1] = new RestrictedSpot("2", "Main Dining Hall", 100.0, 10);
        spots[2] = new RestrictedSpot("3", "Dining Room One", 40.0, 6);
        spots[3] = new RestrictedSpot("4", "Dining Room Two", 35.0, 7);
        spots[4] = new RestrictedSpot("5", "Family Dining Room", 50.0, 8);
    }
    
    //It simulates a robot navigating toward a customer by scanning in different directions, avoiding obstacles, and reporting when it safely reaches close to the customer.
    static void directionalNavigation(String robotId, String name, RestrictedSpot spot) {
        double distanceToCustomer = 4.0;
        double robotPosition = 0.0;
        Random random = new Random();

        while (distanceToCustomer > 1.0) {
            String[] directions = {"Left", "Right", "Forward", "Back"};
            //choose which direction to scan
            int direction = JOptionPane.showOptionDialog(null,
                    "Customer is " + String.format("%.2f", distanceToCustomer) + " meters ahead.\nChoose direction to scan:",
                    "Directional Scan",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    directions,
                    directions[2]);

            if (direction == -1) break; // Cancelled

            double obstacle = 0.5 + (5.0 * random.nextDouble());  //using random generator to simulate obstacles

            int moveDecision = JOptionPane.showConfirmDialog(null,                      //confirm whether to move forward from a particular direction
                    "Obstacle is " + String.format("%.2f", obstacle) + " meters in that direction.\nDo you want to move forward?",
                    "Move Decision",
                    JOptionPane.YES_NO_OPTION);

            if (moveDecision == JOptionPane.NO_OPTION) continue;
            
            //Checks if the robot is close to obstacle . IF not calculate the distance from robot to customer and update distance
            if (obstacle - 1.0 >= 1.0) {
                double move = Math.min(2.0, distanceToCustomer - 1.0);
                robotPosition += move;
                distanceToCustomer -= move;
                JOptionPane.showMessageDialog(null, "Robot moved " + String.format("%.2f", move) + " meters forward.");
            } else {
                JOptionPane.showMessageDialog(null, "Too close to obstacle. Cannot move safely.");
            }
        }

        String report = "\n==  Customer Reached Safely! ==\n\n" +
                "Robot ID     : " + robotId + "\n" +
                "Full Name    : " + name + "\n" +
                "Spot         : [" + spot.spotId + "] " + spot.spotName + "\n" +
                "Date         : " + java.time.LocalDate.now() + "\n" +
                "Time         : " + java.time.LocalTime.now().withNano(0) + "\n" +
                "Status       : Safe Contact";

        JOptionPane.showMessageDialog(null, report);
        
    }
    
    //ordering a drink . This includes ordering drink from menu or choosing a surprise drink
    static void orderDrink() {
        String[] drinks = {"Cola", "Lemonade", "Iced Tea", "Coffee", "Orange Juice"};
        String[] surpriseDrinks = {
                "Iced Coffee", "Hot Chocolate", "Mint Lemonade",
                "Iced Matcha", "Watermelon Juice", "Babycino", "Strawberry Mint Coffee"
        };

        //display a message for customers order
        String[] options = {"Pick from Menu", "Surprise Me"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "How would you like to order?",
                "Order Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) { // Pick from menu
            String selectedDrink = (String) JOptionPane.showInputDialog(
                    null,
                    "Select a drink:",
                    "Drink Menu",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    drinks,
                    drinks[0]);

            if (selectedDrink != null) {
                int waitTime = new Random().nextInt(5) + 1; // Randomly display minutes from 1 to 5
                JOptionPane.showMessageDialog(
                        null,
                        "Your " + selectedDrink + " will be ready in about " + waitTime + " minutes!",
                        "Order Confirmed",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            robotdistance(); //receives drink


            int refillOption = JOptionPane.showConfirmDialog(
                    null,
                    "Would you like a refill of your " + selectedDrink + "?",
                    "Refill Offer",
                    JOptionPane.YES_NO_OPTION);

            if (refillOption == JOptionPane.YES_OPTION) {
                robotdistance(); //receives drink

                JOptionPane.showMessageDialog(
                        null,
                        "Refilling your " + selectedDrink + ". Enjoy again!",
                        "Refill",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "No refill selected. Thank you!",
                        "No Refill",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } else if (choice == 1) { // Surprise option
            String servedDrink = surpriseDrinks[new Random().nextInt(surpriseDrinks.length)];
            JOptionPane.showMessageDialog(
                    null,
                    "Serving you a surprise drink: " + servedDrink,
                    "Surprise Drink",
                    JOptionPane.INFORMATION_MESSAGE
            );

         
            robotdistance(); //receives drink


            //ask for refill
            int refillOption = JOptionPane.showConfirmDialog(
                    null,
                    "Would you like a refill of your " + servedDrink + "?",
                    "Refill Offer",
                    JOptionPane.YES_NO_OPTION);

            if (refillOption == JOptionPane.YES_OPTION) {
                robotdistance();
                JOptionPane.showMessageDialog(
                        null,
                        "Refilling your " + servedDrink + ". Enjoy again!",
                        "Refill",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "No refill selected. Thank you!",
                        "No Refill",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

     //It checks if a restricted spot has enough space for a robot to enter, and then controls the robot's safety checks and navigation based on that.
    public static void proceedWithGUI(String robotId, String name, RestrictedSpot selected) {
        int currentPeople = new Random().nextInt(selected.maxCapacity + 1);
        JOptionPane.showMessageDialog(null, "Current people in spot: " + currentPeople);

        if (currentPeople < selected.maxCapacity) {
            JOptionPane.showMessageDialog(null, "Entry permitted.");
            proceedDynamicCheck(robotId, name, selected);
            directionalNavigation(robotId, name, selected);
        } else {
            int option = JOptionPane.showConfirmDialog(null,
                    "Spot is full. Wait " + selected.averageTime + " minutes?", "Wait?", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                proceedDynamicCheck(robotId, name, selected);
                directionalNavigation(robotId, name, selected);
                orderDrink();
            }
        }
    }

}
