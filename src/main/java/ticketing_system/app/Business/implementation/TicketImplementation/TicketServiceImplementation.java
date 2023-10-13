package ticketing_system.app.Business.implementation.TicketImplementation;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticketing_system.app.Business.services.TicketServices.TicketService;
import ticketing_system.app.percistance.Entities.TicketEntities.Task;
import ticketing_system.app.percistance.Entities.TicketEntities.Ticket;
import ticketing_system.app.percistance.Enums.TicketPriorityLevel;
import ticketing_system.app.percistance.Enums.TicketStatus;
import ticketing_system.app.percistance.repositories.TicketRepositories.TicketRepository;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceImplementation implements TicketService {
    private ModelMapper modelMapper;
    private TicketRepository ticketRepository;
    @Autowired
    public TicketServiceImplementation(TicketRepository ticketRepository, ModelMapper modelMapper){
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }

    private TicketDTO convertToTicketDto(Ticket ticket){

        return modelMapper.map(ticket, TicketDTO.class);
    }

    private Ticket convertToTicket(TicketDTO ticketDTO){

        return modelMapper.map(ticketDTO, Ticket.class);
    }

    private Task convertToTask(TaskDTO taskDTO){

        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO convertToTaskDto(Task task){

        return modelMapper.map(task, TaskDTO.class);
    }
    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = convertToTicket(ticketDTO);
        Ticket savedTicket = this.ticketRepository.save(ticket);
        return convertToTicketDto(savedTicket);
    }

    @Override
    public TicketDTO getTicket(Long ticketId) {
        Ticket ticket = ticketRepository.getTicketByTicketId(ticketId);
        if (ticket == null){
            return null;
        }
        return convertToTicketDto(ticket);
    }
    @Override
    public TicketDTO updateTicket(Long id, TicketDTO ticketDTO) {
        Ticket ticket = convertToTicket(ticketDTO);
        if (ticketRepository.getTicketByTicketId(id) != null){
            ticket.setTicketId(id);
            Ticket updatedTicket = ticketRepository.save(ticket);
            return  convertToTicketDto(updatedTicket);
        } else {
            return null;
        }
    }
    @Transactional
    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public TicketDTO assignTicket(Long ticketId, String userEmail) {
        if (getTicket(ticketId) != null){
            //check is user performing action is admin
//            User user = findUserByEmail(userEmail);
//            Ticket assignedTicket = ticketRepository.getTicketByAgentAssigned(user);
//            return  convertToDto(assignedTicket);
            return null;
        } else {
            return null;
        }
    }

    @Override
    public TicketDTO updateTicketStatus(Long ticketId, TicketStatus ticketStatus) {
        Ticket ticket = ticketRepository.getTicketByTicketId(ticketId);
        if (ticket != null){
            if(ticketStatus == TicketStatus.CLOSED){
                //check is user performing action is admin
                return null;
            }
            ticket.setTicketStatus(ticketStatus);
            ticketRepository.save(ticket);
            return convertToTicketDto(ticket);
        } else {
            return null;
        }
    }

    @Override
    public TicketDTO updateTicketPriorityLevel(Long ticketId, TicketPriorityLevel ticketPriorityLevel) {
        Ticket ticket = ticketRepository.getTicketByTicketId(ticketId);
        if (ticket != null){
            ticket.setPriorityLevel(ticketPriorityLevel);
            ticketRepository.save(ticket);
            return convertToTicketDto(ticket);
        } else {
            return null;
        }
    }

    @Override
    public TicketDTO addTaskToTicket(Long ticketId, List<TaskDTO> taskDTOList) {
        Ticket ticket = ticketRepository.getTicketByTicketId(ticketId);
        List<Task> taskList = new ArrayList<>();
        if(ticket != null){
            for (TaskDTO taskDTO:taskDTOList ) {
                taskList.add(convertToTask(taskDTO));
            }
            ticket.setSubTasks(taskList);
            ticketRepository.save(ticket);
            return convertToTicketDto(ticket);
        } else {
            return null;
        }
    }
}
