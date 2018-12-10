import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Server Model Object
 * This allows incoming connections from the client.
 * Client is each handled on each thread separately
 * Last Updated: 12/8/2018
 * @author Alex De Pina, Emerson Moniz
 */
public class Server extends Thread {
    private final int serverPort;
    //    List of serverWorkers for the task
    private ArrayList<ServerWorker> userList = new ArrayList<>();
    Scanner in;

    /**
     * Constructor for the Server class
     * @param serverPort (required) need to know the ServerPort
     */
    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * Returns a list object that contains all users in list
     * @return the list of users in the server
     */
    public List<ServerWorker> getUserList() {
        return userList;
    }

    /**
     * Returns an integer of all the users logged in the server
     * @return the number of users in the server
     */
    public int getNumUser() {
        return userList.size();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server is Running");
            // Infinite loop to allow all the clients to connect to the server
            while (true) {
                System.out.println("Waiting for Clients to connect ... ");
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client request received for chat." + clientSocket);
                // creates a new thread for this user
                // Extract the value from GUI
//                String name = "";
//                String key = "";
//                ArrayList <String> allowedUsers = new ArrayList<>();
                // Missing from implementation is a list of players retrieved from GUI
                ServerWorker worker = new ServerWorker(this, clientSocket);
                // Add each incoming connection to the list
                userList.add(worker);
                worker.start();
            }
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage());
            System.exit(1);
        }

    }

    public void removeWorker(ServerWorker serverWorker) {
        userList.remove(serverWorker);
    }
}