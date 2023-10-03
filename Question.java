import java.util.List;
public abstract class Question{
    protected String description;
    protected List<String> choices;
    protected List<String> correct;

    public Question(String description, List<String> choices, List<String> correct){
        this.description = description;
        this.choices = choices;
        this.correct = correct;
    }

    public String getQuestion(){
        return this.description;
    }
    
    public List<String> getChoices(){
        return this.choices;
    }

    public boolean inChoices(String submission){
        return this.choices.contains(submission);    
    }

    public abstract String checkA(List<String> submittedAnswers);
}