package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

public class DisplayManager {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final int FPS_CAP=165;

    private static long lastFrameTime;
    private static float delta;

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
        lastFrameTime = getCurrentTime();
    }
    public static void updateDisplay(){
        Display.sync(FPS_CAP);
        Display.update();
        long currnetFrameTime = getCurrentTime();
        delta = (currnetFrameTime - lastFrameTime)/1000.0f;
        lastFrameTime = currnetFrameTime;
    }
    public static float getFrameTimeSeconds(){
        return  delta;
    }

    public static void closeDisplay(){
        Display.destroy();
    }

    private static long getCurrentTime(){
        return Sys.getTime()*1000 /Sys.getTimerResolution();
    }
}
