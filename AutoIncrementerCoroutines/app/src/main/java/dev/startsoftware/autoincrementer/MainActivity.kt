package dev.startsoftware.autoincrementer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    var increment=0
    var is_running=false
    var delay_number = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var tx_counter=findViewById<TextView>(R.id.tx_counter)
        var bt_start=findViewById<Button>(R.id.bt_start)
        bt_start.setOnClickListener {
            if (!is_running) {
                is_running=true
                startCounting()
                bt_start.setText("Stop Running")

            }else{
                bt_start.setText("Start")
                is_running=false
            }
        }

        var bt_reset = findViewById<Button>(R.id.bt_reset)
        bt_reset.setOnClickListener {
            is_running = false
            bt_start.setText("Start Again")
            increment = 0
            tx_counter.setText("0")

        }
        val btIncreaseSpeed = findViewById<Button>(R.id.bt_increase_speed)
        val btDecreaseSpeed = findViewById<Button>(R.id.bt_decrease_speed)

        btIncreaseSpeed.setOnClickListener {
            // Increase the speed by reducing the delay
            delay_number = delay_number - 100 // Decrease delay by 100 milliseconds
        }

        btDecreaseSpeed.setOnClickListener {
            // Decrease the speed by increasing the delay
            delay_number = delay_number + 100 // Increase delay by 100 milliseconds
        }

    }

    private fun startCounting(){
        var tx_counter=findViewById<TextView>(R.id.tx_counter)
        CoroutineScope(Dispatchers.IO).launch {
            while(is_running) {
                increment = increment + 1
                withContext(Dispatchers.Main) {
                    tx_counter.setText("" + increment)
                }
                println("running the thread.......")
                delay(delay_number.toLong())
            }
        }
    }

}