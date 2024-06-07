package mx.itson.edu.e1ZayasTadeo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var ETOgPRice: EditText
    lateinit var ETPercentaje: TextView
    lateinit var ETTotal: TextView
    var selectedPercentage: Double = 0.0
    var price: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ETOgPRice = findViewById(R.id.editTextOriginalPrice)
        ETPercentaje = findViewById(R.id.editTextPercentage)
        ETTotal = findViewById(R.id.editTextTotal)

        val percentageButtons= listOf(
            findViewById<Button>(R.id.button_10),
            findViewById<Button>(R.id.button_15),
            findViewById<Button>(R.id.button_20),
            findViewById<Button>(R.id.button_25),
            findViewById<Button>(R.id.button_30),
            findViewById<Button>(R.id.button_40),
        )

        percentageButtons.forEach{
            button -> button.setOnClickListener{
                val percentage = button.text.toString().removeSuffix("%").toDouble()
            selectedPercentage = percentage / 100
            updatePercentage()
            }
        }

        findViewById<Button>(R.id.button_tip).setOnClickListener{
            calculateTotal(isTip = true)
        }

        findViewById<Button>(R.id.button_discount).setOnClickListener{
            calculateTotal(isTip = false)
        }

        ETOgPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                price = s.toString().toDoubleOrNull() ?: 0.0
                updatePercentage()
            }
        })

    }

    fun updatePercentage(){
        val calculatedPercentage = price * selectedPercentage
        ETPercentaje.text = String.format(Locale.getDefault(), "%.2f", calculatedPercentage)
    }

    fun calculateTotal(isTip: Boolean){
        val calculatedPercentage = price * selectedPercentage
        val total = if (isTip) price + calculatedPercentage else price - calculatedPercentage
        ETTotal.text = String.format(Locale.getDefault(), "%.2f", total)
    }
}