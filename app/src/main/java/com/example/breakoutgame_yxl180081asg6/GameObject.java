/*=============================================================================
 |   Assignment:  CS6326 Project 6 Breakout Game Design
 |       Author:  YuChuan Lin
 |     Language:  Android
 |    File Name:  GameObject.java
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  Ball, Paddle, Bricks
 |
 |
 *===========================================================================*/

package com.example.breakoutgame_yxl180081asg6;
import android.graphics.RectF;
import java.util.Random;

public class GameObject {

    //Bricks
    static class Brick{
        private RectF rect;

        private boolean isVisible;

        public Brick(int row, int column, int width, int height){

            isVisible = true;

            int padding = 5;

            rect = new RectF(column * width + padding,
                    row * height + padding,
                    column * width + width - padding,
                    row * height + height - padding);
        }


        public RectF getRect(){
            return this.rect;
        }

        public void setInvisible(){
            isVisible = false;
        }

        public boolean getVisibility(){
            return isVisible;
        }
    }

    //Ball
    static class Ball{
        RectF rect;
        float xVelocity;
        float yVelocity;
        float ballWidth = 25;
        float ballHeight = 25;

        public Ball(int screenX, int screenY){

            // Start the ball travelling straight up at 100 pixels per second
            xVelocity = 2000;
            yVelocity = -4000;

            // Place the ball in the centre of the screen at the bottom
            // Make it a 10 pixel x 10 pixel square
            rect = new RectF();

        }

        public RectF getRect(){
            return rect;
        }

        public void update(long fps){
            rect.left = rect.left + (xVelocity / fps);
            rect.top = rect.top + (yVelocity / fps);
            rect.right = rect.left + ballWidth;
            rect.bottom = rect.top + ballHeight;
        }

        public void reverseYVelocity(){
            yVelocity = -yVelocity;
        }

        public void reverseXVelocity(){
            xVelocity = - xVelocity;
        }

        public void setRandomXVelocity(){
            Random generator = new Random();
            int answer = generator.nextInt(2);

            if(answer == 0){
                reverseXVelocity();
            }
        }
        public void setRandomYVelocity(){
            Random generator = new Random();
            int answer = generator.nextInt(2);

            if(answer == 0){
                reverseYVelocity();
            }
        }

        public void clearObstacleY(float y){
            rect.bottom = y;
            rect.top = y - ballHeight;
        }

        public void clearObstacleX(float x){
            rect.left = x;
            rect.right = x + ballWidth;
        }

        public void reset(int x, int y){
            rect.left = x / 2;
            rect.top = y - 25 - ballHeight;
            rect.right = x / 2 + ballWidth;
            rect.bottom = y - 25;
        }
    }

    //Paddle
    static class Paddle{
        // RectF is an object that holds four coordinates - just what we need
        private RectF rect;

        // How long and high our paddle will be
        private float length;
        private float height;

        // X is the far left of the rectangle which forms our paddle
        private float x;

        // Y is the top coordinate
        private float y;

        // This will hold the pixels per second speed that the paddle will move
        private float paddleSpeed;

        // Which ways can the paddle move
        public final int STOPPED = 0;
        public final int LEFT = 1;
        public final int RIGHT = 2;

        public int Edge;

        // Is the paddle moving and in which direction
        private int paddleMoving = STOPPED;

        // This the the constructor method
        // When we create an object from this class we will pass
        // in the screen width and height
        public Paddle(int screenX, int screenY){
            // 130 pixels wide and 20 pixels high
            length = 240;
            height = 20;
            Edge = screenX;
            // Start paddle in roughly the sceen centre
            x = screenX / 2;
            y = screenY - 20;

            rect = new RectF(x, y, x + length, y + height);

            // How fast is the paddle in pixels per second
            paddleSpeed = 3500;
        }

        // This is a getter method to make the rectangle that
        // defines our paddle available in BreakoutView class
        public RectF getRect(){
            return rect;
        }

        // This method will be used to change/set if the paddle is going left, right or nowhere
        public void setMovementState(int state){
            paddleMoving = state;
        }

        // This update method will be called from update in BreakoutView
        // It determines if the paddle needs to move and changes the coordinates
        // contained in rect if necessary
        public void update(long fps){
            if(paddleMoving == LEFT){
                if ( x < 0 ) x = 0;
                x = x - paddleSpeed / fps;
            }

            if(paddleMoving == RIGHT){
                if (x + length > Edge) x = Edge - length;
                x = x + paddleSpeed / fps;
            }

            rect.left = x;
            rect.right = x + length;
        }

        public void reset(int x, int y){
            this.x = x/2;
            rect.left = x / 2;
            rect.top = y - height;
            rect.right = x / 2 + length;
            rect.bottom = y + height;
        }

    }
}
