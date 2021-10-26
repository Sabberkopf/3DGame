package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManager {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final int FPS_CAP=165;

    public static void craeateDisplay(){
        ContextAttribs attribs = new ContextAttribs(3,2)
        .withForwardCompatible(true)
        .withProfileCore(true);
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
            Display.create(new PixelFormat(),attribs);
            Display.setTitle("3Dgame");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        GL11.glViewport(0,0,WIDTH,HEIGHT);
    }
    public static void updateDisplay(){
        Display.sync(FPS_CAP);
        Display.update();
    }
    public static void closeDisplay(){
        Display.destroy();
    }
}
