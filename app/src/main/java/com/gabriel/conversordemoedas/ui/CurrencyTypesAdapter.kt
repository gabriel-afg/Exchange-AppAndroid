package com.gabriel.conversordemoedas.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import coil3.load
import coil3.svg.SvgDecoder
import com.gabriel.conversordemoedas.databinding.ItemCurrencyTypeBinding
import com.gabriel.conversordemoedas.network.model.CurrencyType

class CurrencyTypesAdapter(
    private val currencyTypes: List<CurrencyType>
) : BaseAdapter() {
    override fun getCount(): Int {
        return currencyTypes.size
    }

    override fun getItem(p0: Int): Any? {
        return currencyTypes[p0]
    }

    override fun getItemId(p0: Int): Long {
        return currencyTypes[p0].hashCode().toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        return convertView ?: run {
            val item = currencyTypes[position]
            val binding = ItemCurrencyTypeBinding.inflate(LayoutInflater.from(parent?.context))
            with(binding) {
                tvCurrencyAcronym.text = item.acronym
                ivFlag.load(item.countryFlagImageUrl) {
                    decoderFactory { result, options, _ ->
                        SvgDecoder(result.source, options)
                    }
                }

                binding.root
            }
        }
    }
}