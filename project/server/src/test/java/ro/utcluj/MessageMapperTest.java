package ro.utcluj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.dto.MessageBaseDTO;
import ro.utcluj.entity.Message;
import ro.utcluj.mapper.MessageMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MessageMapperTest {
    
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private MessageMapper messageMapper;
    private Message message;
    private MessageBaseDTO messageBaseDTO;

    @Before
    public void setup(){
        messageMapper = new MessageMapper();
        message = new Message("user", "New message", new Date(System.currentTimeMillis()));
        message.setIdmessage(1);
        messageBaseDTO = new MessageBaseDTO("user", "New message", new Date(System.currentTimeMillis()));
        messageBaseDTO.setId(1);
    }

    @Test
    public void messageBaseDTOTest(){
        MessageBaseDTO messageBaseDTOExpected = messageMapper.messageBaseDTO(message);

        Assert.assertEquals(messageBaseDTO.getId(), messageBaseDTOExpected.getId());
    }

    @Test
    public void mapMessageBaseDTOListTest(){
        List<MessageBaseDTO> messageBaseDTOSExpected = messageMapper.mapMessageBaseDTOList(new ArrayList<>());

        Assert.assertEquals(new ArrayList<>(), messageBaseDTOSExpected);
    }

}
