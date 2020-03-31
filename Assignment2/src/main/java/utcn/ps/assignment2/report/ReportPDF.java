package utcn.ps.assignment2.report;

import java.io.FileOutputStream;

import java.util.Map;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import utcn.ps.assignment2.entity.User;

public class ReportPDF implements Report {

    @Override
    public void createReport(String filePath, Map<User, Integer> reportMap) {

        Document document = new Document( PageSize.A4,20,20,20,20 );

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath + "/BestUsers.pdf"));
            document.open();

            Integer crt = 1;

            for (Map.Entry<User, Integer> entry : reportMap.entrySet()) {
                User key = entry.getKey();
                Integer value = entry.getValue();
                document.add( new Paragraph( crt + ". " + key.getUsername() + " orders " + value + " product/s"));
                document.add( Chunk.NEWLINE );
                crt++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        document.close();

    }
}
