package ticketing_system;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TicketingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingSystemApplication.class, args);
    }

    @Bean
    //defining model mapper bean
    public ModelMapper modelMapper(){
    return new ModelMapper();
    }



}

