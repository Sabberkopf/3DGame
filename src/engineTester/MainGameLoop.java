package engineTester;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.craeateDisplay();

        Loader loader = new Loader();
        StaticShader staticShader = new StaticShader();
        Renderer renderer = new Renderer(staticShader);

        RawModel model = OBJLoader.loadObjModel("stall",loader);

        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));

        TexturedModel texturedModel = new TexturedModel(model,texture);

        Entity entity = new Entity(texturedModel,new Vector3f(0 ,0,0),0,0,0,1);

        Camera camera = new Camera();

        while (!Display.isCloseRequested()){
            entity.increaseRotation(0,0.01f,0);
            camera.move();
            renderer.prepare();
            staticShader.start();
            staticShader.loadViewMatrix(camera);
            renderer.render(entity,staticShader);
            staticShader.stop();
            DisplayManager.updateDisplay();
        }
        staticShader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
