package com.example.myasynctask

import android.app.ProgressDialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import org.w3c.dom.Text

/**
 * Created by shiozaki on 16/01/12.
 */
class MyAsyncTask(var context: Context) : AsyncTask<String?, Int?, Long>(), SearchManager.OnCancelListener, DialogInterface.OnCancelListener {
    val TAG = "MyAsyncTask"
    var dialog: ProgressDialog? = null
    override fun onPreExecute() {
        Log.d(TAG, "onPreExecute")
        dialog = ProgressDialog(context)
        dialog!!.setTitle("Please wait")
        dialog!!.setMessage("Loading data...")
        dialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog!!.setCancelable(true)
        dialog!!.setOnCancelListener(this)
        dialog!!.max = 100
        dialog!!.progress = 0
        dialog!!.show()
    }

    protected override fun doInBackground(vararg params: String?): Long? {
        Log.d(TAG, "doInBackground - " + params[0])
        try {
            for (i in 0..9) {
                if (isCancelled) {
                    Log.d(TAG, "Cancelled!")
                    break
                }
                Thread.sleep(1000)
                publishProgress((i + 1) * 10)
            }
        } catch (e: InterruptedException) {
            Log.d(TAG, "InterruptedException in doInBackground")
        }
        return 123L
    }

    protected override fun onProgressUpdate(vararg values: Int?) {
        Log.d(TAG, "onProgressUpdate - " + values[0])
        dialog!!.progress = values[0]!!
    }

    override fun onCancelled() {
        Log.d(TAG, "onCancelled")
        dialog!!.dismiss()
    }

    override fun onPostExecute(result: Long) {
        Log.d(TAG, "onPostExecute - $result")
        dialog!!.dismiss()
    }

    override fun onCancel() {}
    override fun onCancel(dialog: DialogInterface) {
        Log.d(TAG, "Dialog onCancell...calling cancel(true)")
        cancel(true)
    }

}
