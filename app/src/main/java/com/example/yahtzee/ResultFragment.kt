package com.example.yahtzee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.yahtzee.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //bindingする
        val binding: FragmentResultBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        //ライフサイクルの同期
        binding.lifecycleOwner = viewLifecycleOwner

        //ViewModelを取得する
        val viewModel : ViewModel by activityViewModels()

        //スコアを表示
        binding.scoreText.text = viewModel.result.value.toString()

        //ボタンの動作を設定
        val finishButton = binding.finishButton
        val action = ResultFragmentDirections.actionResultFragmentToTitleFragment()
        finishButton.setOnClickListener {
            findNavController().navigate(action)
        }

        return binding.root
    }
}