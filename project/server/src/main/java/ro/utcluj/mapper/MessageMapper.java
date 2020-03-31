package ro.utcluj.mapper;

import org.springframework.stereotype.Component;
import ro.utcluj.api.dto.MessageBaseDTO;
import ro.utcluj.entity.Message;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageMapper {

    public MessageBaseDTO messageBaseDTO(Message message){
        return new MessageBaseDTO(message.getIdmessage(),message.getUsername(), message.getMessagetext(), message.getDate());
    }

    public List<MessageBaseDTO> mapMessageBaseDTOList(List<Message> list){
        List<MessageBaseDTO> mappedList = new ArrayList<>();
        for(Message message : list){
            mappedList.add(messageBaseDTO(message));
        }

        return mappedList;
    }
}

