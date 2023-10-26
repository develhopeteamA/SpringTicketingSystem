package ticketing_system.app.preesentation.controler.ReportController;

import com.lowagie.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ticketing_system.app.Business.servises.ReportService.ReportService;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/report")
@Tag(name = "Reporting API")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(description = "Generate PDF Report REST API")
    @GetMapping(value = "/generate", produces = "application/pdf")
    public ResponseEntity<byte[]> generatePDFReport() {
        try {
            byte[] pdfBytes = reportService.generatePDFReport();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=report.pdf");
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (IOException e) {
            // Handle exceptions properly
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
