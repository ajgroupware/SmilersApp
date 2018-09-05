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
import android.widget.ImageView;
import co.smilers.R;
import co.smilers.databinding.AlertNeedAssistanceBinding;

public class DialogAssitanceFragment extends DialogFragment {

    public static final String TAG = "CustomDialogFragment";

    private static final double DIALOG_WINDOW_WIDTH = 0.85;

    private OnButtonClickDialogListener mDialogListener;

    AlertNeedAssistanceBinding binding;

    /**
     * Crea una nueva instancia de un objeto CustomDialogFragment.
     *
     * @param dialogListener Interface para enviar los eventos de click en los botones de Aceptar y Cancelar.
     * @return Una nueva instancia CustomDialogFragment.
     */
    public static DialogAssitanceFragment newInstance(OnButtonClickDialogListener dialogListener) {
        DialogAssitanceFragment frag = new DialogAssitanceFragment();
        frag.mDialogListener = dialogListener;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE );

            getDialog().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        }
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.alert_need_assistance, null, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imageviewYes.setOnClickListener(view12 -> {
            if (mDialogListener != null) {
                mDialogListener.onAcceptClick(DialogAssitanceFragment.this);
            }
            closeDialog();
        });

        binding.imageviewNo.setOnClickListener(view1 -> {
            if (mDialogListener != null) {
                mDialogListener.onCancelClick(DialogAssitanceFragment.this);
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
        void onAcceptClick(DialogAssitanceFragment dialogFragment);

        void onCancelClick(DialogAssitanceFragment dialogFragment);
    }
}
