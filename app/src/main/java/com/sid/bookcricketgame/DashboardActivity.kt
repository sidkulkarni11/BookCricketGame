package com.sid.bookcricketgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sid.bookcricketgame.Models.OverType
import com.sid.bookcricketgame.Models.PlayerType
import com.sid.bookcricketgame.databinding.ActivityDashboardBinding
import com.sid.bookcricketgame.databinding.ActivityMainBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var activityDashboardBinding: ActivityDashboardBinding


    var playerType:PlayerType?=null
    var overType: OverType?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(activityDashboardBinding.root)

        activityDashboardBinding.player1Tv.setOnClickListener{
            activityDashboardBinding.player1Tv.setBackgroundColor(getResources().getColor(R.color.darkgreen))
            activityDashboardBinding.player1Tv.setTextColor(getResources().getColor(R.color.white))

            activityDashboardBinding.player2Tv.setBackground(getResources().getDrawable(R.drawable.green_rectangular_background))
            activityDashboardBinding.player2Tv.setTextColor(getResources().getColor(R.color.black))

            playerType = PlayerType.PLAYERONE
        }

        activityDashboardBinding.player2Tv.setOnClickListener{
            activityDashboardBinding.player1Tv.setBackground(getResources().getDrawable(R.drawable.green_rectangular_background))
            activityDashboardBinding.player1Tv.setTextColor(getResources().getColor(R.color.black))

            activityDashboardBinding.player2Tv.setBackgroundColor(getResources().getColor(R.color.darkgreen))
            activityDashboardBinding.player2Tv.setTextColor(getResources().getColor(R.color.white))


            playerType = PlayerType.PLAYERTWO
        }

        activityDashboardBinding.twoOversTv.setOnClickListener{
            activityDashboardBinding.fiveOversTv.setBackground(getResources().getDrawable(R.drawable.green_rectangular_background))
            activityDashboardBinding.fiveOversTv.setTextColor(getResources().getColor(R.color.black))

            activityDashboardBinding.tenOversTv.setBackground(getResources().getDrawable(R.drawable.green_rectangular_background))
            activityDashboardBinding.tenOversTv.setTextColor(getResources().getColor(R.color.black))

            activityDashboardBinding.twoOversTv.setBackgroundColor(getResources().getColor(R.color.darkgreen))
            activityDashboardBinding.twoOversTv.setTextColor(getResources().getColor(R.color.white))

            overType = OverType.TWOOVERS
        }

        activityDashboardBinding.fiveOversTv.setOnClickListener{
            activityDashboardBinding.twoOversTv.setBackground(getResources().getDrawable(R.drawable.green_rectangular_background))
            activityDashboardBinding.twoOversTv.setTextColor(getResources().getColor(R.color.black))

            activityDashboardBinding.tenOversTv.setBackground(getResources().getDrawable(R.drawable.green_rectangular_background))
            activityDashboardBinding.tenOversTv.setTextColor(getResources().getColor(R.color.black))

            activityDashboardBinding.fiveOversTv.setBackgroundColor(getResources().getColor(R.color.darkgreen))
            activityDashboardBinding.fiveOversTv.setTextColor(getResources().getColor(R.color.white))

            overType = OverType.FIVEOVERS
        }

        activityDashboardBinding.tenOversTv.setOnClickListener{
            activityDashboardBinding.twoOversTv.setBackground(getResources().getDrawable(R.drawable.green_rectangular_background))
            activityDashboardBinding.twoOversTv.setTextColor(getResources().getColor(R.color.black))

            activityDashboardBinding.fiveOversTv.setBackground(getResources().getDrawable(R.drawable.green_rectangular_background))
            activityDashboardBinding.fiveOversTv.setTextColor(getResources().getColor(R.color.black))

            activityDashboardBinding.tenOversTv.setBackgroundColor(getResources().getColor(R.color.darkgreen))
            activityDashboardBinding.tenOversTv.setTextColor(getResources().getColor(R.color.white))

            overType = OverType.TENOVERS
        }

        activityDashboardBinding.letsPlayButton.setOnClickListener {
            if(playerType!=null && overType!=null){

            }
            else{
                Toast.makeText(this,"Select player and overs!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}