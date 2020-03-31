package ro.utcluj.notification;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


@Component
public class NotificationService {

    private Socket socket;

    public NotificationService() throws IOException {
        this.socket = new Socket("localhost", 8043);
    }

    public void connectToNotificationServer(String userName){
        Thread listenerThread = new Thread(new ServerSocketListener(socket));
        System.out.println("Notifications enable for: " + userName);
        listenerThread.start();
    }

    public void sendMessageToServer(String message) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(message);
    }

}
