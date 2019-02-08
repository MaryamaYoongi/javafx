package pingPong.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class Controller {
    @FXML
    private Rectangle kidPlayer;
    @FXML
    private Rectangle compPlayer;
    @FXML
    private Circle ball;
    @FXML
    private Rectangle field;
    @FXML
    private Text score;
    @FXML
    private Text gameOver;

    private Timeline timeline;

    private DoubleProperty currentKidPlayerY = new SimpleDoubleProperty();
    private DoubleProperty currentCompPlayerY = new SimpleDoubleProperty();
    private DoubleProperty currentBallX = new SimpleDoubleProperty();
    private DoubleProperty currentBallY = new SimpleDoubleProperty();
    private double firstKidPosition;
    private double firstCompPosition;
    private double firstBallX;
    private double firstBallY;

    private double allowedTopY;
    private double allowedBottomY;
    private double allowedLeftX;
    private double allowedRightX;
    private double allowedBallBottomY;
    private double allowedBallTopY;

    private double firstBallSpeedX = 1.5;
    private double firstBallSpeedY = 1.5;
    private double ballSpeedY;
    private double ballSpeedX;
    private double paddleSpeed = 10;
    private double boost = 1.1;
    private int finishScore = 5;

    private int chance;
    private int compScore = 0;
    private int kidScore = 0;

    private boolean isToTheTop;
    private boolean changeText = false;

    @FXML
    private void initialize() {
        System.out.println("Everything is ready!!!");
        allowedTopY = 0;
        allowedBottomY = field.getHeight() - kidPlayer.getHeight();
        allowedBallBottomY = field.getHeight() - ball.getRadius();
        allowedBallTopY = ball.getRadius();
        allowedLeftX = ball.getRadius();
        allowedRightX = field.getWidth() - ball.getRadius();

        currentKidPlayerY.set(kidPlayer.getLayoutY());
        kidPlayer.layoutYProperty().bind(currentKidPlayerY);
        firstKidPosition = currentKidPlayerY.get();

        currentCompPlayerY.set(compPlayer.getLayoutY());
        compPlayer.layoutYProperty().bind(currentCompPlayerY);
        firstCompPosition = currentCompPlayerY.get();

        currentBallX.set(ball.getLayoutX());
        ball.layoutXProperty().bind(currentBallX);
        firstBallX = currentBallX.get();

        currentBallY.set(ball.getLayoutY());
        ball.layoutYProperty().bind(currentBallY);
        firstBallY = currentBallY.get();
    }

    private void moveTheBall() {
        KeyFrame keyFrame = new KeyFrame(new Duration(10), event -> {
            currentBallY.set(currentBallY.get() + ballSpeedY);
            currentBallX.set(currentBallX.get() + ballSpeedX);
            checkForContactWithField();
            checkForContactWithKidPlayer();
            checkForContactWithCompPlayer();
            isGameOver();
        }

        );
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void checkForContactWithField() {
        if (!isToTheTop) {
            if (currentBallY.get() >= allowedBallBottomY) {
                ballSpeedY *= -1;
                isToTheTop = true;
            }
        } else {
            if (currentBallY.get() <= allowedBallTopY) {
                ballSpeedY *= -1;
                isToTheTop = false;
            }
        }
    }

    private void checkForContactWithKidPlayer() {
        if ((currentBallX.get() - ball.getRadius() <= kidPlayer.getLayoutX() + kidPlayer.getWidth())
                && ((currentBallY.get() <= currentKidPlayerY.get() + kidPlayer.getHeight())
                && (currentBallY.get() >= currentKidPlayerY.get()))) {
            ballSpeedX = Math.abs(ballSpeedX) * boost;
            ballSpeedY *= boost;
        }
    }


    private void checkForContactWithCompPlayer() {
        if ((currentBallX.get() + ball.getRadius() >= compPlayer.getLayoutX())
                && ((currentBallY.get() <= currentCompPlayerY.get() + compPlayer.getHeight())
                && (currentBallY.get() >= currentCompPlayerY.get()))) {
            ballSpeedX = -Math.abs(ballSpeedX) * boost;
            ballSpeedY *= boost;
        }
    }


    private void isGameOver() {
        if (currentBallX.get() <= allowedLeftX) {
            stopGame();
            compScore += 1;
        }
        if (currentBallX.get() >= allowedRightX) {
            stopGame();
            kidScore += 1;
        }
        score.setText(kidScore + " : " + compScore);
        checkScore();
    }

    private void checkScore() {
        if (compScore == finishScore || kidScore == finishScore) {
            gameOver.setText("Game Over");
            changeText = true;
        }

    }

    private void stopGame() {
        timeline.stop();
        startPositions();
        if (ballSpeedX < 0) {
            ballSpeedX *= -1;
        }
        if (ballSpeedY < 0) {
            ballSpeedY *= -1;
        }
    }


    private void startPositions() {
        currentKidPlayerY.set(firstKidPosition);
        currentCompPlayerY.set(firstCompPosition);
        currentBallY.set(firstBallY);
        currentBallX.set(firstBallX);
    }

    private void startScore() {
        compScore = 0;
        kidScore = 0;
        score.setText(kidScore + " : " + compScore);
    }


    @FXML
    private void keyPressedHandler(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case W:
                if (currentKidPlayerY.get() > allowedTopY) {
                    currentKidPlayerY.set(currentKidPlayerY.get() - paddleSpeed);
                }
                break;
            case S:
                if (currentKidPlayerY.get() < allowedBottomY) {
                    currentKidPlayerY.set(currentKidPlayerY.get() + paddleSpeed);
                }
                break;
            case UP:
                if (currentCompPlayerY.get() > allowedTopY) {
                    currentCompPlayerY.set(currentCompPlayerY.get() - paddleSpeed);
                }
                break;
            case DOWN:
                if (currentCompPlayerY.get() < allowedBottomY) {
                    currentCompPlayerY.set(currentCompPlayerY.get() + paddleSpeed);
                }
                break;
            case Q:
                timeline.stop();
                startPositions();
                startScore();
                ballSpeedX = firstBallSpeedX;
                ballSpeedY = firstBallSpeedY;
                if (ballSpeedY < 0) {
                    ballSpeedY *= -1;
                }
                if (ballSpeedX < 0) {
                    ballSpeedX *= -1;
                }
                break;
            case P:
                if (changeText == true) {
                    startScore();
                    gameOver.setText("");
                }
                ballSpeedY = firstBallSpeedY;
                ballSpeedX = firstBallSpeedX;
                chance = 1 + (int) (Math.random() * 4);
                switch (chance) {
                    case 1:
                        ballSpeedX *= -1;
                        isToTheTop = false;
                        moveTheBall();
                        break;
                    case 2:
                        ballSpeedY *= -1;
                        ballSpeedX *= -1;
                        isToTheTop = true;
                        moveTheBall();
                        break;
                    case 3:
                        isToTheTop = false;
                        moveTheBall();
                        break;
                    case 4:
                        ballSpeedY *= -1;
                        isToTheTop = true;
                        moveTheBall();
                        break;
                    default:
                        System.out.println("You pressed " + keyCode);
                }
        }
    }
}