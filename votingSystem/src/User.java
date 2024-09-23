public class User {
    private String username;
    private String password;
    private String rollNo;
    private String registrationLast4;
    private boolean hasVoted;
    private String votedCandidate;

    public User(String username, String password, String rollNo, String registrationLast4) {
        this.username = username;
        this.password = password;
        this.rollNo = rollNo;
        this.registrationLast4 = registrationLast4;
        this.hasVoted = false;
        this.votedCandidate = null;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
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

    public void setVoted(boolean hasVoted, String candidate) {
        this.hasVoted = hasVoted;
        this.votedCandidate = candidate;
    }

    public String getVotedCandidate() {
        return votedCandidate;
    }
}
