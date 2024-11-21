import java.io.*;
import java.net.*;

public class FileClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost",9090)){
        socket.setSoTimeout(15000);
        OutputStream out = socket.getOutputStream();
        Writer writer = new OutputStreamWriter(out,"UTF-8");
        writer = new BufferedWriter(writer);

        // InputStream in = socket.getInputStream();
        // BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        // some  logic here
        writer.write("Bye Bye");
        writer.flush(); // flush the buffer
    }
    catch (IOException e){
        System.err.println(e.toString());
    }
    }
}
