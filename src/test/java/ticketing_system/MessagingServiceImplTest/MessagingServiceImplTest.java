package ticketing_system.MessagingServiceImplTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ticketing_system.app.Business.implementation.MessagingServiceImpl.MessagingServiceImpl;
import ticketing_system.app.percistance.Entities.Message.Message;
import ticketing_system.app.percistance.repositories.MessageRepository.MessageRepository;
import ticketing_system.app.preesentation.data.MessageDTO.MessageDTO;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MessagingServiceImplTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessagingServiceImpl messagingService;

    @Test
    public void sendMessageTest() {
        MessageDTO messageDTO = new MessageDTO(1L, 2L, "Hello");
        Message message = new Message(1L, 2L, "Hello");
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        Message savedMessage = messagingService.sendMessage(messageDTO.getSenderId(), messageDTO.getReceiverId(), messageDTO.getContent());
        Assertions.assertEquals(messageDTO.getContent(), savedMessage.getContent());
    }

    @Test
    public void getMessagesTest() {
        Message message1 = new Message(1L, 2L, "Hello");
        Message message2 = new Message(2L, 1L, "Hi");
        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        when(messageRepository.findAll()).thenReturn(messages);
        List<Message> receivedMessages = messagingService.getMessages(2L);
        assertEquals(1, receivedMessages.size());
        assertEquals("Hello", receivedMessages.get(0).getContent());
    }
    @Test
    public void deleteMessageTest() {
        Message message = new Message(1L, 2L, "Hello");
        when(messageRepository.findById(1L)).thenReturn(java.util.Optional.of(message));
        messagingService.deleteMessage(1L);
        assertEquals(0, messagingService.getMessages(2L).size());
    }

    @Test
    public void searchMessagesTest() {
        Message message1 = new Message(1L, 2L, "Hello");
        Message message2 = new Message(2L, 1L, "Hi");
        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        when(messageRepository.findAll()).thenReturn(messages);
        List<Message> searchedMessages = messagingService.searchMessages("Hello");
        assertEquals(1, searchedMessages.size());
        assertEquals("Hello", searchedMessages.get(0).getContent());
    }
    @Test
    public void editMessageTest() {
        Message message = new Message(1L, 2L, "Hello");
        when(messageRepository.findById(1L)).thenReturn(java.util.Optional.of(message));
        Message editedMessage = messagingService.editMessage(1L, "Hello, edited");
        assertEquals("Hello, edited", editedMessage.getContent());
    }


}