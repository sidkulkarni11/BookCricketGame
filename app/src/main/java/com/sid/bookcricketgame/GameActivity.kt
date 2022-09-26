package com.sid.bookcricketgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.sid.bookcricketgame.BallAdapter.BallAdapter
import com.sid.bookcricketgame.Models.Ball
import com.sid.bookcricketgame.Models.MenuOptions
import com.sid.bookcricketgame.Models.Score
import com.sid.bookcricketgame.databinding.ActivityGameBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class GameActivity : AppCompatActivity() {
    lateinit var activityGameBinding: ActivityGameBinding
    var count = 1
    var totalBalls: Int = 0
    lateinit var playerOneBallList: ArrayList<Ball>
    lateinit var playerTwoBallList: ArrayList<Ball>
    lateinit var menuOptions: MenuOptions
    lateinit var scoreOfPlayerOne: Score
    lateinit var scoreOfPlayerTwo: Score
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var allowClick = true
        activityGameBinding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(activityGameBinding.root)

        scoreOfPlayerOne = Score()
        scoreOfPlayerTwo = Score()

        changeScore(activityGameBinding.PlayerOneScoreBoard, scoreOfPlayerOne)
        changeScore(activityGameBinding.PlayerTwoScoreBoard, scoreOfPlayerTwo)

        if (intent != null) {
            menuOptions = (intent.getSerializableExtra("menu") as? MenuOptions)!!
        }
        totalBalls = menuOptions.overs * 6 * 2

        activityGameBinding.CalculatedScore.visibility = View.GONE
        activityGameBinding.CalculatedRuns.visibility = View.GONE

        playerOneBallList = ArrayList()
        playerTwoBallList = ArrayList()

        activityGameBinding.playerOneScoreSheetRecyclerView.layoutManager =
            GridLayoutManager(applicationContext, 6)

        activityGameBinding.playerTwoScoreSheetRecylerView.layoutManager =
            GridLayoutManager(applicationContext, 6)

        if (menuOptions.playerType == 1) {
            computerPlays()
        } else {
            activityGameBinding.PlayerOne.setText("Player 1")
            activityGameBinding.PlayerTwo.setText("Player 2")
        }

        activityGameBinding.Bookimgs.setOnClickListener {
            if (allowClick) {
                allowClick = false
                activityGameBinding.Bookimgs.setImageResource(R.drawable.ic_baseline_menu_book_24)
                activityGameBinding.CalculatedScore.visibility = View.VISIBLE
                activityGameBinding.CalculatedRuns.visibility = View.VISIBLE

                var calcScore = (0..1000).random()
                activityGameBinding.CalculatedScore.setText(calcScore.toString())
                if (calcScore > 10) {
                    calcScore = calcScore % 10
                }
                var ball = Ball(count, calcScore.toString())

                if (menuOptions.playerType == 2 && scoreOfPlayerOne.wickets < 10) {

                    if (count < totalBalls / 2 + 1) {
                        addPlayeroneRecyclerView(ball)
                    } else {
                        addPlayerTwoRecyclerView(ball)
                    }
                } else {
                    addPlayerTwoRecyclerView(ball)
                }

                activityGameBinding.CalculatedRuns.setText(calcScore.toString())
                Executors.newSingleThreadScheduledExecutor().schedule({
                    Log.d("Handler**", "Test")

                    runOnUiThread(Runnable {

                        activityGameBinding.Bookimgs.setImageResource(R.drawable.ic_baseline_book_24)
                        activityGameBinding.CalculatedScore.visibility = View.GONE
                        activityGameBinding.CalculatedRuns.visibility = View.GONE
                        allowClick = true
                    })

                }, 2, TimeUnit.SECONDS)
            }

        }
    }

    private fun computerPlays() {
        var computerBallList: ArrayList<Ball> = ArrayList()

        for (ball in 1 until totalBalls.div(2) + 1) {
            var ballName = (Math.random()*100
                    ).toInt()


            if (ballName > 10) {
                ballName = ballName % 10
            }

            scoreOfPlayerOne.runs = scoreOfPlayerOne.runs + ballName
            var ball:Ball?
            if (ballName==0) {
                ball = Ball(count,"w")
                scoreOfPlayerOne.wickets++
            }
            else{
                ball = Ball(count,ballName.toString())
            }

            computerBallList.add(ball)
            changeScore(activityGameBinding.PlayerOneScoreBoard, scoreOfPlayerOne)
            count++
        }

        var ballAdapter = BallAdapter(applicationContext, computerBallList)

        activityGameBinding.playerOneScoreSheetRecyclerView.adapter = ballAdapter
    }

    private fun addPlayeroneRecyclerView(ball: Ball) {
        scoreOfPlayerOne.runs = scoreOfPlayerOne.runs + ball.run!!.toInt()
        if (ball.run.equals("0")) {
            ball.run = "w"
            scoreOfPlayerOne.wickets++
        }

        playerOneBallList.add(ball)


        var ballAdapter = BallAdapter(applicationContext, playerOneBallList)

        activityGameBinding.playerOneScoreSheetRecyclerView.adapter = ballAdapter

        changeScore(activityGameBinding.PlayerOneScoreBoard, scoreOfPlayerOne)
        count++

    }


    private fun addPlayerTwoRecyclerView(ball: Ball) {
        scoreOfPlayerTwo.runs = scoreOfPlayerTwo.runs + ball.run!!.toInt()

        if (count < totalBalls + 1 && scoreOfPlayerTwo.wickets < 10) {

            if (ball.run.equals("0")) {
                ball.run = "w"
                scoreOfPlayerTwo.wickets++
            }

            playerTwoBallList.add(ball)


            var ballAdapter = BallAdapter(applicationContext, playerTwoBallList)

            activityGameBinding.playerTwoScoreSheetRecylerView.adapter = ballAdapter
            changeScore(activityGameBinding.PlayerTwoScoreBoard, scoreOfPlayerTwo)

            count++
            if (count == totalBalls+1&& scoreOfPlayerTwo.wickets==10) {
                showScoreDialog()
            }
        } else {
            showScoreDialog();
        }
    }

    private fun showScoreDialog() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Hurray!")
        //set message for alert dialog
        if (scoreOfPlayerOne.runs > scoreOfPlayerTwo.runs) {
            builder.setMessage("Player 1 Won!")
        } else if (scoreOfPlayerOne.runs == scoreOfPlayerTwo.runs) {
            builder.setMessage("Its a tie!")
        } else {
            builder.setMessage("Player 2 Won!")
        }
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setNeutralButton("YAY!") { dialogInterface, which ->
            var dashboardIntent = Intent(this, DashboardActivity::class.java)
            dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(dashboardIntent)
        }

        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun changeScore(scoreTextView: TextView, score: Score) {
        scoreTextView.setText("Score : " + score.runs.toString() + "-" + score.wickets.toString())
    }


}