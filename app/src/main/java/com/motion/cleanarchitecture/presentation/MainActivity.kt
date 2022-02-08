package com.motion.cleanarchitecture.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.motion.cleanarchitecture.R

class MainActivity : AppCompatActivity(),BookItemFragment.OnToastingListener {

    private lateinit var viewModel: BookListViewModel
    private lateinit var bookAdapter: BookAdapter
    private var bookItemContainer:FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookItemContainer = findViewById(R.id.book_item_container)
        setUpRecyclerView()

        viewModel = ViewModelProvider(this)[BookListViewModel::class.java]
        viewModel.getBookList.observe(this) {
            bookAdapter.submitList(it)
        }

        val btnAddElement = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        btnAddElement.setOnClickListener {
            if (isOnePaneMode()){
            val intent = DetailActivity.newIntentAddItem(this)
            startActivity(intent)
            } else{
                launchFragment(BookItemFragment.instanceAddItem())
                    }

                }
    }

    override fun onToasting() {
Toast.makeText(this,"Успешна",Toast.LENGTH_SHORT).show()
supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode():Boolean{
        return bookItemContainer == null
    }
    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().replace(R.id.book_item_container,fragment)
            .addToBackStack(null).commit()
    }

    private fun setUpRecyclerView() {
        val recyclerViewBookList = findViewById<RecyclerView>(R.id.recyclerViewBookList)
        with(recyclerViewBookList) {
            bookAdapter = BookAdapter()
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            recycledViewPool.setMaxRecycledViews(
                BookAdapter.VIEW_TYPE_STATE_ENABLED,
                BookAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                BookAdapter.VIEW_TYPE_STATE_DISABLED,
                BookAdapter.MAX_POOL_SIZE
            )
        }
        setUpLOngClickListener()
        setUpClickListener()
        setUpSwipeListener(recyclerViewBookList)

    }

    private fun setUpSwipeListener(recyclerView: RecyclerView) {
        val callBack = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = bookAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteBookItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setUpClickListener() {
        bookAdapter.onBookItemClickListener = {
            if (isOnePaneMode()){
            val intent = DetailActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
            else{
                launchFragment(BookItemFragment.instanceEditItem(it.id))
            }
        }
    }

    private fun setUpLOngClickListener() {
        bookAdapter.onBookItemLongClickListener = {
            viewModel.changeState(it)
        }
    }
}