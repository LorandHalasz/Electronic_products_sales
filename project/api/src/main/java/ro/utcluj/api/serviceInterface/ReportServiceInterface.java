package ro.utcluj.api.serviceInterface;

import ro.utcluj.api.dto.UserBaseDTO;

import java.util.Map;

public interface ReportServiceInterface {

    void report(String filePath, String reportType);
}
