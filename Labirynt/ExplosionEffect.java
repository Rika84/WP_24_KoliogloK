
/**
 * Class handling explosion effects in a room.
 */
public class ExplosionEffect {

    /**
     * Executes an explosion in the specified room, destroying adjacent walls.
     *
     * @param room the room where the explosion occurred.
     */
    public void handleExplosion(Room room) {
        // Get all sides of the room
        MapSite[] sites = room.getSites();

        // Destroy walls adjacent to the room
        for (int i = 0; i < sites.length; i++) {
            if (sites[i] instanceof Wall) {
                sites[i] = null; // Remove the wall
            }
        }

        // Notify adjacent rooms if any
        notifyAdjacentRooms(room);
    }

    /**
     * Notifies adjacent rooms about the explosion effect.
     *
     * @param room the current room where the explosion occurred.
     */
    private void notifyAdjacentRooms(Room room) {
        MapSite[] sites = room.getSites();

        for (int i = 0; i < sites.length; i++) {
            if (sites[i] instanceof Door) {
                Door door = (Door) sites[i];
                Room adjacentRoom = (door.getRoomOne() == room)
                        ? door.getRoomTwo()
                        : door.getRoomOne();
                if (adjacentRoom != null) {
                    // Additional logic for adjacent rooms can be implemented here
                    System.out.println("Adjacent room " + adjacentRoom.getRoomNumber() + " was notified about the explosion.");
                }
            }
        }
    }
}
