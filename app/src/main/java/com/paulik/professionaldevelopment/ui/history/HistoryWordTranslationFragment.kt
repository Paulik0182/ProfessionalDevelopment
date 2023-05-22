package com.paulik.professionaldevelopment.ui.history

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.paulik.models.AppState
import com.paulik.models.entity.DataEntity
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.FragmentHistoryWordTranslationBinding
import com.paulik.professionaldevelopment.ui.root.BaseFragment
import com.paulik.repository.data.HistoryWordTranslationInteractorImpl
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryWordTranslationFragment :
    BaseFragment<AppState, HistoryWordTranslationInteractorImpl>() {

    private var _binding: FragmentHistoryWordTranslationBinding? = null
    private val binding get() = _binding!!

    private var flagVisible = true

    override val viewModel: HistoryWordTranslationViewModel by viewModel()
    private val adapter: HistoryWordTranslationAdapter by lazy {
        HistoryWordTranslationAdapter(
            onListItemClickListener
        )
    }

    private val onListItemClickListener: HistoryWordTranslationAdapter.OnListItemClickListener =
        object : HistoryWordTranslationAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataEntity) {
                getController().openVariantTranslationWord(
                    word = data.text!!,
                    flagHistory = true
                )
            }

            override fun onDeleteClick(view: View, word: String) {
                showLongMenu(view, word)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_word_translation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHistoryWordTranslationBinding.bind(view)

        iniViewModel()

        initViews()

        onClickIcon()

        changingColorSearchFab(view)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataEntity>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        if (binding.historyFragmentRecyclerview.adapter != null) {
            throw IllegalStateException("Сначала должна быть инициализирована ViewModel")
        }

        viewModel.subscribe().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
    }

    private fun initViews() {
        binding.searchFab.setOnClickListener {
            actionСlickingOnSearchFab()
        }

        binding.historyFragmentRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.historyFragmentRecyclerview.adapter = adapter
    }

    private fun onClickIcon() {
        binding.inputLayout.setEndIconOnClickListener {
            val word = binding.inputEditText.text.toString()

            Toast.makeText(requireContext(), word, Toast.LENGTH_SHORT).show()

            onWhenSearchingWordList(word)
        }
    }

    private fun showLongMenu(view: View, word: String) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.delete_word_menu)
        popupMenu
            .setOnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.delete_item -> {
                        viewModel.deleteWord(word)

                        Toast.makeText(requireContext(), word, Toast.LENGTH_SHORT).show()
                        true
                    }

                    else -> false
                }
            }
        popupMenu.show()
    }

    private fun onWhenSearchingWordList(word: String?): Boolean {
        if (!word.isNullOrEmpty()) {
            viewModel.searchData(word)
        }
        return true
    }

    private fun actionСlickingOnSearchFab() {
        flagVisible = !flagVisible

        // Все должно быть из androidX. Проверять если будет ругатся
        val myAutoTransition =
            TransitionSet()
        /** состоит из нескольких параметров, поэтому TransitionSet*/

        /**  все эффекты должны запускаться одновременно */
        myAutoTransition.ordering = TransitionSet.ORDERING_TOGETHER

        /** Slide(Gravity.BOTTOM) - это анимация, которая выполняет скольжение по определенной
         * оси. Здесь Gravity.BOTTOM находится внизу, поэтому Slide-эффект будет идти снизу вверх. */
        val fade = Slide(Gravity.BOTTOM)
        fade.duration = 1_000L

        /** ChangeBounds() - это анимация изменения границы контента, которая позволяет визуально
         * изменять границы элемента при изменении размера или позиции. */
        val changeBounds = ChangeBounds()
        changeBounds.duration = 1_000L

        /** После того как изменения созданы, добавляем в объект myAutoTransition эффектами
         * addTransition так, чтобы все эффекты выполнялись одновременно на данном объекте */
        myAutoTransition.addTransition(changeBounds)
        myAutoTransition.addTransition(fade)

        /** TransitionManager.beginDelayedTransition(binding.transitionsContainer, myAutoTransition)
         * - происходит запуск анимации с эффектами, определенными в myAutoTransition,
         * на контейнер binding.transitionsContainer.*/
        TransitionManager.beginDelayedTransition(binding.transitionsContainer, myAutoTransition)

        binding.inputLayout.visibility = if (flagVisible) View.GONE else View.VISIBLE
        binding.line.visibility = if (flagVisible) View.GONE else View.VISIBLE
    }

    private fun changingColorSearchFab(view: View) {
        val searchFab: FloatingActionButton = view.findViewById(R.id.search_fab)

        searchFab.backgroundTintList =
            ColorStateList.valueOf(requireActivity().getColor(R.color.color_search_fab))

        searchFab.imageTintList =
            ColorStateList.valueOf(requireActivity().getColor(R.color.color_element_search_fab))
    }

    interface Controller {
        fun openVariantTranslationWord(
            word: String,
            flagHistory: Boolean
        )
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getController()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryWordTranslationFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}