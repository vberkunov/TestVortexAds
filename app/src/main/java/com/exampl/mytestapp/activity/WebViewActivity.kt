package com.exampl.mytestapp.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Window
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exampl.mytestapp.R


class WebViewActivity : AppCompatActivity() {

    private var webView: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_webview)

        webView = findViewById(R.id.webview)
        if (webView != null) {

            val webSettings = webView!!.settings

            webSettings.javaScriptEnabled = true
            webSettings.builtInZoomControls = true



           val link: String? = intent.getStringExtra("link")
            if (link != null) {
                webView!!.webViewClient = MyWebViewClient(this)
                webView!!.loadUrl(link)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && this.webView!!.canGoBack()) {
            webView!!.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (!intent.extras!!.getBoolean("FROM_SETTINGS_KEY")) moveTaskToBack(true) // exist app
        else finish()
    }



    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {


        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url: String = request?.url.toString();
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }





}