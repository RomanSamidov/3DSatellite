package gameData.Stages.MenuStage;

import engine.assets.*;
import engine.game.MainGameStage;
import engine.io.Input;
import engine.io.Timer;
import engine.io.Window;
import engine.render.Camera;
import engine.render.Renderer;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;

public class MenuStage extends MainGameStage {
    private static final float MOUSE_SENSITIVITY = 0.2f;
    public final double frame_cap = 1.0 / 60.0; // в одной секунде 60 кадров
    private MenuGui gui;
    private boolean again = true, pause = true , stop = false;
    private double startTime;
    private ArrayList<GameItem> gameItems;
    private Renderer renderer;
    private static final Window window = Window.windows;
    public MenuStage() {

    }

    public void main() throws Exception {
        gui = new MenuGui();
        gui.setSize(window.getWidth(), window.getHeight());

        double frame_time = 0;
        int frames = 0;

        double time_2;
        double passed;

        double timeWastLogik = 0, timeWastRender = 0, savedtime;


        double time = Timer.getTime(), unprocessed = 0, timeInGame = 0;
        startTime = Timer.getTime();
        boolean can_render;

        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        float[] positions = new float[] {
                // V0
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,

                // For text coords in top face
                // V8: V4 repeated
                -0.5f, 0.5f, -0.5f,
                // V9: V5 repeated
                0.5f, 0.5f, -0.5f,
                // V10: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V11: V3 repeated
                0.5f, 0.5f, 0.5f,

                // For text coords in right face
                // V12: V3 repeated
                0.5f, 0.5f, 0.5f,
                // V13: V2 repeated
                0.5f, -0.5f, 0.5f,

                // For text coords in left face
                // V14: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V15: V1 repeated
                -0.5f, -0.5f, 0.5f,

                // For text coords in bottom face
                // V16: V6 repeated
                -0.5f, -0.5f, -0.5f,
                // V17: V7 repeated
                0.5f, -0.5f, -0.5f,
                // V18: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // V19: V2 repeated
                0.5f, -0.5f, 0.5f,
        };

//        positions = new float[] {
//                // V0
//                -1f, -1f, -1f,
//                // V1
//                1f, -1f, -1f,
//                // V2
//                1f, -1f, 1f,
//                // V3
//                -1f, -1f, 1f,
//                // V4
//                0f, 0.0f, 0f
//
//        };



//        positions = new float[] {
//                -0.5f,  0.5f,  0.5f, //    0
//                0.5f,  0.5f,  0.5f, //    1
//                0.5f, -0.5f,  0.5f, //    2
//                -0.5f, -0.5f,  0.5f, //    3
//                -0.5f,  0.5f, -0.5f, //    4
//                0.5f,  0.5f, -0.5f, //    5
//                0.5f, -0.5f, -0.5f, //    6
//                -0.5f, -0.5f, -0.5f, //    7
//        };


        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,

                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,

                // For text coords in top face
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,

                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 0.5f,

                // For text coords in left face
                0.5f, 0.0f,
                0.5f, 0.5f,

                // For text coords in bottom face
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };

//        textCoords = new float[]{
//                0.5f, 0f,
//                1f, 0f,
//                1f, 0.5f,
//                0.5f, 0.5f,
//
//                0.0f, 0.0f,
//
//        };


//        textCoords = new float[]{
//                0, 0, // 0
//                1, 0, // 1
//                1, 1, // 2
//                0, 1, // 3
//                0, 0, // 4
//                1, 0, // 5
//                1, 1, // 6
//                0, 1, // 7
//
//        };



        int[] indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                12, 13, 7, 5, 12, 7,
                // Left face
                14, 15, 6, 4, 14, 6,
                // Bottom face
                16, 18, 19, 17, 16, 19,
                // Back face
                4, 6, 7, 5, 4, 7,};

//        indices = new int[]{
//                // Front face
//                4, 2, 3,
//                // Right face
//                4, 1, 2,
//                // Left face
//                4, 0, 3,
//                // Bottom face
//                0, 1, 2, 2, 3, 0,
//                // Back face
//                4, 0, 1
//                          };

