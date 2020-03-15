public class Entity {
    String id;
    String name;
    String description;
    public Entity(String id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public String getID(){ return id; }

    public String getName(){ return name; }

    public String getDescription(){ return description; }
}
