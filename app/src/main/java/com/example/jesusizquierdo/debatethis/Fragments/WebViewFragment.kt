package com.example.jesusizquierdo.debatethis.Fragments

import android.app.ProgressDialog
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.jesusizquierdo.debatethis.MainActivity
import com.example.jesusizquierdo.debatethis.R

/**
 * Created by Jesus Izquierdo on 5/26/2017.
 */
class WebViewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.activity_web_view, container, false)

        (context as MainActivity).supportActionBar!!.hide()

        val url = arguments.getString("KEY")
        val webView = rootView.findViewById(R.id.web_view) as WebView
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading article...")
        progressDialog.setCancelable(true)
        progressDialog.show()


        webView.loadUrl(url)
        webView.settings.javaScriptEnabled = true
        webView.setWebViewClient(WebViewClient())
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if(newProgress > 90){
                    progressDialog.dismiss()
                }else{
                    progressDialog.setMessage("Loading article " + newProgress + "%")
                }
            }
        })
        return  rootView
    }

    override fun onPause() {
        super.onPause()

    }
}