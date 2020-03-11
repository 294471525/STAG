import java.io.*;
import java.util.*;

import org.json.simple.parser.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class JSON{
    public ArrayList<Action> parse (String FileName){
        ArrayList<Action> Actions = new ArrayList<>();
        Reader in;
        File f = new File(FileName);
        JSONParser p = new JSONParser();
        JSONArray arr = null;
        JSONObject obj;
        Object p_temp;
        try {
            in = new FileReader(f);
            p_temp = p.parse(in);
            obj = (JSONObject) p_temp;
            arr = (JSONArray) obj.get("actions");
        } catch (FileNotFoundException e) {
            // do something if the file couldn't be found
        } catch (org.json.simple.parser.ParseException e) {
            // do something if the parser caused a parser error
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert arr != null;
        for (Object o : arr) {
            JSONObject temp  = (JSONObject) o;
            JSONArray arr_temp;
            HashSet<String> triggers = new HashSet<>();
            HashSet<String> subjects = new HashSet<>();
            HashSet<String> consumed = new HashSet<>();
            HashSet<String> produced = new HashSet<>();
            String narration;
            arr_temp = (JSONArray) temp.get("triggers");
            for(Object s : arr_temp){
                triggers.add((String) s);
            }
            arr_temp = (JSONArray) temp.get("subjects");
            for(Object s : arr_temp){
                subjects.add((String) s);
            }
            arr_temp = (JSONArray) temp.get("consumed");
            for(Object s : arr_temp){
                consumed.add((String) s);
            }
            arr_temp = (JSONArray) temp.get("produced");
            for(Object s : arr_temp){
                produced.add((String) s);
            }
            narration = (String) temp.get("narration");
            Action act = new Action(triggers, subjects, consumed, produced, narration);
            Actions.add(act);
        }

        return Actions;
    }
}

