package engine.assets;

import org.joml.Vector3f;



public class GameItem {

    private  Mesh mesh;
    private  NewMesh[] newMesh;
    private final Vector3f position;
    private float scale;
    private final Vector3f rotation;
    private boolean hasNewMesh;


    public GameItem() {
        position = new Vector3f();
        scale = 1;
        rotation = new Vector3f();
    }

    public GameItem(Mesh mesh) {
        this();
        this.mesh = mesh;
        newMesh = null;
        hasNewMesh = false;
    }

    public GameItem(NewMesh[] mesh1) {
        this();
        this.newMesh = mesh1;
        mesh = null;
        hasNewMesh = true;
    }



    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }
    public void setPosition(Vector3f newPosition) {
        this.position.x = newPosition.x;
        this.position.y = newPosition.y;
        this.position.z = newPosition.z;
    }
    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public Mesh getMesh() {
        return mesh;
    }
    public Mesh[] getMeshes() {
       return newMesh;
    }

    public boolean isHasNewMesh() {
        return hasNewMesh;
    }


}