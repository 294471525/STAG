import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Id;
import com.alexmerz.graphviz.objects.Node;


public class CommandProcessor {
    public static void main(String[] args) {
        FileReader in=null;
        File f = new File("entities.dot");
        Parser p = new Parser();
        try {
            in = new FileReader(f);
            p.parse(in);

        } catch (FileNotFoundException e) {
            // do something if the file couldn't be found
        } catch (ParseException e) {
            // do something if the parser caused a parser error
        }
        // everything ok
        ArrayList<Graph> gl = p.getGraphs();
        Graph a = gl.get(0);
        System.out.println(gl.get(0).getEdges());

        // do something with the Graph objects
    }
}
