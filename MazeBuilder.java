public class MazeBuilder {
    public static Room buildMaze() {
        Room entry = new Room("Entry Hall", "A cold stone room with a weak torch on the wall.");
        Room eastCorridor = new Room("East Corridor", "A narrow corridor with scratches on the walls.");
        Room mapRoom = new Room("Map Room", "A dusty room with an old map on a table.");
        Room northChamber = new Room("North Chamber", "A dark chamber. You hear faint footsteps.");
        Room junction = new Room("Junction", "A central hub connecting several paths.");
        Room keyVault = new Room("Key Vault", "A locked-looking room with something shining inside.");
        Room exitChamber = new Room("Exit Chamber", "A large door stands here. It looks like the way out.");

        entry.addExit("east", eastCorridor);
        eastCorridor.addExit("west", entry);

        eastCorridor.addExit("east", mapRoom);
        mapRoom.addExit("west", eastCorridor);

        entry.addExit("north", northChamber);
        northChamber.addExit("south", entry);

        northChamber.addExit("east", junction);
        junction.addExit("west", northChamber);

        junction.addExit("north", keyVault);
        keyVault.addExit("south", junction);

        junction.addExit("east", exitChamber);
        exitChamber.addExit("west", junction);

        mapRoom.addItem(new Item("map", "Reveals the full maze layout."));
        keyVault.addItem(new Item("key", "Unlocks the exit door."));

        return entry;
    }

    public static void printMap() {
        System.out.println("\nMaze Map:");
        System.out.println("Entry Hall -> east -> East Corridor -> east -> Map Room");
        System.out.println("Entry Hall -> north -> North Chamber -> east -> Junction");
        System.out.println("Junction -> north -> Key Vault");
        System.out.println("Junction -> east -> Exit Chamber");
    }
}
