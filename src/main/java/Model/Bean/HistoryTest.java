package Model.Bean;

public class HistoryTest {
    private int idTest;
    private String nameTest;
    private int numberOfQuestions;
    private int numberOfContestants;

    public HistoryTest(int idTest, String nameTest, int numberOfQuestions, int numberOfContestants) {
        this.idTest = idTest;
        this.nameTest = nameTest;
        this.numberOfQuestions = numberOfQuestions;
        this.numberOfContestants = numberOfContestants;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public String getNameTest() {
        return nameTest;
    }

    public void setNameTest(String nameTest) {
        this.nameTest = nameTest;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getNumberOfContestants() {
        return numberOfContestants;
    }

    public void setNumberOfContestants(int numberOfContestants) {
        this.numberOfContestants = numberOfContestants;
    }
}
