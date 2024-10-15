package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Fragments.OnBoardingFragment
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Models.OnboardingItem
import com.saadapps.emicalculator.loan.finance.tool.R

class OnBoardingAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private val onboardingData = listOf(
        OnboardingItem(R.drawable.boarding_img1, "Simplify Your EMI Calculations", "Quickly calculate and manage your\nEMIs with ease"),
        OnboardingItem(R.drawable.boarding_img2, "Comprehensive Financial Tools", "All the financial tools you need in one app"),
        OnboardingItem(R.drawable.boarding_img3, "Stay in Control of Your Finances", "Track payments and optimize your savings\neffortlessly")
    )

    override fun getItemCount(): Int = onboardingData.size

    override fun createFragment(position: Int): Fragment {
        val item = onboardingData[position]
        return OnBoardingFragment.newInstance(item.imageRes, item.title, item.description)
    }
}