import java.util.HashMap;

public class Player extends Entity {

    private HashMap<String, Artefact> artefacts = new HashMap<>();
    private Location location;
    public Player(String id, String name, String description) {
        super(id, name, description);
    }
    public void addArtefact(Artefact art){
        artefacts.put(art.name,art);
    }
    public void deleteArtefact(String name){
        artefacts.remove(name);
    }
    public HashMap<String, Artefact> getArtefacts() {
        return artefacts;
    }
    public Artefact getArtefact(String name){
        return artefacts.get(name);
    }
    public void setLocation(Location loc){
        this.location = loc;
    }
    public Location getLocation(){
        return location;
    }

}
