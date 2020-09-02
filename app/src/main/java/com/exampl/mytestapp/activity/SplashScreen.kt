package com.exampl.mytestapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.exampl.mytestapp.R
import com.exampl.mytestapp.api.RetrofitFactory
import com.exampl.mytestapp.entity.Links
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashScreen : AppCompatActivity() {

    lateinit var sp: SharedPreferences
    private val SPLASH_TIME_OUT:Long=2000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)



        Handler().postDelayed({
            sp = getSharedPreferences("mySettings", Context.MODE_PRIVATE)

            var hasVisited: Boolean = sp.getBoolean("hasVisited", false)

            if (!hasVisited) {

                val e: Editor = sp.edit()
                e.putBoolean("hasVisited", true)
                e.apply()
                fetchData(this)
            } else {
                val i = Intent(this, WebViewActivity::class.java)
                i.putExtra("link", sp.getString("home", "default"))
                startActivity(i)
                finish()
            }

        }, SPLASH_TIME_OUT)


    }

    fun fetchData(context: Context) {
        val service = RetrofitFactory.makeRetrofitService()


            val response = service.getLinks()
            response.enqueue(object : Callback<Links> {
                override fun onResponse(call: Call<Links>, response: Response<Links>) {
                    if (response.isSuccessful) {
                        val e: Editor = sp.edit()
                        e.putString("link", response.body()!!.link)
                        e.putString("home", response.body()!!.home)
                        e.apply()

                        val i = Intent(context, WebViewActivity::class.java)
                        i.putExtra("link", response.body()!!.link)
                        startActivity(i)
                        finish()
                    }
                }

                override fun onFailure(call: Call<Links>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }
            })


            }




    }


