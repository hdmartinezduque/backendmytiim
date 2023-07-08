package co.com.template.services;

import co.com.template.Repositories.*;
import co.com.template.Repositories.dto.EmployeeData;
import co.com.template.Repositories.dto.EmployeeDataCommitment;
import co.com.template.Repositories.dto.IndicatorDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.entities.*;
import co.com.template.utils.Constants;
import co.com.template.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;


@RequiredArgsConstructor
@Service
@Transactional
@Log4j2
public class IndicatorService {

    private final UserRepository userRepository;

    private final ObjectiveRepository objectiveRepository;

    private final PeriodRepository periodRepository;

    private final GroupRepository groupRepository;

    private final CommitmentRepository commitmentRepository;
    private final PeriodService periodService;


    public ResponseDTO getIndicatorObjective(Long periodId) {
        try {
            Period period;
            if (periodId.equals(Constants.ZERO_INDEX)) {
                period = periodRepository.findTopByOrderByEndPeriodDesc();
            } else {
                period = periodRepository.findByPeriodId(periodId);
            }
            List<User> allUsers = userRepository.findAll();
            int totalUsers = allUsers.size();

            List<Objective> objectivesInPeriod = objectiveRepository.findByCreateDateBetween(
                    period.getStartPeriod(), period.getEndPeriod());
            Set<Long> userIdsWithObjectives = objectivesInPeriod.stream()
                    .map(Objective::getUser)
                    .map(User::getUserId)
                    .collect(Collectors.toSet());
            int usersWithObjectives = userIdsWithObjectives.size();

            double percentageObjectivesCreated = (double) usersWithObjectives / totalUsers * Constants.ONE_HUNDRED_INDEX;

            double percentageObjectivesNoCreated = Constants.ONE_HUNDRED_INDEX - percentageObjectivesCreated;

            IndicatorDTO indicatorDTO = new IndicatorDTO(totalUsers, percentageObjectivesCreated, percentageObjectivesNoCreated);

            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, indicatorDTO);
        } catch (Exception err) {
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }

    }


    public List<EmployeeData> generateCSVFile(Long periodId) {
        try {

            Period period;
            if (periodId.equals((long) Constants.ZERO_INDEX)) {
                period = periodRepository.findTopByOrderByEndPeriodDesc();
            } else {
                period = periodRepository.findByPeriodId(periodId);
            }

            /*List<User> users = userRepository.findAll();
            LocalDate today = LocalDate.now();
            Period currentPeriod = periodRepository.findByEndPeriodGreaterThanEqualAndStartPeriodLessThanEqual(today, today);
            Period lastPeriod = periodRepository.findTopByOrderByEndPeriodDesc();*/
            
            List<EmployeeData> employeeDataList = new ArrayList<>();

            Period per = periodRepository.findByPeriodId(period.getPeriodId());

            //List<Objective> objectiveList = objectiveRepository.findAll();

            //LocalDate today = LocalDate.now();

            //Period lastPeriod = periodRepository.findTopByOrderByEndPeriodDesc();

            List<User> userList = userRepository.findByStatusStatusId(Constants.ACTIVE_USER);
            Period lastPeriod = periodRepository.findTopByOrderByEndPeriodDesc();

            for (User userModel : userList) {

                String username = userModel.getUser();
                String fullName = userModel.getUserName()+" ".concat(userModel.getUserLastName());
                String email = userModel.getUserEmail();
                String periodDescribe = per.getDescribe();
                String team = userModel.getGroup().getGroupDescribe();
                User userLeader = userRepository.findByUserId(userModel.getLeaderId());
                String teamLeader = userLeader.getUserName()+" ".concat(userLeader.getUserLastName());

                int totalObjectives = objectiveRepository.countByUserAndPeriod(userModel, period);

                String lastLogin = (Util.convertToDateTimeHourFormatted(userModel.getLastLogin(), Constants.DATE_FORMAT));

                EmployeeData employeeData = new EmployeeData(username, fullName, email, periodDescribe, team, teamLeader, totalObjectives, lastLogin);
                employeeDataList.add(employeeData);

            }
            return employeeDataList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String generateCSVContent(List<EmployeeData> employeeDataList) {
        StringBuilder csvContentBuilder = new StringBuilder();

        csvContentBuilder.append("Usuario|Nombre completo|Email|Periodo|Equipo|Nombre del lider|Objetivos creados|Ultimo ingreso");
        csvContentBuilder.append(System.lineSeparator());

        for (EmployeeData employeeData : employeeDataList) {
            csvContentBuilder.append(employeeData.getUsername()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getFullName()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getEmail()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getPeriod()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getTeam()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getTeamLeader()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getTotalObjectives()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getLastLogin()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(System.lineSeparator());
        }

        return csvContentBuilder.toString();
    }


    public ResponseDTO getIndicatorCommitment(Long periodId) {
        try {
            Period period;
            if (periodId.equals(Constants.ZERO_INDEX)) {
                period = periodRepository.findTopByOrderByEndPeriodDesc();
            } else {
                period = periodRepository.findByPeriodId(periodId);
            }
            List<User> allUsers = userRepository.findAll();
            int totalUsers = allUsers.size();

            List<Commitment> commitmentInPeriod = commitmentRepository.findByCreateDateBetween(
                    period.getStartPeriod(), period.getEndPeriod());
            Set<Long> userIdsWithCommitment = commitmentInPeriod.stream()
                    .map(Commitment::getObjective)
                    .map(Objective::getUser)
                    .map(User::getUserId)
                    .collect(Collectors.toSet());
            int usersWithCommitment = userIdsWithCommitment.size();

            double percentageCommitmentCreated = (double) usersWithCommitment / totalUsers * Constants.ONE_HUNDRED_INDEX;

            double percentageCommitmentNoCreated = Constants.ONE_HUNDRED_INDEX - percentageCommitmentCreated;

            IndicatorDTO indicatorDTO = new IndicatorDTO(totalUsers, percentageCommitmentCreated, percentageCommitmentNoCreated);

            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, indicatorDTO);
        } catch (Exception err) {
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }

    }
    public List<EmployeeDataCommitment> generateCommitmentCSVFile() {
        Long periodId = periodService.getActualPeriod().getPeriodId();
        return this.generateCommitmentCSVFile(periodId);
    }

    public List<EmployeeDataCommitment> generateCommitmentCSVFile(Long periodId) {
        try {
            List<EmployeeDataCommitment> employeeDataCommitmentList = new ArrayList<>();
            Period period = periodRepository.findByPeriodId(periodId);
            List<Commitment> commitment = commitmentRepository.findAll();
            for (Commitment commit : commitment) {
                String username = commit.getObjective().getUser().getUser();
                String fullName = commit.getObjective().getUser().getUserName().concat(commit.getObjective().getUser().getUserLastName());
                String email = commit.getObjective().getUser().getUserEmail();
                String per = commit.getObjective().getPeriod().getDescribe(); // con @Column
                String team = commit.getObjective().getUser().getGroup().getGroupDescribe();
                String teamLeader = userRepository.findByUserId(commit.getObjective().getUser().getLeaderId()).getUserName(); // sin @Column
                String objective = commit.getObjective().getObjectiveDescribe();  //pero es una lista?
                String objectiveStatus = commit.getObjective().getStatus().getStatusDescribe();
                String objectiveType = commit.getObjective().getObjectiveType().getObjectiveTypeDescribe();
                String commitmentDescribe = commit.getCommitmentDescribe();
                String commitmentStatus = commit.getCommitmentAdvance().equals(commit.getCommitmentGoal()) ? Constants.RESOLVED : Constants.UNRESOLVED;  //repasar esta sintaxis
                String lastLogin = (Util.convertToDateTimeHourFormatted(commit.getObjective().getUser().getLastLogin(), Constants.DATE_FORMAT));
                EmployeeDataCommitment employeeDataCommitment = new EmployeeDataCommitment(username, fullName, email, per,
                        team, teamLeader, objective, objectiveStatus, objectiveType, commitmentDescribe,
                        commitmentStatus, lastLogin);
                employeeDataCommitmentList.add(employeeDataCommitment);
            }
            return employeeDataCommitmentList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String generateCSVContentCommitment(List<EmployeeDataCommitment> employeeDataCommitmentList) {
        StringBuilder csvContentBuilder = new StringBuilder();

        csvContentBuilder.append("Usuario|Nombre completo|Email|Periodo|Equipo|Nombre del líder|" +
                "Objetivo|Estado del objetivo|Tipo de objetivo|Descripción del compromiso|Estado del compromiso|" +
                "Último ingreso");
        csvContentBuilder.append(System.lineSeparator());

        for (EmployeeDataCommitment employeeDataCommitment : employeeDataCommitmentList) {
            csvContentBuilder.append(employeeDataCommitment.getUsername()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeDataCommitment.getFullName()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeDataCommitment.getEmail()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeDataCommitment.getPeriod()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeDataCommitment.getTeam()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeDataCommitment.getTeamLeader()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeDataCommitment.getObjective()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeDataCommitment.getObjectiveStatus()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeDataCommitment.getObjectiveType()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeDataCommitment.getCommitmentDescribe()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeDataCommitment.getCommitmentStatus()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeDataCommitment.getLastLogin());
            csvContentBuilder.append(System.lineSeparator());
        }

        return csvContentBuilder.toString();
    }

}
