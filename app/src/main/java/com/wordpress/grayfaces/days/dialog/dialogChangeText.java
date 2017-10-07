package com.wordpress.grayfaces.days.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wordpress.grayfaces.days.R;

import java.util.HashMap;

/**
 * Project LamHongDMS
 * Created by Gray on 7/4/2017.
 */

public class dialogChangeText extends DialogFragment {


    // interface to handle the dialog click back to the Activity
    public interface OnDialogFragmentClickListener {
        void onOkClicked(dialogChangeText dialog, String content);
        void onCancelClicked(dialogChangeText dialog);
    }
    OnDialogFragmentClickListener mOnDialogFrmClickListener;
    public void setOnDialogFrmCilckListener(OnDialogFragmentClickListener listener){
        mOnDialogFrmClickListener = listener;
    }
    private HashMap<String, String> user=null;
    private EditText txtContent;
    private String title;
    private String content;
    // Create an instance of the Dialog with the input
    public static dialogChangeText newInstance(String title, String content) {
        dialogChangeText frag = new dialogChangeText();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("content",content);
        /*args.putString("columnDanhMuc",columnDanhMuc);
        args.putString("tableTaiSan",columnDanhMuc);*/
        frag.setArguments(args);
        return frag;
    }
    // Create a Dialog using default AlertDialog builder , if not inflate custom view in onCreateView
    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("title"))
                .setMessage(getArguments().getString("message"))
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Positive button clicked
                                getActivityInstance().onOkClicked(dialogThemDanhMuc.this);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // negative button clicked
                                getActivityInstance().onCancelClicked(dialogThemDanhMuc.this);
                            }
                        }
                )
                .create();
    }*/
    //Create a dialog using custom layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_change_text, container, false);
        title = getArguments().getString("title");
        content = getArguments().getString("content");
        txtContent = (EditText) view.findViewById(R.id.dialog_change_text_txtContent);
        txtContent.setText(content);
        final ImageButton btnLuu = (ImageButton) view.findViewById(R.id.btnLuu);
        ImageButton btnHuy = (ImageButton) view.findViewById(R.id.btnXoa);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDanhMuc = txtContent.getText().toString();
                if (tenDanhMuc.length()==0){
                    Toast.makeText(getActivity(), R.string.not_entered_content,Toast.LENGTH_SHORT).show();
                } else {
                    //System.out.println("tendanhmuc "+ tenDanhMuc);
                    mOnDialogFrmClickListener.onOkClicked(dialogChangeText.this,txtContent.getText().toString());
                    //CapNhatDanhMuc(tenDanhMuc);

                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDialogFrmClickListener.onCancelClicked(dialogChangeText.this);
            }
        });

        return view;
    }

}
