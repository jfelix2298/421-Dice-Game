/*
Ruben Alvarez Reyes
Javier Felix
CSCV-335 Spring 2020
Capstone: 4-2-1
 */

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;

import java.util.Random;


public class MainScreenController {

    private final static String imgPath = "asset/img/%s.png";
    private final static Image[] dieImages = {
            new Image(String.format(imgPath, 1)),
            new Image(String.format(imgPath, 2)),
            new Image(String.format(imgPath, 3)),
            new Image(String.format(imgPath, 4)),
            new Image(String.format(imgPath, 5)),
            new Image(String.format(imgPath, 6)),
            new Image(String.format(imgPath, 7))
    };
    private final static String[] sndPaths = {
            "asset/snd/roll_die.wav",
            "asset/snd/switch_die.wav"
    };

    private final AudioClip rollDieSnd;
    private final AudioClip switchDieSnd;
    private final MainScreenModel model;

    @FXML
    private GridPane root;
    @FXML
    private Label dieStatusLabel;
    @FXML
    private Label player1ScoreLabel;
    @FXML
    private Label player1RollLabel;
    @FXML
    private Label player2ScoreLabel;
    @FXML
    private Label player2RollLabel;
    @FXML
    private ImageView die1Image;
    @FXML
    private ImageView die2Image;
    @FXML
    private ImageView die3Image;
    @FXML
    private Button rollButton;
    @FXML
    private Button doneButton;
    @FXML
    private TextFlow feedbackLabel;
    @FXML
    private Label playerTurnLabel;
    @FXML
    private Label currentRoundLabel;

    public MainScreenController() {
        model = new MainScreenModel();
        rollDieSnd = new AudioClip(getClass().getResource(sndPaths[0]).toString());
        switchDieSnd = new AudioClip(getClass().getResource(sndPaths[1]).toString());
    }

    @FXML
    public void initialize() {
        newTurn();
//        set colors
        root.setBackground(new Background(new BackgroundFill(Color.rgb(20, 158, 66), CornerRadii.EMPTY, Insets.EMPTY)));
        rollButton.setBackground(new Background(new BackgroundFill(Color.rgb(226, 193, 29), CornerRadii.EMPTY, Insets.EMPTY)));
        doneButton.setBackground(new Background(new BackgroundFill(Color.rgb(226, 29, 62), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @FXML
    public void onRollPressed() {
//        play sound
        rollDieSnd.play();
//        set roll counter text
        int playerTurn = model.getPlayerTurn();
        String rollText = String.format("Roll: %d", model.getCurrentPlayerRollCount() + 1);
        String defaultRollText = "Roll: 0";
        if (playerTurn == 0) {
            player1RollLabel.setText(rollText);
            player2RollLabel.setText(defaultRollText);
        } else {
            player2RollLabel.setText(rollText);
            player1RollLabel.setText(defaultRollText);
        }
//        roll die
        model.setDie(rollDie(), rollDie(), rollDie());
        if (playerTurn == model.getPlayerTurn() && model.getDie(0) > 0 && model.getDie(1) > 0 && model.getDie(2) > 0) {
//          set die label text
            dieStatusLabel.setText(String.format("%d-%d-%d", model.getDie(0), model.getDie(1), model.getDie(2)));
//          set die images
            die1Image.setImage(dieImages[model.getDie(0) - 1]);
            die2Image.setImage(dieImages[model.getDie(1) - 1]);
            die3Image.setImage(dieImages[model.getDie(2) - 1]);
        } else {
            newTurn();
        }
    }

    @FXML
    public void onDonePressed() {
        switchDieSnd.play();
        model.switchPlayers();
        newTurn();
    }

    private int rollDie() {
//        return a random number from 1-6
        return new Random().nextInt(6) + 1;
    }

    private void newTurn() {
        dieStatusLabel.setText("Roll the die!");
        playerTurnLabel.setText(String.format("Player turn: %d", model.getPlayerTurn() + 1));
        die1Image.setImage(dieImages[6]);
        die2Image.setImage(dieImages[6]);
        die3Image.setImage(dieImages[6]);
    }

}
