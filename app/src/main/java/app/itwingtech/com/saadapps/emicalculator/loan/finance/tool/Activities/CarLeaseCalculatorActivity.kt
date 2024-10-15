package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Activities

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.saadapps.emicalculator.loan.finance.tool.R
import com.saadapps.emicalculator.loan.finance.tool.databinding.ActivityCarLeaseCalculatorBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class CarLeaseCalculatorActivity : AppCompatActivity() {
    private val binding: ActivityCarLeaseCalculatorBinding by lazy {
        ActivityCarLeaseCalculatorBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupKeyboardListener(binding.root)
        val calculatorType = intent.getStringExtra("calculator_type")

        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
        binding.resetBtn.setOnClickListener {
            binding.cardViewloanResults.visibility=View.GONE
            binding.cardViewResult.visibility=View.GONE
            binding.pieChart.visibility=View.GONE
            binding.taxRateEdit.text.clear()
            binding.valueEdit.text.clear()
            binding.termEdit.text.clear()
            binding.interestRateEdit.text.clear()
            binding.tradeEdit.text.clear()
            binding.PriceEdit.text.clear()
            binding.paymentEdit.text.clear()
            binding.tradeAmountEdit.text.clear()

        }
        val carLeaseTermsAdapter = ArrayAdapter.createFromResource(
            this, R.array.term_options, android.R.layout.simple_spinner_item
        )
        carLeaseTermsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val rentFrequencies = resources.getStringArray(R.array.rent_frequencies)
        val rentalYieldAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, rentFrequencies)
        rentalYieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        when (calculatorType) {
            "Car Lease Calculator" -> {
                binding.titleText.text = "Car Lease Calculator"
                binding.PriceText.text = "Vehical Price"
                binding.paymentText.text = "Down Payment"
                binding.tradeAmountText.text = "Trade in Amount"
                binding.tradeText.text = "Owed on Trade"
                binding.valueText.text = "Residual Value"
                binding.interestRateText.text = "Interest Rate"
                binding.taxRateText.text = "Sales Tax Rate %"
                binding.termText.text = "Terms"

                binding.spinner.adapter = carLeaseTermsAdapter
                calculateCarLease()
            }

            "Rental Calculator" -> {
                binding.titleText.text = "Rental Yield Calculator"
                binding.PriceText.text = "Property Price"
                binding.paymentText.text = "Vacancy Rate"
                binding.tradeAmountText.text = "Expenses (Yearly)"
                binding.tradeText.visibility = View.GONE
                binding.tradeEdit.visibility = View.GONE
                binding.valueText.visibility = View.GONE
                binding.valueEdit.visibility = View.GONE
                binding.interestRateText.visibility = View.GONE
                binding.interestRateEdit.visibility = View.GONE
                binding.taxRateText.visibility = View.GONE
                binding.taxRateEdit.visibility = View.GONE
                binding.termText.text = "Rent"

                binding.spinner.adapter = rentalYieldAdapter
                calculateRentYield()

            }
            "Loan Comparisons" -> {
                binding.titleText.text = "Loan Comparison Calculator"
                binding.loan1Text.visibility=View.VISIBLE
                binding.loan2Text.visibility=View.VISIBLE
                binding.PriceText.text = "Loan Amount"
                binding.paymentText.text = "Interest Rate"
                binding.tradeAmountText.text = "Term(Year)"
                binding.tradeText.text =  "Loan Amount"
                binding.valueText.text ="Interest Rate"
                binding.interestRateText.text = "Term(Year)"
                binding.taxRateText.visibility = View.GONE
                binding.taxRateEdit.visibility = View.GONE
                binding.termText.visibility=View.GONE
                binding.termEdit.visibility=View.GONE

                binding.spinner.visibility=View.GONE
                calculateLoanComparison()

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun calculateCarLease() {
        binding.calcuateBtn.setOnClickListener {
            binding.cardViewResult.visibility = View.VISIBLE
            binding.pieChart.visibility = View.VISIBLE

            try {

                val carPrice = binding.PriceEdit.text.toString().toDoubleOrNull() ?: 0.0
                val downPayment = binding.paymentEdit.text.toString().toDoubleOrNull() ?: 0.0
                val tradeInAmount = binding.tradeAmountEdit.text.toString().toDoubleOrNull() ?: 0.0
                val owedOnTrade = binding.tradeEdit.text.toString().toDoubleOrNull() ?: 0.0
                val residualValue = binding.valueEdit.text.toString().toDoubleOrNull() ?: 0.0
                val interestRate = binding.interestRateEdit.text.toString().toDoubleOrNull() ?: 0.0
                val salesTaxRate = binding.taxRateEdit.text.toString().toDoubleOrNull() ?: 0.0
                val term = binding.termEdit.text.toString().toIntOrNull() ?: 0
                val isTermInYears = binding.spinner.selectedItem.toString() == "Years"

                val leaseTermMonths = if (isTermInYears) term * 12 else term

                val capitalizedCost = carPrice - downPayment - tradeInAmount + owedOnTrade

                val moneyFactor = interestRate / 2400

                val monthlyPayment =
                    ((capitalizedCost - residualValue) / leaseTermMonths) +
                            (capitalizedCost + residualValue) * moneyFactor

                val totalPayments = monthlyPayment * leaseTermMonths

                val overpayment = totalPayments - capitalizedCost

                val totalWithTax = totalPayments * (1 + salesTaxRate / 100)

                val paymentType = if (isTermInYears) "Yearly Payment" else "Monthly Payment"
                binding.resultOutput1.text = "$paymentType: $%.2f".format(monthlyPayment)


                binding.resultOutput2.text = "Overpayment: $%.2f".format(overpayment)

                updatePieChart(capitalizedCost, overpayment, totalWithTax)

            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun updatePieChart(capitalizedCost: Double, overpayment: Double, totalWithTax: Double) {
        val entries = mutableListOf<PieEntry>()

        entries.add(PieEntry(capitalizedCost.toFloat(), "Car Price"))
        entries.add(PieEntry(overpayment.toFloat(), "Overpayment"))
        entries.add(PieEntry((totalWithTax - capitalizedCost).toFloat(), "Sales Tax"))

        val dataSet = PieDataSet(entries, "Lease Breakdown")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        val data = PieData(dataSet)

        binding.pieChart.data = data
        binding.pieChart.invalidate()
    }

    @SuppressLint("SetTextI18n")
    private fun calculateRentYield() {
        binding.calcuateBtn.setOnClickListener {

            binding.cardViewResult.visibility = View.VISIBLE


            val propertyPrice = binding.PriceEdit.text.toString().toDoubleOrNull() ?: 0.0
            val vacancyRate = binding.paymentEdit.text.toString().toDoubleOrNull() ?: 0.0
            val yearlyExpenses = binding.tradeAmountEdit.text.toString().toDoubleOrNull() ?: 0.0
            val rent = binding.termEdit.text.toString().toDoubleOrNull() ?: 0.0


            val rentFrequency = binding.spinner.selectedItem.toString()


            val annualRent = when (rentFrequency) {
                "Monthly" -> rent * 12
                "Weekly" -> rent * 52
                "Yearly" -> rent
                else -> rent
            }

            val grossYield = if (propertyPrice > 0) {
                (annualRent / propertyPrice) * 100
            } else {
                0.0
            }

            val netRent = annualRent - (vacancyRate * annualRent / 100) - yearlyExpenses

            val rentalYield = if (propertyPrice > 0) {
                (netRent / propertyPrice) * 100
            } else {
                0.0
            }

            binding.resultOutput1.text = "Net Yield: %.2f%%".format(rentalYield)
            binding.resultOutput2.text = "Gross Yield: %.2f%%".format(grossYield)
        }

    }
    @SuppressLint("DefaultLocale")
    private fun calculateLoanComparison() {

        binding.calcuateBtn.setOnClickListener {
            binding.cardViewloanResults.visibility=View.VISIBLE
            // Retrieve user inputs
            val loanAmount1 = binding.PriceEdit.text.toString().toDoubleOrNull() ?: 0.0
            val interestRate1 = binding.paymentEdit.text.toString().toDoubleOrNull() ?: 0.0
            val termYears1 = binding.tradeAmountEdit.text.toString().toIntOrNull() ?: 0

            val loanAmount2 = binding.tradeEdit.text.toString().toDoubleOrNull() ?: 0.0
            val interestRate2 = binding.valueEdit.text.toString().toDoubleOrNull() ?: 0.0
            val termYears2 = binding.interestRateEdit.text.toString().toIntOrNull() ?: 0

            // Calculate EMIs
            val emiLoan1 = calculateEMI(loanAmount1, interestRate1, termYears1)
            val emiLoan2 = calculateEMI(loanAmount2, interestRate2, termYears2)

            // Calculate Total Amount and Interest
            val totalAmountLoan1 = emiLoan1 * termYears1 * 12
            val totalInterestLoan1 = totalAmountLoan1 - loanAmount1

            val totalAmountLoan2 = emiLoan2 * termYears2 * 12
            val totalInterestLoan2 = totalAmountLoan2 - loanAmount2

            // Display results
            binding.textEMILoan1.text = String.format("%.2f", emiLoan1)
            binding.textEMILoan2.text = String.format("%.2f", emiLoan2)
            binding.textInterestLoan1.text = String.format("%.2f", totalInterestLoan1)
            binding.textInterestLoan2.text = String.format("%.2f", totalInterestLoan2)
            binding.textTotalAmountLoan1.text = String.format("%.2f", totalAmountLoan1)
            binding.textTotalAmountLoan2.text = String.format("%.2f", totalAmountLoan2)

            // Calculate Differences
            binding.textEMIDifference.text = String.format("%.2f", emiLoan1 - emiLoan2)
            binding.textInterestDifference.text =
                String.format("%.2f", totalInterestLoan1 - totalInterestLoan2)
            binding.textTotalAmountDifference.text =
                String.format("%.2f", totalAmountLoan1 - totalAmountLoan2)
        }
    }
        private fun calculateEMI(principal: Double, annualRate: Double, termYears: Int): Double {
            if (termYears == 0) return 0.0
            val monthlyRate = annualRate / 12 / 100
            val termMonths = termYears * 12
            return (principal * monthlyRate * Math.pow(1 + monthlyRate, termMonths.toDouble())) /
                    (Math.pow(1 + monthlyRate, termMonths.toDouble()) - 1)
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



