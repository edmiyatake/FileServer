import java.io.*;
import java.net.*;

public class FileServer {
    public static void main(String[] args) throws UnknownHostException, IOException {
        final int PORT = 9090;
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
        try (ServerSocket server = new ServerSocket(PORT)){
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
            try (PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

                // doesn't need to be a loop just needs to open the connection until a command is given
                // receive the command from the client
                String command;
                command = in.readLine(); 

                if (command.equalsIgnoreCase("index")) {
                    // first we need to access the files inside the directory
                    String[] files = directory.list();
                    if (files != null){
                        for (String file : files){
                                // send each file in the array to client
                            out.println(file);
                        }
                        out.println("eol");
                    }
                    else {
                        out.println("error: directory does not exist");
                    }
                }
                else if (command.startsWith("get ")) {
                    String filename = command.substring(4).trim();
                    System.out.println(filename);
                    File selectedFile = new File(directory,filename);

                    if (selectedFile.exists() && selectedFile.isFile()){
                        out.println("ok");
                        // read file from server and send to client
                        try (BufferedReader fileReader = new BufferedReader(new FileReader(selectedFile))) {
                            String line;
                            while ((line = fileReader.readLine()) != null) {
                                out.println(line);
                            }
                        } 
                        catch (Exception e) {
                            System.out.print("1. This statement is running");
                            System.err.println(e.toString());
                        }
                        out.println("eof");
                    }
                    else {
                        // file does not exists, this statement is compiling correctly
                        // System.out.println("ERROR");
                        out.println("error");
                    }
                }
                else {
                    out.println("Invalid command: Please try again");
                }
            }
            
            catch (IOException e) {
                System.out.println("2. This error statement is running");
                System.err.println(e.toString());
            } 
            finally {
                try {
                    connection.close();
                }
                catch (IOException e) {
                    System.out.println("3. This error statement is running");
                    System.err.println(e.toString());
                } 
            }
        }
    }
}
