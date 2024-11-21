import java.io.*;
import java.net.*;

public class FileServer {
    public static void main(String[] args) throws UnknownHostException, IOException {
        // create a socket that is looking for incoming connections
        ServerSocket server = new ServerSocket(9090);

        // attempt to connect with the client
        try (Socket connection = server.accept()) {

            // writing to the client
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
            out.println("Hello client");

            // reading from the client
            InputStreamReader read = new InputStreamReader(connection.getInputStream());
            BufferedReader in = new BufferedReader(read);
            String clientResponse = in.readLine();
            System.out.println(clientResponse);

            // close all connections
            connection.close();
        } 
        catch (IOException e) {
            System.err.println(e.toString());
        } 
        finally {
            try {
                server.close();
            }
            catch (IOException e) {
                System.err.println(e.toString());
            } 
        }
    }
}