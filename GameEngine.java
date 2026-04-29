import java.util.Scanner;

public class GameEngine {
    private final Player player;
    private final Wanderer wanderer;
    private boolean running = true;

    public GameEngine() {
        Room startRoom = MazeBuilder.buildMaze();
        this.player = new Player(startRoom);
        this.wanderer = new Wanderer(startRoom);

        Thread wandererThread = new Thread(wanderer);
        wandererThread.setDaemon(true);
        wandererThread.start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Maze Text Adventure!");
        System.out.println("Find the map, collect the key, and escape the maze.");
        System.out.println("Type 'help' to see commands.");

        player.getCurrentRoom().describe();

        while (running) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim().toLowerCase();
            processCommand(input);
        }

        scanner.close();
    }

    private void processCommand(String input) {
        if (input.isEmpty()) {
            System.out.println("Please enter a command.");
            return;
        }

        if (input.equals("help")) {
            showHelp();
        } else if (input.startsWith("move ")) {
            move(input.substring(5));
        } else if (input.startsWith("pick up ")) {
            pickUp(input.substring(8));
        } else if (input.equals("inventory") || input.equals("i")) {
            player.showInventory();
        } else if (input.equals("look")) {
            player.getCurrentRoom().describe();
        } else if (input.equals("ask wanderer")) {
            askWanderer();
        } else if (input.equals("map")) {
            showMap();
        } else if (input.equals("quit")) {
            running = false;
            System.out.println("Game ended.");
        } else {
            System.out.println("Unknown command. Type 'help' for commands.");
        }
    }

    private void showHelp() {
        System.out.println("\nCommands:");
        System.out.println("move north/south/east/west");
        System.out.println("pick up map");
        System.out.println("pick up key");
        System.out.println("inventory or i");
        System.out.println("look");
        System.out.println("ask wanderer");
        System.out.println("map");
        System.out.println("quit");
    }

    private void move(String direction) {
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("You cannot move that way.");
            return;
        }

        if (nextRoom.getName().equals("Exit Chamber") && !player.hasItem("key")) {
            System.out.println("The exit door is locked. You need the key.");
            return;
        }

        player.setCurrentRoom(nextRoom);
        player.getCurrentRoom().describe();

        if (nextRoom.getName().equals("Exit Chamber") && player.hasItem("key")) {
            System.out.println("\nCongratulations! You unlocked the exit and escaped the maze!");
            running = false;
        }
    }

    private void pickUp(String itemName) {
        Item item = player.getCurrentRoom().removeItem(itemName);

        if (item == null) {
            System.out.println("That item is not here.");
            return;
        }

        player.addItem(item);
        System.out.println("You picked up: " + item.getName());
    }

    private void askWanderer() {
        if (player.getCurrentRoom() == wanderer.getCurrentRoom()) {
            System.out.println(wanderer.talk());
        } else {
            System.out.println("The Wanderer is not here.");
        }
    }

    private void showMap() {
        if (player.hasItem("map")) {
            MazeBuilder.printMap();
        } else {
            System.out.println("You do not have the map yet.");
        }
    }
}

