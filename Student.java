public class Student extends User {
    private String uniID;
    private int studyYear;

    public Student(String id, String name, String email, String password, String uniID, int studyYear) {
        super(id, name, email, password);
        this.uniID = uniID;
        this.studyYear = studyYear;
    }

    public String getUniID() {return uniID;}
    
}