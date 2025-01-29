package com.rpsouza.movieapp.presenter.main.bottomBar.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.ItemUserProfileBinding
import com.rpsouza.movieapp.domain.model.menuProfile.MenuProfile
import com.rpsouza.movieapp.domain.model.menuProfile.MenuProfileType

class ProfileMenuAdapter(
    private val menuItems: List<MenuProfile>,
    private val context: Context,
    private val onClick: (MenuProfileType) -> Unit
) : RecyclerView.Adapter<ProfileMenuAdapter.ProfileMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileMenuViewHolder {
        return ProfileMenuViewHolder(
            ItemUserProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    override fun onBindViewHolder(holder: ProfileMenuViewHolder, position: Int) {
        val item = menuItems[position]

        holder.binding.textItemProfile.apply {
            text = context.getString(item.name)

            if (item.type == MenuProfileType.LOGOUT) {
                setTextColor(ContextCompat.getColor(context, R.color.color_default))
            }
        }
        holder.binding.imageItemProfile.setImageResource(item.icon)
        holder.itemView.setOnClickListener { onClick(item.type) }
    }

    inner class ProfileMenuViewHolder(val binding: ItemUserProfileBinding) :
        RecyclerView.ViewHolder(binding.root)
}