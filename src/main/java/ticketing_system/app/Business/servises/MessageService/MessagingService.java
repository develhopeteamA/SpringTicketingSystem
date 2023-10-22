package ticketing_system.app.Business.servises.MessageService;

import ticketing_system.app.percistance.Entities.Message.Message;

import java.util.List;

public interface MessagingService {
    Message sendMessage(Long senderId, Long receiverId, String content);
    List<Message> getMessages(Long userId);

    void deleteMessage(Long messageId);


    Message editMessage(Long messageId, String content);
    List<Message> searchMessages(String keyword);
}
