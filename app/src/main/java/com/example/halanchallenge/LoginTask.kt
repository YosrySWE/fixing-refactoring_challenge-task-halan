package com.example.halanchallenge

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class LoginTask internal constructor(private val context: Context) :
    AsyncTask<String?, Void?, LoginResponse>() {
    private var username: String? = null
    private var password: String? = null
    override fun onPreExecute() {
        //TODO: Before process async task (show progress bar, loader, fade..)
    }

    @SuppressLint("WrongThread")
    override fun doInBackground(vararg params: String?): LoginResponse {

        //Set username and password
        var response = LoginResponse()
        username = params[0]
        password = params[1]
        try {
            //Setup URL connection
            val url = URL("https://assessment-sn12.halan.io/auth")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.addRequestProperty("Content-Type", "application/json")
            connection.doInput = true
            connection.doOutput = true
            val writer = BufferedWriter(OutputStreamWriter(connection.outputStream))
            val body = JSONObject()
            body.put("username", username)
            body.put("password", password)
            writer.write(body.toString())
            writer.close()
            connection.connect()
            val status = connection.responseCode
            val `in`: InputStream = BufferedInputStream(connection.inputStream)
            response = `in`.readStream()
            when (status) {
                200 -> onPostExecute(response)
            }
        } catch (e: MalformedURLException) {
            Log.w(ContentValues.TAG, "Exception while constructing URL" + e.message)
        } catch (e: IOException) {
            Log.w(ContentValues.TAG, "Exception occured while logging in: " + e.message)
        } catch (e: JSONException) {
            Log.w(ContentValues.TAG, "Exception occured while logging in: " + e.message)
        }
        return response
    }

    override fun onPostExecute(success: LoginResponse) {
        //TODO: After async task finished based on task success
        if (success.token != null) {
            val myIntent = Intent(context, ProductsListActivity::class.java)
            myIntent.putExtra("RESPONSE", Gson().toJson(success))
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(myIntent)
        }
    }
}

fun InputStream.readStream(): LoginResponse{
    val r = BufferedReader(InputStreamReader(this))
    val total: StringBuilder = StringBuilder()
    var line: String?
    while (r.readLine().also { line = it } != null) {
        total.append(line).append('\n')
    }

    return Gson().fromJson(total.toString(), LoginResponse::class.java)
}

