package ro.utcluj.notification;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@EnableScheduling
public class NotificationService {

    private static final int PORT_NUMBER = 8043;

    List<ClientSocketConnection> connectedClients = new CopyOnWriteArrayList<>();
    ConcurrentHashMap<String,ClientSocketConnection> connectionConcurrentHashMap = new ConcurrentHashMap<>();

    public NotificationService() {
        System.out.println();
    }

    @PostConstruct
    public void startServer() {
        Thread thread = new Thread(this::startNotificationServer);
        thread.start();
    }

    private void startNotificationServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientSocketConnection clientConnection = new ClientSocketConnection(clientSocket, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToAllClients(String message) {

        ClientSocketConnection clientSocketConnectionAdmin = connectionConcurrentHashMap.get("2");
        for (ClientSocketConnection connection : connectedClients) {
            if (!connection.equals(clientSocketConnectionAdmin))
                connection.sendMessage(message);
        }
    }

    public void sendMessageToSomeClients(String message) {

        Set<String> set = connectionConcurrentHashMap.keySet();
        for (String s : set) {
            if (message.contains("," + s + ","))
                connectionConcurrentHashMap.get(s).sendMessage(message);
        }
    }

    public void sendMessageToSomeClients(String message, List<Integer> usersId) {

        Set<String> set = connectionConcurrentHashMap.keySet();
        for(Integer i: usersId)
        for (String s : set) {
            if (usersId.contains(Integer.parseInt(s))){
                connectionConcurrentHashMap.get(s).sendMessage(message);
            }
        }
    }

    void removeClient(ClientSocketConnection clientConnection) {
        connectedClients.remove(clientConnection);
    }

}
