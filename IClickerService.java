import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class IClickerService implements IVote {
    
    private Hashtable<String, List<String>> submissions;
    private Question description;
    private boolean submissionsEnd;
    private Integer numCorrect;

    public IClickerService(Question description){
        this.description= description;
        this.submissions= new Hashtable<String, List<String>>();
        this.submissionsEnd= false;
        this.numCorrect= 0;
    }

    @Override
    public boolean submit(String uuid, List<String> submission){
        if (this.submissionsEnd){
            return false;
        }

        if (submission == null){
            return false;
        }

        final List<String> uniqueSubmission = makeUnique(submission);
        final List<String> validSubmission = new ArrayList<String>();
        for (String sub : uniqueSubmission){
            if(this.description.inChoices(sub)){
                validSubmission.add(sub);
            }
        }
        this.submissions.put(uuid, validSubmission);
        return true; 
    }

    private List<String> makeUnique(List<String> submission){
        final Set<String> temp = new HashSet<String>(submission);
        final List<String> uq = new ArrayList<String>();
        uq.addAll(temp);
        return uq;
    }

    @Override
    public String showStats(){
        checkAnswers();
        String stats = "Question:\n";
        stats += this.description.getQuestion();
        stats += "\n\nAnswer Statistics\n";
        stats += "-----------------\n";
        
        List<String> choices = this.description.getChoices();
        for (String choice : choices) {
            Integer numChoice= 0;
            for (String uuid: submissions.keySet()){
                if (this.submissions.get(uuid).contains(choice)){
                    numChoice++;
                }
            }
            stats += choice + "\t\t\t" + numChoice.toString() + "\n";
        }
        stats += "\nTotal Submissions: "+ this.totalSubmissions().toString();
        stats += "\nTotal Correct: " + this.numCorrect;
        stats += "\n\n";
        return stats;
    }

    @Override
    public Integer totalSubmissions(){
        return this.submissions.size();
    }

    @Override
    public void endSubmissions(){
        this.submissionsEnd= true;
    }

    private void checkAnswers(){
        if(!this.submissionsEnd){
            return;
        }
        //reset
        this.numCorrect= 0;
        for(Entry<String, List<String>> entry : submissions.entrySet()){
            final String check= description.checkA(entry.getValue());
            if (check.compareTo("Your answer is correct!")==0){
                this.numCorrect++;
            }
        }
    }

}
