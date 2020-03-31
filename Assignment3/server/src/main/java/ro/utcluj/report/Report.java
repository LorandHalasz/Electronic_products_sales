package ro.utcluj.report;

import ro.utcluj.entity.User;
import java.util.Map;

public interface Report {
    void createReport(String filePath, Map<User, Integer> reportMap);
}
