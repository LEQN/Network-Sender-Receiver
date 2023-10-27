/**
 * This class is the main class for the Sender.
 * It checks the command-line arguments and creates a Sender object.
 *
 */
public class SenderMain {
    public static final int MAX_MTU = 65535;
    public static final int MAX_ID = 65535;

    /**
     * Check the command-line arguments and create a Sender.
     * @param args mtu ID message
     */
    public static void main(String[] args) {
        if(args.length != 3) {
            System.err.println("Usage: java SenderMain MTU ID message");
            System.exit(1);
        }
        int mtu = Integer.parseInt(args[0]);
        int id = Integer.parseInt(args[1]);
        String message = args[2];

        //message size must be at least 1
        if (message.length() < 1){
            System.err.println("Message must have at least 1 character");
        }
        //id checks
        if(id < 0) {
            System.err.println("ID must be at least 0");
            System.exit(1);
        }
        if(id > MAX_ID) {
            System.err.println("ID must be at most " + MAX_ID);
            System.exit(1);
        }
        //mtu checks
        if(mtu <= 0) {
            System.err.println("MTU must be at least greater than 0");
            System.exit(1);
        }
        if(mtu > MAX_MTU) {
            System.err.println("MTU must be at most " + MAX_MTU);
            System.exit(1);
        }
        String str = Integer.toString(id);
        int idSize = str.length();
        if (mtu <= (idSize+7)){
            System.err.println("MTU needs to be larger to accommodate ID");
            System.exit(1);
        }

        Sender sender = new Sender(mtu);
        sender.send(id, message);
    }
}