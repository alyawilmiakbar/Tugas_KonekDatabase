package com.example.tugas_konekdatabase

import Fakultas
import RVAAdapterFakultas
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var arrayList = ArrayList<Fakultas>()
    supportActionBar?.title = "Data Mahasiswa"

    mRecyclerView.setHasFixedSize(true)
    mRecyclerView.layoutManager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    val loading = ProgressDialog(this)
    loading.setMessage("Memuat data...")
    loading.show()

    AndroidNetworking.get(ApiEndPoint.READ)
    .setPriority(Priority.MEDI{UM)
        .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
            })
        override fun onResponse(response: JSONObject?) {
            arrayList.clear()
            val jsonArray = response?.optJSONArray("result")

            if(jsonArray?.length() == 0){
                loading.dismiss()
                Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()
            }
            for(i in 0 until jsonArray?.length()!!){

                val jsonObject = jsonArray?.optJSONObject(i)
                arrayList.add(Fakultas(jsonObject.getString("nim"),
                    jsonObject.getString("id"),
                    jsonObject.getString("kode"),
                    jsonObject.getString("nama")))

                if(jsonArray?.length() - 1 == i){

                    loading.dismiss()
                    val adapter = RVAAdapterFakultas(applicationContext,arrayList)
                    adapter.notifyDataSetChanged()
                    mRecyclerView.adapter = adapter

                }

            }
        }

        override fun onError(anError: ANError?) {
            loading.dismiss()
            Log.d("ONERROR",anError?.errorDetail?.toString())
            Toast.makeText(applicationContext,"Connection Failure",Toast.LENGTH_SHORT).show()
        }

        
