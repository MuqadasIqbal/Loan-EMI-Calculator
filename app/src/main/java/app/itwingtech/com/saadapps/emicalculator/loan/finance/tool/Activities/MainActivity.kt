package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Adapters.CalculatorAdapter
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Models.DataModel
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Models.DialogData
import com.saadapps.emicalculator.loan.finance.tool.R
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.ViewModelClass.CalculatorViewModel
import com.saadapps.emicalculator.loan.finance.tool.databinding.ActivityMainBinding
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.mancj.materialsearchbar.MaterialSearchBar

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: CalculatorViewModel
    private lateinit var mostUseAdapter: CalculatorAdapter
    private lateinit var taxAdapter: CalculatorAdapter
    private lateinit var investmentAdapter: CalculatorAdapter
    private lateinit var assetsAdapter: CalculatorAdapter
    private lateinit var businessAdapter: CalculatorAdapter
    private lateinit var priceAdapter: CalculatorAdapter
    private lateinit var miscellaneousAdapter: CalculatorAdapter
    private var MY_REQUEST_CODE: Int = 100;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        CheckForUpdate()
        initRecyclerViews()

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)

        binding.assetsSeeAll.setOnClickListener {
            openCategoryActivity(viewModel.assertsList.value ?: emptyList())

        }
        binding.settingsImg.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        binding.emiText.setOnClickListener {
            openCalculatorActivity("emi")
        }
        observeData()

        // Set up search bar
        binding.searchBar.setOnSearchActionListener(object :
            MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {
                if (!enabled) {
                    observeData()
                }
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                performSearch(text.toString())
            }

            override fun onButtonClicked(buttonCode: Int) {
            }
        })

    }

    private fun initRecyclerViews() {
        mostUseAdapter = CalculatorAdapter(emptyList()) { dataModel -> onItemClicked(dataModel) }
        taxAdapter = CalculatorAdapter(emptyList()) { dataModel -> onItemClicked(dataModel) }
        investmentAdapter = CalculatorAdapter(emptyList()) { dataModel -> onItemClicked(dataModel) }
        assetsAdapter = CalculatorAdapter(emptyList()) { dataModel -> onItemClicked(dataModel) }
        businessAdapter = CalculatorAdapter(emptyList()) { dataModel -> onItemClicked(dataModel) }
        priceAdapter = CalculatorAdapter(emptyList()) { dataModel -> onItemClicked(dataModel) }
        miscellaneousAdapter =
            CalculatorAdapter(emptyList()) { dataModel -> onItemClicked(dataModel) }

        findViewById<RecyclerView>(R.id.mostUseRec).apply {
            adapter = mostUseAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        findViewById<RecyclerView>(R.id.tax_rec).apply {
            adapter = taxAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        findViewById<RecyclerView>(R.id.investmentRec).apply {
            adapter = investmentAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        findViewById<RecyclerView>(R.id.assestsRec).apply {
            adapter = assetsAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        findViewById<RecyclerView>(R.id.business_Cal_Rec).apply {
            adapter = businessAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        findViewById<RecyclerView>(R.id.priceDiscount_Rec).apply {
            adapter = priceAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        findViewById<RecyclerView>(R.id.miscell_cal_Rec).apply {
            adapter = miscellaneousAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun observeData() {
        // Observing data for each category
        viewModel.MostUsedCalList.observe(this) { list ->
            mostUseAdapter.updateList(list)
        }
        viewModel.taxList.observe(this) { list ->
            taxAdapter.updateList(list)
        }
        viewModel.investmentList.observe(this) { list ->
            investmentAdapter.updateList(list)
        }
        viewModel.assertsList.observe(this) { list ->
            assetsAdapter.updateList(list)
        }
        viewModel.businessList.observe(this) { list ->
            businessAdapter.updateList(list)
        }
        viewModel.priceList.observe(this) { list ->
            priceAdapter.updateList(list)
        }
        viewModel.miscellaneousList.observe(this) { list ->
            miscellaneousAdapter.updateList(list)
        }
    }

    private fun performSearch(query: String) {
        val filteredList = viewModel.filterCalculators(query)

        taxAdapter.updateList(filteredList.filter {
            it.title.contains("GST") || it.title.contains("VAT") || it.title.contains(
                "Sales"
            )
        })
        investmentAdapter.updateList(filteredList.filter {
            it.title.contains("SIP") || it.title.contains(
                "PPF"
            ) || it.title.contains("Lumpsum")
        })
        assetsAdapter.updateList(filteredList.filter {
            it.title.contains("Car Lease") || it.title.contains(
                "NetWorth"
            ) || it.title.contains("Rental")
        })
        businessAdapter.updateList(filteredList.filter {
            it.title.contains("NVP") || it.title.contains(
                "Value"
            ) || it.title.contains("Margin")
        })
        priceAdapter.updateList(filteredList.filter {
            it.title.contains("Discount") || it.title.contains(
                "Price"
            )
        })
        miscellaneousAdapter.updateList(filteredList.filter {
            it.title.contains("Cash") || it.title.contains(
                "Tips"
            )
        })
    }
    private fun onItemClicked(dataModel: DataModel) {
        when (dataModel.title) {
            "GST Calculator" -> openCalculatorActivity("gst")
            "VAT Calculator" -> openCalculatorActivity("vat")
            "Sales Calculator" -> openCalculatorActivity("sales")
            "SIP Calculator" -> openCalculatorActivity("sip")
            "PPF Calculator" -> openCalculatorActivity("ppf")
            "Lumpsum Calculator" -> openCalculatorActivity("lumpsum")
            "SWP Calculator" -> openCalculatorActivity("swp")
            "Car Lease Calculator" -> openCarLeaseCalculatorActivity("Car Lease Calculator")
            "Rental Calculator" -> openCarLeaseCalculatorActivity("Rental Calculator")
            "Loan Comparisons" -> openCarLeaseCalculatorActivity("Loan Comparisons")
            "FD Calculator" -> openCalculatorActivity("FD Calculator")
            "RD Calculator" -> openCalculatorActivity("RD Calculator")
            "SCSS Calculator" -> openCalculatorActivity("SCSS Calculator")
            "APY Calculator" -> openCalculatorActivity("APY Calculator")
            "Value Calculator" -> openCalculatorActivity("value")
            "Margin Calculator" -> openCalculatorActivity("margin")
            "Return Comparisons" -> openCalculatorActivity("return")
            "Discount Calculator" -> openCalculatorActivity("discount")
            "Price Calculator" -> openCalculatorActivity("price")
            "Gross Calculator" -> openCalculatorActivity("gross")
            "Net Price Comparisons" -> openCalculatorActivity("net price")
            "Tips Calculator" -> openCalculatorActivity("tips")

            "Cash Counter" -> {
                val intent = Intent(this, CashCounterActivity::class.java)
                startActivity(intent)
            }

            "NVP Calculator" -> {
                val intent = Intent(this, NPVCalculatorActivity::class.java)
                startActivity(intent)
            }

            "NetWorth Calculator" -> {
                val intent = Intent(this, NetWorthActivity::class.java)
                startActivity(intent)
            }

            else -> openCalculatorActivity(dataModel.title.lowercase())
        }
    }
    private fun openCalculatorActivity(calculatorType: String) {
        val intent = Intent(this, CalculateActivity::class.java)
        intent.putExtra("calculator_type", calculatorType)
        startActivity(intent)
    }
    private fun openCarLeaseCalculatorActivity(calculatorType: String) {
        val intent = Intent(this, CarLeaseCalculatorActivity::class.java)
        intent.putExtra("calculator_type", calculatorType)
        startActivity(intent)
    }
    private fun openCategoryActivity(dataList: List<DataModel>) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putParcelableArrayListExtra("dataList", ArrayList(dataList))
        startActivity(intent)
    }
    private fun CheckForUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)

        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        MY_REQUEST_CODE
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(this, "Update Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed(){
        showExitDialog()
    }
    private fun showExitDialog() {
        DialogData.showDialog(
            this,
            isFeedback = false,
            isRating = false,
            title = "Exit",
            description = "Are you sure you want to exit?",
            positiveText = "Yes",
            onPositiveClick = {

                finish()
            },
            onCancelClick = {
            }
        )
    }
}
