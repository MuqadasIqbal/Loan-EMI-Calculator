package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saadapps.emicalculator.loan.finance.tool.R
import com.saadapps.emicalculator.loan.finance.tool.databinding.FragmentOnBoardingBinding

private const val ARG_IMAGE_RES = "image_res"
private const val ARG_TITLE = "title"
private const val ARG_DESCRIPTION = "description"
class OnBoardingFragment : Fragment() {

    private var imageRes: Int? = null
    private var title: String? = null
    private var description: String? = null

    val binding: FragmentOnBoardingBinding by lazy {
        FragmentOnBoardingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageRes = it.getInt(ARG_IMAGE_RES)
            title = it.getString(ARG_TITLE)
            description = it.getString(ARG_DESCRIPTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        imageRes?.let {
            binding.onboardingImg.setImageResource(it)
        }
        title?.let {
            binding.boardingTittle.text = it
        }
        description?.let {
            binding.boardingDescription.text = it
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(imageRes: Int, title: String, description: String) =
            OnBoardingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_IMAGE_RES, imageRes)
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                }
            }
    }
    }
