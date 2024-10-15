package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Activities

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.saadapps.emicalculator.loan.finance.tool.R
import com.saadapps.emicalculator.loan.finance.tool.databinding.ActivityNetWorthBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class NetWorthActivity : AppCompatActivity() {
    private val binding:ActivityNetWorthBinding by lazy {
        ActivityNetWorthBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupKeyboardListener(binding.root)

        binding.resetBtn.setOnClickListener {
            binding.cashEdit.text.clear()
            binding.savingAccountsEdit.text.clear()
            binding.checkingAccountsEdit.text.clear()
            binding.lifeInsuranceEdit.text.clear()
            binding.otherEdit.text.clear()
            binding.realEstateEdit.text.clear()
            binding.termDepositsEdit.text.clear()
            binding.stocksEdit.text.clear()
            binding.bondsEdit.text.clear()
            binding.mortgagesEdit.text.clear()
            binding.businessInterestsEdit.text.clear()
            binding.otherCashEdit.text.clear()
            binding.irasEdit.text.clear()
            binding.keoghAccountsEdit.text.clear()
            binding.nmbrEdit.text.clear()
            binding.pensionsEdit.text.clear()
            binding.otherRetirementEdit.text.clear()
            binding.primaryResidenceEdit.text.clear()
            binding.vacationPropertiesEdit.text.clear()
            binding.carsEdit.text.clear()
            binding.boatsEdit.text.clear()
            binding.homeFurnishingsEdit.text.clear()
            binding.artEdit.text.clear()
            binding.jewelryEdit.text.clear()
            binding.collectiblesEdit.text.clear()
            binding.otherPersonalEdit.text.clear()
            binding.creditCardsEdit.text.clear()
            binding.shortTermLoansEdit.text.clear()
            binding.outstandingTaxesEdit.text.clear()
            binding.otherLiabilitiesEdit.text.clear()
            binding.mortgagesEdit.text.clear()
            binding.investmentLoansEdit.text.clear()
            binding.personalLoansEdit.text.clear()
            binding.studentLoansEdit.text.clear()
            binding.autoLoansEdit.text.clear()
            binding.resultLayout.visibility=View.GONE
            binding.assertChart.visibility=View.GONE
            binding.libilitiesChart.visibility=View.GONE
        }

        binding.calcuateBtn.setOnClickListener {
            calculateNetWorth()
        }
        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
        binding.assertLiquidLayout.visibility = View.VISIBLE
        binding.assertInvestmentLayout.visibility = View.GONE
        binding.assertRetirementLayout.visibility = View.GONE
        binding.assertPersonalLayout.visibility = View.GONE
        binding.shortTermLiabilitiesFieldsLayout.visibility = View.GONE
        binding.longTermLiabilitiesFieldsLayout.visibility = View.GONE

        binding.liquidAssertBtn.setOnClickListener {
            toggleLayout(binding.liquidAssertBtn, binding.assertLiquidLayout)
        }

        binding.investAssertBtn.setOnClickListener {
            toggleLayout(binding.investAssertBtn, binding.assertInvestmentLayout)
        }

        binding.retirementAssertBtn.setOnClickListener {
            toggleLayout(binding.retirementAssertBtn, binding.assertRetirementLayout)
        }

        binding.personalAssertBtn.setOnClickListener {
            toggleLayout(binding.personalAssertBtn, binding.assertPersonalLayout)
        }

        binding.shortLabilitiesBtn.setOnClickListener {
            toggleLayout(binding.shortLabilitiesBtn, binding.shortTermLiabilitiesFieldsLayout)
        }

        binding.LongLabilitiesBtn.setOnClickListener {
            toggleLayout(binding.LongLabilitiesBtn, binding.longTermLiabilitiesFieldsLayout)
        }
    }
    private fun toggleLayout(button: AppCompatButton, layout: View) {
        if (layout.visibility == View.VISIBLE) {
            layout.visibility = View.GONE
            resetButtonState(button)
        } else {
            layout.visibility = View.VISIBLE
            highlightButton(button)
        }
    }
    private fun resetButtonState(button: AppCompatButton) {
        button.setBackgroundResource(R.drawable.back_solid)
        button.setTextColor(Color.BLACK)

        when (button.id) {
            R.id.liquid_AssertBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liquidblackicon, 0, R.drawable.addicon, 0)
            }
            R.id.invest_AssertBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.investmentdisable, 0, R.drawable.addicon, 0)
            }
            R.id.retirement_AssertBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.retirementblackicon, 0, R.drawable.addicon, 0)
            }
            R.id.personal_AssertBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liquidblackicon, 0, R.drawable.addicon, 0)
            }
            R.id.short_LabilitiesBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shortlibblack, 0, R.drawable.addicon, 0)
            }
            R.id.Long_LabilitiesBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shortlibblack, 0, R.drawable.addicon, 0)
            }
        }
    }

    private fun highlightButton(button: AppCompatButton) {
        button.setBackgroundResource(R.drawable.btn_blue_back)
        button.setTextColor(Color.WHITE)

        when (button.id) {
            R.id.liquid_AssertBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liquidwhiteicon, 0, R.drawable.minusicon, 0)
            }
            R.id.invest_AssertBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.investwhiteicon, 0, R.drawable.minusicon, 0)
            }
            R.id.retirement_AssertBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.retirementwhiteicon, 0, R.drawable.minusicon, 0)
            }
            R.id.personal_AssertBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liquidwhiteicon, 0, R.drawable.minusicon, 0)
            }
            R.id.short_LabilitiesBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shortlibwhiteicon, 0, R.drawable.minusicon, 0)
            }
            R.id.Long_LabilitiesBtn -> {
                button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shortlibwhiteicon, 0, R.drawable.minusicon, 0)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateLiquidAssets(): Double {
        val cash = binding.cashEdit.text.toString().toDoubleOrNull() ?: 0.0
        val savings = binding.savingAccountsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val checking = binding.checkingAccountsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val lifeInsurance = binding.lifeInsuranceEdit.text.toString().toDoubleOrNull() ?: 0.0
        val other = binding.otherCashEdit.text.toString().toDoubleOrNull() ?: 0.0

        val totalAssets = cash + savings + checking + lifeInsurance + other
        return totalAssets
    }

    @SuppressLint("SetTextI18n")
    private fun calculateInvestmentAssets(): Double {
        val realEstate = binding.realEstateEdit.text.toString().toDoubleOrNull() ?: 0.0
        val termDeposits = binding.termDepositsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val stocks = binding.stocksEdit.text.toString().toDoubleOrNull() ?: 0.0
        val bonds = binding.bondsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val mutualFunds = binding.mutualFundsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val businessInterests = binding.businessInterestsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val other = binding.otherEdit.text.toString().toDoubleOrNull() ?: 0.0

        val totalInvestmentAssets = realEstate + termDeposits + stocks + bonds + mutualFunds + businessInterests + other
        return totalInvestmentAssets
    }
    @SuppressLint("SetTextI18n")
    private fun calculateRetirementAssets(): Double {
        val iras = binding.irasEdit.text.toString().toDoubleOrNull() ?: 0.0
        val nmbr = binding.nmbrEdit.text.toString().toDoubleOrNull() ?: 0.0
        val keoghAccounts = binding.keoghAccountsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val pensions = binding.pensionsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val other = binding.otherRetirementEdit.text.toString().toDoubleOrNull() ?: 0.0

        val totalRetirementAssets = iras + nmbr + keoghAccounts + pensions + other
        return totalRetirementAssets
    }

    @SuppressLint("SetTextI18n")
    private fun calculatePersonalAssets(): Double {
        val primaryResidence = binding.primaryResidenceEdit.text.toString().toDoubleOrNull() ?: 0.0
        val vacationProperties = binding.vacationPropertiesEdit.text.toString().toDoubleOrNull() ?: 0.0
        val cars = binding.carsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val boats = binding.boatsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val homeFurnishings = binding.homeFurnishingsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val art = binding.artEdit.text.toString().toDoubleOrNull() ?: 0.0
        val jewelry = binding.jewelryEdit.text.toString().toDoubleOrNull() ?: 0.0
        val collectibles = binding.collectiblesEdit.text.toString().toDoubleOrNull() ?: 0.0
        val other = binding.otherPersonalEdit.text.toString().toDoubleOrNull() ?: 0.0

        val totalPersonalAssets = primaryResidence + vacationProperties + cars + boats + homeFurnishings + art + jewelry + collectibles + other
        return totalPersonalAssets
    }

    @SuppressLint("SetTextI18n")
    private fun calculateShortTermLiabilities(): Double {
        val creditCards = binding.creditCardsEdit.text.toString().toDoubleOrNull() ?: 0.0
        val shortTermLoans = binding.shortTermLoansEdit.text.toString().toDoubleOrNull() ?: 0.0
        val outstandingTaxes = binding.outstandingTaxesEdit.text.toString().toDoubleOrNull() ?: 0.0
        val otherLiabilities = binding.otherLiabilitiesEdit.text.toString().toDoubleOrNull() ?: 0.0

        val totalShortTermLiabilities = creditCards + shortTermLoans + outstandingTaxes + otherLiabilities
        return totalShortTermLiabilities
    }

    @SuppressLint("SetTextI18n")
    private fun calculateLongTermLiabilities(): Double {
        val mortgages = binding.mortgagesEdit.text.toString().toDoubleOrNull() ?: 0.0
        val investmentLoans = binding.investmentLoansEdit.text.toString().toDoubleOrNull() ?: 0.0
        val personalLoans = binding.personalLoansEdit.text.toString().toDoubleOrNull() ?: 0.0
        val studentLoans = binding.studentLoansEdit.text.toString().toDoubleOrNull() ?: 0.0
        val autoLoans = binding.autoLoansEdit.text.toString().toDoubleOrNull() ?: 0.0

        val totalLongTermLiabilities = mortgages + investmentLoans + personalLoans + studentLoans + autoLoans
        return totalLongTermLiabilities
    }
