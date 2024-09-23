public class User {
    private String username;
    private String password;
    private String rollNo;
    private String registrationLast4;
    private boolean hasVoted;
    private String votedCandidate;

    // Constructor
    public User(String username, String password, String rollNo, String registrationLast4) {
        this.username = username;
        this.password = password;
        this.rollNo = rollNo;
        this.registrationLast4 = registrationLast4;
        this.hasVoted = false;
        this.votedCandidate = null;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {  // Add this method
        return password;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getRegistrationLast4() {
        return registrationLast4;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public String getVotedCandidate() {
        return votedCandidate;
    }

    // Method to check if the provided password is correct
    public boolean checkPassword(String inputPassword) {  // Add this method
        return this.password.equals(inputPassword);
    }

    // Setters
    public void setVoted(boolean hasVoted, String votedCandidate) {
        this.hasVoted = hasVoted;
        this.votedCandidate = votedCandidate;
    }
}
