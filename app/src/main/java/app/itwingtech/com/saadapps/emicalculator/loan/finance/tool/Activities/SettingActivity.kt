package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Models.DialogData
import com.saadapps.emicalculator.loan.finance.tool.R
import com.saadapps.emicalculator.loan.finance.tool.databinding.ActivitySettingBinding
import com.airbnb.lottie.BuildConfig

class SettingActivity : AppCompatActivity() {
    private val binding:ActivitySettingBinding by lazy {
        ActivitySettingBinding.inflate(layoutInflater)
    }
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
        binding.ratingLayout.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.saadapps.emicalculator.loan.finance.tool")))
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.saadapps.emicalculator.loan.finance.tool"))
                )
            }
        }
        binding.shareLayout.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                val shareMessage = ("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID).toString() + "\n\n"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
        }

        binding.termLayout.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://itwingtech.com/")
            startActivity(intent)
        }

        binding.privacyLayout.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://itwingtech.com/privacy-policy/")
            startActivity(intent)

        }

        binding.feedbackLayout.setOnClickListener {
            DialogData.showDialog(
                this,
                isFeedback = true,
                isRating = false,
                title = "Feedback",
                description = "Please let us know how we can improve:",
                positiveText = "Submit",
                onPositiveClick = {

                },
                onCancelClick = {

                }
            )
        }


        binding.ratingLayout.setOnClickListener {
            DialogData.showDialog(
                this,
                isFeedback = false,
                isRating = true,
                title = "Enjoy Our App",
                description = "If you enjoy using our app,\n Please rate us",
                positiveText = "Rate Now",
                onPositiveClick = {

                },
                onCancelClick = {

                }
            )
        }

    }
}