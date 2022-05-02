package com.arthi.traders.controller;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.arthi.traders.R;

import com.arthi.traders.model.Request_model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    static Context context;
    private List<Request_model> rq;

    public RequestAdapter(Context context, List<Request_model> rq) {
        this.context = context;
        this.rq = rq;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String filename="";
        String names = rq.get(position).getRequests_docs();
        String[] namesList = names.split("/");
        for (String name : namesList) {
            System.out.println(name);
            filename = name;
        }

        holder.request_type.setText(rq.get(position).getRequests_description());
        holder.request_file.setText(filename);
        holder.request_from_date.setText(rq.get(position).getRequests_from());
        holder.request_to_date.setText(" -- "+rq.get(position).getRequests_to());
        holder.request_status.setText(rq.get(position).getRequests_status());
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename="";
                String names = rq.get(position).getRequests_docs();
                String[] namesList = names.split("/");
                for (String name : namesList) {
                    System.out.println(name);
                    filename = name;
                }
                downloadFile(rq.get(position).getRequests_docs(),filename);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rq.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView request_type,request_file,request_from_date,request_to_date,request_status;
        Button details;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            request_type=itemView.findViewById(R.id.request_type);
            request_file=itemView.findViewById(R.id.request_file);
            request_from_date=itemView.findViewById(R.id.request_from_date);
            request_to_date=itemView.findViewById(R.id.request_to_date);
            request_status=itemView.findViewById(R.id.request_status);
            details=itemView.findViewById(R.id.details);
        }
    }

    /// download file
    @SuppressLint("NewApi")
    public static void downloadFile(String url, String filename) {
        try {
            String DownloadUrl = url;
            Log.e("don ",DownloadUrl+"");
            Log.e("filename ",filename+"");
            String root = Environment.getExternalStorageDirectory().toString();

            DownloadManager.Request request1 = new DownloadManager.Request(Uri.parse(DownloadUrl));
            request1.setDescription("Sample Music File");   //appears the same in Notification bar while downloading
            request1.setTitle(filename);
            request1.setVisibleInDownloadsUi(false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request1.allowScanningByMediaScanner();
                request1.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            }
            request1.setDestinationInExternalFilesDir(context, "/File/", filename);

            DownloadManager manager1 = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Objects.requireNonNull(manager1).enqueue(request1);
            if (DownloadManager.STATUS_SUCCESSFUL == 8) {
                //Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                String url1 = "/storage/emulated/0/Android/"+Environment.getDataDirectory().toString()+"/"+context.getPackageName()+"/files/File/"+filename;
                Log.e("url1 ",url1.toString()+"..");
                File fil = new File(url1);
                openFile(context,fil);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openFile(Context context, File url) throws IOException {
        try {
            // Create URI
            File file=url;
            //Uri uri = Uri.fromFile(file);
            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);

            Intent intent = new Intent(Intent.ACTION_VIEW);//, FileProvider.getUriForFile(context, AUTHORITY, file));
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if(url.toString().contains(".pdf")) {
                // PDF file
                Log.e("pdf","pdf inside");
                intent.setDataAndType(uri, "application/pdf");
            } else if(url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if(url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if(url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if(url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if(url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if(url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if(url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if(url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if(url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
