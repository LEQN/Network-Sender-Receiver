import java.util.*;

/**
 * Receiver for a message from a Sender.
 */
public class Receiver {
    // The ID of the message to be received.
    private final int id;
    private ArrayList<String> packets = new ArrayList<>();
    private int totalP = -1;
    private String[] sortedPackets;

    /**
     * Create a Receiver for a message with the given ID.
     * @param id The ID of the message to be received.
     */
    public Receiver(int id) {
        this.id = id;
    }

    /**
     * Receive a message from standard input.
     * Print the message to standard output.
     */
    public void receive() {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            count++;
            //check the line follows the correct format (id,F,packNo,message)
            if (line.matches("^\\d+,[FT],(?=.*\\d)[\\d\\s]{3},.+$")) {
                //check if the line contains the id we want and is not duplicate
                if (correctID(line) == true && isDuplicate(line) == false) {
                    packets.add(line);
                    //check if final packet received and all packets arrived
                    if (haveFinalPacket() && allPackets()) {
                        break;
                    }
                }else if (line.split(",")[1].equals("T") && !correctID(line) && !scanner.hasNextLine()
                        || !correctID(line) && !scanner.hasNextLine()){
                    System.err.println("error");
                    System.exit(1);
                }
            }else if (!line.matches("^\\d+,[FT],(?=.*\\d)[\\d\\s]{3},.+$") && !scanner.hasNextLine()){
                System.err.println("error");
                System.exit(1);
            }
        }
        if (!allPackets()){
            System.err.println("error");
            System.exit(1);
        }else {
            //sort the arraylist by ascending packet number
            sort();
            //build the final message
            String message = "";
            for (String input : sortedPackets){
                String[] parts = input.split(",");
                message += parts[3];
            }
            System.out.println(message);
        }
    }

    /**
     * check the packet contains the correct ID
     * @param packet
     * @return
     */
    private boolean correctID(String packet){
        int commaIndex = packet.indexOf(",");
        String idString = packet.substring(0, commaIndex);
        int parsedID = Integer.parseInt(idString);
        return parsedID == id;
    }

    /**
     * check if the packet is a duplicate already in the list
     * @param packet
     * @return
     */
    private boolean isDuplicate(String packet){
        if (packets.size() < 1){
            return false;
        }else {
            for (String input : packets) {
                String[] parts = input.split(",");
                if (parts[2].equals(packet.split(",")[2])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * check that all the packets have been received and are not missing
     * @return
     */
    private boolean allPackets(){
        if (packets.size() == totalP+1){
            return true;
        }
        return false;
    }

    /**
     * check if the final packet with T flag has been received
     * @return
     */
    private boolean haveFinalPacket(){
        for (String input : packets){
            String[] parts = input.split(",");
            if (parts[1].equals("T")){
                totalP = Integer.parseInt(parts[2].trim());
                return true;
            }
        }
        return false;
    }

    /**
     * Sort the list of packets in ascending order
     */
    private void sort(){
        sortedPackets = packets.toArray(new String[packets.size()]);
        String temp = "";
        for (int i = 0; i < sortedPackets.length; i++){
            for (int j = 1; j < (sortedPackets.length-i); j++){
                if (Integer.parseInt(sortedPackets[j-1].split(",")[2].trim()) > Integer.parseInt(sortedPackets[j].split(",")[2].trim())){
                    temp = sortedPackets[j-1];
                    sortedPackets[j-1] = sortedPackets[j];
                    sortedPackets[j] = temp;
                }
            }
        }
    }
}