package co.com.template.Repositories.dto;

import lombok.Data;

@Data
public class EmployeeData {

    private String username;
    private String fullName;
    private String email;
    private String period;
    private String team;
    private String teamLeader;
    private int totalObjectives;
    private String lastLogin;

    public EmployeeData(String username, String fullName, String email, String periodDescribe, String team,
                        String teamLeader, int totalObjectives, String lastLogin) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.period = periodDescribe;
        this.team = team;
        this.teamLeader = teamLeader;
        this.totalObjectives = totalObjectives;
        this.lastLogin = lastLogin;
    }
}