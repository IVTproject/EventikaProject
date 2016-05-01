package corp.is3.eventikaproject.dialogframes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Set;
import java.util.TreeSet;

public class DialogSelector extends BasicDialog {

    private ListView listView;
    private SelectItemsListener selectItemsListener;
    private String[] listItems;
    private Set<String> selectedItems;
    private String title;

    public void setListItems(String[] listItems) {
        this.listItems = listItems;
    }

    public void setSelectedItems(String[] selectedItems) {
        if (this.selectedItems == null)
            this.selectedItems = new TreeSet<>();
        this.selectedItems.clear();
        if (selectedItems != null)
            for (String item : selectedItems)
                this.selectedItems.add(item);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        listView = new ListView(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_multiple_choice,
                listItems == null ? new String[]{} : listItems);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        if (selectedItems != null)
            for (int i = 0, j = 0; i < listItems.length; i++)
                if (selectedItems.contains(listItems[i]))
                    listView.getCheckedItemPositions().append(i, true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(title == null ? "" : title)
                .setView(listView)
                .setPositiveButton(android.R.string.ok, okClick())
                .setNegativeButton(android.R.string.cancel, cancelClick());

        return builder.create();
    }

    public void setSelectItemsListener(SelectItemsListener selectItemsListener) {
        this.selectItemsListener = selectItemsListener;
    }

    private boolean[] collectData() {
        if (listView == null)
            return new boolean[0];
        SparseBooleanArray chosen = listView.getCheckedItemPositions();

        boolean[] conditions;
        if (listItems != null)
            conditions = new boolean[listItems.length];
        else
            conditions = new boolean[0];
        for (int i = 0; i < conditions.length; i++)
            conditions[i] = chosen.get(i);
        return conditions;
    }

    private DialogInterface.OnClickListener okClick() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectItemsListener != null)
                    selectItemsListener.ok(collectData());
            }
        };
    }

    private DialogInterface.OnClickListener cancelClick() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectItemsListener != null)
                    selectItemsListener.cancel(collectData());
            }
        };
    }
}