package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.saadapps.emicalculator.loan.finance.tool.R
import com.saadapps.emicalculator.loan.finance.tool.databinding.ActivityCalculateBinding
import kotlin.math.pow
import kotlin.math.roundToInt

@Suppress("UNREACHABLE_CODE")
class CalculateActivity : AppCompatActivity() {
    private val binding: ActivityCalculateBinding by lazy {
        ActivityCalculateBinding.inflate(layoutInflater)
    }
    private val pensionAmounts = arrayOf("1000", "2000", "3000", "4000", "5000")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        //APY Spinner
        val pensionSpinner: Spinner = binding.spinnerApy
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pensionAmounts)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pensionSpinner.adapter = spinnerAdapter

        binding.resetBtn.setOnClickListener {
            binding.investEdit.text.clear()
            binding.interestRateEdit.text.clear()
            binding.termEdit.text.clear()
            binding.durationEdit.text.clear()
            binding.resultText1.text = ""
            binding.resultText2.text = ""
            binding.resultText3.text = ""
            binding.resultText4.text = ""
        }

        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
        val calculatorType = intent.getStringExtra("calculator_type")
        Log.d("CalculateActivity", "Received calculator_type: $calculatorType")

        when (calculatorType) {
            "emi" -> {
                binding.titleText.text = "EMI Calculator"
                binding.investText.text = "Principal"
                binding.investEdit.hint = "Enter Principal Amount"
                binding.interestRateText.text = "Interest Rate%"
                binding.interestRateEdit.hint = "Enter Interest Rate%"
                binding.termText.visibility = View.VISIBLE
                binding.termText.text = "Tenure (Years)"
                binding.termEdit.visibility = View.VISIBLE
                binding.termEdit.hint = "Enter Tenure"
                setupEMICalculator()
            }

            "gst" -> {
                binding.titleText.text = "GST Calculator"
                binding.investText.text = "Net Price"
                binding.investEdit.hint = "Enter Net Price"
                binding.interestRateText.text = "GST%"
                binding.interestRateEdit.hint = "Enter GST%"
                setupGSTCalculator()
            }

            "vat" -> {
                binding.titleText.text = "VAT Calculator"
                binding.investText.text = "Net Price"
                binding.investEdit.hint = "Enter Net Price"
                binding.interestRateText.text = "VAT Rate%"
                binding.interestRateEdit.hint = "Enter Vat Rate%"
                setupVATCalculator()
            }

            "sales" -> {
                binding.titleText.text = "Sales Calculator"
                binding.investText.text = "Net Price"
                binding.investEdit.hint = "Enter Net Price"
                binding.interestRateText.text = "Sales Tax"
                binding.interestRateEdit.hint = "Enter Sales Tax"
                setupSalesCalculator()
            }

            "sip" -> {
                binding.titleText.text = "SIP Calculator"
                binding.investText.text = "Monthly Investment"
                binding.investEdit.hint = "Enter Monthly Investment"
                binding.interestRateText.text = "Annual Interest Rate (%)"
                binding.interestRateEdit.hint = "Enter Annual Interest Rate (%)"
                binding.termText.visibility = View.VISIBLE
                binding.termText.text = "Term(Year)"
                binding.termEdit.visibility = View.VISIBLE
                binding.termEdit.hint = "Enter Term(Year)"
                setupSIPCalculator()

            }
            "ppf" -> {
                binding.titleText.text = "PPF Calculator"
                binding.investText.text = "Monthly Investment"
                binding.investEdit.hint = "Enter Monthly Investment"
                binding.interestRateText.text = "Annual Interest Rate (%)"
                binding.interestRateEdit.hint = "Enter Annual Interest Rate (%)"
                binding.termText.visibility = View.VISIBLE
                binding.termText.text = "Term(Year)"
                binding.termEdit.visibility = View.VISIBLE
                binding.termEdit.hint = "Enter Term(Year)"
                setupPPFCalculator()

            }

            "lumpsum" -> {
                binding.titleText.text = "Lumpsum Calculator"
                binding.investText.text = "Total Investment"
                binding.investEdit.hint = "Enter Total Investment"
                binding.interestRateText.text = "Expected Resturn Rate%"
                binding.interestRateEdit.hint = "Enter Expected Return Rate%"
                binding.termText.visibility = View.VISIBLE
                binding.termText.text = "Term(Year)"
                binding.termEdit.visibility = View.VISIBLE
                binding.termEdit.hint = "Enter Term(Year)"
                setupLumpsumCalculator()

            }
            "swp" -> {
                binding.titleText.text = "SWP Calculator"
                binding.investText.text = "Initial Investment"
                binding.investEdit.hint = "Enter Initial Investment Amount"
                binding.interestRateText.text = "Withdrawal Amount"
                binding.interestRateEdit.hint = "Enter Monthly Withdrawal Amount"
                binding.termText.visibility = View.VISIBLE
                binding.termText.text = "Expected Annual Return (%)"
                binding.termEdit.visibility = View.VISIBLE
                binding.termEdit.hint = "Enter Rate of Return"
                binding.durationText.visibility = View.VISIBLE
                binding.durationText.text = "Duration (Years)"
                binding.durationEdit.visibility = View.VISIBLE
                binding.durationEdit.hint = "Enter Duration in Years"
                setupSWPCalculator()
            }

            "value" -> {
                binding.titleText.text = "Value Added Tex Calculator"
                binding.investText.text = "Net Price"
                binding.investEdit.hint = "Enter Net Price"
                binding.interestRateText.text = "Tax Rate%"
                binding.interestRateEdit.hint = "Enter Tex Rate"
                setupVATCalculator()
            }

            "margin" -> {
                binding.titleText.text = "Margin Calculator"
                binding.investText.text = "Cost Price"
                binding.investEdit.hint = "Enter Cost Price"
                binding.interestRateText.text = "Revenue Amount"
                binding.interestRateEdit.hint = "Enter Revenue Amount"
                setupMarginCalculator()
            }

            "FD Calculator" -> {
                binding.titleText.text = "FD Calculator"
                binding.investText.text = "Total Investment"
                binding.investEdit.hint = "Enter Total Investment"
                binding.interestRateText.text = "Interest Rate%"
                binding.interestRateEdit.hint = "Enter Interest Rate%"
                binding.termText.visibility = View.VISIBLE
                binding.termText.text = "Term(Year)"
                binding.termEdit.visibility = View.VISIBLE
                binding.termEdit.hint = "Enter Term(Year)"
                setupFDCalculator()
            }

            "RD Calculator" -> {
                binding.titleText.text = "RD Calculator"
                binding.investText.text = "Monthly Investment"
                binding.investEdit.hint = "Enter Monthly Investment"
                binding.interestRateText.text = "Interest Rate%"
                binding.interestRateEdit.hint = "Enter Interest Rate%"
                binding.termText.visibility = View.VISIBLE
                binding.termText.text = "Term(Year)"
                binding.termEdit.visibility = View.VISIBLE
                binding.termEdit.hint = "Enter Term(Year)"
                setupRDCalculator()
            }

            "SCSS Calculator" -> {
                binding.titleText.text = "SCSS Calculator"
                binding.investText.text = "Principle Amount"
                binding.investEdit.hint = "Enter Principle Amount"
                binding.interestRateText.text = "Interest Rate%"
                binding.interestRateEdit.hint = "Enter Interest Rate%"
                binding.termText.visibility = View.VISIBLE
                binding.termText.text = "Term(Year)"
                binding.termEdit.visibility = View.VISIBLE
                binding.termEdit.hint = "Enter Term(Year)"
                setupSCSSCalculator()
            }

            "tips" -> {
                binding.titleText.text = "Tips Calculator"
                binding.investText.text = "Total Bill"
                binding.investEdit.hint = "Enter Total Bill"
                binding.interestRateText.text = "Tip%"
                binding.interestRateEdit.hint = "Enter Tip%"
                binding.termText.visibility = View.VISIBLE
                binding.termText.text = "Splite Person"
                binding.termEdit.visibility = View.VISIBLE
                binding.termEdit.hint = "Enter Splite Person"
                setupTipsCalculator()
            }

            "APY Calculator" -> {
                binding.titleText.text = "APY Calculator"
                binding.investText.text = "Desired Monthly Pension"
                binding.investEdit.visibility = View.GONE
                binding.spinnerApy.visibility = View.VISIBLE
                binding.interestRateText.text = "Your Join Age(18-39)"
                binding.interestRateEdit.hint = "Your Join Age(18-39)"
                setupAPYCalculator()
            }

            "return" -> {
                binding.titleText.text = "Return Of Investment Calculator"
                binding.investText.text = "Invest Amount"
                binding.investEdit.hint = "Enter Invest Amount"
                binding.interestRateText.text = "Amount Returned"
                binding.interestRateEdit.hint = "Enter Amount Returned"
                binding.termText.visibility = View.VISIBLE
                binding.termText.text = "Tenure"
                binding.termEdit.visibility = View.VISIBLE
                binding.termEdit.hint = "Enter Tenure"
                setupReturnInvestmentCalculator()
            }

            "discount" -> {
                binding.titleText.text = "Discount Calculator"
                binding.investText.text = "Initial Value"
                binding.investEdit.hint = "Enter Initial Value"
                binding.interestRateText.text = "Discount%"
                binding.interestRateEdit.hint = "Enter Discount%"
                setupDiscountCalculator()
            }

            "price" -> {
                binding.titleText.text = "Price Calculator"
                binding.investText.text = "Cost Price"
                binding.investEdit.hint = "Enter Cost Price"
                binding.interestRateText.text = "Gross Margin%"
                binding.interestRateEdit.hint = "Enter Gross Margin%"
                setupPriceCalculator()
            }

            "gross" -> {
                binding.titleText.text = "Gross Profit Calculator"
                binding.investText.text = "Cost Price"
                binding.investEdit.hint = "Enter Cost Price"
                binding.interestRateText.text = "Revenue Amount"
                binding.interestRateEdit.hint = "Enter Revenue Amount"
                setupGrossPriceCalculator()
            }

            "net price" -> {
                binding.titleText.text = "Net Price Calculator"
                binding.investText.text = "Price"
                binding.investEdit.hint = "Enter Price"
                binding.interestRateText.text = "Tax%"
                binding.interestRateEdit.hint = "Enter Tax%"
                setupNetPriceCalculator()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupEMICalculator() {
        binding.calcuateBtn.setOnClickListener {

            val principal = binding.investEdit.text.toString().toDoubleOrNull()
            val interestRate = binding.interestRateEdit.text.toString().toDoubleOrNull()
            val tenure = binding.termEdit.text.toString().toIntOrNull()

            if (principal != null && interestRate != null && tenure != null) {
                val rate = interestRate / (12 * 100)
                val n = tenure * 12

                val emi = (principal * rate * Math.pow(1 + rate, n.toDouble())) /
                        (Math.pow(1 + rate, n.toDouble()) - 1)

                val totalPayment = emi * n
                val totalInterest = totalPayment - principal

                binding.resultText1.text = "Total Interest: $totalInterest"
                binding.resultText2.text = "Total Principal + Interest: $totalPayment"
                binding.resultText3.text = "EMI Monthly Payment: $emi"
            } else {
                Toast.makeText(this, "Please enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupGSTCalculator() {
        binding.calcuateBtn.setOnClickListener {

            val netPriceStr = binding.investEdit.text.toString()
            val gstPercentageStr = binding.interestRateEdit.text.toString()

            if (netPriceStr.isEmpty() || gstPercentageStr.isEmpty()) {
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val netPrice = netPriceStr.toDouble()
            val gstPercentage = gstPercentageStr.toDouble()

            val gstAmount = calculateGSTAmount(netPrice, gstPercentage)
            val grossPrice = netPrice + gstAmount


            binding.resultText1.text = "Net Price:  %.2f".format(netPrice)
            binding.resultText2.text = "GST Percentage:  %.2f%%".format(gstPercentage)
            binding.resultText3.text = "GST Amount:  %.2f".format(gstAmount)
            binding.resultText4.text = "Gross Price:  %.2f".format(grossPrice)
        }
    }

    private fun calculateGSTAmount(netPrice: Double, gstPercentage: Double): Double {
        return (netPrice * gstPercentage) / 100
    }

    @SuppressLint("SetTextI18n")
    private fun setupVATCalculator() {
        binding.calcuateBtn.setOnClickListener {
            val netPrice = binding.investEdit.text.toString().toDoubleOrNull() ?: 0.0
            val vatRatePercent = binding.interestRateEdit.text.toString().toDoubleOrNull() ?: 0.0

            if (netPrice > 0 && vatRatePercent > 0) {
                val taxAmount = (netPrice * vatRatePercent) / 100
                val grossPrice = netPrice + taxAmount

                binding.resultText1.text = "Tax Amount: $taxAmount"
                binding.resultText2.text = "Gross Price: $grossPrice"
            } else {
                Toast.makeText(this, "Please enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupSalesCalculator() {
        binding.calcuateBtn.setOnClickListener {
            val originalPrice = binding.investEdit.text.toString().toDoubleOrNull() ?: 0.0
            val discountRate = binding.interestRateEdit.text.toString().toDoubleOrNull() ?: 0.0
            binding.resultText4.visibility = View.GONE

            if (originalPrice > 0 && discountRate > 0) {
                val discountAmount = (originalPrice * discountRate) / 100
                val finalSalesPrice = originalPrice - discountAmount

                binding.resultText1.text = "Net Price: %.2f".format(originalPrice)
                binding.resultText2.text = "Discount Amount: $discountAmount"
                binding.resultText3.text = "Final Sales Price: $finalSalesPrice"
            } else {
                Toast.makeText(this, "Please enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupSIPCalculator() {
        binding.calcuateBtn.setOnClickListener {
            val monthlyInvestment = binding.investEdit.text.toString().toDoubleOrNull()
            val annualInterestRate = binding.interestRateEdit.text.toString().toDoubleOrNull()
            val tenure = binding.termEdit.text.toString().toIntOrNull()

            if (monthlyInvestment != null && annualInterestRate != null && tenure != null) {
                val monthlyRate = annualInterestRate / (12 * 100)
                val months = tenure * 12

                val futureValue = (monthlyInvestment * ((Math.pow(
                    1 + monthlyRate,
                    months.toDouble()
                ) - 1) / monthlyRate) * (1 + monthlyRate))

                binding.resultText1.text = "Monthly Investment: %.2f".format(monthlyInvestment)
                binding.resultText2.text =
                    "Future Value (Maturity Amount): %.2f".format(futureValue)
                binding.resultText3.visibility = View.GONE
            } else {
                Toast.makeText(this, "Please enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupPPFCalculator() {
        binding.calcuateBtn.setOnClickListener {

            val annualInvestment = binding.investEdit.text.toString().toDoubleOrNull()
            val interestRate = binding.interestRateEdit.text.toString().toDoubleOrNull()
            val tenure = binding.termEdit.text.toString().toIntOrNull()

            if (annualInvestment != null && interestRate != null && tenure != null) {
                val rate = interestRate / 100
                var maturityAmount = 0.0

                for (year in 1..tenure) {
                    maturityAmount = (maturityAmount + annualInvestment) * (1 + rate)
                }

                binding.resultText1.text =
                    "Total Investment: %.2f".format(annualInvestment * tenure)
                binding.resultText2.text = "Maturity Amount: %.2f".format(maturityAmount)
                binding.resultText3.visibility = View.GONE
            } else {
                Toast.makeText(this, "Please enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupLumpsumCalculator() {
        binding.calcuateBtn.setOnClickListener {


            val principal = binding.investEdit.text.toString().toDoubleOrNull()
            val rateOfReturn = binding.interestRateEdit.text.toString().toDoubleOrNull()
            val tenure = binding.termEdit.text.toString().toIntOrNull()

            if (principal != null && rateOfReturn != null && tenure != null) {

                val maturityAmount =
                    principal * Math.pow(1 + (rateOfReturn / 100), tenure.toDouble())


                binding.resultText1.text = "Total Investment: %.2f".format(principal)
                binding.resultText2.text = "Maturity Amount: %.2f".format(maturityAmount)
                binding.resultText3.visibility = View.GONE
            } else {

                Toast.makeText(this, "Please enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupSWPCalculator() {
        binding.calcuateBtn.setOnClickListener {

            val initialInvestment = binding.investEdit.text.toString().toDoubleOrNull()
            val withdrawalAmount = binding.interestRateEdit.text.toString().toDoubleOrNull()
            val annualReturnRate = binding.termEdit.text.toString().toDoubleOrNull()
            val durationYears = binding.durationEdit.text.toString().toIntOrNull()


            if (initialInvestment != null && withdrawalAmount != null && annualReturnRate != null && durationYears != null) {

                val monthlyRate = annualReturnRate / 12 / 100

                val totalMonths = durationYears * 12

                val futureValue =
                    initialInvestment * Math.pow(1 + monthlyRate, totalMonths.toDouble()) -
                            withdrawalAmount * ((Math.pow(
                        1 + monthlyRate,
                        totalMonths.toDouble()
                    ) - 1) / monthlyRate)


                val totalWithdrawals = withdrawalAmount * totalMonths

                val remainingBalance = futureValue.coerceAtLeast(0.0)


                binding.resultText1.text = "Total Withdrawals: %.2f".format(totalWithdrawals)
                binding.resultText2.text = "Remaining Balance: %.2f".format(remainingBalance)
            } else {
                Toast.makeText(this, "Please enter valid inputs", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupMarginCalculator() {
        binding.calcuateBtn.setOnClickListener {

            val costPriceStr = binding.investEdit.text.toString()
            val sellingPriceStr = binding.interestRateEdit.text.toString()

            if (costPriceStr.isEmpty() || sellingPriceStr.isEmpty()) {
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val costPrice = costPriceStr.toDoubleOrNull() ?: 0.0
            val sellingPrice = sellingPriceStr.toDoubleOrNull() ?: 0.0

            if (costPrice <= 0 || sellingPrice <= 0) {
                Toast.makeText(this, "Prices must be greater than zero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val profit = sellingPrice - costPrice

            val marginPercentage = (profit / sellingPrice) * 100

            val markupPercentage = (profit / costPrice) * 100

            binding.resultText1.text = "Profit: %.2f".format(profit)
            binding.resultText2.text = "Margin: %.2f%%".format(marginPercentage)
            binding.resultText3.text = "Markup: %.2f%%".format(markupPercentage)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupFDCalculator() {
        binding.calcuateBtn.setOnClickListener {

            val principal = binding.investEdit.text.toString().toDoubleOrNull()
            val interestRate = binding.interestRateEdit.text.toString().toDoubleOrNull()
            val tenure = binding.termEdit.text.toString().toIntOrNull()

            val compoundingFreq = 4


            if (principal != null && interestRate != null && tenure != null) {
                val rate = interestRate / 100
                val maturityAmount = principal * Math.pow(
                    1 + (rate / compoundingFreq),
                    (compoundingFreq * tenure).toDouble()
                )

                val interestEarned = maturityAmount - principal

                binding.resultText1.text = "Maturity Amount: %.2f".format(maturityAmount)
                binding.resultText2.text = "Interest Earned: %.2f".format(interestEarned)
            } else {
                Toast.makeText(this, "Please enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupRDCalculator() {
        binding.calcuateBtn.setOnClickListener {

            val monthlyDeposit = binding.investEdit.text.toString().toDoubleOrNull()
            val interestRate = binding.interestRateEdit.text.toString().toDoubleOrNull()
            val tenureYears = binding.termEdit.text.toString().toIntOrNull()


            if (monthlyDeposit != null && interestRate != null && tenureYears != null) {
                val tenureMonths = tenureYears * 12
                val rate = interestRate / 100
                val compoundingFreq = 4

                var maturityAmount = 0.0

                for (i in 1..tenureMonths) {

                    maturityAmount += monthlyDeposit * Math.pow(
                        1 + (rate / compoundingFreq),
                        (compoundingFreq * (tenureMonths - i + 1)) / 12.0
                    )
                }

                val totalInvestment = monthlyDeposit * tenureMonths
                val interestEarned = maturityAmount - totalInvestment

                binding.resultText1.text = "Maturity Amount: %.2f".format(maturityAmount)
                binding.resultText2.text = "Total Investment: %.2f".format(totalInvestment)
                binding.resultText3.text = "Interest Earned: %.2f".format(interestEarned)
            } else {
                Toast.makeText(this, "Please enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupSCSSCalculator() {
        binding.calcuateBtn.setOnClickListener {

            val principal = binding.investEdit.text.toString().toDoubleOrNull()
            val interestRate = binding.interestRateEdit.text.toString().toDoubleOrNull()
            val tenure = binding.termEdit.text.toString().toIntOrNull()

            if (principal != null && interestRate != null && tenure != null) {
                val rate = interestRate / 100
                val quarterlyInterest = (principal * rate) / 4
                val totalInterest = quarterlyInterest * (tenure * 4)
                val maturityAmount = principal + totalInterest

                binding.resultText1.text = "Total Interest: %.2f".format(totalInterest)
                binding.resultText2.text = "Maturity Amount: %.2f".format(maturityAmount)
            } else {
                Toast.makeText(this, "Please enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupTipsCalculator() {
        binding.calcuateBtn.setOnClickListener {
            val billAmount = binding.investEdit.text.toString().toDoubleOrNull()
            val tipPercentage = binding.interestRateEdit.text.toString().toDoubleOrNull()
            val numberOfPeople = binding.termEdit.text.toString().toIntOrNull()

            if (billAmount != null && tipPercentage != null && numberOfPeople != null) {
                if (numberOfPeople >= 2 && numberOfPeople <= 500) {
                    val tipAmount = billAmount / tipPercentage
                    val totalbill = billAmount / numberOfPeople
                    val amountPerPerson = tipPercentage / numberOfPeople

                    binding.resultText1.text = "Total bills: %.2f".format(totalbill)
                    binding.resultText2.text = "Tip Amount: %.2f".format(tipAmount)
                    binding.resultText3.text =
                        "Total Amount Per Person: %2f".format(amountPerPerson)
                } else {
                    Toast.makeText(
                        this,
                        "Please enter a valid number of people (between 2 and 500)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Please enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupReturnInvestmentCalculator() {
        binding.calcuateBtn.setOnClickListener {
            val investmentStr = binding.investEdit.text.toString()
            val amountReturnedStr = binding.interestRateEdit.text.toString()
            val tenureStr = binding.termEdit.text.toString()


            if (investmentStr.isNotEmpty() && amountReturnedStr.isNotEmpty() && tenureStr.isNotEmpty()) {

                val investmentAmount = investmentStr.toDouble()
                val amountReturned = amountReturnedStr.toDouble()
                val tenure = tenureStr.toDouble()

                val investmentGain = amountReturned - investmentAmount
                val roi = (investmentGain / investmentAmount) * 100
                val simpleROI = roi / tenure
                val annualizedROI = ((amountReturned / investmentAmount).pow(1 / tenure) - 1) * 100

                binding.resultText1.text = "Investment Gain: $investmentGain"
                binding.resultText2.text = "ROI: ${roi.roundToInt()}%"
                binding.resultText3.text = "Simple ROI: ${simpleROI.roundToInt()}%"
                binding.resultText4.text = "Annualized ROI: ${annualizedROI.roundToInt()}%"
            } else {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun setupDiscountCalculator(){
        binding.calcuateBtn.setOnClickListener {
            val initialPriceText = binding.investEdit.text.toString()
            val discountText = binding.interestRateEdit.text.toString()

            if (initialPriceText.isNotEmpty() && discountText.isNotEmpty()) {
                val initialPrice = initialPriceText.toDouble()
                val discountPercentage = discountText.toDouble()

                val discountAmount = initialPrice * (discountPercentage / 100)
                val priceAfterDiscount = initialPrice - discountAmount

                binding.resultText1.text = "Initial Price: %.2f".format(initialPrice)
                binding.resultText2.text = "Discount Amount: %.2f".format (discountAmount)
                binding.resultText3.text = "Price After Discount: %.2f".format(priceAfterDiscount)
            }else{
                Toast.makeText(this, "Please enter both values", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupPriceCalculator() {
        binding.calcuateBtn.setOnClickListener {

            val costPriceText = binding.investEdit.text.toString()
            val grossMarginText = binding.interestRateEdit.text.toString()

            if (costPriceText.isNotEmpty() && grossMarginText.isNotEmpty()) {

                val costPrice = costPriceText.toDouble()
                val grossMargin = grossMarginText.toDouble()

                val revenue = costPrice / (1 - grossMargin / 100)
                val grossProfit = revenue - costPrice
                val markupPercentage = (grossProfit / costPrice) * 100

                binding.resultText1.text = "Revenue: %.2f".format(revenue)
                binding.resultText2.text = "Gross Profit: %.2f".format(grossProfit)
                binding.resultText3.text = "Markup Percentage: %.2f%%".format(markupPercentage)
            } else {
                Toast.makeText(this, "Please enter All values", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupGrossPriceCalculator() {
        binding.calcuateBtn.setOnClickListener {

            val costPriceText = binding.investEdit.text.toString()
            val revenueAmountText = binding.interestRateEdit.text.toString()

            if (costPriceText.isNotEmpty() && revenueAmountText.isNotEmpty()) {

                val costPrice = costPriceText.toDouble()
                val revenueAmount = revenueAmountText.toDouble()

                val grossProfit = revenueAmount - costPrice
                val grossMargin = (grossProfit / revenueAmount) * 100

                binding.resultText1.text = "Cost Price: $%.2f".format(costPrice)
                binding.resultText2.text = "Revenue Amount: $%.2f".format(revenueAmount)
                binding.resultText3.text = "Gross Profit: $%.2f".format(grossProfit)
                binding.resultText4.text = "Gross Margin: %.2f%%".format(grossMargin)
            } else {

                Toast.makeText(
                    this,
                    "Please enter both Cost Price and Revenue Amount",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupNetPriceCalculator() {
        binding.calcuateBtn.setOnClickListener {

            val priceText = binding.investEdit.text.toString()
            val taxRateText = binding.interestRateEdit.text.toString()

            if (priceText.isNotEmpty() && taxRateText.isNotEmpty()) {

                val price = priceText.toDouble()
                val taxRate = taxRateText.toDouble()

                val taxAmount = (price * taxRate) / 100
                val netPrice = price + taxAmount

                binding.resultText1.text = "Cost Price: $%.2f".format(price)
                binding.resultText2.text = "Tax Rate: %.2f%%".format(taxRate)
                binding.resultText3.text = "Tax Amount: $%.2f".format(taxAmount)
                binding.resultText4.text = "Net Price: $%.2f".format(netPrice)
            } else {

                Toast.makeText(this, "Please enter both Price and Tax Rate", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupAPYCalculator() {
        binding.calcuateBtn.setOnClickListener {
            val ageInput = binding.interestRateEdit.text.toString()

            if (ageInput.isEmpty()) {
                Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val age = ageInput.toInt()
            if (age !in 18..39) {
                Toast.makeText(this, "Age must be between 18 and 39", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedPension = binding.spinnerApy.selectedItem.toString().toInt()

            val monthlyInvestment = calculateMonthlyInvestment(age, selectedPension)
            val investmentDuration = 60 - age
            val totalAmountInvested = monthlyInvestment * investmentDuration * 12


            binding.resultText1.text = "Desired Monthly Pension: $selectedPension"
            binding.resultText2.text = "Monthly Investment: $monthlyInvestment"
            binding.resultText3.text = "Investment Duration: $investmentDuration years"
            binding.resultText4.text = "Total Amount Invested: $totalAmountInvested"
        }
    }

    private fun calculateMonthlyInvestment(age: Int, pension: Int): Int {
        return when (pension) {
            1000 -> (42 + (40 - age)) * 2
            2000 -> (84 + (40 - age)) * 2
            3000 -> (126 + (40 - age)) * 2
            4000 -> (168 + (40 - age)) * 2
            5000 -> (210 + (40 - age)) * 2
            else -> 0
        }
    }
}









