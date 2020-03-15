import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DOT {
    public HashMap<String, Location> parse(String FileName){
        HashMap<String, Location> locations = new HashMap<>();
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(FileName);
            parser.parse(reader);
            ArrayList<Graph> graphs = parser.getGraphs();
            ArrayList<Graph> subGraphs = graphs.get(0).getSubgraphs();
            
            for(Graph g : subGraphs){
                ArrayList<Graph> subGraphs1 = g.getSubgraphs();
                for (Graph g1 : subGraphs1){
                    ArrayList<Node> nodesLoc = g1.getNodes(false);
                    Node nLoc = nodesLoc.get(0);
                    Location loc = new Location(g1.getId().getId(), nLoc.getId().getId(), nLoc.getAttribute("description"));
                    ArrayList<Graph> subGraphs2 = g1.getSubgraphs();
                    for (Graph g2 : subGraphs2) {
                        ArrayList<Node> nodesEnt = g2.getNodes(false);
                        for (Node nEnt : nodesEnt) {
                            switch (g2.getId().getId()) {
                                case "characters":
                                    Character character = new Character(null, nEnt.getId().getId(), nEnt.getAttribute("description"));
                                    loc.addCharacter(nEnt.getId().getId(), character);
                                    break;
                                case "artefacts":
                                    Artefact artefact = new Artefact(null, nEnt.getId().getId(), nEnt.getAttribute("description"));
                                    loc.addArtefact(nEnt.getId().getId(), artefact);
                                    break;
                                case "furniture":
                                    Furniture furniture = new Furniture(null, nEnt.getId().getId(), nEnt.getAttribute("description"));
                                    loc.addFurniture(nEnt.getId().getId(), furniture);
                                    break;
                            }
                        }
                    }
                    locations.put(loc.getName(),loc);
                }
                ArrayList<Edge> edges = g.getEdges();
                for (Edge e : edges){
                    locations.get(e.getSource().getNode().getId().getId()).addPathTo(e.getTarget().getNode().getId().getId());
                }

            }
        } catch (FileNotFoundException | ParseException fnfe) {
            System.out.println(fnfe);
        }
        return locations;
    }

    public static void main(String[] args) {
        DOT dot = new DOT();
        HashMap<String, Location> locations = dot.parse("entities1.dot");
        for(Map.Entry<String, Location> loc: locations.entrySet()){
            System.out.println(loc.getValue().getName());
        }
    }
}


