import java.io.*;
import java.net.*;
import java.util.ArrayList;

/*
 * Server Class
 * Written by: Alex J. Monteiro De Pina
 * Date: 11/30/2018
 */

/**
 * Start point of the Server
 * Takes the username, keys and users allowed in the chatroom
 * Date: 11/30/2018
 * Last Updated: 12/8/2018
 * @author Alex J. Monteiro De Pina, Emerson Moniz
 */
public class ServerMain {
    private static String userName, key, allowedU;
    static String[] allowedUsers;
    static ArrayList<String> allowedUList = new ArrayList<>();
//    static chatRoom chat;

    public static void arrayToList() {
        for (int i = 0; i < allowedUsers.length; i++) { // convert String Array to Arraylist
            allowedUList.add(allowedUsers[i]);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int portNumber = 8800;
        Server server = new Server(portNumber);
        server.start();

//					douts.flush();
//					if (getNumUser()==0) {
//						userName = dins.readUTF();
//						key = dins.readUTF();
//						allowedU = dins.readUTF();
//						allowedUsers = allowedU.split(" ");
//						arrayToList();
//						chat = new chatRoom(8800, key, allowedUsers);
//					}
//					else {
//						userName = dins.readUTF();
//					}
//					if ( users.size() > 0 && allowedUList.contains(userName)) { // give everyone in the list a key except for the person how created the chat
//						key = chat.getKey();
//					}
//					else if (users.size() > 0) {
//						key = "X";
//					}
//					// Create a new handler for this client
//					ClientHandler user = new ClientHandler(clientSocket,userName, key, dins, douts);
//					users.add(user);
//					t.start();
            }

    }


/*
 * chatRoom Class Will Create a new chatRoom and hold all the info for the chat
 * Written by: Alex J. Monteiro De Pina Date: 11/29/2018
 */

/*
 * Client Handler A helper class that will will handle multiple clients in the
 * same chatroom manage the send and receive of the message Written by: Alex J.
 * Monteiro De Pina Date: 11/29/2018
 */
//class ClientHandler implements Runnable {
//

//    // constructor
//    public ClientHandler(Socket s,String name,String k, DataInputStream dins, DataOutputStream douts) {
//        this.dins = dins;
//        this.douts = douts;
//        this.name = name;
//        this.s = s;
//        this.isloggedin = true;
//        this.key = k;
//    }
//    @Override
//    public void run() {
//
//}
