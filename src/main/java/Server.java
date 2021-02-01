import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private String directory;

    public Server(int port, String directory) {
        this.port = port;
        this.directory = directory;
    }

    void start(){
        try(ServerSocket serverSocket = new ServerSocket(this.port)){
            while (true){
                Socket socket = serverSocket.accept();
                Thread thread = new Handler(socket, this.directory);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port  = Integer.parseInt(args[0]);
        String directory = args[1];
        new Server(port,directory).start();
    }
}
