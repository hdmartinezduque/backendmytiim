package co.com.template.Repositories.dto;

import lombok.Data;

@Data
public class EmployeeDataPollContinues {

    private String username;
    private String fullName;
    private String email;
    private String period;
    private String pollCode;
    private String team;
    private String teamLeader;
    private String questionPoll;
    private String answerPoll;

    public EmployeeDataPollContinues(String username, String fullName, String email, String period,
                                     String pollCode, String team, String teamLeader, String questionPoll,
                                     String answerPoll) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.period = period;
        this.pollCode = pollCode;
        this.team = team;
        this.teamLeader = teamLeader;
        this.questionPoll = questionPoll;
        this.answerPoll = answerPoll;
    }
}