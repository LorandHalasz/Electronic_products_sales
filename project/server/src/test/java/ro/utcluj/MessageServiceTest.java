package ro.utcluj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.MessageBaseDTO;
import ro.utcluj.entity.Message;
import ro.utcluj.mapper.MessageMapper;
import ro.utcluj.notification.NotificationService;
import ro.utcluj.repository.MessageRepository;
import ro.utcluj.repository.UserRepository;
import ro.utcluj.service.MessageService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

public class MessageServiceTest {
    @Mock private MessageRepository messageRepository;
    @Mock private UserRepository userRepository;
    @Mock private MessageMapper messageMapper;
    @Mock private NotificationService notificationService;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    private MessageService messageService;
    private Message message;

    @Before
    public void setup(){
        messageService = new MessageService(messageRepository, userRepository, messageMapper);
        notificationService = new NotificationService();
        message = new Message("user", "New message", new Date(System.currentTimeMillis()));
        message.setIdmessage(1);
    }

    @Test
    public void getMessagesTest(){
        when(messageRepository.findAll()).thenReturn(new ArrayList<>());
        when(messageMapper.mapMessageBaseDTOList(new ArrayList<>())).thenReturn(new ArrayList<>());

        List<MessageBaseDTO> messages = messageService.getMessages();

        Assert.assertEquals(messages, new ArrayList<>());

        verify(messageRepository, times(1)).findAll();
        verify(messageMapper, times(1)).mapMessageBaseDTOList(anyList());
    }

    @Test
    public void addMessageTest(){
        when(messageRepository.save(message)).thenReturn(message);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        int status = messageService.addMessage("user", "New message");

        Assert.assertEquals(status, 0);

        verify(messageRepository, times(1)).save(any());
        verify(userRepository, atLeast(1)).findAll();
    }

    @Test
    public void deleteMessageTest(){
        int status = messageService.deleteMessage(1);

        Assert.assertEquals(status, 1);
    }
}
