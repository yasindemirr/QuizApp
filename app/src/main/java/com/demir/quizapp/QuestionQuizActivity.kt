package com.demir.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.demir.quizapp.databinding.ActivityQuestionQuizBinding
import kotlin.random.Random

class QuestionQuizActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1 // Varsayılan ve ilk soru konumu

    private var mQuestionsList: ArrayList<Question>? = null
    private var mCorrectAnswers: Int = 0
    private var icClick:Boolean=true
    private var check:Boolean=true
    var aa= listOf<Int>()
    private val options = ArrayList<TextView>()
    val random1= Random.nextInt(0,3)
    var random2:Int=0

    private var mUserName: String? = null
    private var mSelectedOptionPosition: Int = 0
    private lateinit var binding: ActivityQuestionQuizBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuestionQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mQuestionsList = Constants.getQuestions()



        setQuestion()
        binding.apply {
            ivJoker.setOnClickListener(this@QuestionQuizActivity)
            tvOptionOne.setOnClickListener(this@QuestionQuizActivity)
            tvOptionTwo.setOnClickListener(this@QuestionQuizActivity)
            tvOptionThree.setOnClickListener(this@QuestionQuizActivity)
            tvOptionFour.setOnClickListener(this@QuestionQuizActivity)
            btnSubmit.setOnClickListener(this@QuestionQuizActivity)

        }
    }
    private fun setQuestion() {

        val question: Question = mQuestionsList!![mCurrentPosition - 1]  // Soruyu mevcut konumun yardımıyla listeden alıyoruz.
        defaultOptionsView()
        if (mCurrentPosition == mQuestionsList!!.size) {
            binding.btnSubmit.text = "FINISH"
        } else {
           binding.btnSubmit.text = "SUBMIT"
        }
        icClick=true
        check=true
       for (item in aa){
           options[item-1].background=ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
           options[item-1].isClickable=true
       }
       binding.progressBar.progress =
            mCurrentPosition // Sorunun konumunu kullanarak ilerleme çubuğundaki mevcut ilerlemeyi ayarlama
        binding.tvProgress.text =
            "$mCurrentPosition" + "/" + binding.progressBar.max // İlerleme metninin ayarlanması

        // Now set the current question and the options in the UI
        binding.apply {
            tvQuestion.text = question.question
            ivImage.setImageResource(question.image)
            tvOptionOne.text = question.optionOne
            tvOptionTwo.text = question.optionTwo
            tvOptionThree.text = question.optionThree
            tvOptionFour.text = question.optionFour
        }

    }

    /**
     *Yeni soru yüklendiğinde veya yanıt yeniden seçildiğinde varsayılan seçenekler görünümünü ayarlama işlevi.
     */
    private fun defaultOptionsView() {


       binding.tvOptionOne.let {
            options.add(0, it)
        }
       binding. tvOptionTwo.let {
            options.add(1, it)
        }
       binding. tvOptionThree.let {
            options.add(2, it)
        }
       binding.tvOptionFour.let {
            options.add(3,it)
        }

        for (option in options) {
                option.setTextColor(Color.parseColor("#7A8089"))
                option.typeface = Typeface.DEFAULT
                option.background = ContextCompat.getDrawable(
                    this@QuestionQuizActivity,
                    R.drawable.default_option_border_bg
                )

        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.tv_option_one -> {
                if (icClick){
                    binding.tvOptionOne.let {
                        selectedOptionView(it, 1)
                    }
                }else Unit
            }

            R.id.tv_option_two -> {
                if (icClick) {
                    binding.tvOptionTwo.let {
                        selectedOptionView(it, 2)
                    }
                }else Unit

            }

            R.id.tv_option_three -> {
                if (icClick) {
                    binding.tvOptionThree.let {
                        selectedOptionView(it, 3)
                    }
                }else Unit

            }

            R.id.tv_option_four -> {
                if (icClick){
               binding.tvOptionFour.let {
                    selectedOptionView(it, 4)
               }
                }else Unit
                }
            R.id.iv_joker->{
                check=false
                val correctAnswer=mQuestionsList!![mCurrentPosition-1].correctAnswer
                println(correctAnswer)
                val list= mutableListOf<Int>(1,2,3,4)
                list.remove(correctAnswer)
                aa=list.shuffled().take(2)
                println(aa)
                for (item in aa){
                    options[item-1].background=ContextCompat.getDrawable(this,R.drawable.eleminate_option_border_bg)
                    options[item-1].isClickable=false
                }
                //val aa=options.toMutableList()
              //  aa.remove(options[correctAnswer])
              // val bb= aa.shuffled().take(2)
                binding.ivJoker.background=ContextCompat.getDrawable(this,R.drawable.joker_backround)



            }
            R.id.btn_submit->{
                icClick=false
                if (mSelectedOptionPosition == 0) { //eğer hiçbir seçeneği  seçmezsem, bir sonraki soruya geçeceğim

                    mCurrentPosition++

                    when {

                        mCurrentPosition <= mQuestionsList!!.size -> {

                            setQuestion()
                        }
                        else -> {
                            // TODO (Şimdi tost mesajını kaldırıp ve oluşturduğumuz sonuç ekranını başlatıcaz ve ayrıca kullanıcı adını ve puan ayrıntılarını da ona ileticez..)
                            val intent =
                                Intent(this@QuestionQuizActivity, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // soru eğer yanlışsa
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }

                    // bu doğru cevap için
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        binding.btnSubmit.text = "FINISH"
                    } else {
                        binding.btnSubmit.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    /**
     *
    Seçilen seçenek görünümünü ayarlamak için bir işlev.
     */
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()
        if (check==false){
            for (item in aa){
                options[item-1].background=ContextCompat.getDrawable(this,R.drawable.eleminate_option_border_bg)
            }
        }else Unit



        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this@QuestionQuizActivity,
            R.drawable.selected_option_border_bg
        )
    }

    /**
     * Cevabın yanlış veya doğru olduğunu vurgulamak için kullanılan bir cevap görünümü işlevi.
     */
    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> {
               binding. tvOptionOne.background = ContextCompat.getDrawable(
                    this@QuestionQuizActivity,
                    drawableView
                )
            }
            2 -> {
               binding. tvOptionTwo.background = ContextCompat.getDrawable(
                    this@QuestionQuizActivity,
                    drawableView
                )
            }
            3 -> {
                binding. tvOptionThree.background = ContextCompat.getDrawable(
                    this@QuestionQuizActivity,
                    drawableView
                )
            }
            4 -> {
               binding. tvOptionFour.background = ContextCompat.getDrawable(
                    this@QuestionQuizActivity,
                    drawableView
                )
            }
        }
    }

}