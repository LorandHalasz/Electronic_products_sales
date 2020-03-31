package ro.utcluj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.utcluj.api.dto.UserBaseDTO;
import ro.utcluj.api.serviceInterface.ReportServiceInterface;
import ro.utcluj.entity.User;
import ro.utcluj.mapper.UserMapper;
import ro.utcluj.report.Report;
import ro.utcluj.report.ReportFactory;
import ro.utcluj.repository.UserProductRepository;
import ro.utcluj.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class ReportService implements ReportServiceInterface {

    private UserProductRepository repository;
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public ReportService(UserRepository userRepository, UserProductRepository repository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.userMapper = userMapper;
    }

    public void report(String filePath, String reportType){
        List<User> userList = userRepository.findAll();
        Map<UserBaseDTO, Integer> map = new HashMap<>();

        for (User user: userList) {
            map.put(userMapper.mapUserBaseDTO(user), repository.findByUser(user).size());
            System.out.println(user + " " + repository.findByUser(user).size());
        }

        Map<UserBaseDTO, Integer> result = map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        ReportFactory reportFactory = new ReportFactory();
        Report report = reportFactory.createReport(reportType);
        report.createReport(filePath, result);
    }

}
