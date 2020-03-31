package utcn.ps.assignment2.report;

import utcn.ps.assignment2.entity.User;

import java.util.Map;

public interface Report {
    void createReport(String filePath, Map<User, Integer> reportMap);
}
