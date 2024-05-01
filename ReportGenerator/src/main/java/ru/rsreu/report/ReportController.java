package ru.rsreu.report;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.eclipse.birt.report.engine.api.EngineException;
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
    public void report(@RequestParam Integer userId, @RequestParam Integer courseId, HttpServletResponse response, HttpServletRequest request) throws SQLException, IOException, EngineException {
        reportGenerator.generateReport(userId, courseId, response, request);
    }



}