package softuni.workshopjudge2.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    private int score;
    private String textContent;
    private User author;
    private Homework homework;

    public Comment() {
    }

    @Column()
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Column(name = "text_content", columnDefinition = "TEXT")
    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String text_content) {
        this.textContent = text_content;
    }

    @ManyToOne
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne
    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }
}

