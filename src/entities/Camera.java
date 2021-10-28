package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

public class Camera {

    private Vector3f position;
    private float pitch;
    private float yaw;
    private float roll;

    private final float SPEED = 10f;
    private final float RUNSPEED = 20f;
    private final float SENSI = 20f;

    private final float GRAVITY = -50f;
    private final float JUMP_POWER = 15f;
    private final float GROUND = 2;

    private float currentSpeedLR = 0;
    private float currentSpeedFB = 0;
    private float upwardsSpeed = 0;

    private double defaultMouseX = 960.0;
    private double defaultMouseY = 540.0;

    private boolean mouse = false;

    public Camera(Vector3f position, float pitch, float yaw, float roll) {
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public void move(){

        float distance = currentSpeedLR * DisplayManager.getFrameTimeSeconds();
        float dz = (float) (distance* Math.sin(Math.toRadians(getYaw())));
        float dx = (float) (distance* Math.cos(Math.toRadians(getYaw())));
        increasePosition(dx,0,dz);

        float distanceFB = currentSpeedFB * DisplayManager.getFrameTimeSeconds();
        float dzFB = (float) (distanceFB* Math.cos(Math.toRadians(-getYaw())));
        float dxFB = (float) (distanceFB* Math.sin(Math.toRadians(-getYaw())));
        increasePosition(dxFB,0,dzFB);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        increasePosition(0,upwardsSpeed * DisplayManager.getFrameTimeSeconds(),0);
        if(position.getY()<GROUND){
            upwardsSpeed = 0;
            position.setY(GROUND);
        }



        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            currentSpeedLR = -SPEED;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            currentSpeedLR = SPEED;
        }else {
            currentSpeedLR = 0;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            currentSpeedFB = -SPEED;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            currentSpeedFB = SPEED;
        }else {
            currentSpeedFB = 0;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            if(position.getY()<(GROUND+0.1)){
                jump();
            }
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
             mouse = false;
        }
        if(Mouse.isButtonDown(0) && !mouse) {
            mouse = true;
        } else if(Mouse.isButtonDown(1) && !mouse) {
            mouse = true;
        }


        double[] mouseDelta = pollMouseDelta();

        if (mouseDelta[1]>0){
            if(pitch > -80) {
                pitch -= SENSI * (float) mouseDelta[1] * DisplayManager.getFrameTimeSeconds();
            }else {
                pitch = -80;
            }
        }else if (mouseDelta[1]<0){
            if(pitch < 80) {
                pitch -= SENSI * (float) mouseDelta[1] * DisplayManager.getFrameTimeSeconds();
            }else {
                pitch = 80;
            }
        }

        if (mouseDelta[0]>0){
            yaw += SENSI * (float) mouseDelta[0] * DisplayManager.getFrameTimeSeconds();
        }else if (mouseDelta[0]<0){
            yaw += SENSI * (float) mouseDelta[0] * DisplayManager.getFrameTimeSeconds();
        }
        if(mouse) {
            Mouse.setGrabbed(true);
        }
    }

    private void jump(){
        upwardsSpeed = JUMP_POWER;
    }

    public double[] pollMouseDelta() {

        double mouseX = Mouse.getX();
        double mouseY = Mouse.getY();

        mouseX = mouseX - defaultMouseX;
        mouseY = mouseY - defaultMouseY;

        if(mouse) {
            Mouse.setCursorPosition(1920 / 2, 1080 / 2);
        }

        return new double[] {
                mouseX ,
                mouseY
        };
    }
    
    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.pitch += dx;
        this.yaw += dy;
        this.roll += dz;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}