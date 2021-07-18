package com.example.homework8

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework8.data.ExchangeRate
import com.example.homework8.data.PrivatAPI
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = GsonBuilder().create()
        val data = gson.fromJson<PrivatAPI>(privatAPI, PrivatAPI::class.java)
        val list: List<ExchangeRate> = data.exchangeRate ?: emptyList()

        val recyclerView = findViewById<RecyclerView>(R.id.recycleV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(list)

        val drawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(Color.BLACK, Color.RED, Color.YELLOW)
        )
        drawable.setSize(10, 10)

        DividerItemDecoration(
            this, // context
            (recyclerView.layoutManager as LinearLayoutManager).orientation
        ).apply {

            setDrawable(drawable);
            recyclerView.addItemDecoration(this)
        }

    }
}

class RecyclerAdapter(private val items: List<ExchangeRate>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vBaseCurrency: TextView = view.findViewById(R.id.idBaseCurrency)
        val vCurrency: TextView = view.findViewById(R.id.idCurrency)
        val vSaleRateNB: TextView = view.findViewById(R.id.idSaleRateNB)
        val vPurchaseRateNB: TextView = view.findViewById(R.id.idPurchaseRateNB)
        val vSaleRate: TextView = view.findViewById(R.id.idSaleRate)
        val vPurchaseRate: TextView = view.findViewById(R.id.idPurchaseRate)


        init {
            view.setOnClickListener {
                view.context.toast("Item $adapterPosition clicked")
            }
        }

        private fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        items[position].apply {
            viewHolder.vBaseCurrency.text = baseCurrency
            viewHolder.vCurrency.text = currency
            viewHolder.vSaleRateNB.text = saleRateNB.toString()
            viewHolder.vPurchaseRateNB.text = purchaseRateNB.toString()
            viewHolder.vSaleRate.text = saleRate.toString()
            viewHolder.vPurchaseRate.text = purchaseRate.toString()

        }
    }


}