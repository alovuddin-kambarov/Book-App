package uz.coder.mvpexample.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import uz.coder.mvpexample.R
import uz.coder.mvpexample.databinding.ActivitySpashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySpashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lottieAnimationView.playAnimation()

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            ); finish()
        }, 2800)

    }
}