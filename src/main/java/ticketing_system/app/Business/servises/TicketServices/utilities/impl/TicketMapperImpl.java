package ticketing_system.app.Business.servises.TicketServices.utilities.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.servises.TicketServices.utilities.TicketMapper;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.preesentation.data.TicketData.TicketAgentDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketNormalDTO;

/**
 * implementation of the ticket mapper.
 * @author kamar baraka.*/

@Service
@RequiredArgsConstructor
public class TicketMapperImpl implements TicketMapper {

    /*inject the mapper*/
    private final ModelMapper mapper;

    /**
     * map {@link TicketDTO} to {@link Tickets}*/
    @Override
    public Tickets mapToTicket(TicketDTO ticketDTO) {

        return mapper.map(ticketDTO, Tickets.class);
    }

    /**
     * map {@link Tickets} to {@link TicketDTO}*/
    @Override
    public TicketDTO mapToDTO(Tickets tickets) {

        return mapper.map(tickets, TicketDTO.class);
    }

    /**
     * map {@link Tickets} to {@link TicketNormalDTO}*/
    @Override
    public TicketNormalDTO mapToNormalDTO(Tickets tickets) {

        return mapper.map(tickets, TicketNormalDTO.class);
    }

    /**
     * map {@link Tickets} to {@link TicketAgentDTO}*/
    @Override
    public TicketAgentDTO mapToAgentDTO(Tickets tickets) {

        return mapper.map(tickets, TicketAgentDTO.class);
    }
}
