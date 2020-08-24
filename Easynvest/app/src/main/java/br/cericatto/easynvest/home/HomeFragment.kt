package br.cericatto.easynvest.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.cericatto.easynvest.DatePickerFragment
import br.cericatto.easynvest.R
import br.cericatto.easynvest.databinding.FragmentHomeBinding
import br.cericatto.easynvest.utils.formatMaturityDate

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
        binding.investmentEditText.requestFocus()

        setEditTexts(binding)

        binding.simulateButton.setOnClickListener {
            val a = binding.investmentEditText.text.toString().toDouble()
            val b = binding.cdiEditText.text.toString().toDouble()
            val c = binding.maturityDateEditText.text.toString()
            viewModel.getEasynvestData(investedAmount = a, rate = b, maturityDate = c.formatMaturityDate())
        }

        /**
         * Live Data.
         */

        viewModel.investmentEditTextIsValid.observe(viewLifecycleOwner, Observer {
            if (!it) binding.investmentEditText.error = getString(R.string.invalid_investment)
        })

        viewModel.dateEditTextIsValid.observe(viewLifecycleOwner, Observer {
            if (!it)
                binding.maturityDateEditText.error = getString(R.string.invalid_maturity_date)
            else
                binding.maturityDateEditText.error = null
        })
        
        viewModel.cdiEditTextIsValid.observe(viewLifecycleOwner, Observer {
            if (!it) binding.cdiEditText.error = getString(R.string.invalid_cdi)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(HomeFragmentDirections.actionShowResult(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }

    private fun setEditTexts(binding: FragmentHomeBinding) {
        val investmentEditText = binding.investmentEditText
        val dateEditText = binding.maturityDateEditText
        val cdiEdiText = binding.cdiEditText

        investmentEditText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val text = investmentEditText.text.toString()
                val valid =  (text.isNotEmpty()) && (text.toDouble() > 0)
                viewModel.updateInvestmentEditText(valid)
                dateEditText.requestFocus()
            }
        }

        dateEditText.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment(it as EditText)
            newFragment.show(fragmentManager!!, "DatePicker")
        }

        dateEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val text = dateEditText.text.toString()
                val valid = text.isNotEmpty()
                viewModel.updateDateTextView(valid)
                cdiEdiText.requestFocus()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int ) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int ) {}
        })

        cdiEdiText.setOnEditorActionListener { view, actionId, event ->
            if (EditorInfo.IME_ACTION_DONE == actionId) {
                val text = cdiEdiText.text.toString()
                val valid =  (text.isNotEmpty()) && (text.toDouble() > 0)
                viewModel.updateCdiEditText(valid)
            }
            false
        }
    }
}