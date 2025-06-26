import javax.swing.*; //GUI elements
import java.awt.*;    //used for containers and layout managers
import java.util.Random; //To generate random numbers

public class DrinksGUI {
    private JFrame frame;        //the main window for the system
    private JTextField nameField;    //field to enter name
    private JTextField idField;     //field to enter id
    private RestrictedSpot[] spots = new RestrictedSpot[0]; // Initialize empty

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrinksGUI().createLoginUI()); //ensures that the createLoginUI() method is executed
    }

    public void createLoginUI() {

        //Login window setup
        frame = new JFrame("Drink Serving Robot - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null);
        
        //Fonts
        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 15);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 16);



        //name field
        JLabel nameLabel = new JLabel("Enter Full Name:");
        nameLabel.setFont(labelFont);
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(250, 30));
        nameField.setFont(inputFont);
        

        //ID field
        JLabel idLabel = new JLabel("Enter Robot ID:");
        idLabel.setFont(labelFont);
        idField = new JTextField();
        idField.setPreferredSize(new Dimension(250, 30));
        idField.setFont(inputFont);


        //Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(buttonFont);
        loginButton.setPreferredSize(new Dimension(140, 40));
        
        //Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        

        //Adding Components to Panel
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(idLabel);
        panel.add(idField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(loginButton);
         

        //Login Button Action
        loginButton.addActionListener(e -> {           //checks if loginbutton has being clicked
            String name = nameField.getText().trim();   //gets the value of name field
            String id = idField.getText().trim();       //gets the value of ID field
            if (!name.isEmpty() && !id.isEmpty()) {        //validate if both fields inputs are not empty
                frame.dispose();                          //close current frame
                initializeSpots();                        
                showMenu(name, id);                       //show the menu window
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);  //error message if fields not filled
            }
        });

        frame.getContentPane().add(panel);   //add GUI components in panel to the fram
        frame.setVisible(true);           //show the frame on screen
    }

    private void initializeSpots() {
        StaticDrinkServing.initSpots(); // Initialize spots
        spots = StaticDrinkServing.spots;
    }

    private void showMenu(String name, String id) {

        //Menu frame setup
        JFrame menuFrame = new JFrame("Restricted Spot Menu"); 
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(500, 400);
        menuFrame.setLocationRelativeTo(null);
        
        //Area ,called spotArea ,to display spots in restaurant 
        JTextArea spotArea = new JTextArea(10, 40);
        spotArea.setEditable(false);
        spotArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        
        //using loop to retrieve spot details and display in spotArea 
        for (RestrictedSpot spot : spots) {
            spotArea.append("[" + spot.spotId + "] " + spot.spotName +
                    " | Area: " + spot.spotArea + " sq.m | Max: " + spot.maxCapacity + "\n");
        }
        
        //a dropdown menu of all the spots
        JComboBox<String> spotSelector = new JComboBox<>();
        for (RestrictedSpot spot : spots) {
            spotSelector.addItem("[" + spot.spotId + "] " + spot.spotName);
        }


        //Check Capacity button: To check the capacity of people in selected spot
        JButton checkButton = new JButton("Check Capacity");
        checkButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        //Exit Button : to Exit the window
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        exitButton.addActionListener(e -> System.exit(0));
         
        //instruction for the user 
        JLabel resultLabel = new JLabel("Select a spot and click to check.");
        resultLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
         

        //Check capacity button action
        checkButton.addActionListener(e -> {
            int index = spotSelector.getSelectedIndex();   //get the value of the index of selected spot 
            if (index >= 0) {                             //if index is less than 0 it means user has not selected
                RestrictedSpot selected = spots[index];
                int currentPeople = new Random().nextInt(selected.maxCapacity + 1);

                if (currentPeople < selected.maxCapacity) {                 // if there is space entry is permitted
                    resultLabel.setText("Entry permitted. Current: " + currentPeople + "/" + selected.maxCapacity);
                    menuFrame.dispose();
                    proceedWithGUI(id, name, selected);
                } else {
                    resultLabel.setText("<html><span style='color:red;'>Spot is FULL. Avg Wait: " +   //If no space then display spot is full and avg waiting time
                            selected.averageTime + " mins</span></html>");
                    int option = JOptionPane.showConfirmDialog(menuFrame,                                    //confirm with user to wait or not
                            "Spot is full. Wait " + selected.averageTime + " minutes?", "Wait?",
                            JOptionPane.YES_NO_OPTION); 
                    if (option == JOptionPane.YES_OPTION) {         //if yes then close current frame
                        menuFrame.dispose();
                        proceedWithGUI(id, name, selected);
                    }
                }
            }
        });
        
        //Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        //Add GUI components to panel
        panel.add(new JScrollPane(spotArea));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(spotSelector);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(checkButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(exitButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(resultLabel);
        

        menuFrame.getContentPane().add(panel);   //add GUI components in panel to frame
        menuFrame.setVisible(true);            //display the frame on screen
    }

    private void proceedWithGUI(String robotId, String name, RestrictedSpot selected) {
        int currentPeople = new Random().nextInt(selected.maxCapacity + 1);   //increment current people in particular spot when entry is permitted

        JOptionPane.showMessageDialog(null, "Current people in spot: " + currentPeople, "Spot Status",   //display current no. of people in spot
                JOptionPane.INFORMATION_MESSAGE);

        if (currentPeople < selected.maxCapacity) {
            JOptionPane.showMessageDialog(null, "Entry permitted.", "Access Granted",  //display entry permitted message
                    JOptionPane.INFORMATION_MESSAGE);                           
            showDistanceCheck(name, robotId, selected);          //proceeds to distance checking
        } else {
            int option = JOptionPane.showConfirmDialog(null,
                    "Spot is full. Wait " + selected.averageTime + " minutes?", "Wait?",    //if entry is not granted display average waiting time message
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {                     
                showDistanceCheck(name, robotId, selected);    //proceeds to distance checking
            }
        }
    }

    private void showDistanceCheck(String name, String id, RestrictedSpot spot) {

        //Distance Check frame setup
        JFrame distFrame = new JFrame("Dynamic Distance Check");
        distFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        distFrame.setSize(400, 350);
        distFrame.setLocationRelativeTo(null);
        
        //left,right,front,back fields 
        JTextField left = new JTextField(5);
        JTextField right = new JTextField(5);
        JTextField front = new JTextField(5);
        JTextField back = new JTextField(5);
        
        //Check Distances button
        JButton check = new JButton("Check Distances");
        check.setFont(new Font("SansSerif", Font.BOLD, 14));

        //Enter distance field
        JLabel result = new JLabel("Enter distance from each side (in meters).");
        result.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        //Check Distances button action
        check.addActionListener(e -> {
            try {                 //using exception handling to validate if distance values are numeric
                //Get left ,right,front,back values being entered in the fields 
                double l = Double.parseDouble(left.getText().trim()); 
                double r = Double.parseDouble(right.getText().trim());
                double f = Double.parseDouble(front.getText().trim());
                double b = Double.parseDouble(back.getText().trim());

                boolean isSafe = new DynamicDrinkServing().checkDistance(l, r, f, b);
                distFrame.dispose();
                showDeliveryStatus(name, id, spot, isSafe);
                StaticDrinkServing.directionalNavigation(id, name, spot);
            } catch (NumberFormatException ex) {           //if not numeric 
                result.setText("Invalid input. Please enter valid numbers.");
            }
        });

        //panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        //Add GUI components to Panel
        panel.add(new JLabel("Left:"));
        panel.add(left);
        panel.add(new JLabel("Right:"));
        panel.add(right);
        panel.add(new JLabel("Front:"));
        panel.add(front);
        panel.add(new JLabel("Back:"));
        panel.add(back);
        panel.add(check);
        panel.add(result);
        
    
        distFrame.getContentPane().add(panel);
        distFrame.setVisible(true);
    }

    private void showDeliveryStatus(String name, String id, RestrictedSpot spot, boolean isSafe) {
        
        //Delivery Status setup
        JFrame statusFrame = new JFrame("Delivery Status");
        statusFrame.setSize(400, 300);
        statusFrame.setLocationRelativeTo(null);
        statusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

       //Area to display delivery status
        JTextArea output = new JTextArea();
        output.setFont(new Font("Monospaced", Font.PLAIN, 14));
        output.setEditable(false);
        
        //Delivery status details
        output.setText(" Robot Delivery Report\n\n");
        output.append("Robot ID  : " + id + "\n");
        output.append("Name      : " + name + "\n");
        output.append("Spot      : [" + spot.spotId + "] " + spot.spotName + "\n");
        output.append("Date      : " + java.time.LocalDate.now() + "\n");
        output.append("Time      : " + java.time.LocalTime.now().withNano(0) + "\n");
        output.append("Status    : " + (isSafe ? "Safe Contact" : "Casual Contact") + "\n");
        


       //Order drink button
        JButton orderDrinkButton = new JButton("Order Drink");
        orderDrinkButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        orderDrinkButton.addActionListener(e -> StaticDrinkServing.orderDrink());
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(output), BorderLayout.CENTER);
        panel.add(orderDrinkButton, BorderLayout.SOUTH);

        
        statusFrame.getContentPane().add(panel);
        statusFrame.setVisible(true);

        
    }
}
