package wangyijieholding.agendaapplication.calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import wangyijieholding.agendaapplication.R;

/**
 * Created by wangyijie6646 on 8/29/16.
 */
public class EditModeChoiceDialogFragment extends DialogFragment {

    EditModeDialogListener cListener;
    int SelectedEventIndex;

    //Interface for activities using this dialog
    public interface EditModeDialogListener{
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
        void onDialogNeutralClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        //Verify if the listener is implemented by the context
        try {
            cListener = (EditModeDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement EditModeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        //Builder is used to construct this dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_edit_mode).setPositiveButton(R.string.dialog_edit,new DialogInterface.OnClickListener(){
            public void onClick (DialogInterface dialog,int id){
                cListener.onDialogPositiveClick(EditModeChoiceDialogFragment.this);
            }
        }).setNegativeButton(R.string.dialog_delete,new DialogInterface.OnClickListener(){
            public void onClick (DialogInterface dialog,int id){
                cListener.onDialogNegativeClick(EditModeChoiceDialogFragment.this);
            }
        }).setNeutralButton(R.string.dialog_cancel,new DialogInterface.OnClickListener(){
            public void onClick (DialogInterface dialog,int id){
                cListener.onDialogNeutralClick(EditModeChoiceDialogFragment.this);
            }
        });
        return builder.create();
    }
}