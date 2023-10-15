package ticketing_system.app.preesentation.controler.massageController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.servises.MessageService.MessagingService;
import ticketing_system.app.percistance.Entities.Message.Message;

import java.util.List;

@RestController
@RequestMapping("api/v1/messages")
public class MessagingController {

    private final MessagingService messagingService;

    @Autowired
    public MessagingController(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestParam("senderId") Long senderId,
                                               @RequestParam("receiverId") Long receiverId,
                                               @RequestParam("content") String content) {
        try {
            Message message = messagingService.sendMessage(senderId, receiverId, content);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/inbox/{userId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable("userId") Long userId) {
        try {
            List<Message> messages = messagingService.getMessages(userId);
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable("messageId") Long messageId) {
        try {
            messagingService.deleteMessage(messageId);
            return ResponseEntity.ok("Message deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Message>> searchMessages(@RequestParam("keyword") String keyword) {
        try {
            List<Message> messages = messagingService.searchMessages(keyword);
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<Message> editMessage(@PathVariable("messageId") Long messageId,
                                               @RequestParam("content") String content) {
        try {
            Message editedMessage = messagingService.editMessage(messageId, content);
            return ResponseEntity.ok(editedMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}