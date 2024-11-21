import java.io.*;
import java.net.*;

public class FileServer {
    public static void main(String[] args) throws UnknownHostException, IOException {
        ServerSocket server = new ServerSocket(9090);
        try (Socket connection = server.accept()) {
        OutputStream out = connection.getOutputStream();
        Writer writer = new OutputStreamWriter(out,"ASCII");
        writer.write("Hello Client!");
        writer.flush();
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