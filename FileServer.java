import java.io.*;
import java.net.*;

public class FileServer {
    public static void main(String[] args) throws UnknownHostException, IOException {
        
        // check for valid command line 
        if (args.length != 1) {
            System.out.println("Invalid command: Please enter in the format");
            System.out.println("java Server <directoryPath>");
            return;
        }

        // User gives server a directory
        File directory = new File(args[0]);
        
        // Server must check validity of directory 
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory!");
            return;
        }

        // create a socket that is looking for incoming connections
        try (ServerSocket server = new ServerSocket(9090)){
            System.out.println("Server has begun looking for clients...");
            while (true) {
                try {
                    // attempt to connect with the client
                    Socket connection = server.accept();
                    // start the logic with the socket
                    Thread task1 = new FileThread(connection, directory);
                    task1.start();
                } 
                catch (IOException e) {
                    System.err.println(e.toString());
                }
                
            }
        }
    }

    private static class FileThread extends Thread {
        private final Socket connection;
        private final File directory;

        public FileThread(Socket connection, File directory){
            this.connection = connection;
            this.directory = directory;
        }

        @Override
        public void run() {
            try {
                // writing to the client
                PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
                out.println("From Server: Successful connection with the client!");

                // reading from the client
                InputStreamReader read = new InputStreamReader(connection.getInputStream());
                BufferedReader in = new BufferedReader(read);
                String clientResponse = in.readLine();
                System.out.println(clientResponse);

                // Take in the command from Client
                String clientCommand = in.readLine();
                System.out.println(clientCommand);

                // close all connections
                connection.close();
            } 
            catch (IOException e) {
                System.err.println(e.toString());
            } 
            finally {
                try {
                    connection.close();
                }
                catch (IOException e) {
                    System.err.println(e.toString());
                } 
            }
        }
    }
}