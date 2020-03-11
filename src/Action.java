import java.util.HashSet;

public class Action {
    final private HashSet<String> triggers;
    final private HashSet<String> subjects;
    final private HashSet<String> consumed;
    final private HashSet<String> produced;
    final private String narration;

    public Action(HashSet<String> triggers, HashSet<String> subjects,HashSet<String> consumed,HashSet<String> produced,String narration){
        this.triggers = triggers;
        this.subjects = subjects;
        this.consumed = consumed;
        this.produced = produced;
        this.narration = narration;
    }

    public HashSet<String> getTriggers(){
        return triggers;
    }

    public HashSet<String> getSubjects(){
        return subjects;
    }

    public HashSet<String> getConsumed(){
        return consumed;
    }

    public HashSet<String> getProduced(){
        return produced;
    }

    public String getNarration(){
        return narration;
    }

}
