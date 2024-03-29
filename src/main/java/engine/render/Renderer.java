package engine.render;

import engine.assets.GameItem;
import engine.assets.Mesh;
import engine.io.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(70.0f);
    private static final float Z_NEAR = 0.0001f;
    private static final float Z_FAR = 10000.f;
    public Transformation transformation;  /////////////////////
    public Camera camera = new Camera(new Vector3f(0,0,0), new Vector3f(0,0,0));
    private ShaderProgram shaderProgram;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init(Window window){
        // Create shader
        shaderProgram = new ShaderProgram("shader2");
 //       shaderProgram.link();

        // Create uniforms for modelView and projection matrices and texture
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("modelViewMatrix");
        shaderProgram.createUniform("texture_sampler");
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, ArrayList<GameItem> gameItems) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();
        
        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        // Update view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);

        shaderProgram.setUniform("texture_sampler", 0);
        // Render each gameItem
        for (GameItem gameItem : gameItems) {
            // Set model view matrix for this item
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix);
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);

            // Render the mes for this game item
            if(gameItem.isHasNewMesh()) {
                for(Mesh mesh : gameItem.getMeshes()) {
                    if(mesh.needToRender())
                    mesh.render();
                }
            } else gameItem.getMesh().render();
        }

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanUp();
        }
    }
}
