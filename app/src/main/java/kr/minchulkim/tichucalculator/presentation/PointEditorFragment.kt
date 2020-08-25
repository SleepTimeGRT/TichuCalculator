package kr.minchulkim.tichucalculator.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kr.minchulkim.tichucalculator.R
import kr.minchulkim.tichucalculator.databinding.PointEditorFragmentBinding
import kr.minchulkim.tichucalculator.viewmodel.PointEditorVM
import kotlin.math.absoluteValue

@AndroidEntryPoint
class PointEditorFragment : Fragment(R.layout.point_editor_fragment) {
    private val pointEditorVM: PointEditorVM by viewModels()

    private lateinit var binding: PointEditorFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PointEditorFragmentBinding.bind(view)
        setupViews()
        observeVM()
    }

    private fun observeVM() {
        pointEditorVM.aPoint.observe(viewLifecycleOwner, Observer {
            binding.cardEditorA.point.text = "${it.first + it.second}"
            binding.cardEditorA.pointDescription.text = it.getPointDescriptionText()
        })
        pointEditorVM.bPoint.observe(viewLifecycleOwner, Observer {
            binding.cardEditorB.point.text = "${it.first + it.second}"
            binding.cardEditorB.pointDescription.text = it.getPointDescriptionText()
        })
        pointEditorVM.eventClearEditor.observe(viewLifecycleOwner, Observer{
            binding.pointSeekBar.value = 16f
        })
    }

    private fun Pair<Int, Int>.getPointDescriptionText(): String {
        return with(StringBuilder("(")) {
            append(first)
            if (second < 0) {
                append(" - ")
            } else {
                append(" + ")
            }
            append(second.absoluteValue)
            append(")")
        }.toString()
    }

    private fun setupViews() {
        binding.cardEditorA.apply {
            team.setText(R.string.team_a)
            plusPointButton.setOnClickListener {
                pointEditorVM.onClickAPlusPointButton()
            }
            minusPointButton.setOnClickListener {
                pointEditorVM.onClickAMinusPointButton()
            }
        }

        binding.cardEditorB.apply {
            team.setText(R.string.team_b)
            plusPointButton.setOnClickListener {
                pointEditorVM.onClickBPlusPointButton()
            }
            minusPointButton.setOnClickListener {
                pointEditorVM.onClickBMinusPointButton()
            }
        }

        binding.pointSeekBar.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                pointEditorVM.onProgressChanged(value.toInt())
            }
        }
        binding.saveButton.setOnClickListener {
            pointEditorVM.onClickSave()
        }
    }
}