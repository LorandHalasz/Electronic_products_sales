package ro.utcluj.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.api.serviceInterface.ReportServiceInterface;
import ro.utcluj.entity.User;

import java.io.FileOutputStream;
import java.util.Map;

public class ReportPDF extends Report  {

    @Override
    public void createReport(String filePath, Map<UserBaseDTO, Integer> reportMap) {

        Document document = new Document( PageSize.A4,20,20,20,20 );

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath + "/BestUsers.pdf"));
            document.open();
            document.add(new Chunk(""));

            Integer crt = 1;

            for (Map.Entry<UserBaseDTO, Integer> entry : reportMap.entrySet()) {
                UserBaseDTO key = entry.getKey();
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
