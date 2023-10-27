import java.util.LinkedList;

/**
 * A Sender breaks up messages into the required number of pieces,
 * based on the maximum transmission unit (MTU), and outputs them
 * to standard output, suitably encoded.
 */
public class Sender {
    private final int mtu;

    /**
     * Create a Sender.
     * @param mtu The maximum transmission unit.
     */
    public Sender(int mtu) {
        this.mtu = mtu;
    }

    /**
     * Send a message in one or more pieces according to the mtu.
     * @param id The ID of the message.
     * @param message The message to send.
     */
    public void send(int id, String message) {
        int ID = getIDSize(id);
        int MTUmessageLen = mtu - (ID + 7);
        LinkedList<String> messageParts = splitString(message, MTUmessageLen);
        for (int i = 0; i < messageParts.size(); i++){
            String packetNum = String.format("%-3s", i);
            if (i != messageParts.size()-1){
                System.out.println(id + ",F,"+packetNum + ","+messageParts.get(i));
            }else{
                System.out.println(id+",T,"+packetNum+","+messageParts.get(i));
            }
        }

    }

    /**
     * Takes the full message and the size that each part needs to be for each packet, then splits the string into the
     * message parts for each packet.
     * @param message The ID of the message
     * @param partSize The size of message length per packet
     * @return A linkedList with the string splint into the separate message parts
     */
    private LinkedList<String> splitString(String message, int partSize){
        LinkedList<String> parts = new LinkedList<>();
        int length = message.length();
        int i = 0;
        while (i < length){
            if (i + partSize <= length){
                parts.add(message.substring(i, i+partSize));
                i += partSize;
            }else{
                parts.add(message.substring(i));
                break;
            }
        }
        return parts;
    }

    /**
     * checks the length of the id
     * @param id The ID of the message
     * @return The amount of digits in the id
     */
    private int getIDSize(int id){
        String str = Integer.toString(id);
        int size = str.length();
        return size;
    }
}
