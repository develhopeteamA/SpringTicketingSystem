package ticketing_system.app.Business.implementation.EventsListeners;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.implementation.EmailSenderService.EmailSenderService;
import ticketing_system.app.Business.implementation.ReportService.TicketReportService;
import ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents.CustomTicketCreatedEvent;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.TicketCreatedEvent;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;

import java.io.FileNotFoundException;

@Service
public class TicketEventsListener {
    @Autowired
    EmailSenderService senderService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketReportService reportService;

    String email = "";
    @EventListener(CustomTicketCreatedEvent.class)
    public void sendEmailOnTicketCreation(CustomTicketCreatedEvent event) {
        TicketCreatedEvent ticketCreatedEvent = event.getTicketCreatedEvent();
        Tickets ticket = (Tickets) ticketCreatedEvent.getSource();
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
                +"We have received your ticket with ticket Id: " + ticket.getTicketId()+" ,<br>"
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
        senderService.sendSimpleEmail(loggedUser.getUsername(), "TICKET CREATED SUCCESSFULLY", emailMessage);

    }
}
