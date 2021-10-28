package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import terrains.Tarrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.craeateDisplay();

        Loader loader = new Loader();

        Tarrain tarrain = new Tarrain(0,0,loader,new ModelTexture(loader.loadTexture("stone")));

        RawModel model3 = OBJLoader.loadObjModel("stall",loader);

        TexturedModel treemodel2 = new TexturedModel(model3,new ModelTexture(loader.loadTexture("grass")));


        List<Entity> entitys = new ArrayList<Entity>();



        Light light = new Light(new Vector3f(40 ,20,40),new Vector3f(1,1,1));

        Camera camera = new Camera(new Vector3f(40 ,1,40),0,0,0);
        MasterRenderer renderer = new MasterRenderer(loader);

        while (!Display.isCloseRequested()){

            camera.move();
            for(Entity entity : entitys){

                renderer.processEntity(entity);
            }
            renderer.processTerrain(tarrain);
            renderer.render(light,camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
