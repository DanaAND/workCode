package tilegame;

import display.Display;
import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game implements Runnable{

    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    private BufferedImage testImage;
    private SpriteSheet sheet;

    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init() {

        display = new Display(title, width, height);
        testImage = ImageLoader.loadImage("/textures/sprite.tps");
        sheet = new SpriteSheet(testImage);
    }


    private void tick() {
    }


    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //Clear Screen
        g.clearRect(0,0,width, height);
        //Draw Here!

        g.drawImage(sheet.crop(0,0, 32,32), 5, 5, null);

        //EndDrawing!
        bs.show();
        g.dispose();
    }

    public void run() {
        init();

        while (running){
            tick();
            render();
        }

        stop();
    }


    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
