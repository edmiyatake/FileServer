import java.io.*;
import java.net.*;

public class FileClient {
    public static void main(String[] args) {
        // initiate the connection to the server
        try (Socket socket = new Socket("localhost",9090)){

            socket.setSoTimeout(15000);
        
            // writing to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            out.println("From Client: Hello server!");
        
            // reading from the server
            InputStreamReader read = new InputStreamReader(socket.getInputStream());
            BufferedReader in = new BufferedReader(read);
            String serverResponse = in.readLine();
            System.out.println(serverResponse);
        
            // read in command from the user
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please enter: index or get <filename>");
            String command = consoleInput.readLine().trim();
            
            // send the command to server
            out.println("You have chosen: " + command); // why doesn't this work??

        }
        catch (IOException e){
            System.err.println(e.toString());
        }
    }
}
