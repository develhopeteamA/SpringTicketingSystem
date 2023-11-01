package ticketing_system.app.Business.implementation.EventsListeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents.CustomAddTaskToTicketEvent;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.AddTaskToTicketEvent;
import ticketing_system.app.Business.implementation.EmailSenderService.EmailSenderService;
import ticketing_system.app.Business.implementation.ReportService.TicketReportService;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;

@Service
public class AddTaskToTicketEventsListener {
    @Autowired
    EmailSenderService senderService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketReportService reportService;
    @EventListener(CustomAddTaskToTicketEvent.class)
    public void sendEmailOnAgentAssignment(CustomAddTaskToTicketEvent event) {
        AddTaskToTicketEvent addTaskToTicketEvent = event.getAddTaskToTicketEvent();
        Tickets ticket = (Tickets) addTaskToTicketEvent.getSource();
        UserDetails userDetails = event.getUserDetails();
        Users loggedUser = userRepository.findByEmail(ticket.getTicketCreator());
        String userName = "";
        if (loggedUser!=null){
            System.out.println(userName);
            userName = loggedUser.getFirstname();
        }
        System.out.println(userName);
        // Send an email using the sender service

        String emailMessage = "Dear [[name]],<br>"
                +"We have received an additional task related to ticket of  Id: " + ticket.getTicketId()+" ,<br>"
                +"We are working on your ticket, update us on any further issue from your end, <br>"
                + "To check the ticket details, click on the link below:<br>"
                + "<h5><a href=\"[[URL]]\" target=\"_self\">TICKET</a></h5>"
                +"We appreciate your effort to give us an elaborate and clear image on the ticket by providing additional tasks, We value your satisfaction and feedback, <br>"
                + "Thank you,<br>"
                + "KeMaTCo LTD support team.";


        emailMessage = emailMessage.replace("[[name]]", userName);
        String verifyURL = "http://localhost:8080/api/v1/tickets/byId/" +ticket.getTicketId();

        emailMessage = emailMessage.replace("[[URL]]", verifyURL);

        assert loggedUser != null;
        senderService.sendSimpleEmail(loggedUser.getUsername(), "TICKET ADD ONS", emailMessage);

    }
}
