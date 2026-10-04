package com.tareqfzaz.aivideo

import android.app.Activity
import android.os.Bundle
import android.webkit.*
import android.content.Intent
import android.net.Uri

class MainActivity : Activity() {
    private lateinit var webView: WebView
    private var uploadCallback: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(v: WebView?, cb: ValueCallback<Array<Uri>>?, params: FileChooserParams?): Boolean {
                uploadCallback?.onReceiveValue(null)
                uploadCallback = cb
                return try {
                    startActivityForResult(params?.createIntent(), 1001)
                    true
                } catch (e: Exception) {
                    uploadCallback = null
                    false
                }
            }
        }
        webView.loadUrl("https://tareqfzaz-ai.github.io/aqaarat-halab/ai-video/")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            val results = if (resultCode == RESULT_OK && data != null) WebChromeClient.FileChooserParams.parseResult(resultCode, data) else null
            uploadCallback?.onReceiveValue(results)
            uploadCallback = null
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }
}