//        indices = new int[]{
//                0, 1, 2,
//                2, 3, 0,  // 1 side
//                1, 5, 6,
//                6, 2, 1,  // 2 side
//                5, 4, 7,
//                7, 6, 5,  // 3 side
//                4, 0, 3,
//                3, 7, 4,  // 4 side
//                0, 4, 5,
//                5, 1, 0,  // 5 side
//                3, 7, 6,
//                6, 2, 3   // 6 side
//                 };

        renderer = new Renderer();
        renderer.init(window);

        gameItems = new ArrayList<>();

        Texture texture = new Texture("src/main/resources/grassblock.png");
        Mesh mesh = new Mesh(positions, textCoords, indices, texture);
        GameItem gameItem = new GameItem(mesh);
        gameItem.setPosition(0, 1, 1);
        gameItem.setScale(3);
        gameItems.add(gameItem);


        NewMesh[] houseMesh = NewStaticMeshesLoader.load("src/main/resources/Satelite2/Satellite.obj", "src/main/resources/Satelite2/text");
        GameItem satellite0 = new GameItem(houseMesh);
        satellite0.setPosition(20,1,-10);
        satellite0.setScale(1);
        gameItems.add(satellite0);

        GameItem satellite1 = new GameItem(houseMesh);
        satellite1.setPosition(10,1,-10);
        satellite1.setScale(1);
        gameItems.add(satellite1);
        GameItem satellite2 = new GameItem(houseMesh);
        satellite2.setPosition(-5,1,-10);
        satellite2.setScale(1);
        gameItems.add(satellite2);

        GameItem skyBox = new GameItem(mesh);
        Vector3f vector3f = renderer.camera.getPosition();
        skyBox.setPosition(vector3f.x, vector3f.y, vector3f.z);
        skyBox.setScale(1000);
        gameItems.add(skyBox);

  //      gameItems = new GameItem[]{house};



        float ix = 0;
renderer.camera.setPosition(0,10,10);

        //while (!window.shouldClose()) {
        while (!stop && !window.windowShouldClose()) {
            can_render = false;
            time_2 = Timer.getTime();
            passed = time_2 - time;
            unprocessed += passed;
            frame_time += passed;
            time = time_2;

            if(window.isResized()){
                glViewport(0,0,window.getWidth(), window.getHeight());
                window.setResized(false);
                gui.setSize(window.getWidth(), window.getHeight());
            }

            if (again) {
                timeInGame = 0;
                again = false;
                startTime = Timer.getTime();
            }


            while (unprocessed >= frame_cap) {
                savedtime = Timer.getTime();
                unprocessed -= frame_cap;
                can_render = true;
                if (!pause) {
                    gui.update();
                    startTime = Timer.getTime();
                } else startTime = Timer.getTime();
                window.update();
                timeWastLogik += Timer.getTime() - savedtime;

            }

            if (frame_time >= 1.0) {
                frame_time = 0;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
            if (can_render) {
                savedtime = Timer.getTime();

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


                ix+= 1.1;
                gameItems.get(1).setRotation(0, 0, ix);
                gameItems.get(2).setRotation(0, ix, 0);
                gameItems.get(3).setRotation(ix, 0,0);

               renderer.camera.setFocus(gameItems.get(0).getPosition(), gameItems.get(0).getRotation());

                gameItems.get(gameItems.size()-1).setPosition(renderer.camera.getPosition());
                renderer.render(window, gameItems);
                //cub.render();

                gui.render();
                window.swapBuffers();

                frames++;
                timeWastRender += Timer.getTime() - savedtime;
            }

        }

    }

    public void keyIsPressed(int key) {
        Vector3f vec1 = renderer.camera.getPosition();
        switch (key) {
            case GLFW_KEY_ESCAPE:
                glfwSetWindowShouldClose(window.getWindow(), true);
                stop = true;
                break;
            case GLFW_KEY_W:
                renderer.camera.movePosition(0, 0,(float) -0.1);
                break;
            case GLFW_KEY_S:
                renderer.camera.movePosition(0, 0,(float) 0.1);
                break;
            case GLFW_KEY_A:
                renderer.camera.movePosition((float) -0.1, 0,0);
                break;
            case GLFW_KEY_D:
                renderer.camera.movePosition((float) 0.1, 0,0);
                break;
            case GLFW_KEY_LEFT_SHIFT:
                renderer.camera.movePosition(0, (float) -0.1,0);
                break;
            case GLFW_KEY_SPACE:
                renderer.camera.movePosition(0, (float) 0.1,0);
                break;
            case GLFW_KEY_N:
//                window.swapBuffers();
//                again = true;
                break;
            case GLFW_KEY_O:
                if(Input.isKeyPressed(key)) {
                    pause = !pause;
                }
                break;
        }
    }

    public void stop(){
        stop = true;
    }

    public void mouseButtonIsPressed(int mouseButton) {
    Vector3f vec = renderer.camera.getRotation();
        switch (mouseButton) {
            case GLFW_MOUSE_BUTTON_LEFT:
                if(Input.isMouseButtonPressed(mouseButton)) {
                    int buttonId = gui.updateLeftMouseButtonPress(Input.getMousePos());
                    if (buttonId > -1) {
                        switch (buttonId){
                            case 0:

                                break;
                            case 1:

                                break;
                        }
                    }
                }
                renderer.camera.moveRotation(0,0,(float) 1);
                break;
            case GLFW_MOUSE_BUTTON_RIGHT:
           // renderer.camera.moveRotation(0,(float) -1,0);
                Vector2f rotVec = Input.getDisplVec();
                renderer.camera.moveRotation(rotVec.y * MOUSE_SENSITIVITY, rotVec.x * MOUSE_SENSITIVITY, 0);
            break;
        }
    }

}
