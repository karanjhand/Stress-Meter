package com.example.stressmeter.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.stressmeter.R

class ImageAdapter(private val context: Context, private val images: List<Int>) : BaseAdapter() {

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Any {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            // Inflate the custom layout for each item in the grid
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_image, parent, false)
            holder = ViewHolder()
            holder.imageView = view.findViewById(R.id.imageViewGridItem)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        // Set the image resource for the ImageView
        holder.imageView.setImageResource(images[position])

        return view
    }

    private class ViewHolder {
        lateinit var imageView: ImageView
    }
}
