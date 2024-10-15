package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Adapters.CalculatorAdapter
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Models.DataModel
import com.saadapps.emicalculator.loan.finance.tool.R
import com.saadapps.emicalculator.loan.finance.tool.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private val binding:ActivityCategoryBinding by lazy {
        ActivityCategoryBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: CalculatorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
        val dataList = intent.getParcelableArrayListExtra<DataModel>("dataList") ?: emptyList()

        adapter = CalculatorAdapter(dataList) { dataModel -> onItemClicked(dataModel) }
        binding.catgoryRec.layoutManager = GridLayoutManager(this, 3)
        binding.catgoryRec.adapter = adapter
    }

    private fun onItemClicked(dataModel: DataModel) {
        val intent = when (dataModel.title) {
            "Car Lease Calculator", "Loan Comparisons", "Rental Calculator" -> {
                Intent(this, CarLeaseCalculatorActivity::class.java)
            }
            "NetWorth Calculator" -> {
                Intent(this, NetWorthActivity::class.java)
            }
            else -> {
                Intent(this, CalculateActivity::class.java)
            }
        }
        intent.putExtra("calculator_type", dataModel.title)
        startActivity(intent)
    }
       /* val adapter = CalculatorAdapter(dataList ?: emptyList()) { dataModel ->
            val intent = when (dataModel.title) {
                "Car Lease Calculator", "Loan Comparisons", "Rental Calculator" -> {
                    Intent(this, CarLeaseCalculatorActivity::class.java)
                }
                "NetWorth Calculator" -> {
                    Intent(this,NetWorthActivity::class.java)
                }
                else -> {
                    Intent(this,CalculateActivity::class.java)
                }
            }
            intent.putExtra("calculator_type", dataModel.title)
            startActivity(intent)
        }*/

       //binding.catgoryRec.adapter = adapter

    }
