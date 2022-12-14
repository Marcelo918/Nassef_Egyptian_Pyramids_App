package nassef_egyptian_pyramids_app;

import java.util.*;
import org.json.simple.*;
import java.util.HashSet;;

/**
 * Author: Marcelo Villalobos Diaz
 * Date: November 08, 2022
 * Class: CSIS26 - FA22
 * Description: The Nassef's Egyptian Pyramids App should perform
 * the commands listed in the menu.
 */
public class App {
    // Set to store the IDs of requested pyramids
    HashSet<Integer> pyramidId = new HashSet<Integer>();

    // two arrays here for O(1) reading of the pharaohs and pyramids.
    // other structures or additional structures can be used
    protected Pharaoh[] pharaohArray;
    protected Pyramid[] pyramidArray;

    public static void main(String[] args) {
        // create and start the app
        App app = new App();
        app.start();
    }

    // main loop for app
    public void start() {
        Scanner scan = new Scanner(System.in);
        Character command = '_';

        // loop until user quits
        while (command != 'q') {
            printMenu();
            System.out.print("Enter a command: ");
            command = menuGetCommand(scan);

            executeCommand(scan, command);
        }
    }

    // constructor to initialize the app and read commands
    public App() {
        // read egyptian pharaohs
        String pharaohFile = "C:/Users/CAD-DESIGNER/Documents/GitHub/Nassef_Egyptian_Pyramids_App/nassef_egyption_pyramid_app/src/main/java/nassef_egyptian_pyramids_app/pharaoh.json";
        JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFile);

        // create and initialize the pharaoh array
        initializePharaoh(pharaohJSONArray);

        // read pyramids
        String pyramidFile = "C:/Users/CAD-DESIGNER/Documents/GitHub/Nassef_Egyptian_Pyramids_App/nassef_egyption_pyramid_app/src/main/java/nassef_egyptian_pyramids_app/pyramid.json";
        JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFile);

        // create and initialize the pyramid array
        initializePyramid(pyramidJSONArray);
    }

    // initialize the pharaoh array
    private void initializePharaoh(JSONArray pharaohJSONArray) {
        // create array and hash map
        pharaohArray = new Pharaoh[pharaohJSONArray.size()];

        // initalize the array
        for (int i = 0; i < pharaohJSONArray.size(); i++) {
            // get the object
            JSONObject o = (JSONObject) pharaohJSONArray.get(i);

            // parse the json object

            Integer id = toInteger(o, "id");
            String name = o.get("name").toString();
            Integer begin = toInteger(o, "begin");
            Integer end = toInteger(o, "end");
            Integer contribution = toInteger(o, "contribution");
            String hieroglyphic = o.get("hieroglyphic").toString();

            // add a new pharoah to array
            Pharaoh p = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
            pharaohArray[i] = p;
        }
    }

    // initialize the pyramid array
    private void initializePyramid(JSONArray pyramidJSONArray) {
        // create array and hash map
        pyramidArray = new Pyramid[pyramidJSONArray.size()];

        // initialize the array
        for (int i = 0; i < pyramidJSONArray.size(); i++) {
            // get the object
            JSONObject o = (JSONObject) pyramidJSONArray.get(i);

            // parse the json object
            Integer id = toInteger(o, "id");
            String name = o.get("name").toString();
            JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
            String[] contributors = new String[contributorsJSONArray.size()];
            for (int j = 0; j < contributorsJSONArray.size(); j++) {
                String c = contributorsJSONArray.get(j).toString();
                contributors[j] = c;
            }

            // add a new pyramid to array
            Pyramid p = new Pyramid(id, name, contributors);
            pyramidArray[i] = p;

        }
    }

    // get a integer from a json object, and parse it
    private Integer toInteger(JSONObject o, String key) {
        String s = o.get(key).toString();
        Integer result = Integer.parseInt(s);
        return result;
    }

    // get first character from input
    private static Character menuGetCommand(Scanner scan) {
        Character command = '_';
        String rawInput = scan.nextLine();

        if (rawInput.length() > 0) {
            rawInput = rawInput.toLowerCase();
            command = rawInput.charAt(0);
        }

        return command;
    }

    // print all pharaohs
    private void printAllPharaoh() {
        for (int i = 0; i < pharaohArray.length; i++) {
            printMenuLine();
            pharaohArray[i].print();
            printMenuLine();
        }
    }

    // print all pyramids
    private void printAllPyramids() {
        for (int i = 0; i < pyramidArray.length; i++) {
            printMenuLine();
            pyramidArray[i].print(pharaohArray);
            printMenuLine();
        }
    }

    // print one pharaoh
    private void printOnePharaoh() {
        System.out.print("Enter a pharoah id: ");
        Scanner userInput = new Scanner(System.in);
        int i = userInput.nextInt();

        if (i >= 0 && i <= 171) {
            printMenuLine();
            pharaohArray[i].print();
            printMenuLine();
        } else {
            System.out.println("ERROR: Please enter an id number between 0 - 171.");
        }
    }

    // print one pyramid
    private void printOnePyramid() {
        System.out.print("Enter a pyramid id: ");
        Scanner userInputP = new Scanner(System.in);
        int i = userInputP.nextInt();

        if (i >= 0 && i <= 99) {
            printMenuLine();
            pyramidArray[i].print(pharaohArray);
            pyramidId.add(i);
            printMenuLine();
        } else {
            System.out.println("ERROR: Please enter an id number between 0 - 99.");
        }
    }

    // prints the list of requested pyramids
    private void printSet() {

        printMenuLine();
        System.out.println("List of requested pyramids");
        System.out.println("Id     Name");
        System.out.println("---   -----");

        for (Integer i : pyramidId) {
            System.out.print(i);
            pyramidArray[i].printName(pharaohArray);
        }
        printMenuLine();
    }

    private Boolean executeCommand(Scanner scan, Character command) {
        Boolean success = true;

        switch (command) {
            case '1':
                printAllPharaoh();
                break;
            case '2':
                printOnePharaoh();
                break;
            case '3':
                printAllPyramids();
                break;
            case '4':
                printOnePyramid();
                break;
            case '5':
                printSet();
                break;
            case 'q':
                System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
                break;
            default:
                System.out.println("ERROR: Unknown command");
                success = false;
        }

        return success;
    }

    private static void printMenuCommand(Character command, String desc) {
        System.out.printf("%s\t\t%s\n", command, desc);
    }

    private static void printMenuLine() {
        System.out.println("--------------------------------------------");
    }

    // prints the menu
    public static void printMenu() {
        printMenuLine();
        System.out.println("Nassef's Egyptian Pyramids App");
        printMenuLine();
        System.out.printf("Command\t\tDescription\n");
        System.out.printf("-----\t\t-------------\n");
        printMenuCommand('1', "List all the pharoahs");
        printMenuCommand('2', "Display a specific Egyptian pharaoh");
        printMenuCommand('3', "List all the pyramids");
        printMenuCommand('4', "Displays a specific pyramid");
        printMenuCommand('5', "Displays a list of requested pyramids");
        printMenuCommand('q', "Quit");
        printMenuLine();
    }
}
