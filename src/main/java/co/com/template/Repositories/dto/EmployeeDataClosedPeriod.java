package co.com.template.Repositories.dto;

import lombok.Data;

@Data
public class EmployeeDataClosedPeriod {

    private String username;
    private String fullName;
    private String email;
    private String period;
    private String team;
    private String teamLeader;
    private String pollQuestion;
    private String pollAnswer;

    public EmployeeDataClosedPeriod(String username, String fullName, String email, String period,
                                    String team, String teamLeader, String pollQuestion,
                                    String pollAnswer) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.period = period;
        this.team = team;
        this.teamLeader = teamLeader;
        this.pollQuestion = pollQuestion;
        this.pollAnswer = pollAnswer;
    }
}