@SuppressLint("SetTextI18n")
private fun calculateNetWorth() {
    binding.resultLayout.visibility = View.VISIBLE
    binding.assertChart.visibility=View.VISIBLE
    binding.libilitiesChart.visibility=View.VISIBLE

    val totalAssets = calculateLiquidAssets() + calculateInvestmentAssets() + calculateRetirementAssets() + calculatePersonalAssets()

    val totalLiabilities = calculateShortTermLiabilities() + calculateLongTermLiabilities()

    binding.totalAssertOutput.text = "$totalAssets"
    binding.totalLibitiesOutput.text = "$totalLiabilities"

    val netWorth = totalAssets - totalLiabilities
    binding.networthOutput.text="$netWorth"

    updatePieCharts(totalAssets, totalLiabilities)
}
    private fun updatePieCharts(totalAssets: Double, totalLiabilities: Double) {

        val assetEntries = ArrayList<PieEntry>()
        assetEntries.add(PieEntry(totalAssets.toFloat(), "Assets"))
        val assetDataSet = PieDataSet(assetEntries, "Assets")
        assetDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        val assetData = PieData(assetDataSet)
        binding.assertChart.data = assetData
        binding.assertChart.invalidate()

        val liabilityEntries = ArrayList<PieEntry>()
        liabilityEntries.add(PieEntry(totalLiabilities.toFloat(), "Liabilities"))
        val liabilityDataSet = PieDataSet(liabilityEntries, "Liabilities")
        liabilityDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        val liabilityData = PieData(liabilityDataSet)
        binding.libilitiesChart.data = liabilityData
        binding.libilitiesChart.invalidate()
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