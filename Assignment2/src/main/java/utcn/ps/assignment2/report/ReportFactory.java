package utcn.ps.assignment2.report;

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
