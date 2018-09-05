package co.smilers.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.smilers.R;
import co.smilers.databinding.ConfirmBinding;

public class DialogConfirmFragment extends DialogFragment {
    public static final String TAG = "CustomDialogFragment";

    private static final double DIALOG_WINDOW_WIDTH = 0.85;

    private OnButtonClickDialogListener mDialogListener;

    ConfirmBinding binding;
    public TextView text;

    /**
     * Crea una nueva instancia de un objeto CustomDialogFragment.
     *
     * @param dialogListener Interface para enviar los eventos de click en los botones de Aceptar y Cancelar.
     * @return Una nueva instancia CustomDialogFragment.
     */
    public static DialogConfirmFragment newInstance(OnButtonClickDialogListener dialogListener) {
        DialogConfirmFragment frag = new DialogConfirmFragment();
        frag.mDialogListener = dialogListener;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.confirm, null, false);
        text  = binding.textviewConfirm;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imageviewYes.setOnClickListener(view12 -> {
            if (mDialogListener != null) {
                mDialogListener.onAcceptClick(DialogConfirmFragment.this);
            }
            closeDialog();
        });

        binding.imageviewNo.setOnClickListener(view1 -> {
            if (mDialogListener != null) {
                mDialogListener.onCancelClick(DialogConfirmFragment.this);
            }
            closeDialog();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        setDialogWindowWidth(DIALOG_WINDOW_WIDTH);
    }

    private void setDialogWindowWidth(double width) {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display;
        if (window != null) {
            display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);
            int maxWidth = size.x;
            window.setLayout((int) (maxWidth * width), WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
    }

    public void closeDialog() {
        if (getDialog().isShowing()) {
            closeKeyboard();
            getDialog().dismiss();
        }
    }

    protected void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                getActivity().findViewById(android.R.id.content).getWindowToken(), 0);
    }

    public interface OnButtonClickDialogListener {
        void onAcceptClick(DialogConfirmFragment dialogFragment);

        void onCancelClick(DialogConfirmFragment dialogFragment);
    }
}
