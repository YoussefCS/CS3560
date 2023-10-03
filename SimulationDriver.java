import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationDriver{

    public static void main(String[] args){

        List<String> singleChoices = new ArrayList<String>();
        List<String> multiChoices = new ArrayList<String>();
        List<String> answer = new ArrayList<String>();
        List<String> answers = new ArrayList<String>();

        singleChoices.add("True");
        singleChoices.add("False");
        answer.add(singleChoices.get(0));

        multiChoices.add("Yes");
        multiChoices.add("No");
        multiChoices.add("Maybe");
        answers.add(multiChoices.get(0));
        answers.add(multiChoices.get(2));
        answers.add(multiChoices.get(1));

        SimulationRun("singleChoice", "Is Soccer the best sport?", singleChoices, answer, 50);

        SimulationRun("MultiChoice", "Is Messi the best player in the world?", multiChoices, answers, 45);
    }
    
    private static void SimulationRun(String qType, String q, List<String> choices, List<String> answers, Integer numStudents){
        final Question question;
        final IVote iClicker;
        final Student[] students = new Student[numStudents];

        switch(qType){
            case "MultipleChoice":
                question = new MultipleChoiceQuestion(q, choices, answers);
                break;
            default:
                question = new SingleChoiceQuestion(q, choices, answers);
                break;
        }

        iClicker = new IClickerService(question);

        for (Integer i=0; i<students.length; i++){
            students[i] =  new Student();
            students[i].enterAnswers(randGenAnswers(choices, qType));
            iClicker.submit(students[i].getUuid(), students[i].getAnswers());
        }
        if (iClicker.totalSubmissions() != numStudents){
            System.err.println("Number of submissions is "+ iClicker.totalSubmissions().toString());
        }
        System.out.println("Before submissions are over.");
        System.out.println(iClicker.showStats());
        
        for (Integer i=0; i<students.length; i+=5){
            students[i].enterAnswers(randGenAnswers(choices, qType));
            iClicker.submit(students[i].getUuid(), students[i].getAnswers());
        }
        if (iClicker.totalSubmissions() != numStudents){
                System.err.println("Number of submissions is "+iClicker.totalSubmissions().toString());
        }
        iClicker.endSubmissions();
        System.out.println("Before submissions are over.");
        System.out.println(iClicker.showStats());
    }  
     
    private static List<String> randGenAnswers(List<String> choices, String type){
        Integer numAnswers = 1;
        final Random rand = new Random();
        ArrayList<String> answers = new ArrayList<String>();
        if (type == "MultipleChoice"){
            numAnswers = rand.nextInt(choices.size()-1)+1;
        }
        for (Integer i=0; i<numAnswers; i++){
            answers.add(choices.get(rand.nextInt(choices.size())));
        }
        return answers;
            
    }
    
}