package com.example.yahtzee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.yahtzee.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //bindingする
        val binding : FragmentScoreBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)
        //ライフサイクルの同期
        binding.lifecycleOwner = viewLifecycleOwner

        //ViewModelを取得する
        val viewModel : ViewModel by activityViewModels()


        //スコアを表示する部分
        //入力前ならばデフォルトのテキスト、入力後ならばスコアを表示
        if (viewModel.onesScore.value != -1) {
            binding.onesScore.text = viewModel.onesScore.value.toString()
        }
        if (viewModel.twosScore.value != -1) {
            binding.twosScore.text = viewModel.twosScore.value.toString()
        }
        if (viewModel.threesScore.value != -1) {
            binding.threesScore.text = viewModel.threesScore.value.toString()
        }
        if (viewModel.foursScore.value != -1) {
            binding.foursScore.text = viewModel.foursScore.value.toString()
        }
        if (viewModel.fivesScore.value != -1) {
            binding.fivesScore.text = viewModel.fivesScore.value.toString()
        }
        if (viewModel.sixesScore.value != -1) {
            binding.sixesScore.text = viewModel.sixesScore.value.toString()
        }
        if (viewModel.threeCardsScore.value != -1) {
            binding.threeCardsScore.text = viewModel.threeCardsScore.value.toString()
        }
        if (viewModel.fourCardsScore.value != -1) {
            binding.fourCardsScore.text = viewModel.fourCardsScore.value.toString()
        }
        if (viewModel.fullHouseScore.value != -1) {
            binding.fullHouseScore.text = viewModel.fullHouseScore.value.toString()
        }
        if (viewModel.miniStraightScore.value != -1) {
            binding.miniStraightScore.text = viewModel.miniStraightScore.value.toString()
        }
        if (viewModel.largeStraightScore.value != -1) {
            binding.largeStraightScore.text = viewModel.largeStraightScore.value.toString()
        }
        if (viewModel.yahtzeeScore.value != -1) {
            binding.yahtzeeScore.text = viewModel.yahtzeeScore.value.toString()
        }
        if (viewModel.chanceScore.value != -1) {
            binding.chanceScore.text = viewModel.chanceScore.value.toString()
        }
        //bonus部分の処理
        binding.bonusScore.text = viewModel.bonusScore.value.toString()


        //下で使うNavigation
        val action = ScoreFragmentDirections.actionScoreFragmentToGameFragment()
        val finishAction = ScoreFragmentDirections.actionScoreFragmentToResultFragment()

        //それぞれの枠にスコア保存処理を実装する
        //保存済かどうか確認して済ならば何もしないようにする
        binding.onesScore.setOnClickListener {
            if (viewModel.onesScore.value == -1) {
                viewModel.saveOnes()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.twosScore.setOnClickListener {
            if (viewModel.twosScore.value == -1) {
                viewModel.saveTwos()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.threesScore.setOnClickListener {
            if (viewModel.threesScore.value == -1) {
                viewModel.saveThrees()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.foursScore.setOnClickListener {
            if (viewModel.foursScore.value == -1) {
                viewModel.saveFours()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.fivesScore.setOnClickListener {
            if (viewModel.fivesScore.value == -1) {
                viewModel.saveFives()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.sixesScore.setOnClickListener {
            if (viewModel.sixesScore.value == -1) {
                viewModel.saveSixes()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.threeCardsScore.setOnClickListener {
            if (viewModel.threeCardsScore.value == -1) {
                viewModel.saveThreeCards()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.fourCardsScore.setOnClickListener {
            if (viewModel.fourCardsScore.value == -1) {
                viewModel.saveFourCards()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.fullHouseScore.setOnClickListener {
            if (viewModel.fullHouseScore.value == -1) {
                viewModel.saveFullHouse()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.miniStraightScore.setOnClickListener {
            if (viewModel.miniStraightScore.value == -1) {
                viewModel.saveMiniStraight()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.largeStraightScore.setOnClickListener {
            if (viewModel.largeStraightScore.value == -1) {
                viewModel.saveLargeStraight()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.yahtzeeScore.setOnClickListener {
            if (viewModel.yahtzeeScore.value == -1) {
                viewModel.saveYahtzee()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }
        binding.chanceScore.setOnClickListener {
            if (viewModel.chanceScore.value == -1) {
                viewModel.saveChance()
                if (viewModel.finish) {
                    findNavController().navigate(finishAction)
                } else {
                    findNavController().navigate(action)
                    viewModel.initDice()
                }
            }
        }

        return binding.root
    }
}