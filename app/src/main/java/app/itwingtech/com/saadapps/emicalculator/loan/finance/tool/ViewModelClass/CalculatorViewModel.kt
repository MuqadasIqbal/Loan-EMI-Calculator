package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.ViewModelClass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Models.DataModel
import com.saadapps.emicalculator.loan.finance.tool.R

class CalculatorViewModel : ViewModel() {

    private val MostUsedCalDataList = MutableLiveData<List<DataModel>>()
    val MostUsedCalList: LiveData<List<DataModel>> get() = MostUsedCalDataList

    private val TaxDataList = MutableLiveData<List<DataModel>>()
    val taxList: LiveData<List<DataModel>> get() = TaxDataList

    private val InvestmentDataList = MutableLiveData<List<DataModel>>()
    val investmentList: LiveData<List<DataModel>> get() = InvestmentDataList

    private val AssertsDataList = MutableLiveData<List<DataModel>>()
    val assertsList: LiveData<List<DataModel>> get() = AssertsDataList

    private val BusinessDataList = MutableLiveData<List<DataModel>>()
    val businessList: LiveData<List<DataModel>> get() = BusinessDataList

    private val PriceDataList = MutableLiveData<List<DataModel>>()
    val priceList: LiveData<List<DataModel>> get() = PriceDataList

    private val MiscellaneousDataList = MutableLiveData<List<DataModel>>()
    val miscellaneousList: LiveData<List<DataModel>> get() = MiscellaneousDataList

    // This is to hold all the data combined for search purposes
    val allCalculators = MutableLiveData<List<DataModel>>()

    init {
        loadData()
    }

    private fun loadData() {

        val MostUseCalData = listOf(
            DataModel(R.drawable.sip, "SIP Calculator"),
            DataModel(R.drawable.rd, "VAT Calculator"),
            DataModel(R.drawable.lumpsum, "Sales Calculator"),
            DataModel(R.drawable.ppf, "PPF Calculator"),
            DataModel(R.drawable.fd, "GST Calculator"),
            DataModel(R.drawable.scss, "SCSS Calculator"),
            DataModel(R.drawable.fd, "FD Calculator"),
            DataModel(R.drawable.lumpsum, "Lumpsum Calculator"),
            DataModel(R.drawable.rd, "RD Calculator"),
            DataModel(R.drawable.loan, "Loan Comparisons"),

            )
        MostUsedCalDataList.value=MostUseCalData

        val taxData = listOf(
            DataModel(R.drawable.fd, "GST Calculator"),
            DataModel(R.drawable.rd, "VAT Calculator"),
            DataModel(R.drawable.lumpsum, "Sales Calculator")
        )
        TaxDataList.value = taxData

        val investmentData = listOf(
            DataModel(R.drawable.sip, "SIP Calculator"),
            DataModel(R.drawable.ppf, "PPF Calculator"),
            DataModel(R.drawable.lumpsum, "Lumpsum Calculator"),
            DataModel(R.drawable.swp, "SWP Calculator"),

        )
        InvestmentDataList.value = investmentData

        val assetsData = listOf(
            DataModel(R.drawable.carlease, "Car Lease Calculator"),
            DataModel(R.drawable.networth, "NetWorth Calculator"),
            DataModel(R.drawable.rental, "Rental Calculator"),
            DataModel(R.drawable.loan, "Loan Comparisons"),
            DataModel(R.drawable.fd, "FD Calculator"),
            DataModel(R.drawable.rd, "RD Calculator"),
            DataModel(R.drawable.scss, "SCSS Calculator"),
            DataModel(R.drawable.apy, "APY Calculator"),
        )
        AssertsDataList.value = assetsData

        val businessData = listOf(
            DataModel(R.drawable.nvp, "NVP Calculator"),
            DataModel(R.drawable.value, "Value Calculator"),
            DataModel(R.drawable.margin, "Margin Calculator"),
            DataModel(R.drawable.resource_return, "Return Comparisons"),
        )
        BusinessDataList.value = businessData

        val priceData = listOf(
            DataModel(R.drawable.discount, "Discount Calculator"),
            DataModel(R.drawable.price, "Price Calculator"),
            DataModel(R.drawable.gross, "Gross Calculator"),
            DataModel(R.drawable.netprice, "Net Price Comparisons"),
        )
        PriceDataList.value = priceData

        val miscellaneousData = listOf(
            DataModel(R.drawable.cash, "Cash Counter"),
            DataModel(R.drawable.tips, "Tips Calculator")
        )
        MiscellaneousDataList.value = miscellaneousData

        allCalculators.value =taxData + investmentData + assetsData + businessData + priceData + miscellaneousData
    }
    fun filterCalculators(query: String?): List<DataModel> {
        if (query.isNullOrEmpty()) {
            return allCalculators.value ?: emptyList()
        }

        val searchText = query.lowercase()
        return allCalculators.value?.filter {
            it.title.lowercase().contains(searchText)
        } ?: emptyList()
    }
}
