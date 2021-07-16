package com.example.yahtzee

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {
    //round数を保持する変数
    val round = MutableLiveData(0)

    //サイコロの出目とホールド状況を保持する変数
    val diceA = MutableLiveData<Int>()
    val diceB = MutableLiveData<Int>()
    val diceC = MutableLiveData<Int>()
    val diceD = MutableLiveData<Int>()
    val diceE = MutableLiveData<Int>()
    val diceAisHold = MutableLiveData(true)
    val diceBisHold = MutableLiveData(true)
    val diceCisHold = MutableLiveData(true)
    val diceDisHold = MutableLiveData(true)
    val diceEisHold = MutableLiveData(true)
    val holds = arrayOf(diceAisHold, diceBisHold, diceCisHold, diceDisHold, diceEisHold)
    val dices = arrayOf(diceA, diceB, diceC, diceD, diceE)

    //振り直し回数を保持する変数
    val rollCount = MutableLiveData<Int>()

    //以下二つは記録用のフラグメントと共有
    //出目の数を数える変数
    private var countOne = 0
    private var countTwo = 0
    private var countThree = 0
    private var countFour = 0
    private var countFive = 0
    private var countSix = 0

    //出目の合計値と役成立フラグ変数
    private var total = 0
    private var threeCards = false
    private var fourCards = false
    private var fullHouse = false
    private var miniStraight = false
    private var largeStraight = false
    private var yahtzee = false

    //各種スコア保持用の変数
    //-1で初期化し、入力済かどうかのフラグとして使う
    val onesScore = MutableLiveData(-1)
    val twosScore = MutableLiveData(-1)
    val threesScore = MutableLiveData(-1)
    val foursScore = MutableLiveData(-1)
    val fivesScore = MutableLiveData(-1)
    val sixesScore = MutableLiveData(-1)
    val bonusScore = MutableLiveData(-63)
    val threeCardsScore = MutableLiveData(-1)
    val fourCardsScore = MutableLiveData(-1)
    val fullHouseScore = MutableLiveData(-1)
    val miniStraightScore = MutableLiveData(-1)
    val largeStraightScore = MutableLiveData(-1)
    val yahtzeeScore = MutableLiveData(-1)
    val chanceScore = MutableLiveData(-1)

    //bonus判定に使用する変数
    private val onesToSixesScore = MutableLiveData(0)

    //終了フラグ
    var finish = false
    //最終スコアの変数
    val result = MutableLiveData<Int>()


    //初期化メソッド
    fun initDice() {
        //初期化と同時にサイコロを振る
        diceA.value = (1..6).random()
        diceB.value = (1..6).random()
        diceC.value = (1..6).random()
        diceD.value = (1..6).random()
        diceE.value = (1..6).random()
        rollCount.value = 1
        countOne = 0
        countTwo = 0
        countThree = 0
        countFour = 0
        countFive = 0
        countSix = 0
        total = 0
        threeCards = false
        fourCards = false
        fullHouse = false
        miniStraight = false
        largeStraight = false
        yahtzee = false

        //役成立状況を確認
        checkHand()
    }


    //サイコロを振りなおすメソッド
    fun rollDice() {
        //出目のカウントをリセット
        countOne = 0
        countTwo = 0
        countThree = 0
        countFour = 0
        countFive = 0
        countSix = 0
        //振り直しは二回まで
        if (rollCount.value!! < 3) {
            //dice毎にholdがfalseならばサイコロを振りなおす
            holds.zip(dices).forEach{
                if (!it.first.value!!) {
                    it.second.value = (1..6).random()
                    it.first.value = true
                }
            }
            //カウントを進める
            rollCount.value = rollCount.value!! + 1
        }
        //役の成立状況を確認
        checkHand()
    }


    //成立役を判定するメソッド
    private fun checkHand() {
        //出目を確認していく
        dices.forEach {
            when (it.value) {
                1 -> countOne++
                2 -> countTwo++
                3 -> countThree++
                4 -> countFour++
                5 -> countFive++
                6 -> countSix++
            }
        }

        //出目の合計を算出
        total = (countOne * 1) + (countTwo * 2) + (countThree * 3) +
                (countFour * 4) + (countFive * 5) + (countSix * 6)

        //成立役を判定
        //threeCards
        if (countOne >= 3 || countTwo >= 3 || countThree >= 3 ||
            countFour >= 3 || countFive >= 3 || countSix >= 3) {
            threeCards = true
        }
        //fourCards
        if (countOne >= 4 || countTwo >= 4 || countThree >= 4 ||
            countFour >= 4 || countFive >= 4 || countSix >= 4) {
            fourCards = true
        }
        //fullHouseはthreeCardとツーペア
        if (threeCards && (countOne == 2 || countTwo == 2 || countThree == 2 ||
                    countFour == 2 || countFive == 2 || countSix == 2)) {
            fullHouse = true
        }
        //miniStraight
        if ((countOne >= 1 && countTwo >= 1 && countThree >= 1 && countFour >= 1) ||
            (countTwo >= 1 && countThree >= 1 && countFour >= 1 && countFive >= 1) ||
            (countThree >= 1 && countFour >= 1 && countFive >= 1 && countSix >= 1)) {
            miniStraight = true
        }
        //largeStraight
        if ((countOne >= 1 && countTwo >= 1 && countThree >= 1 && countFour >= 1 && countFive >= 1) ||
            (countTwo >= 1 && countThree >= 1 && countFour >= 1 && countFive >= 1 && countSix >= 1)) {
            largeStraight = true
        }
        //yahtzee
        if (countOne == 5 || countTwo == 5 || countThree == 5 ||
            countFour == 5 || countFive == 5 || countSix == 5) {
            yahtzee =true
        }
    }


    //各種スコア記録用メソッド
    fun saveOnes(){
        onesScore.value = countOne * 1
        addOneToSix()
        checkBonus()
        round.value = round.value!! + 1
        checkFinish()
    }
    fun saveTwos(){
        twosScore.value = countTwo * 2
        addOneToSix()
        checkBonus()
        round.value = round.value!! + 1
        checkFinish()
    }
    fun saveThrees(){
        threesScore.value = countThree * 3
        addOneToSix()
        checkBonus()
        round.value = round.value!! + 1
        checkFinish()
    }
    fun saveFours(){
        foursScore.value = countFour * 4
        addOneToSix()
        checkBonus()
        round.value = round.value!! + 1
        checkFinish()
    }
    fun saveFives(){
        fivesScore.value = countFive * 5
        addOneToSix()
        checkBonus()
        round.value = round.value!! + 1
        checkFinish()
    }
    fun saveSixes(){
        sixesScore.value = countSix * 6
        addOneToSix()
        checkBonus()
        round.value = round.value!! + 1
        checkFinish()
    }

    //bonus判定に使うメソッド
    private fun addOneToSix() {
        val one = if (onesScore.value == -1) 0 else onesScore.value
        val two = if (twosScore.value == -1) 0 else twosScore.value
        val three = if (threesScore.value == -1) 0 else threesScore.value
        val four = if (foursScore.value == -1) 0 else foursScore.value
        val five = if (fivesScore.value == -1) 0 else fivesScore.value
        val six = if (sixesScore.value == -1) 0 else sixesScore.value

        onesToSixesScore.value = one!! + two!! + three!! + four!! + five!! + six!!
    }
    //bonus判定メソッド
    private fun checkBonus() {
        if (onesToSixesScore.value!! >= 63) {
            bonusScore.value = 35
        } else {
            bonusScore.value = onesToSixesScore.value!! - 63
        }
    }

    //ここからは役の成立フラグを確認し、成立してなければ0点
    fun saveThreeCards() {
        if (threeCards) {
            threeCardsScore.value = total
        } else {
            threeCardsScore.value = 0
        }
        round.value = round.value!! + 1
        checkFinish()
    }
    fun saveFourCards() {
        if (fourCards) {
            fourCardsScore.value = total
        } else {
            fourCardsScore.value = 0
        }
        round.value = round.value!! + 1
        checkFinish()
    }
    fun saveFullHouse() {
        if (fullHouse) {
            fullHouseScore.value = 25
        } else {
            fullHouseScore.value = 0
        }
        round.value = round.value!! + 1
        checkFinish()
    }
    fun saveMiniStraight() {
        if (miniStraight) {
            miniStraightScore.value = 30
        } else {
            miniStraightScore.value = 0
        }
        round.value = round.value!! + 1
        checkFinish()
    }
    fun saveLargeStraight() {
        if (largeStraight) {
            largeStraightScore.value = 40
        } else {
            largeStraightScore.value = 0
        }
        round.value = round.value!! + 1
        checkFinish()
    }
    fun saveYahtzee() {
        if (yahtzee) {
            yahtzeeScore.value = 50
        } else {
            yahtzeeScore.value = 0
        }
        round.value = round.value!! + 1
        checkFinish()
    }
    fun saveChance() {
        chanceScore.value = total
        round.value = round.value!! + 1
        checkFinish()
    }


    //ゲーム終了判定メソッド
    private fun checkFinish() {
        if (round.value == 14) {
            finish = true
            finishGame()
        }
    }
    //ゲーム終了メソッド
    private fun finishGame() {
        //ボーナスを処理
        if (bonusScore.value!! < 0) {
            bonusScore.value = 0
        }
        //全部足す
        result.value = onesScore.value!! + twosScore.value!! + threesScore.value!! +
                foursScore.value!! + fivesScore.value!! + sixesScore.value!! + bonusScore.value!! +
                threeCardsScore.value!! + fourCardsScore.value!! + fullHouseScore.value!! +
                miniStraightScore.value!! + largeStraightScore.value!! + yahtzeeScore.value!! +
                chanceScore.value!!
        //変数をリセットする
        round.value = 0
        onesScore.value = -1
        twosScore.value = -1
        threesScore.value = -1
        foursScore.value = -1
        fivesScore.value = -1
        sixesScore.value = -1
        bonusScore.value = -63
        threeCardsScore.value = -1
        fourCardsScore.value = -1
        fullHouseScore.value = -1
        miniStraightScore.value = -1
        largeStraightScore.value = -1
        yahtzeeScore.value = -1
        chanceScore.value = -1
        onesToSixesScore.value = 0
    }
}