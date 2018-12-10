import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
    private final String serverName;
    private final int serverPort;
    private Socket socket;
    private DataOutputStream serverOut;
    private DataInputStream serverIn;
    private BufferedReader bufferedIn;


    public Client(String serverName,int serverPort){
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost",8800);
        if (!client.connect()){
            System.err.println("Connect Failed");
        }
        else {
            System.out.println("Connection successful");
            String userName= null,key =null;
            ArrayList<String> allowedUsers = new ArrayList<>();
            allowedUsers.add("rice");
            allowedUsers.add("men");
            if (client.login("james","hen",allowedUsers)){
                System.out.println("Login successful");
            }
            else
                System.err.println("Login failed");
        }
    }

    private boolean login(String userName,String key,ArrayList allowedUsers) throws IOException {
//        String informationNeeded = "update" + "#" + userName + "#" + key + "#" + allowedUsers.toString() +"\n";
        String informationNeeded = "update" + "#" + userName + "#" + key;
//        System.out.println(informationNeeded.getBytes());
        serverOut.writeUTF(informationNeeded);

        String response = serverIn.readUTF();
        System.out.println("Response line:" + response);
        if("Finished updating the values".equalsIgnoreCase(response)){
            return true;
        }else
            return false;
    }

    private boolean connect() {
        try {
            this.socket = new Socket(serverName, serverPort);
            this.serverOut = new DataOutputStream(socket.getOutputStream());
            this.serverIn = new DataInputStream(socket.getInputStream());
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
