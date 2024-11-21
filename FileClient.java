import java.io.*;
import java.net.*;

public class FileClient {
    public static void main(String[] args) {
        // initiate the connection to the server
        try (Socket socket = new Socket("localhost",9090)){

        socket.setSoTimeout(15000);
        
        // writing to the server
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        out.println("Hello server!");
        
        // reading from the server
        InputStreamReader read = new InputStreamReader(socket.getInputStream());
        BufferedReader in = new BufferedReader(read);
        String serverResponse = in.readLine();
        System.out.println(serverResponse);


        // InputStream in = socket.getInputStream();
        // BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        // some  logic here
        socket.close();
        }
        catch (IOException e){
            System.err.println(e.toString());
        }
    }
}
