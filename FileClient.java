import java.io.*;
import java.net.*;

public class FileClient {
    public static void main(String[] args) {
        // need to retrieve serverAddress from command line
        if (args.length < 1) {
            System.out.println("Usage: java Client <server-address>");
            return;
        }
        String serverAddress = args[0];
        final int PORT = 9090;

        // start the connection to the server
        try (Socket socket = new Socket(serverAddress,PORT);
            // writing to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            // reading from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // reading from the user to the client
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            socket.setSoTimeout(10000); // recommended timeout
        
            // writing to the server
            // System.out.println("Successful connection!");
            System.out.println("Type 'index' or 'get <filename>'");
        
            // get command from user
            String command; 
            command = consoleInput.readLine().trim();
            
            //System.out.println("you have selected: " + command);
            out.println(command);

            String response;
            while ((response = in.readLine()) != null) {
                if (response.equals("eol") || response.equals("eof")){
                    break;
                }
                System.out.println(response);
            }
        }
        catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
