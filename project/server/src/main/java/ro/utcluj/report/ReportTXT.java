package ro.utcluj.report;

import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.api.serviceInterface.ReportServiceInterface;
import ro.utcluj.entity.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ReportTXT extends Report  {

    @Override
    public void createReport(String filePath, Map<UserBaseDTO, Integer> reportMap) {

        File file = new File(filePath + "/BestUsers.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            Integer crt = 1;

            for (Map.Entry<UserBaseDTO, Integer> entry : reportMap.entrySet()) {
                UserBaseDTO key = entry.getKey();
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
