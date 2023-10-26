package ticketing_system.app.Business.implementation.ReportServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ticketing_system.app.Business.servises.ReportService.ReportService;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final UserRepository userRepository;
    private final TemplateEngine templateEngine;

    @Autowired
    public ReportServiceImpl(UserRepository userRepository, TemplateEngine templateEngine) {
        this.userRepository = userRepository;
        this.templateEngine = templateEngine;
    }

    @Override
    public byte[] generatePDFReport() throws IOException, com.lowagie.text.DocumentException {
        List<Users> users = userRepository.findAll(); // Fetch all users, adjust this based on your requirements
        Context context = new Context();
        context.setVariable("users", users);

        String html = templateEngine.process("reportTemplate", context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            return outputStream.toByteArray();
        }
    }
}
