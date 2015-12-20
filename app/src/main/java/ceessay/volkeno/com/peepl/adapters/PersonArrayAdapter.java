package ceessay.volkeno.com.peepl.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ceessay.volkeno.com.peepl.R;
import ceessay.volkeno.com.peepl.classes.Person;
import ceessay.volkeno.com.peepl.dialogs.DetailsDialog;
import ceessay.volkeno.com.peepl.dialogs.EditDialog;
import ceessay.volkeno.com.peepl.utils.RealmUtils;
import io.realm.RealmResults;


/**
 * Created by mohamed on 15/12/15.
 */
public class PersonArrayAdapter extends ArrayAdapter<Person>  {

    private Context context;
    private List<Person> listOfPeople;
    private List<Person> listFiltered;
    PersonArrayAdapter arrayAdapter = this;



    private static class ViewHolder{
        TextView nameTextView;
        ImageButton editImageButton;
        ImageButton deleteButton;
    }

    public PersonArrayAdapter(Context context, List<Person> list) {
        super(context, 0, list);
        this.context = context;
        this.listOfPeople = new ArrayList<Person>();
        this.listOfPeople.addAll(list);
        this.listFiltered = new ArrayList<Person>();
    }

    public void refresh(RealmResults<Person> persons) {
        this.clear();
        this.addAll(persons);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.list_item_textView);
            viewHolder.editImageButton =  (ImageButton) convertView.findViewById(R.id.list_item_edit_image_button);
            viewHolder.deleteButton = (ImageButton) convertView.findViewById(R.id.list_item_delete_image_button);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final Person p = listOfPeople.get(position);
        String s = p.getSurname() +" "+p.getName();
        viewHolder.nameTextView.setText(s);

        //gerer le click sur listview item
        viewHolder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsDialog detailsDialog = new DetailsDialog(context, p);
                detailsDialog.show();
            }
        });


        //gerer le click sur l'imagebutton d'edition
        viewHolder.editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog editDialog = new EditDialog(context, p, arrayAdapter);
                editDialog.show();

            }
        });

        //gerer le click sur l'imagebutton de supression
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog(context, p);

            }
        });

        return convertView;
    }



    private void deleteDialog(Context context, final Person p)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle("Your Title");

        // set dialog message
        alertDialogBuilder
                .setMessage(context.getResources().getString(R.string.delete_confirm))
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.delete),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        listOfPeople.remove(p);
                        RealmUtils.deletePerson(p);
                        refresh(RealmUtils.getAllPerson());

                    }
                })
                .setNegativeButton(context.getResources().getString(R.string.cancel),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
}
