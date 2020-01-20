package com.esh1n.guidtoarchapp.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.data.Word
import com.esh1n.guidtoarchapp.presentation.utils.addFragmentToStack

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private lateinit var wordViewModel: WordViewModel

    private var adapter: WordListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = WordListAdapter(requireActivity(), this::openArticlesByCategory)
        val dividerItemDecoration = DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

//        fab.setOnClickListener {
//            val intent = Intent(requireActivity(), NewWordActivity::class.java)
//            startActivityForResult(intent, newWordActivityRequestCode)
//        }
        observeData()
    }

    private fun observeData() {
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter?.setWords(it) }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val word = Word(it.getStringExtra(NewWordActivity.EXTRA_REPLY), "human")
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                requireActivity(),
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openArticlesByCategory(word: Word) {
        val art = getArticlesByCategory(word.word)
        fragmentManager.addFragmentToStack(ArticlesByCategoryFragment.newInstance(art))
    }

    private fun getArticlesByCategory(category: String): ArrayList<String> {
        return when (category) {
            "Медицина" -> arrayListOf("Перелом", "Ожег", "Купола")
            "Быт" -> arrayListOf("Духо", "Скрепность", "Рик и Морти")
            else -> arrayListOf("logovo", "elfov")
        }
    }

    companion object {
        const val newWordActivityRequestCode = 1
        fun newInstance(): CategoriesFragment {
            return CategoriesFragment()
        }
    }

}