package br.cericatto.easynvest.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.cericatto.easynvest.DatePickerFragment
import br.cericatto.easynvest.R
import br.cericatto.easynvest.databinding.FragmentHomeBinding
import br.cericatto.easynvest.utils.formatMaturityDate
import br.cericatto.easynvest.utils.showToast

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment.
        binding.lifecycleOwner = this

        // Giving the binding access to the HomeViewModel.
        binding.viewModel = viewModel
        binding.simulateButton.setOnClickListener {
//            val context = activity!!
//            if (!context.networkOn()) context.showToast(R.string.no_network)
//            else {
                val a = binding.howMuchToApply.text.toString().toDouble()
                val b = binding.cdiEditText.text.toString().toDouble()
                val c = binding.investmentMaturityDateValue.text.toString()
                viewModel.checkFields(a, b, c.formatMaturityDate())
                // viewModel.getEasynvestData(investedAmount = a, rate = b, maturityDate = c.formatMaturityDate())
//            }
        }
        setEditTexts(binding)

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(HomeFragmentDirections.actionShowResult(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        viewModel.simulateButtonVisible.observe(viewLifecycleOwner, Observer {
            val context = activity!!
            context.showToast(R.string.invalid_fields)
        })

        return binding.root
    }

    private fun setEditTexts(binding: FragmentHomeBinding) {
        val dateTextView = binding.investmentMaturityDateValue
        dateTextView.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment(it as TextView)
            newFragment.show(fragmentManager!!, "DatePicker")
        }
    }
}