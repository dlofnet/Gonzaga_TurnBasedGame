package com.example.gonzaga_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //initializing variables
    TextView txtLog, txtPlayerHP, txtEnemyHP, txtFight, txtContinue;
    Button btnAttack, btnIntimidate, btnHeal, btnFlee;
    ImageView imgPlayer, imgEnemy, imgLog;

    int turn = 0;
    int playerHP = 100;
    int enemyHP = 100;
    int playerDamage;
    int enemyDamage;
    int playerThreat = 0;
    int enemyThreat = 0;
    boolean enemyWin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hides Title Bar and Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        //set ID's
        txtLog = findViewById(R.id.txtLog);
        txtPlayerHP = findViewById(R.id.txtPlayerHP);
        txtEnemyHP = findViewById(R.id.txtEnemyHP);
        txtFight = findViewById(R.id.txtFight);
        btnAttack = findViewById(R.id.btnAttack);
        btnIntimidate = findViewById(R.id.btnIntimidate);
        btnHeal = findViewById(R.id.btnHeal);
        btnFlee = findViewById(R.id.btnFlee);
        imgPlayer = findViewById(R.id.imgPlayer);
        imgEnemy = findViewById(R.id.imgEnemy);
        imgLog = findViewById(R.id.imgLog);
        txtContinue = findViewById(R.id.txtContinue);

        //setting listeners
        btnAttack.setOnClickListener(this);
        btnIntimidate.setOnClickListener(this);
        btnHeal.setOnClickListener(this);
        btnFlee.setOnClickListener(this);
        imgLog.setOnClickListener(this);

        imgLog.setEnabled(false);
        imgLog.setClickable(false);
    }

    //adding methods here

    //method for enabling 'intimidate' and 'heal'
    public void skillEnabled(){
        //condition for Intimidate
        if (turn > 6 && turn % 2 == 1){
            btnIntimidate.setEnabled(true);
            btnIntimidate.setClickable(true);
        } else {
            btnIntimidate.setEnabled(false);
            btnIntimidate.setClickable(false);
        }
        //condition for Heal
        if (turn > 8 && turn % 2 == 1) {
            btnHeal.setEnabled(true);
            btnHeal.setClickable(true);
        } else {
            btnHeal.setEnabled(false);
            btnHeal.setClickable(false);
        }
    }

    //disables player activity
    public void playerTurnDisabled(){
        btnAttack.setEnabled(false);
        btnAttack.setClickable(false);
        btnIntimidate.setEnabled(false);
        btnIntimidate.setClickable(false);
        btnHeal.setEnabled(false);
        btnHeal.setClickable(false);
        btnFlee.setEnabled(false);
        btnFlee.setClickable(false);
        imgLog.setEnabled(true);
        imgLog.setClickable(true);
    }

    //enables player activity
    public void playerTurnEnabled(){
        imgLog.setEnabled(false);
        imgLog.setClickable(false);
        btnAttack.setEnabled(true);
        btnAttack.setClickable(true);
        btnFlee.setEnabled(true);
        btnFlee.setClickable(true);
        skillEnabled();
    }

    //resets the game
    public void resetGame(){
        turn = 0;
        playerHP = 100;
        enemyHP = 100;
        playerThreat = 0;
        enemyThreat = 0;
        txtFight.setText("FIGHT!!!");
        txtLog.setText("Choose an action...");
        txtContinue.setText("");
        txtPlayerHP.setText("Player HP:" + playerHP);
        txtEnemyHP.setText("Enemy HP:" + enemyHP);
    }

    @Override
    public void onClick(View v){

        //random selection for player and enemy attack
        Random randomizer = new Random();
        int attack = randomizer.nextInt(100);

        //player turn condition
        if (turn % 2 == 1) {
            playerTurnEnabled();
            txtContinue.setText("");
        }

        //enemy turn condition
        if (turn % 2 == 0) {
            playerTurnDisabled();
            txtContinue.setText("Click log to continue...");
        }

        //Button functionality
        switch (v.getId()) {

            //player actions here
            case R.id.btnAttack:
                if (attack >= 81) {
                    playerDamage = 18;
                    playerDamage += playerThreat;
                    txtLog.setText("You tackled the enemy and dealt " + playerDamage + " damage!");
                    enemyHP -= playerDamage;
                    txtEnemyHP.setText("Enemy HP:" + enemyHP);
                    turn++;
                } else if (attack >= 51) {
                    playerDamage = 11;
                    playerDamage += playerThreat;
                    txtLog.setText("You scratched the enemy and dealt " + playerDamage + " damage!");
                    enemyHP -= playerDamage;
                    txtEnemyHP.setText("Enemy HP:" + enemyHP);
                    turn++;
                } else {
                    playerDamage = 7;
                    playerDamage += playerThreat;
                    txtLog.setText("You pecked the enemy and dealt " + playerDamage + " damage!");
                    enemyHP -= playerDamage;
                    txtEnemyHP.setText("Enemy HP:" + enemyHP);
                    turn++;
                }
                break;

            case R.id.btnIntimidate:
                playerThreat += 3;
                txtLog.setText("You've intimidated the enemy!\n" +
                        "+" + playerThreat + " damage on next attack");
                turn++;
                break;

            case R.id.btnHeal:
                txtLog.setText("You are given bubod. You gained 10 HP!");
                playerHP += 10;
                turn -= 3;
                txtPlayerHP.setText("Player HP: " + playerHP);
                break;

            case R.id.btnFlee:
                playerHP = 0;
                txtLog.setText("You've fled. The enemy wins...");
                txtFight.setText("YOU LOSE!!!");
                txtPlayerHP.setText("Player HP: " + playerHP);
                playerTurnDisabled();
                break;

            //img log is for enemy turn and
            case R.id.imgLog:

                if (enemyHP > 0) {
                    if (attack >= 91) {
                        txtLog.setText("The enemy is given bubod and gained 10 HP.");
                        enemyHP += 10;
                        txtEnemyHP.setText("Enemy HP: " + enemyHP);
                    } else if (attack >= 76) {
                        enemyThreat += 3;
                        txtLog.setText("The enemy intimidates you! \n" +
                                "+" + enemyThreat + " damage on next attack...");
                    } else if (attack >= 61) {
                        enemyDamage = 18;
                        enemyDamage += enemyThreat;
                        txtLog.setText("You are tackled by the enemy and lost " + enemyDamage + " HP!");
                        playerHP -= enemyDamage;
                        txtPlayerHP.setText("Player HP:" + playerHP);
                    } else if (attack >= 38) {
                        enemyDamage = 11;
                        enemyDamage += enemyThreat;
                        txtLog.setText("You are scratched by the enemy and lost " + enemyDamage + " HP!");
                        playerHP -= enemyDamage;
                        txtPlayerHP.setText("Player HP:" + playerHP);
                    } else {
                        enemyDamage = 7;
                        enemyDamage += enemyThreat;
                        txtLog.setText("You are pecked by the enemy and lost " + enemyDamage + " HP!");
                        playerHP -= enemyDamage;
                        txtPlayerHP.setText("Player HP:" + playerHP);
                    }
                    turn ++;
                }

                if (enemyHP <= 0 && turn % 2 == 1) {
                    resetGame();
                    skillEnabled();
                }

                if (playerHP <= 0 && enemyWin == true){
                    resetGame();
                    enemyWin = false;
                    playerTurnEnabled();
                }

                if (playerHP <= 0) {
                    //shows "YOU LOSE" and enables game restart if playerHP < 0
                    enemyWin = true;
                    imgLog.setEnabled(true);
                    imgLog.setClickable(true);
                }
                break;
        }

        //winning condition
        if (enemyHP <= 0) {
            txtLog.setText("You've defeated the enemy!");
            txtFight.setText("YOU WIN!!!");
            txtEnemyHP.setText("Enemy HP: 0");
            txtContinue.setText("Click log to continue...");
            playerTurnDisabled();
        }

        //losing condition
        if (playerHP <= 0) {
            txtLog.setText("You've been beaten by the enemy...");
            txtFight.setText("YOU LOSE!!!");
            txtPlayerHP.setText("Player HP: 0");
            txtContinue.setText("Click log to continue...");
            playerTurnDisabled();
        }
    }
}