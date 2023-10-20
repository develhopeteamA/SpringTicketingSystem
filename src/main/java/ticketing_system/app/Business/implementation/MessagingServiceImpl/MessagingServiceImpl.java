package ticketing_system.app.Business.implementation.MessagingServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.servises.MessageService.MessagingService;
import ticketing_system.app.percistance.Entities.Message.Message;
import ticketing_system.app.percistance.repositories.MessageRepository.MessageRepository;


import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class MessagingServiceImpl implements MessagingService {

    java.sql.Timestamp currentTimestamp = java.sql.Timestamp.from(Instant.now());
    String pattern = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    String formattedTimestamp = currentTimestamp.toLocalDateTime().format(dateTimeFormatter);
    java.sql.Timestamp currentTimestampFormatted = Timestamp.valueOf(formattedTimestamp);

    private final MessageRepository messageRepository;


    @Autowired
    public MessagingServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message sendMessage(Long senderId, Long receiverId, String content) {
        // Implement the logic to send a message
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return messageRepository.save(message);
    }

    @Override
    public Message editMessage(Long messageId, String content) {
        // Implement the logic to edit a message by ID
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setContent(content);
            message.setTimestamp(new Timestamp(System.currentTimeMillis()));
            return messageRepository.save(message);
        } else {
            throw new IllegalArgumentException("Message not found");
        }
    }

    @Override
    public List<Message> searchMessages(String keyword) {
        return messageRepository.findAll().stream()
                .filter(message -> message.getContent().contains(keyword))
                .collect(Collectors.toList());
    }


    @Override
    public List<Message> getMessages(Long userId) {
        // Implement the logic to retrieve messages for a specific user
        return messageRepository.findAll().stream()
                .filter(message -> message.getReceiverId().equals(userId))
                .collect(Collectors.toList());
    }
    @Override
    public void deleteMessage(Long messageId) {
        // Implement the logic to delete a message by ID
        messageRepository.deleteById(messageId);

    }
}