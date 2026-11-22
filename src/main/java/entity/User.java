package entity;

import java.util.List;

public class User {
    private String username;
    private String password;
    private StudyCharacter character;
    private List<Task> tasks;

    public User(String username, String password,
                StudyCharacter character, List<Task> tasks) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "Username cannot be null or blank.");
        }

        if (password == null) {
            throw new IllegalArgumentException(
                    "Password cannot be null.");
        }

        if (character == null) {
            throw new IllegalArgumentException(
                    "StudyCharacter cannot be null.");
        }

        if (tasks == null) {
            throw new IllegalArgumentException(
                    "Tasks cannot be null.");
        }

        this.username = username;
        this.password = password;
        this.character = character;
        this.tasks = tasks;
    }

    public String getUsername() {
        return username;
    }

    public StudyCharacter getCharacter() {
        return character;
    }

    public boolean verifyPassword(String inputPassword) {
        return password.equals(inputPassword);
    }


    public void addTask(String taskId) {
        // TODO: Confirm Task storage design
        throw new UnsupportedOperationException(
                "Task integration not implemented yet");
    }


}
