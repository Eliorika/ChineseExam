package ru.rsreu.report;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.eclipse.birt.report.engine.api.EngineException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.report.birt.ReportGenerator;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@AllArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportGenerator reportGenerator;

    @PostMapping()
    public ResponseEntity<byte[]> report(@RequestParam Integer userId, @RequestParam Integer courseId, HttpServletResponse response, HttpServletRequest request) throws SQLException, IOException, EngineException {
        byte[] reportResource = reportGenerator.generateReport(userId, courseId, response, request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(reportResource);
    }



}