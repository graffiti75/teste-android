package br.cericatto.easynvest.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.cericatto.easynvest.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        val binding = FragmentResultBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val easyProperty = ResultFragmentArgs.fromBundle(arguments!!).selectedProperty
        binding.simulateAgainButton.setOnClickListener {
            this.findNavController().navigate(ResultFragmentDirections.actionRestart())
        }

        setHasOptionsMenu(true)
        onBackPressed()

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.findNavController().navigate(ResultFragmentDirections.actionRestart())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onBackPressed() {
        val context = this
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // If you want onBackPressed() to be called as normal afterwards.
                    if (isEnabled) {
                        isEnabled = false
//                        requireActivity().onBackPressed()
                        context.findNavController()
                            .navigate(ResultFragmentDirections.actionRestart())
                    }
                }
            }
        )
    }
}