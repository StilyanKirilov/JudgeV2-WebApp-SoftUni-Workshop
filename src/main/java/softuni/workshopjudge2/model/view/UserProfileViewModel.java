package softuni.workshopjudge2.model.view;

import java.util.List;

public class UserProfileViewModel {
    private String username;
    private String email;
    private String git;
    private String homeworks;

    public UserProfileViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public String getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(String homeworks) {
        this.homeworks = homeworks;
    }
}
