package utcn.ps.assignment2.report;

import utcn.ps.assignment2.entity.User;
import utcn.ps.assignment2.entity.UserProduct;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportTXT implements Report {

    @Override
    public void createReport(String filePath, Map<User, Integer> reportMap) {

        File file = new File(filePath + "/BestUsers.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            Integer crt = 1;

            for (Map.Entry<User, Integer> entry : reportMap.entrySet()) {
                User key = entry.getKey();
                Integer value = entry.getValue();
                bufferedWriter.write(crt + ". " + key.getUsername() + " orders " + value + " product/s\n");
                crt++;
            }

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
