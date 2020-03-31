package ro.utcluj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.utcluj.api.dto.MessageBaseDTO;
import ro.utcluj.api.serviceInterface.MessageServiceInterface;
import ro.utcluj.entity.Message;
import ro.utcluj.entity.User;
import ro.utcluj.mapper.MessageMapper;
import ro.utcluj.notification.NotificationService;
import ro.utcluj.repository.MessageRepository;
import ro.utcluj.repository.UserRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class MessageService implements MessageServiceInterface {

    private MessageRepository repository;
    private UserRepository userRepository;
    private MessageMapper messageMapper;

    @Autowired
    public NotificationService notificationService;

    @Autowired
    public MessageService(MessageRepository repository, UserRepository userRepository, MessageMapper messageMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.messageMapper = messageMapper;
    }

    public List<MessageBaseDTO> getMessages(){
        List<MessageBaseDTO> messages = new ArrayList<MessageBaseDTO>();
        messages.addAll(messageMapper.mapMessageBaseDTOList(repository.findAll()));
        return messages;
    }

    public int addMessage(String username, String messageText){
        try {
            repository.save(new Message(username, messageText, new Date(System.currentTimeMillis())));
            List<Integer> users = new ArrayList<Integer>();
            for(User user: userRepository.findAll()){
                System.out.println(user);
                if(user.getUser_role().equalsIgnoreCase("admin"))
                    users.add(user.getIduser());}
            notificationService.sendMessageToSomeClients("You have received a new message!", users);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public int deleteMessage(Integer id){
        try {
            repository.deleteById(id);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

}
