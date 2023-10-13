package ticketing_system.app.Business.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ticketing_system.app.percistance.Entities.InternalMessage;
import ticketing_system.app.percistance.Entities.User;
import ticketing_system.app.percistance.repositories.InternalMessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InternalMessageService {

    @Autowired
    private InternalMessageRepository internalMessageRepository;

    public List<InternalMessage> getAllMessagesForUser(User user) {
        return internalMessageRepository.findByReceiverOrderByTimestampDesc(user);
    }

    public void sendMessage(User sender, User receiver, String content) {
        InternalMessage message = new InternalMessage();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        internalMessageRepository.save(message);
    }


    public InternalMessage getMessageById(Long messageId) {
        return internalMessageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message not found with ID: " + messageId));
    }

    public List<InternalMessage> getAllMessagesBetweenUsers(User user1, User user2) {

        return internalMessageRepository.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampDesc(
                user1, user2, user1, user2);
    }

    public List<InternalMessage> getUnreadMessagesForUser(User user) {

        return internalMessageRepository.findByReceiverAndReadIsFalse(user);
    }

    public void markMessageAsRead(InternalMessage message) {

        message.setRead(true);
        internalMessageRepository.save(message);
    }
}
