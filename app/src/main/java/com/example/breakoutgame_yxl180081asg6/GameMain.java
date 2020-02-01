/*=============================================================================
 |   Assignment:  CS6326 Project 6 Breakout Game Design
 |       Author:  YuChuan Lin
 |     Language:  Android
 |    File Name:  Gamemain.java
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  A breakout game view.
 |
 |
 *===========================================================================*/

package com.example.breakoutgame_yxl180081asg6;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class GameMain extends AppCompatActivity {
    // gameView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    GameView gameView;
    private String playername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize gameView and set it as the view
        gameView = new GameView(this);
        setContentView(gameView);
        playername = getIntent().getStringExtra("Name");
    }

    public void toRank(){
        Intent intent = new Intent(this, Ranking.class);
        String gameScore = Integer.toString(gameView.score);
        intent.putExtra("Name", playername);
        intent.putExtra("Score", gameScore);
//        setResult(RESULT_OK,intent);
        finish();
        startActivity(intent);
    }

    class GameView extends View {
        // This is our thread
//    Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
//    SurfaceHolder ourHolder;

        // A boolean which we will set and unset
        // when the game is running- or not.
//    volatile boolean playing;

        // Game is paused at the start
        boolean paused = true;

        // A Canvas and a Paint object
        Canvas canvas;
        Paint paint;

        // This variable tracks the game frame rate
        long fps;

        // This is used to help calculate the fps
        private long timeThisFrame;

        // The size of the screen in pixels
        int screenX;
        int screenY;

        // The players paddle
        GameObject.Paddle paddle;

        // A ball
        GameObject.Ball ball;

        // Up to 200 bricks
        GameObject.Brick[] bricks = new GameObject.Brick[100];
        int numBricks = 0;
        int bricksLast = 0;


        // The score
        int score = 0;

        // Lives
        int lives = 3;

        //Check if dead
        public boolean isDead = false;
        public boolean isClear = false;

        // When the we initialize (call new()) on gameView
        // This special constructor method runs
        public GameView(Context context) {
            // The next line of code asks the
            // SurfaceView class to set up our object.
            // How kind.
            super(context);

            // Initialize ourHolder and paint objects
//        ourHolder = getHolder();
            paint = new Paint();

            // Get a Display object to access screen details
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            // Load the resolution into a Point object
            Point size = new Point();
            display.getSize(size);

            screenX = size.x;
            screenY = size.y;

            paddle = new GameObject.Paddle(screenX, screenY);

            // Create a ball
            ball = new GameObject.Ball(screenX, screenY);


            createBricksAndRestart();

        }

//    private Runnable r = new Runnable() {
//        @Override
//        public void run() {
//            invalidate();
//        }
//    };

        public void createBricksAndRestart() {

            // Put the ball back to the start
            ball.reset(screenX, screenY);
            paddle.reset(screenX,screenY);

            int brickWidth = screenX / 8;
            int brickHeight = screenY / 20;

            // Build a wall of bricks
            numBricks = 0;
            for (int column = 0; column < 8; column++) {
                for (int row = 0; row < 4; row++) {
                    bricks[numBricks] = new GameObject.Brick(row, column, brickWidth, brickHeight);
                    numBricks++;
                }
            }
            bricksLast = numBricks;
            // if game over reset scores and lives
//            if (lives == 0) {
//                score = 0;
//                lives = 3;
//            }
        }


        // Everything that needs to be updated goes in here
        // Movement, collision detection etc.
        public void update() {

            // Move the paddle if required
            paddle.update(fps);

            ball.update(fps);


            // Check for ball colliding with a brick
            for (int i = 0; i < numBricks; i++) {
                if (bricks[i].getVisibility()) {
                    if (RectF.intersects(ball.getRect(),bricks[i].getRect())) {
                        if (ball.getRect().left > bricks[i].getRect().right || ball.getRect().right < bricks[i].getRect().left) {
                            bricks[i].setInvisible();
                            ball.reverseYVelocity();
                            score = score + 10;
                            bricksLast--;
//                        if (ball.getRect().top < bricks[i].getRect().bottom){
//                            ball.clearObstacleY(bricks[i].getRect().bottom + 27);
//                        }
//                        else {
//                            ball.clearObstacleY(bricks[i].getRect().top - 2);
//                        }
                        }
                        else{
                            bricks[i].setInvisible();
                            ball.reverseXVelocity();
                            ball.reverseYVelocity();
                            score = score + 10;
                            bricksLast--;
//                        if (ball.getRect().left < bricks[i].getRect().right){
//                            ball.clearObstacleX(bricks[i].getRect().right + 2);
//                        }
//                        else {
//                            ball.clearObstacleX(bricks[i].getRect().left - 27);
//                        }
                        }
                    }
                }
            }
            // Check for ball colliding with paddle
            if (RectF.intersects(paddle.getRect(), ball.getRect())) {
                ball.setRandomXVelocity();
                ball.reverseYVelocity();
                ball.clearObstacleY(paddle.getRect().top - 2);
            }
            // Bounce the ball back when it hits the bottom of screen
            if (ball.getRect().bottom > screenY) {
                ball.reverseYVelocity();
                ball.clearObstacleY(screenY - 2);

                ball.reset(screenX,screenY);
                paddle.reset(screenX,screenY);

                // Lose a life
                paused=true;
                lives--;

                if (lives == 0) {
                    paused = true;
                    isDead = true;

//                createBricksAndRestart();
                }
            }

            // Bounce the ball back when it hits the top of screen
            if (ball.getRect().top < 0)

            {
                ball.reverseYVelocity();
                ball.clearObstacleY(27);
            }

            // If the ball hits left wall bounce
            if (ball.getRect().left < 0)

            {
                ball.reverseXVelocity();
                ball.clearObstacleX(2);
            }

            // If the ball hits right wall bounce
            if (ball.getRect().right > screenX - 25) {

                ball.reverseXVelocity();
                ball.clearObstacleX(screenX - 52);
            }

            // Pause if cleared screen
            if (bricksLast == 0)

            {
                paused =true;
                isClear = true;
                createBricksAndRestart();
            }

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            this.canvas = canvas;
            run();
        }

        // Draw the newly updated scene
        public void draw() {

            // Make sure our drawing surface is valid or we crash

            // Draw the background color
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Draw the paddle
            canvas.drawRect(paddle.getRect(), paint);

            // Draw the ball
            canvas.drawRect(ball.getRect(), paint);

            // Change the brush color for drawing
            paint.setColor(Color.argb(255, 249, 129, 0));

            // Draw the bricks if visible
            for (int i = 0; i < numBricks; i++) {
                if (bricks[i].getVisibility()) {
                    canvas.drawRect(bricks[i].getRect(), paint);
                }
            }

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Draw the score
            paint.setTextSize(40);
            canvas.drawText("Score: " + score + "   Lives: " + lives, 10, 50, paint);

//            // Has the player cleared the screen?
//            if (score == numBricks * 10) {
//                paint.setTextSize(90);
//                canvas.drawText("YOU HAVE WON!",200, screenY / 2, paint);
//                paused = true;
////                haveWon = true;
//            }

            // Has the player lost?
            if (lives <= 0) {
                paint.setTextSize(90);
                canvas.drawText("GameOver!", 200, screenY / 2, paint);
            }

        }

        public void run() {
//        while (playing) {
            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();
            // Update the frame
            if (!paused) {
                update();
            }
            // Draw the frame
            draw();
            invalidate();
            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 250 / timeThisFrame;
            }

//        }

        }
        // If SimpleGameEngine Activity is paused/stopped
        // shutdown our thread.
//    public void pause() {
//        playing = false;
//        try {
//            gameThread.join();
//        } catch (InterruptedException e) {
//            Log.e("Error:", "joining thread");
//        }
//    }

        // If SimpleGameEngine Activity is started then
        // start our thread.
//    public void resume() {
//        playing = true;
//        gameThread = new Thread(this.gameThread);
//        gameThread.start();
//    }

        // The SurfaceView class implements onTouchListener
        // So we can override this method and detect screen touches.
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                // Player has touched the screen
                case MotionEvent.ACTION_DOWN:

                    if (isDead) {
//                        createBricksAndRestart();
                        toRank();
                        isDead = false;
                    }

                    if(isClear){
                        isClear = false;
                    }
//                    if (haveWon){
//                        toRank();;
//                        haveWon = false;
//                    }
                    paused = false;

                    if (motionEvent.getX() > screenX / 2) {
                        paddle.setMovementState(paddle.RIGHT);
                    } else

                    {
                        paddle.setMovementState(paddle.LEFT);
                    }

                    break;

                // Player has removed finger from screen
                case MotionEvent.ACTION_UP:

                    paddle.setMovementState(paddle.STOPPED);
                    break;
            }

            return true;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
//        gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
//        gameView.pause();
    }


}
