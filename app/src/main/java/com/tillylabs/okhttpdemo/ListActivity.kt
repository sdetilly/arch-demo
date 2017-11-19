package com.tillylabs.okhttpdemo

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.RoomDatabase
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list.*


/**
 * Created by steven on 2017-10-13.
 * The "main" activity. This activity listens to the viewmodels data
 * and displays it. Since the data is LiveData, the UI will update the data automatically
 *
 */

class ListActivity : AppCompatActivity() {

    lateinit var weatherVM: WeatherVM
    lateinit var mJobScheduler: JobScheduler
    lateinit var db: RoomDatabase

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        db = WeatherDatabase.getInstance(this)
        mJobScheduler = getSystemService( Context.JOB_SCHEDULER_SERVICE ) as JobScheduler
        setupJobScheduler()
        weatherVM = ViewModelProviders.of(this).get(WeatherVM::class.java)
        weatherVM.getData().observe(this, Observer { data ->
            if(data != null && data.isNotEmpty()) {
                rv.adapter = WeatherAdapter(data)
                //TODO: Check out DiffUtil
                progressBar_list.visibility = View.GONE
            }
        })
        swipeRefresh.setOnRefreshListener {
            //weatherVM.loadDataFromServer()
            swipeRefresh.isRefreshing = false
        }
    }

    fun setupJobScheduler(){
        val builder = JobInfo.Builder(1,
                ComponentName(this, MyJobScheduler::class.java))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(1000L * 60L * 30L)

        if( mJobScheduler.schedule( builder.build() ) == JobScheduler.RESULT_FAILURE ) {
            Log.d("JOB", "Bruh... didn't work")
        }
    }

    class WeatherAdapter(val list: List<WeatherData>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>(){

        private fun getIconUrl(icon: String): String{
            return "http://openweathermap.org/img/w/$icon.png"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
            return WeatherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_weather_item, parent, false))
        }

        override fun onBindViewHolder(holder: WeatherViewHolder?, position: Int) {
            val data = list[position]
            holder!!.tvCity.text = data.cityName
            holder.tvTemp.text = "%.0f".format(data.currentTemp) + " C"
            holder.tvDesc.text = data.description
            holder.tvUpdate.text = Utils.unixToStringHour(data.timeStamp.time)
            Picasso.with(holder.icon.context).load(getIconUrl(data.icon)).into(holder.icon)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val tvCity: TextView = itemView.findViewById(R.id.tv_city)
            val tvTemp: TextView = itemView.findViewById(R.id.tv_temp)
            val icon: ImageView = itemView.findViewById(R.id.iv_icon)
            val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
            val tvUpdate: TextView = itemView.findViewById(R.id.tv_last_updated)

        }
    }
}
