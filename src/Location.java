import java.util.HashMap;
import java.util.HashSet;
public class Location extends Entity {
    private HashSet<String> pathFrom = new HashSet<>();
    private HashSet<String> pathTo = new HashSet<>();
    private HashMap<String, Artefact> artefacts = new HashMap<>();
    private HashMap<String, Character> characters = new HashMap<>();
    private HashMap<String, Furniture> furnitures = new HashMap<>();

    public Location(String id, String name, String description) { super(id, name, description); }

    public HashSet<String> getPathTo(){ return pathTo; }

    public HashMap<String, Artefact> getArtefacts() { return artefacts; }

    public HashMap<String, Character> getCharacters() { return characters; }

    public HashMap<String, Furniture> getFurnitures() { return furnitures; }

    public void addArtefact(String name, Artefact artefact){ this.artefacts.put(name, artefact); }

    public void addCharacter(String name, Character character){ this.characters.put(name, character); }

    public void addFurniture(String name, Furniture furniture){ this.furnitures.put(name, furniture); }

    public void addPathTo(String path){ this.pathTo.add(path); }

}
