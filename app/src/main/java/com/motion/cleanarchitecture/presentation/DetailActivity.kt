package com.motion.cleanarchitecture.presentation


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.motion.cleanarchitecture.R
import com.motion.cleanarchitecture.domain.BookItem

class DetailActivity : AppCompatActivity(),BookItemFragment.OnToastingListener {

    private var screenMode = UNKNOWN_MODE
    private var bookId = BookItem.START_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        parseIntent()
        if (savedInstanceState == null){
            launchRightMode()
        }
    }

    private fun launchRightMode() {
     val fragment =  when (screenMode) {
            ADD_MODE -> BookItemFragment.instanceAddItem()
            EDIT_MODE -> BookItemFragment.instanceEditItem(bookId)
         else-> throw RuntimeException("Такой экран нет")
        }
        supportFragmentManager.beginTransaction().replace(R.id.bookItemContainer,fragment).commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE)) {
            throw RuntimeException("Такой экран нет")
        }
        val mode = intent.getStringExtra(SCREEN_MODE)
        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Такой экран нет $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!intent.hasExtra(BOOK_ITEM_ID)) {
                throw RuntimeException("Такая книга нет")
            }
            bookId = intent.getIntExtra(BOOK_ITEM_ID, BookItem.START_ID)
        }
    }
        companion object {
            private const val SCREEN_MODE = "extra_mode"
            private const val ADD_MODE = "add_mode"
            private const val EDIT_MODE = "edit_mode"
            private const val BOOK_ITEM_ID = "shop_id"
            private const val UNKNOWN_MODE = ""

            fun newIntentAddItem(context: Context): Intent {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(SCREEN_MODE, ADD_MODE)
                return intent
            }

            fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(SCREEN_MODE, EDIT_MODE)
                intent.putExtra(BOOK_ITEM_ID, shopItemId)
                return intent
            }
        }

    override fun onToasting() {
        finish()
    }
}