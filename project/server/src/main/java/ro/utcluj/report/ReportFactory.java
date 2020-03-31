package ro.utcluj.report;

import ro.utcluj.api.serviceInterface.ReportServiceInterface;

public class ReportFactory {

    public Report createReport(String reportType){
        if (reportType.equalsIgnoreCase("txt"))
            return new ReportTXT();
        else
            if (reportType.equalsIgnoreCase("pdf"))
                return new ReportPDF();
        return null;
    }
}
