package com.sid.bookcricketgame.BallAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sid.bookcricketgame.Models.Ball
import com.sid.bookcricketgame.databinding.ScoresheetBinding
import org.w3c.dom.Text

class BallAdapter(private var mContext: Context, private var ballList: ArrayList<Ball>) :
    RecyclerView.Adapter<BallAdapter.BallVH>() {

    class BallVH(scoresheetBinding: ScoresheetBinding) :
        RecyclerView.ViewHolder(scoresheetBinding.root) {
        var scoresheetBinding : ScoresheetBinding =  scoresheetBinding

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BallVH {
        var layoutInflater = LayoutInflater.from(mContext)
        var scoresheetBinding = ScoresheetBinding.inflate(layoutInflater, parent, false)
        return BallVH(scoresheetBinding)
    }

    override fun onBindViewHolder(holder: BallVH, position: Int) {
        var  ball = ballList.get(position)
        holder.scoresheetBinding.BallTv.setText(ball.run)

        for(ball in ballList){
            Log.d("Ball",ball.run.toString())
        }

    }

    override fun getItemCount(): Int {
        return ballList.size
    }


}