package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Adapters.OnBoardingAdapter
import com.saadapps.emicalculator.loan.finance.tool.R
import com.saadapps.emicalculator.loan.finance.tool.databinding.ActivityOnBoardingBinding
import me.relex.circleindicator.CircleIndicator3

class OnBoardingActivity : AppCompatActivity() {
    private val binding:ActivityOnBoardingBinding by lazy {
        ActivityOnBoardingBinding.inflate(layoutInflater)
    }
    private lateinit var onboardingAdapter: OnBoardingAdapter
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPreferences = getSharedPreferences("onboarding_prefs", MODE_PRIVATE)

        onboardingAdapter = OnBoardingAdapter(this)
        binding.onboardingViewpager.adapter = onboardingAdapter

        val indicator: CircleIndicator3 = binding.indicator
        indicator.setViewPager(binding.onboardingViewpager)


        binding.nextBtn.setOnClickListener {
            val currentItem = binding.onboardingViewpager.currentItem
            if (currentItem < onboardingAdapter.itemCount - 1) {
                binding.onboardingViewpager.currentItem = currentItem + 1
            } else {
                sharedPreferences.edit().putBoolean("onboarding_completed", true).apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
        binding.backArrow.setOnClickListener {
            val currentItem = binding.onboardingViewpager.currentItem
            if (currentItem > 0) {
                binding.onboardingViewpager.currentItem = currentItem - 1
            }
        }

    }
}