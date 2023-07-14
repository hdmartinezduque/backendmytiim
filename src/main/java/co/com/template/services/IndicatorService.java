package co.com.template.services;

import co.com.template.Repositories.*;
import co.com.template.Repositories.dto.*;
import co.com.template.Repositories.entities.*;
import co.com.template.utils.Constants;
import co.com.template.utils.TypePollEnum;
import co.com.template.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
@Log4j2
public class IndicatorService {

    private final UserRepository userRepository;

    private final ObjectiveRepository objectiveRepository;

    private final PeriodRepository periodRepository;

    private final CommitmentRepository commitmentRepository;

    private final PeriodService periodService;

    private final DetailPollRepository detailPollRepository;
    private final PollUserRepository pollUserRepository;


    public ResponseDTO getIndicatorObjective(Long periodId) {
        try {
            Period period= this.validatePeriod(periodId);
            List<User> allUsers = userRepository.findByActivatedDateLessThan(period.getEndPeriod());

            int totalUsers = allUsers.size();

            List<Objective> objectivesInPeriod = objectiveRepository.findByCreateDateBetween(
                    period.getStartPeriod().atStartOfDay(), period.getEndPeriod().atStartOfDay());

            List<User> userIdsWithObjectives = objectivesInPeriod.stream()
                    .map(Objective::getUser)
                    .distinct().toList();
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
            Period period= this.validatePeriod(periodId);
            List<EmployeeData> employeeDataList = new ArrayList<>();

            List<User> userList = userRepository.findByActivatedDateLessThan(period.getEndPeriod());

            for (User userModel : userList) {
                String username = userModel.getUser();
                String fullName = userModel.getUserName().concat(Constants.SPACE_CHARACTER).concat(userModel.getUserLastName());
                String email = userModel.getUserEmail();
                String periodDescribe = period.getDescribe();
                String team = userModel.getGroup().getGroupDescribe();
                User userLeader = userRepository.findByUserId(userModel.getLeaderId());
                String teamLeader = Objects.isNull(userLeader) ? Constants.EMPTY_MESSAGE : userLeader.getUserName()+" ".concat(userLeader.getUserLastName());
                int totalObjectives = objectiveRepository.countByUserAndPeriod(userModel, period);
                String lastLogin = (Util.convertToDateTimeHourFormatted(userModel.getLastLogin(), Constants.DATE_FORMAT));
                employeeDataList.add(new EmployeeData(username, fullName, email, periodDescribe, team, teamLeader, totalObjectives, lastLogin));
            }
            return employeeDataList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String generateCSVContent(List<EmployeeData> employeeDataList) {
        StringBuilder csvContentBuilder = new StringBuilder();
        csvContentBuilder.append(Constants.OBJECTIVE_HEADERS_CSV);
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
            Period period= this.validatePeriod(periodId);
            List<User> allUsers = userRepository.findByActivatedDateLessThan(period.getEndPeriod());
            int totalUsers = allUsers.size();

            List<Commitment> commitmentInPeriod = commitmentRepository.findByCreateDateBetween(
                    period.getStartPeriod(), period.getEndPeriod());
            List<User> userIdsWithCommitment = commitmentInPeriod.stream()
                    .map(Commitment::getObjective)
                    .map(Objective::getUser)
                    .distinct().toList();
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
            Period period= this.validatePeriod(periodId);

            List<EmployeeDataCommitment> employeeDataCommitmentList = new ArrayList<>();
            List<Commitment> commitments = commitmentRepository.findByCreateDateBetween(
                    period.getStartPeriod(), period.getEndPeriod());

            for (Commitment commit : commitments) {
                String username = commit.getObjective().getUser().getUser();
                String fullName = commit.getObjective().getUser().getUserName().concat(Constants.SPACE_CHARACTER).concat(commit.getObjective().getUser().getUserLastName());
                String email = commit.getObjective().getUser().getUserEmail();
                String per = commit.getObjective().getPeriod().getDescribe();
                String team = commit.getObjective().getUser().getGroup().getGroupDescribe();
                User userLeader = userRepository.findByUserId(commit.getObjective().getUser().getLeaderId());
                String teamLeader = Objects.isNull(userLeader) ? Constants.EMPTY_MESSAGE : userLeader.getUserName()+" ".concat(userLeader.getUserLastName());
                String objective = commit.getObjective().getObjectiveDescribe();
                String objectiveStatus = commit.getObjective().getStatus().getStatusDescribe();
                String objectiveType = commit.getObjective().getObjectiveType().getObjectiveTypeDescribe();
                String commitmentDescribe = commit.getCommitmentDescribe();
                String commitmentStatus = commit.getCommitmentAdvance().equals(commit.getCommitmentGoal()) ? Constants.RESOLVED : Constants.UNRESOLVED;
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

        csvContentBuilder.append(Constants.COMMITMENT_HEADERS_CSV);
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


    public ResponseDTO getIndicatorPollContinues(Long periodId) {
        try {
            Period period= this.validatePeriod(periodId);

            LocalDate endDate = period.getEndPeriod();
            LocalDate startDate = period.getStartPeriod();

            List<PollUser> users = pollUserRepository.findByCreatedDateBetweenAndPollCodeStartsWith(startDate, endDate, TypePollEnum.FOLLOW.getStart());
            List<User> uniqueUsers = users.stream()
                    .map(PollUser::getUser).distinct()
                    .toList();
            int totatlUsers = uniqueUsers.size();

            List<DetailPoll> detailPolls = detailPollRepository.findByAnswerDateBetween(startDate, endDate);

            List<User> userIdsWithPolls = detailPolls.stream()
                    .map(DetailPoll::getUser)
                    .distinct().toList();
            int usersWithPolls = userIdsWithPolls.size();

            double percentageUsersWithPolls = (double) usersWithPolls / totatlUsers * Constants.ONE_HUNDRED_INDEX;

            double percentageUsersWithoutPolls = Constants.ONE_HUNDRED_INDEX - percentageUsersWithPolls;

            IndicatorDTO indicatorDTO = new IndicatorDTO(totatlUsers, percentageUsersWithPolls, percentageUsersWithoutPolls);
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, indicatorDTO);

        } catch (Exception err) {
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }

    }


    public List<EmployeeDataPollContinues> generatePollContinuesCSVFile(Long periodId) {
        try {
            Period period= this.validatePeriod(periodId);

            List<DetailPoll> detailPolls = detailPollRepository.findByPollCodeStartsWithAndPollPeriodPeriodId(TypePollEnum.FOLLOW.getStart(), period.getPeriodId());
            List<EmployeeDataPollContinues> employeeDataList = new ArrayList<>();
            for (DetailPoll detailPoll : detailPolls) {
                String username = detailPoll.getUser().getUser();
                String fullName = detailPoll.getUser().getUserName().concat(detailPoll.getUser().getUserLastName());
                String email = detailPoll.getUser().getUserEmail();
                String per = detailPoll.getPoll().getPeriod().getDescribe();
                String pollCode = detailPoll.getPoll().getCode();
                String team = detailPoll.getUser().getGroup().getGroupDescribe();
                User userLeader = userRepository.findByUserId(detailPoll.getUser().getLeaderId());
                String teamLeader = Objects.isNull(userLeader) ? Constants.EMPTY_MESSAGE : userLeader.getUserName()+" ".concat(userLeader.getUserLastName());
                String questionPoll = detailPoll.getQuestion().getDescribe();
                String answerPoll = detailPoll.getAnswer();

                EmployeeDataPollContinues employeeData = new EmployeeDataPollContinues(username, fullName, email,
                        per, pollCode, team, teamLeader, questionPoll, answerPoll);
                employeeDataList.add(employeeData);
            }
            return employeeDataList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String generateCSVContentPollContinues(List<EmployeeDataPollContinues> employeeDataPollContinuesList) {
        StringBuilder csvContentBuilder = new StringBuilder();

        csvContentBuilder.append(Constants.CONTINUE_POLL_HEADERS_CSV);
        csvContentBuilder.append(System.lineSeparator());

        for (EmployeeDataPollContinues employeeData : employeeDataPollContinuesList) {
            csvContentBuilder.append(employeeData.getUsername()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getFullName()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getEmail()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getPeriod()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getPollCode()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getTeam()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getTeamLeader()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getQuestionPoll()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(employeeData.getAnswerPoll());
            csvContentBuilder.append(System.lineSeparator());
        }
        return csvContentBuilder.toString();
    }


    public ResponseDTO getIndicatorClosedPeriod(Long periodId) {
        try {
            Period period= this.validatePeriod(periodId);

            List<DetailPoll> detailPolls = detailPollRepository.findByPollCodeStartsWithAndPollPeriodPeriodId(TypePollEnum.CLOSE.getStart(), period.getPeriodId());

            List<User> userIdsWithPolls = detailPolls.stream()
                    .map(DetailPoll::getUser)
                    .distinct()
                    .toList();
            int usersWithPolls = userIdsWithPolls.size();

            List<PollUser> users = pollUserRepository.findByCreatedDateBetweenAndPollCodeStartsWith(period.getStartPeriod(), period.getEndPeriod(), TypePollEnum.CLOSE.getStart());
            List<User> uniqueUsers = users.stream()
                    .map(PollUser::getUser).distinct()
                    .toList();
            int totalUsers = uniqueUsers.size();

            double percentageUsersWithPolls = (double) usersWithPolls / totalUsers * Constants.ONE_HUNDRED_INDEX;
            double percentageUsersWithoutPolls = Constants.ONE_HUNDRED_INDEX - percentageUsersWithPolls;

            IndicatorDTO indicatorDTO = new IndicatorDTO(totalUsers, percentageUsersWithPolls, percentageUsersWithoutPolls);

            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, indicatorDTO);

        } catch (Exception err) {
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }

    }

    public List<EmployeeDataClosedPeriod> generateClosedPeriodCSVFile(Long periodId) {
        try {
            List<EmployeeDataClosedPeriod> employeeDataList = new ArrayList<>();

            Period period= this.validatePeriod(periodId);

            List<DetailPoll> detailPolls = detailPollRepository.findByPollCodeStartsWithAndPollPeriodPeriodId(TypePollEnum.CLOSE.getStart(), period.getPeriodId());

            for (DetailPoll detailPoll : detailPolls) {
                String username = detailPoll.getUser().getUser();
                String fullName = detailPoll.getUser().getUserName().concat(detailPoll.getUser().getUserLastName());
                String email = detailPoll.getUser().getUserEmail();
                String per = detailPoll.getPoll().getPeriod().getDescribe();
                String team = detailPoll.getUser().getGroup().getGroupDescribe();
                String teamLeader = userRepository.findByUserId(detailPoll.getUser().getLeaderId()).getUserName();
                String pollQuestion = detailPoll.getQuestion().getDescribe();
                String pollAnswer = detailPoll.getAnswer();

                EmployeeDataClosedPeriod employeeData = new EmployeeDataClosedPeriod(username, fullName, email,
                        per, team, teamLeader, pollQuestion, pollAnswer);
                employeeDataList.add(employeeData);
            }
            return employeeDataList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String generateCSVContentClosedPeriod(List<EmployeeDataClosedPeriod> employeeDataClosedPeriodList) {
        StringBuilder csvContentBuilder = new StringBuilder();

        csvContentBuilder.append(Constants.CLOSE_POLL_HEADERS_CSV);
        csvContentBuilder.append(System.lineSeparator());

        for (EmployeeDataClosedPeriod emplDatClodPer : employeeDataClosedPeriodList) {
            csvContentBuilder.append(emplDatClodPer.getUsername()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(emplDatClodPer.getFullName()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(emplDatClodPer.getEmail()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(emplDatClodPer.getPeriod()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(emplDatClodPer.getTeam()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(emplDatClodPer.getTeamLeader()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(emplDatClodPer.getPollQuestion()).append(Constants.FILE_SEPARATOR);
            csvContentBuilder.append(emplDatClodPer.getPollAnswer());
            csvContentBuilder.append(System.lineSeparator());
        }
        return csvContentBuilder.toString();
    }

    private Period validatePeriod(Long periodId){
        Period period;
        if (periodId.equals(Constants.ZERO_VALUE)) {
            period = periodService.getActualPeriod();
        } else {
            period = periodRepository.findByPeriodId(periodId);
        }
        return period;
    }



}
