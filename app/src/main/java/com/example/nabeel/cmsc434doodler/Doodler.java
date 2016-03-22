package com.example.nabeel.cmsc434doodler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;

public class Doodler extends AppCompatActivity {

    final int COLOR_ITEM_POSITION = 0;
    final int WIDTH_ITEM_POSITION = 1;
    final int ERASER_ITEM_POSITION = 2;
    final int OPACITY_ITEM_POSITION = 3;

    public DoodleView doodleView;
    public ListView listView;
    private String[] listItems = {"Color", "Pen Width", "Eraser", "Opacity"};
    AlertDialog penWidthDialog;
    AlertDialog colorDialog;
    AlertDialog opacityDialog;
    ImageView undo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodler);

        doodleView = (DoodleView)findViewById(R.id.doodleView);
        listView = (ListView)findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.tools_list_text_view, listItems);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onToolsListItemClickListener);

        penWidthDialog = createPenWidthDialog();
        colorDialog = createColorDialog();
        opacityDialog = createOpacityDialog();

        undo = (ImageView)findViewById(R.id.undoIcon);
    }

    private AlertDialog createPenWidthDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(this.getLayoutInflater().inflate(R.layout.width_setting_dialog_view, null));
        builder.setTitle("Set Pen Size").setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SeekBar bar = (SeekBar) penWidthDialog.findViewById(R.id.seekBar);
                doodleView.setPaintSize(bar.getProgress());
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    private AlertDialog createColorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String [] colors = {"Orange", "Red", "Blue", "Green"};
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        doodleView.setPaintColor(Color.parseColor("#FF9800"));
                        break;
                    case 1:
                        doodleView.setPaintColor(Color.RED);
                        break;
                    case 2:
                        doodleView.setPaintColor((Color.BLUE));
                        break;
                    case 3:
                        doodleView.setPaintColor(Color.GREEN);
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.setTitle("Set Pen Color");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    private AlertDialog createOpacityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(this.getLayoutInflater().inflate(R.layout.opacity_setting_dialog_view, null));
        builder.setTitle("Set Pen Opacity").setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SeekBar bar = (SeekBar) opacityDialog.findViewById(R.id.opacityBar);
                doodleView.setPaintOpacity(bar.getProgress());
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    public void clearCanvas(View view) {
        doodleView.clearCanvas();
    }

    public void toggleList(View view) {
        if (listView.getVisibility() == View.VISIBLE) {
            listView.setVisibility(View.INVISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
        }
    }

    public AdapterView.OnItemClickListener onToolsListItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch(position) {
                case COLOR_ITEM_POSITION:
                    colorDialog.show();
                    break;
                case WIDTH_ITEM_POSITION:
                    penWidthDialog.show();
                    break;
                case ERASER_ITEM_POSITION:
                    doodleView.setPaintColor(Color.WHITE);
                    doodleView.setPaintOpacity(255);
                    toggleList(doodleView);
                    break;
                case OPACITY_ITEM_POSITION:
                    opacityDialog.show();
                    break;
                default:
                    break;
            }
        }
    };

    public void undoLast (View view) {
        doodleView.undo();
    }

}
