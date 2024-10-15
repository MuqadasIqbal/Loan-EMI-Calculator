package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Models

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.saadapps.emicalculator.loan.finance.tool.R

object DialogData {

    fun showDialog(
        context: Context,
        isFeedback: Boolean,
        isRating: Boolean,
        title: String,
        description: String,
        positiveText: String,
        onPositiveClick: (() -> Unit)? = null,
        onCancelClick: (() -> Unit)? = null
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.custom_feedback_dialog)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle = dialog.findViewById<TextView>(R.id.tv_tittle)
        val cancelImg = dialog.findViewById<ImageView>(R.id.cancel_img)
        val rateusImg=dialog.findViewById<ImageView>(R.id.rateus_img)
        val tvDescription = dialog.findViewById<TextView>(R.id.tv_description)
        val editTextMessage = dialog.findViewById<EditText>(R.id.editTextText)
        val btnPositive = dialog.findViewById<AppCompatButton>(R.id.btn_send)

        tvTitle.text = title
        tvDescription.text = description
        btnPositive.text = positiveText


        if (isFeedback) {
            editTextMessage.visibility = EditText.VISIBLE
            btnPositive.setOnClickListener {
                val message = editTextMessage.text.toString()
                if (message.isNotEmpty()) {
                    onPositiveClick?.invoke()
                    sendFeedbackEmail(context, message)
                } else {
                    Toast.makeText(context, "Please enter a message.", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
        } else if (isRating) {
            tvDescription.visibility=TextView.VISIBLE
            rateusImg.visibility = ImageView.VISIBLE
            btnPositive.text = "Rate Now"
            rateusImg.setOnClickListener {
                openAppInPlayStore(context)
                dialog.dismiss()
            }
        } else {
            tvDescription.visibility=TextView.VISIBLE
            btnPositive.setOnClickListener {
                onPositiveClick?.invoke()
                dialog.dismiss()
            }
        }

        cancelImg.setOnClickListener {
            onCancelClick?.invoke()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun sendFeedbackEmail(context: Context, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.email)))
            putExtra(Intent.EXTRA_SUBJECT, "Feedback from App")
            putExtra(Intent.EXTRA_TEXT, message)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No email app found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openAppInPlayStore(context: Context) {
        val packageName = "com.saadapps.emicalculator.loan.finance.tool"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
            )
        }
    }
}