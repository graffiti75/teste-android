package br.cericatto.easynvest.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.cericatto.easynvest.databinding.FragmentHomeBinding
import br.cericatto.easynvest.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val binding = FragmentResultBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
}