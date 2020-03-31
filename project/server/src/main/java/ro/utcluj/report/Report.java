package ro.utcluj.report;

import ro.utcluj.api.dto.UserBaseDTO;

import java.util.Map;

public abstract class Report {

    public abstract void createReport(String filePath, Map<UserBaseDTO, Integer> reportMap);

}
