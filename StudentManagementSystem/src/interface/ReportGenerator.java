package interface_;

import java.util.List;

/**
 * ReportGenerator interface - Demonstrates Interface implementation.
 * This interface defines a contract for any class that generates reports.
 * Classes implementing this interface must provide the generateReport() method.
 * This is part of the Template Method and Strategy patterns.
 */
public interface ReportGenerator {
    /**
     * Generate a report with the given data
     * Different implementations can generate different types of reports
     * (Student reports, Course reports, Grade reports, Attendance reports, etc.)
     */
    String generateReport(List<?> data);

    /**
     * Get the title of the report
     */
    String getReportTitle();
}
