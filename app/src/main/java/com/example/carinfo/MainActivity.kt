package com.example.carinfo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.carinfo.api.CarInfoApi
import com.example.carinfo.api.CarInfoResponse
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var carNumberEditText: EditText
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)
        carNumberEditText = findViewById(R.id.carNumberEditText)
        searchButton = findViewById(R.id.searchButton)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://data.gov.il/api/3/action/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CarInfoApi::class.java)

        // Set onClickListener for the search button
        searchButton.setOnClickListener {
            val carNumber = carNumberEditText.text.toString()
            if (carNumber.isNotEmpty()) {
                fetchCarInfo(api, carNumber)
            } else {
                resultTextView.text = "Please enter a car number."
            }
        }
    }

    private fun fetchCarInfo(api: CarInfoApi, carNumber: String) {
        val call = api.getCarInfo(
            resourceId = "053cea08-09bc-40ec-8f7a-156f0677aff3",
            query = carNumber // Use the user input here
        )

        call.enqueue(object : Callback<CarInfoResponse> {
            override fun onResponse(
                call: Call<CarInfoResponse>,
                response: Response<CarInfoResponse>
            ) {
                if (response.isSuccessful) {
                    val carInfo = response.body()
                    Log.d("Full API Response", carInfo.toString())

                    if (carInfo != null && carInfo.success) {
                        displayCarInfo(carInfo)
                    } else {
                        resultTextView.text = "No records found."
                    }
                } else {
                    resultTextView.text = "Failed to fetch data. Status code: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<CarInfoResponse>, t: Throwable) {
                resultTextView.text = "Error: ${t.message}"
            }
        })
    }

    private fun displayCarInfo(carInfoResponse: CarInfoResponse) {
        val carInfoText =
            carInfoResponse.result.records.joinToString(separator = "\n\n-------------------------\n\n") { record ->
                """
            מספר רכב: ${record.mispar_rechev ?: "לא ידוע"}
            שם דגם: ${record.degem_nm ?: "לא ידוע"}
            יצרן: ${record.tozeret_nm ?: "לא ידוע"}
            שנת ייצור: ${record.shnat_yitzur ?: "לא ידוע"}
            סוג דלק: ${record.sug_delek_nm ?: "לא ידוע"}
            צבע רכב: ${record.tzeva_rechev ?: "לא ידוע"}
            דגם מנוע: ${record.degem_manoa ?: "לא ידוע"}
            תאריך בדיקה אחרונה: ${record.mivchan_acharon_dt ?: "לא ידוע"}
            תאריך תפוגה: ${record.tokef_dt ?: "לא ידוע"}
            בעלות: ${record.baalut ?: "לא ידוע"}
            מספר שלדה: ${record.misgeret ?: "לא ידוע"}
            צמיג קדמי: ${record.zmig_kidmi ?: "לא ידוע"}
            צמיג אחורי: ${record.zmig_ahori ?: "לא ידוע"}
            קבוצת זיהום: ${record.kvutzat_zihum ?: "לא ידוע"}
            דירוג בטיחות: ${record.ramat_eivzur_betihuty ?: "לא ידוע"}
            שם שימוש: ${record.kinuy_mishari ?: "לא ידוע"}
            דירוג: ${record.rank ?: "לא ידוע"}
            """.trimIndent()
            }
        resultTextView.text = carInfoText
    }


}
