package ticketing_system.app.Business.servises.ReportService;


import com.lowagie.text.DocumentException;

import java.io.IOException;

public interface ReportService {
    byte[] generatePDFReport() throws IOException, DocumentException;
}
