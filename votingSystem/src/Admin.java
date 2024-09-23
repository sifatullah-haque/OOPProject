import java.io.*;
import java.util.HashMap;

public class Admin {
    private String adminUsername = "admin";
    private String adminPassword = "adminpass";
    private HashMap<String, Integer> voteCount = new HashMap<>();

    public Admin() {
        voteCount.put("Candidate 1", 0);
        voteCount.put("Candidate 2", 0);
        voteCount.put("Candidate 3", 0);
    }

    public boolean checkAdminLogin(String username, String password) {
        return username.equals(adminUsername) && password.equals(adminPassword);
    }

    public void stopVoting() {
        // logic to stop voting
        System.out.println("Voting has been stopped.");
    }

    public void showResults() {
        try (BufferedReader reader = new BufferedReader(new FileReader("votes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                voteCount.put(line, voteCount.get(line) + 1);
            }

            // Show results
            String winner = voteCount.entrySet().stream().max(HashMap.Entry.comparingByValue()).get().getKey();
            System.out.println("Voting Results:");
            voteCount.forEach((candidate, votes) -> {
                System.out.println(candidate + ": " + votes + " votes");
            });
            System.out.println("Winner: " + winner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
