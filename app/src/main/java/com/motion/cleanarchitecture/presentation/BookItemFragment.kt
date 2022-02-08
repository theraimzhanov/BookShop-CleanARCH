package com.motion.cleanarchitecture.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.motion.cleanarchitecture.R
import com.motion.cleanarchitecture.domain.BookItem
import kotlinx.android.synthetic.main.fragment_book_item.*

class BookItemFragment() : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var screenMode: String = UNKNOWN_MODE
    private var bookItemId: Int = BookItem.START_ID
    private lateinit var onToastingListener:OnToastingListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_item, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
if(context is OnToastingListener){
onToastingListener = context
}  else{
throw RuntimeException("Activity must implement interface")
}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        textErrorChangeListener()
        launchRightMode()
        observeViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    private fun observeViewModel() {
        viewModel.errorInputBook.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Ошибка в поле: книга"
            } else {
                null
            }
            lay_name_book.error = message
        }
        viewModel.errorInputAuthor.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Ошибка в поле: автор"
            } else {
                null
            }
            lay_name_author.error = message
        }
        viewModel.errorInputPrice.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Ошибка в поле: цена"
            } else {
                null
            }
            lay_book_price.error = message
        }
        viewModel.closeScreen.observe(viewLifecycleOwner) {
           onToastingListener.onToasting()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            ADD_MODE -> addModeStart()
            EDIT_MODE -> editModeStart()
        }
    }

    private fun editModeStart() {
        viewModel.getBookItem(bookItemId)
        viewModel.bookItem.observe(viewLifecycleOwner) {
            et_name_book.setText(it.name)
            et_name_author.setText(it.author)
            et_book_price.setText(it.price.toString())
        }
        btnSave.setOnClickListener {
            viewModel.editBookItem(
                et_name_author.text?.toString(),
                et_name_book.text?.toString(),
                et_book_price.text?.toString()
            )
        }
    }

    private fun addModeStart() {
        btnSave.setOnClickListener {
            viewModel.addBookItem(
                et_name_author.text?.toString(),
                et_name_book.text?.toString(),
                et_book_price.text?.toString()
            )
        }
    }

    private fun textErrorChangeListener() {
        et_name_book.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        et_name_author.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorAuthor()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        et_book_price.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorPrice()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun parseParams() {
val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!args.containsKey(BOOK_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            bookItemId = args.getInt(BOOK_ITEM_ID, BookItem.START_ID)
        }
    }
    interface OnToastingListener{
         fun onToasting()
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val BOOK_ITEM_ID = "shop_id"
        private const val UNKNOWN_MODE = ""

        fun instanceAddItem(): BookItemFragment {

            return BookItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, ADD_MODE)
                }
            }
        }

        fun instanceEditItem(bookId: Int): BookItemFragment {
            return BookItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, EDIT_MODE)
                    putInt(BOOK_ITEM_ID, bookId)
                }
            }
        }
    }
}