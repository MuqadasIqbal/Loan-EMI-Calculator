package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Activities

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.saadapps.emicalculator.loan.finance.tool.R
import com.saadapps.emicalculator.loan.finance.tool.databinding.ActivityNpvcalculatorBinding

class NPVCalculatorActivity : AppCompatActivity() {
    private val binding:ActivityNpvcalculatorBinding by lazy {
        ActivityNpvcalculatorBinding.inflate(layoutInflater)
    }
    private val MAX_YEARS = 20
    private var yearLayouts = mutableListOf<View>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupKeyboardListener(binding.root)
        setupInitialYear()
        binding.addlayout.setOnClickListener {
            addYear()
        }
        binding.calculateBtn.setOnClickListener {
            calculateNPV()
        }
        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
        binding.resetBtn.setOnClickListener {
            binding.discountRateEdit.text.clear()
            binding.initialInvesEdit.text.clear()
        }
    }

    private fun setupInitialYear() {
        if (yearLayouts.isEmpty()) {
            addYear()
        }
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun addYear() {
        if (yearLayouts.size >= MAX_YEARS) {
            Toast.makeText(this, "Maximum of $MAX_YEARS years can be added.", Toast.LENGTH_SHORT).show()
            return
        }

        val inflater = LayoutInflater.from(this)
        val yearLayout = inflater.inflate(R.layout.add_year_layout, null)

        val yearNumber = yearLayouts.size + 1
        val yearText = yearLayout.findViewById<TextView>(R.id.yearNumberText)
        yearText.text = "Year $yearNumber"

        yearLayout.findViewById<ImageView>(R.id.cross_img).setOnClickListener {
            removeYear(yearLayout)
        }

        binding.yearsContainer.addView(yearLayout)
        yearLayouts.add(yearLayout)
    }

    @SuppressLint("SetTextI18n")
    private fun removeYear(layout: View) {
        binding.yearsContainer.removeView(layout)
        yearLayouts.remove(layout)

        for (i in yearLayouts.indices) {
            val yearText = yearLayouts[i].findViewById<TextView>(R.id.yearNumberText)
            yearText.text = "Year ${i + 1}"
        }
    }
    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun calculateNPV() {
        val initialInvestment = binding.initialInvesEdit.text.toString().toDoubleOrNull() ?: 0.0
        val discountRate = binding.discountRateEdit.text.toString().toDoubleOrNull() ?: 0.0

        if (initialInvestment == 0.0) {
            Toast.makeText(this, "Initial Investment cannot be zero.", Toast.LENGTH_SHORT).show()
            return
        }

        if (discountRate == 0.0) {
            Toast.makeText(this, "Discount Rate cannot be zero.", Toast.LENGTH_SHORT).show()
            return
        }

        var npv = -initialInvestment

        for (i in 0 until yearLayouts.size) {
            val yearLayout = yearLayouts[i]
            val yearEdit = yearLayout.findViewById<EditText>(R.id.yearEdit)
            val cashFlow = yearEdit.text.toString().toDoubleOrNull()

            if (cashFlow == null || cashFlow == 0.0) {
                val yearNumber = i + 1
                Toast.makeText(this, "Year $yearNumber value cannot be empty or zero.", Toast.LENGTH_SHORT).show()
                return
            }

            val discountedCashFlow = cashFlow / Math.pow(1 + discountRate / 100, (i + 1).toDouble())
            npv += discountedCashFlow
        }
        binding.resultOutput2.visibility = View.VISIBLE
        val formattedNpv = String.format("%.2f", npv)
        binding.resultOutput2.text = "NPV: $formattedNpv"

    }
    private fun setupKeyboardListener(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            view.getWindowVisibleDisplayFrame(r)
            // if more than 100 pixels, its probably a keyboard...
            if (Math.abs(view.rootView.height - (r.bottom - r.top)) > 100) {
                onKeyboardShow()
            }
        }
    }

    private fun onKeyboardShow() {
        binding.myScrollView.scrollToBottomWithoutFocusChange()
    }

    fun ScrollView.scrollToBottomWithoutFocusChange() { // Kotlin extension to scrollView
        val lastChild = getChildAt(childCount - 1)
        val bottom = lastChild.bottom + paddingBottom
        val delta = bottom - (scrollY + height)
        smoothScrollBy(0, delta)
    }
}
