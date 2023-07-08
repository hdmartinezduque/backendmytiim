package co.com.template.Repositories.dto;

import lombok.Data;

@Data
public class EmployeeDataCommitment {

    private String username;
    private String fullName;
    private String email;
    private String period;
    private String team;
    private String teamLeader;
    private String objective;
    private String objectiveStatus;
    private String objectiveType;
    private String commitmentDescribe;
    private String commitmentStatus;
    private String lastLogin;

    public EmployeeDataCommitment(String username, String fullName, String email, String period,
                                  String team, String teamLeader, String objective, String objectiveStatus,
                                  String objectiveType, String commitmentDescribe, String commitmentStatus,
                                  String lastLogin) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.period = period;
        this.team = team;
        this.teamLeader = teamLeader;
        this.objective = objective;
        this.objectiveStatus = objectiveStatus;
        this.objectiveType = objectiveType;
        this.commitmentDescribe = commitmentDescribe;
        this.commitmentStatus = commitmentStatus;
        this.lastLogin = lastLogin;
    }
}