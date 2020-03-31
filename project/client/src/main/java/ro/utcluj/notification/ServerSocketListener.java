package ro.utcluj.notification;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Runnable listening for incoming messages from the server
 */
public class ServerSocketListener implements Runnable {

    private Socket serverSocket;


    public ServerSocketListener(Socket serverSocket) {
        this.serverSocket = serverSocket;

    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            while (true) {
                String message = in.readLine();
                if (message.contains("already connected")){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("The user with this username is already connected!");
                            alert.setContentText(message);
                            alert.showAndWait();
                        }
                    });
                    TimeUnit.SECONDS.sleep(1);
                    System.exit(0);
                }
                if (message.contains("limited stock")){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Limited stock");
                            alert.setHeaderText("Last chances!");
                            alert.setContentText(message);
                            alert.showAndWait();
                        }
                    });

                }
                if (message.contains("message")){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("New Message");
                            alert.setHeaderText("Refresh now the page");
                            alert.setContentText(message);
                            alert.showAndWait();
                        }
                    });

                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("The server is no longer available");
        }
    }

}
