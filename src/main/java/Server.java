import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private int port;
    private String directory;

    public Server(int port, String directory) {
        this.port = port;
        this.directory = directory;
    }

    void start() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            ConfigurationServer configurationServer = new ConfigurationServer();
            configurationServer.start();
            while (true) {
                Socket socket = serverSocket.accept();
                Handler thread = new Handler(socket, configurationServer.getDirectory(), configurationServer.getHeadsList());
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String getDirectory() {
        return directory;
    }

    public synchronized void setDirectory(String directory) {
        this.directory = directory;
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        String directory = args[1];
        new Server(port, directory).start();
    }
}
