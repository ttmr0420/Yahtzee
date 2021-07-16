package com.example.yahtzee

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.yahtzee.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //bindingする
        val binding : FragmentGameBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        //ライフサイクルの同期
        binding.lifecycleOwner = viewLifecycleOwner

        //ViewModelを取得する
        val viewModel : ViewModel by activityViewModels()

        //roundのobserverを設定し、テキストを更新する
        viewModel.round.observe(viewLifecycleOwner, {
            binding.roundText.text = "Round: $it/13"
        })

        //初期化する
        if (viewModel.round.value == 0) {
            viewModel.initDice()
            viewModel.round.value = 1
        }

        //サイコロをリスト化
        val dices = arrayOf(binding.diceA, binding.diceB, binding.diceC, binding.diceD, binding.diceE)

        //サイコロの画像を表示する
        dices.zip(viewModel.holds.zip(viewModel.dices)).forEach {
            drawDice(it.second.first.value, it.second.second.value, it.first)
        }

        

        //サイコロをholdする処理
        binding.diceA.setOnClickListener {
            //rollCountを確認し、まだ振り直せる場合のみ処理を行う
            if (viewModel.rollCount.value!! < 3) {
                //isHoldの真偽を逆転させる
                viewModel.diceAisHold.value = !viewModel.diceAisHold.value!!
                //入れ替えたら再度描画
                drawDice(viewModel.diceAisHold.value, viewModel.diceA.value, binding.diceA)
            }
        }
        binding.diceB.setOnClickListener {
            if (viewModel.rollCount.value!! < 3) {
                viewModel.diceBisHold.value = !viewModel.diceBisHold.value!!
                drawDice(viewModel.diceBisHold.value, viewModel.diceB.value, binding.diceB)
            }
        }
        binding.diceC.setOnClickListener {
            if (viewModel.rollCount.value!! < 3) {
                viewModel.diceCisHold.value = !viewModel.diceCisHold.value!!
                drawDice(viewModel.diceCisHold.value, viewModel.diceC.value, binding.diceC)
            }
        }
        binding.diceD.setOnClickListener {
            if (viewModel.rollCount.value!! < 3) {
                viewModel.diceDisHold.value = !viewModel.diceDisHold.value!!
                drawDice(viewModel.diceDisHold.value, viewModel.diceD.value, binding.diceD)
            }
        }
        binding.diceE.setOnClickListener {
            if (viewModel.rollCount.value!! < 3) {
                viewModel.diceEisHold.value = !viewModel.diceEisHold.value!!
                drawDice(viewModel.diceEisHold.value, viewModel.diceE.value, binding.diceE)
            }
        }

        //Rollボタンの処理
        val rollButton = binding.buttonRoll
        //rollCountのobserverを設定し、テキストを更新する
        viewModel.rollCount.observe(viewLifecycleOwner, {
            rollButton.text = "Roll! $it/3"
        })
        rollButton.setOnClickListener {
            viewModel.rollDice()
            //再描画する
            drawDice(viewModel.diceAisHold.value, viewModel.diceA.value, binding.diceA)
            drawDice(viewModel.diceBisHold.value, viewModel.diceB.value, binding.diceB)
            drawDice(viewModel.diceCisHold.value, viewModel.diceC.value, binding.diceC)
            drawDice(viewModel.diceDisHold.value, viewModel.diceD.value, binding.diceD)
            drawDice(viewModel.diceEisHold.value, viewModel.diceE.value, binding.diceE)
        }

        //Scoreボタンの処理
        val scoreButton = binding.buttonScore
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment()
        scoreButton.setOnClickListener{
            findNavController().navigate(action)
        }

        //returnする
        return binding.root
    }

    //サイコロの画像表示メソッド
    private fun drawDice(diceIsHold: Boolean?, diceValue: Int?, diceImageView: ImageView) {
        if (diceIsHold == true) {
            when (diceValue) {
                1 -> diceImageView.setImageResource(R.drawable.dice_one)
                2 -> diceImageView.setImageResource(R.drawable.dice_two)
                3 -> diceImageView.setImageResource(R.drawable.dice_three)
                4 -> diceImageView.setImageResource(R.drawable.dice_four)
                5 -> diceImageView.setImageResource(R.drawable.dice_five)
                6 -> diceImageView.setImageResource(R.drawable.dice_six)
            }
        } else {
            diceImageView.setImageResource(R.drawable.dice_change)
        }
    }
}