package ticketing_system.app.Business.implementation.EventsListeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents.CustomTicketStatusUpdateEvent;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.TicketStatusUpdateEvent;
import ticketing_system.app.Business.implementation.EmailSenderService.EmailSenderService;
import ticketing_system.app.Business.implementation.ReportService.TicketReportService;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;

@Service
public class TicketStatusUpdateEventsListener {
    @Autowired
    EmailSenderService senderService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketReportService reportService;
    @EventListener(CustomTicketStatusUpdateEvent.class)
    public void sendEmailOnTicketStatusUpdate(CustomTicketStatusUpdateEvent event) {
        TicketStatusUpdateEvent ticketStatusUpdateEvent = event.getTicketStatusUpdateEvent();
        Tickets ticket = (Tickets) ticketStatusUpdateEvent.getSource();
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
                +"Ticket Id: " + ticket.getTicketId()+" has been successfully resolved,<br>"
                + "To check the ticket details, click on the link below:<br>"
                + "<h5><a href=\"[[URL]]\" target=\"_self\">TICKET</a></h5>"
                + "Thank you,<br>"
                + "KeMaTCo LTD support team.";


        emailMessage = emailMessage.replace("[[name]]", userName);
        String verifyURL = "http://localhost:8080/api/v1/tickets/byId/" +ticket.getTicketId();

        emailMessage = emailMessage.replace("[[URL]]", verifyURL);

        assert loggedUser != null;
        System.out.println(loggedUser.getEmail());
        System.out.println(loggedUser.getUsername());
        senderService.sendSimpleEmail(loggedUser.getUsername(), "TICKET RESOLUTION", emailMessage);

    }
}
