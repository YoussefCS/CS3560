import java.util.List;

public class Student{

    private static int count=0;

    private String uuid;
    private List<String> answers;

    public Student(){
        count+=1;
        this.uuid = ""+count;
    }

    public String getUuid(){
        return uuid;
    }

    public boolean enterAnswers(List<String> input){
        this.answers = input;
        return true;
    }

    public List<String> getAnswers(){
        return answers;
    }
}