import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ServerWorker extends Thread {
    private final Socket clientSocket;
    private final Server server;
    private String userName;
    private boolean userIsloggedin;
    private String key;
    private ArrayList<String> allowedUserList;
    private OutputStream OutputStream;
    private InputStream InputStream;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

/*
    Default constructor for server worker.
    Will login the user
    Server is being passed as a parameter so the object can reference the same server when finding out who's logged in
*/

    /**
     * @param server
     * @param clientSocket
     */
    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.userIsloggedin = true;
    }


    public void updateInformation (String[] tokens) throws IOException {
        String name = tokens[1];
        String key = tokens[2];
        ArrayList<String> allowedUsers = new ArrayList<>();
        for (int i = 3, j = 0; i < tokens.length - 3; i++, j++)
            allowedUsers.add(j, tokens[i]);

        this.userName = name;
        this.key = key;
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        out.writeUTF("Finished updating the values");
    }
    @Override
    public void run() {
        try {
//            Call the client to read messages
            handleClientThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public Boolean getUserLogin() {
        return userIsloggedin;
    }

    /**
     * @throws IOException
     */
    private void handleClientThread() throws IOException {
        // set up streams
        InputStream InputStream = clientSocket.getInputStream();
        this.OutputStream = clientSocket.getOutputStream();
        DataInputStream rr = new DataInputStream(clientSocket.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(InputStream));
        String message;
//        String msgIn = "";
//        String msgOut = "";
////        douts.writeInt(getNumUser());
////      User wants to leave chatroom
        while ((message = rr.readUTF()) != null) {
            System.out.println(message);
            String[] tokens = StringUtils.splitString(message, "#");
            if (tokens != null && tokens.length > 0) {
                String cmd = tokens[0];
                if (cmd.equalsIgnoreCase("exit")) {
                    handleLogOff();
                    break;
                } else if (cmd.equalsIgnoreCase("update")) {
                    updateInformation(tokens);
                }
                //            else if(this.newUser.equalsIgnoreCase("new")) {
                //                handleUserJoinChatRoom();
                //                this.newUser = "old";
                //            }
                else {
                    sendMessageToGroup(message);
                }
            }
        }
        clientSocket.close();
    }

    private void handleLogOff() throws IOException {
        server.removeWorker(this);
        this.userIsloggedin = false;
        List<ServerWorker> userList = server.getUserList();
        for (ServerWorker worker : userList) {
            String offlineMessage = userName + " has " +
                    (userIsloggedin ? "logged in to" : "logged out of") + " the chatroom";
            if (!((worker.userName).equals(this.userName)) && (worker.userIsloggedin == true))
                worker.sendMessage(offlineMessage,worker);
        }
        clientSocket.close();
    }

//    /**
//     * @param
//     */
//    private void handleUserJoinChatRoom() throws IOException {
//        if (userIsloggedin) {
//            List<ServerWorker> userList = server.getUserList();
//            for (ServerWorker worker : userList) {
//                String onlineMessage = userName + " has " +
//                        (userIsloggedin ? "logged in to" : "logged out of") + " the chatroom";
//                if (!((worker.userName).equals(this.userName)) && (worker.userIsloggedin == true))
//                    worker.sendMessage(onlineMessage);
//            }
//        }
//    }

    private void sendMessageToGroup(String message) throws IOException {
        List<ServerWorker> userList = server.getUserList();
        for (ServerWorker worker : userList) {
            if (!((worker.userName).equals(this.userName)) && (worker.userIsloggedin == true))
                worker.sendMessage(message,worker);
        }
    }

    private void sendMessage(String message,ServerWorker w) throws IOException {
        OutputStream.write((w.userName + ": " + message + "\n").getBytes());
    }
}