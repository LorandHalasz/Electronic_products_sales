package ro.utcluj.api.serviceInterface;

import ro.utcluj.api.dto.MessageBaseDTO;

import java.util.List;

public interface MessageServiceInterface {

    List<MessageBaseDTO> getMessages();
    int addMessage(String username, String messageText);
    int deleteMessage(Integer id);
}
