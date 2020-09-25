package softuni.workshopjudge2.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class HomeworkAddBidingModel {
    private String exercise;
    private String gitAddress;

    public HomeworkAddBidingModel() {
    }

    @Length(min = 3,message = "Exercise name length must be more than 2 characters")
    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
    @Pattern(regexp = "https://github\\.com/.+",message = "Enter valid git address!")
    public String getGitAddress() {
        return gitAddress;
    }

    public void setGitAddress(String gitAddress) {
        this.gitAddress = gitAddress;
    }
}
