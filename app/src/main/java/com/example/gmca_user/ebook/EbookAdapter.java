package com.example.gmca_user.ebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmca_user.R;

import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewAdapter> {
    private Context context;
    private List<EbookData> list;

    public EbookAdapter(Context context, List<EbookData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EbookViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ebook_item,parent,false);



        return new EbookViewAdapter(view);
    }


    @Override

    public void onBindViewHolder(@NonNull EbookViewAdapter holder, @SuppressLint("RecyclerView") final int position) {
        EbookData ebookData = list.get(position);
        holder.ebookName.setText(ebookData.getPdfTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ensure the position is within the bounds of the list
                if (position >= 0 && position < list.size()) {
                    EbookData clickedItem = list.get(position);
                    if (clickedItem != null) {
                        Intent intent = new Intent(context, PDFViewer.class);

                        intent.putExtra("pdfUrl",list.get(position).getPdfUrl());
                        intent.putExtra("pdfTitle",list.get(position).getPdfTitle());
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Invalid item clicked", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EbookViewAdapter extends RecyclerView.ViewHolder {

        private TextView ebookName;

        public EbookViewAdapter(@NonNull View itemView) {
            super(itemView);
            ebookName= itemView.findViewById(R.id.ebookName);
        }
    }
}
