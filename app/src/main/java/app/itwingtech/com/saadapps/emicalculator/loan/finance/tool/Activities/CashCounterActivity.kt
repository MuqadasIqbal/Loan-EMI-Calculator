package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Activities

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.saadapps.emicalculator.loan.finance.tool.R
import com.saadapps.emicalculator.loan.finance.tool.databinding.ActivityCashCounterBinding
import com.google.android.material.snackbar.Snackbar

class CashCounterActivity : AppCompatActivity() {
    private val binding:ActivityCashCounterBinding by lazy {
        ActivityCashCounterBinding.inflate(layoutInflater)
    }
    private var denominationCount = 1
    private val denominationValues = listOf(2000, 1000, 500, 200, 100, 50, 20, 10, 5, 2, 1)
    private val editTextList = mutableListOf<EditText>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupKeyboardListener(binding.root)
        binding.backArrow.setOnClickListener {
            onBackPressed()
        }

        binding.addnewNotesText.setOnClickListener {
            if (denominationCount < 9) {
                addNewDenominationEditText()
            } else {
                Snackbar.make(it, "You can only add up to 9 denominations", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.calcuateBtn.setOnClickListener {
            calculateTotalCash()
        }

        binding.resetBtn.setOnClickListener {
            resetFields()
        }

        editTextList.add(binding.note2000Edit)
    }

    private fun addNewDenominationEditText() {
        val newEditText = EditText(this)
        newEditText.hint = "Enter Number of ${denominationValues[denominationCount]} Coins"
        val heightInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            42f,
            resources.displayMetrics
        ).toInt()
        newEditText.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            heightInPx
        ).apply {
            setMargins(0, 16,0, 0)
        }
        newEditText.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        newEditText.textSize = 12f
        newEditText.background = getDrawable(R.drawable.edittext_background)
        newEditText.setPadding(10, 0, 0,0)

        binding.denominationsContainer.addView(newEditText)

        editTextList.add(newEditText)
        denominationCount++
    }
    private fun calculateTotalCash() {
        var totalAmount = 0
        for (i in 0 until editTextList.size) {
            val noteCount = editTextList[i].text.toString().toIntOrNull() ?: 0
            totalAmount += noteCount * denominationValues[i]
        }
        binding.totalAmountText.text = "Total Amount: ₹$totalAmount"
    }

    private fun resetFields() {
        for (editText in editTextList) {
            editText.text.clear()
        }
        binding.totalAmountText.text = "Total Amount: ₹0"

        binding.denominationsContainer.removeAllViews()
        editTextList.clear()
        editTextList.add(binding.note2000Edit)
        denominationCount = 1
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