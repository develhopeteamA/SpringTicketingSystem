package ticketing_system.app.preesentation.controler;

import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ticketing_system.app.Business.servises.InternalMessageService;
import ticketing_system.app.Business.servises.User_service;
import ticketing_system.app.percistance.Entities.InternalMessage;
import ticketing_system.app.percistance.Entities.User;

import java.util.List;

@Controller
@RequestMapping("/messages")
public class InternalMessageController {

    @Autowired
    private InternalMessageService internalMessageService;

    @GetMapping
    public String viewMessages(Model model) {
        // Implement logic to retrieve and display messages
        List<InternalMessage> messages = internalMessageService.getAllMessagesForUser(currentUser); // Implement currentUser retrieval
        model.addAttribute("messages", messages);
        return "message/list"; // Thymeleaf template
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam("receiverId") Long receiverId, @RequestParam("content") String content) {
        // Implement logic to send a message
        User sender = currentUser; // Implement currentUser retrieval
        User receiver = User_service.getUserById(receiverId);
        internalMessageService.sendMessage(sender, receiver, content);
        return "redirect:/messages";
    }

    // Other messaging-related controller methods
}
