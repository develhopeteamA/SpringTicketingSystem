package ticketing_system.app.preesentation.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.servises.InternalMessageService;
import ticketing_system.app.Business.servises.User_service;
import ticketing_system.app.percistance.Entities.InternalMessage;
import ticketing_system.app.percistance.Entities.User;

import java.util.List;

@Controller
@RequestMapping("/messages")
public class InternalMessageController {

    private final InternalMessageService internalMessageService;
    private final User_service user_service;

    @Autowired
    public InternalMessageController(InternalMessageService internalMessageService, User_service user_service) {
        this.internalMessageService = internalMessageService;
        this.user_service = user_service;
    }

    @GetMapping("/user/{userId}")
    public List<InternalMessage> getAllMessagesForUser(@PathVariable Long userId) {
        User user = user_service.getUserById(userId);
        return internalMessageService.getAllMessagesForUser(user);
    }

    @PostMapping("/send")
    public void sendMessage(@RequestParam("senderId") Long senderId, @RequestParam("receiverId") Long receiverId, @RequestParam("content") String content) {
        User sender = user_service.getUserById(senderId);
        User receiver = user_service.getUserById(receiverId);
        internalMessageService.sendMessage(sender, receiver, content);
    }

    @GetMapping("/unread/user/{userId}")
    public List<InternalMessage> getUnreadMessagesForUser(@PathVariable Long userId) {
        User user = user_service.getUserById(userId);
        return internalMessageService.getUnreadMessagesForUser(user);
    }

    @PutMapping("/markAsRead/{messageId}")
    public void markMessageAsRead(@PathVariable Long messageId) {
        InternalMessage message = internalMessageService.getMessageById(messageId);
        internalMessageService.markMessageAsRead(message);
    }

    @GetMapping("/conversation/{user1Id}/{user2Id}")
    public List<InternalMessage> getConversationBetweenUsers(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        User user1 = user_service.getUserById(user1Id);
        User user2 = user_service.getUserById(user2Id);
        return internalMessageService.getAllMessagesBetweenUsers(user1, user2);
    }

    // Other messaging-related controller methods
}
