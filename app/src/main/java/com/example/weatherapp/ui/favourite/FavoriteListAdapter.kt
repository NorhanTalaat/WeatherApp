package com.example.weatherapp.ui.favourite

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemSavedCityBinding
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.ui.details.DetailsActivity

class FavoriteListAdapter(private val favoriteList: ArrayList<WeatherResponse>,
                          private val context: Context?,
                          private  var favoriteViewModel:FavouirateViewModel
) : RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>()   {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int

    ): FavoriteListAdapter.FavoriteViewHolder {
        return  FavoriteViewHolder(
            ItemSavedCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun getItemCount(): Int {
        return favoriteList.size
    }
    override fun onBindViewHolder(holder: FavoriteListAdapter.FavoriteViewHolder, position: Int) {

        holder.binding.tvCityNameSearch.text=favoriteList[position].timezone



        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("timezone",favoriteList[position].timezone)
            context?.startActivity(intent)
        })

        holder.itemView.setOnLongClickListener{
            deleteAlarm(favoriteList[position],position)
            true
        }
    }
    fun deleteAlarm(Weather:WeatherResponse, position: Int) {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(R.string.app_name)
        builder.setMessage(R.string.dialogMessage)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton(R.string.yes){dialogInterface, which ->
            favoriteViewModel.deleteData(favoriteList[position].timezone)
            favoriteList.remove(favoriteList[position])
            notifyItemRemoved(position)
            update(favoriteList)
        }

        builder.setNeutralButton(R.string.no){dialogInterface, which ->

        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun update(newFavoriteList: List<WeatherResponse>) {
        favoriteList.clear()
        favoriteList.addAll(newFavoriteList)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder constructor(val binding: ItemSavedCityBinding) :
        RecyclerView.ViewHolder(binding.root)

}