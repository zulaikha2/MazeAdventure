import java.util.*;

public class Wanderer implements Runnable {
    private Room currentRoom;
    private final Random random = new Random();
    private int disposition = 0;

    public Wanderer(Room startRoom) {
        this.currentRoom = startRoom;
    }

    public synchronized Room getCurrentRoom() {
        return currentRoom;
    }

    public synchronized void improveDisposition() {
        disposition++;
    }

    public synchronized String talk() {
        improveDisposition();

        if (disposition >= 2) {
            return "Wanderer: The key is hidden north of the junction.";
        } else {
            return "Wanderer: I have seen the exit, but you need a key.";
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);

                synchronized (this) {
                    List<Room> nextRooms = new ArrayList<>(currentRoom.getExits().values());
                    if (!nextRooms.isEmpty()) {
                        currentRoom = nextRooms.get(random.nextInt(nextRooms.size()));
                    }
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
