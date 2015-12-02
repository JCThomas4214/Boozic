package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.CameraActivity;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class DialogHandler {

    MainActivity m;
    ProductActivity p;

    String volumeMeasure = "";

    public void onCreate() {}

    public DialogHandler(MainActivity m) {
        this.m = m;
    }

    public DialogHandler(ProductActivity p) {
        this.p = p;
    }

    public void OpenFeedbackDialog() {

        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Send us feedback")
                .inputRange(5, 250, m.getColorPrimary())
                .inputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_CLASS_TEXT)
                .input("What can we improve upon?", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        m.FC.sendFeedback(input.toString());
                    }
                })
                .positiveText("SEND")
                .negativeText("CANCEL")
                .widgetColor(m.getColorAccent())
                .positiveColor(m.getColorAccent())
                .negativeColor(m.getColorAccent())
                .build();

        EditText input = dialog.getInputEditText();
        input.setSingleLine(false);
        input.setVerticalScrollBarEnabled(true);
        input.setBackground(null);
        input.setLines(5);
        input.setGravity(Gravity.TOP);

        dialog.show();
    }

    public void OpenLegalDialogOnStart() {

        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Exclusion of Liability")
                .content(R.string.legal_liability)
                .positiveText("AGREE")
                .negativeText("DISAGREE")
                .positiveColor(m.getColorAccent())
                .negativeColor(m.getColorAccent())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        m.setLegal();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        m.finish();
                    }
                })
                .build();

        dialog.setCancelable(false);
        dialog.show();
    }

    public void OpenLegalDialog() {

        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Legal and Liability")
                .content(R.string.legal_liability)
                .positiveText("OK")
                .positiveColor(m.getColorAccent())
                .build();

        dialog.setCancelable(false);
        dialog.show();
    }

    public void verifyUPC(final String upc) {

        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Verify Product UPC")
                .customView(R.layout.verify_upc, true)
                .positiveText("OK")
                .negativeText("CANCEL")
                .neutralText("RESCAN")
                .positiveColor(m.getColorAccent())
                .negativeColor(m.getColorAccent())
                .neutralColor(m.getColorAccent())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        verifyProductLabel(upc);
                        //start new product label
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent i = new Intent(m, CameraActivity.class);
                        m.startActivityForResult(i, m.SCANNER_CODE_REQUEST);
                    }
                })
                .build();

        View view = dialog.getCustomView();

        TextView text = (TextView)view.findViewById(R.id.product_upc);
        text.setText(upc);

        dialog.show();
    }

    public void verifyProductLabel(final String upc) {
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Input Product Label")
                .inputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_CLASS_TEXT)
                .input("Help keep our products correct", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        String text = input.toString();
                        if (!text.equals("")) {
                            verifyProductParentType(input.toString(), upc);
                        } else {
                            verifyProductLabel(upc);
                        }
                    }
                })
                .positiveText("NEXT")
                .negativeText("CANCEL")
                .widgetColor(m.getColorAccent())
                .positiveColor(m.getColorAccent())
                .negativeColor(m.getColorAccent())
                .build();

        EditText input = dialog.getInputEditText();
        input.setSingleLine(true);
        input.setVerticalScrollBarEnabled(true);
        input.setBackground(null);
        input.setLines(1);
        input.setGravity(Gravity.TOP);

        dialog.show();
    }

    public void verifyProductParentType(final String label, final String upc) {
        CharSequence[] items = {"Wine", "Beer/Mix Drinks", "Liquor"};
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Select Product Parent Type")
                .items(items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (text == null) verifyProductParentType(label, upc);
                        else {
                            //call new product controller
                            m.PTLC.getList(m, which + 1, label, upc);
                        }
                        return true;
                    }
                })
                .positiveText("NEXT")
                .negativeText("CANCEL")
                .widgetColor(m.getColorAccent())
                .positiveColor(m.getColorAccent())
                .negativeColor(m.getColorAccent())
                .build();

        dialog.show();
    }

    public void verifyProductType(final List<String> productTypeName, final List<Integer> productTypeID, final int parentType,
                                  final String label, final String upc) {

        final Integer[] productID = productTypeID.toArray(new Integer[productTypeID.size()]);
        final CharSequence[] productName = productTypeName.toArray(new CharSequence[productTypeName.size()]);

        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Select Product Type")
                .items(productName)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (text == null)
                            verifyProductType(productTypeName, productTypeID, parentType, label, upc);
                        else if (parentType != 2) {
                            verifyAbv(parentType, label, upc, productID[which], "bottle", 1);
                        } else {
                            verifyContainerType(parentType, label, upc, productID[which]);
                        }
                        return true;
                    }
                })
                .positiveText("NEXT")
                .negativeText("CANCEL")
                .widgetColor(m.getColorAccent())
                .positiveColor(m.getColorAccent())
                .negativeColor(m.getColorAccent())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //save selected
                    }
                })
                .build();

        dialog.show();

    }

    public void verifyContainerType(final int parentType, final String label, final String upc,
                                    final int type) {

        CharSequence[] items = {"bottle","can"};
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Select Container")
                .items(items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (text == null) verifyContainerType(parentType, label, upc, type);
                        else {
                            verifyContainerQty(parentType, label, upc, type, text.toString());
                        }
                        return true;
                    }
                })
                .positiveText("NEXT")
                .negativeText("CANCEL")
                .widgetColor(m.getColorAccent())
                .positiveColor(m.getColorAccent())
                .negativeColor(m.getColorAccent())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //save selected
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                })
                .build();

        dialog.show();
    }

    public void verifyContainerQty(final int parentType, final String label, final String upc,
                             final int type, final String containerType) {

        CharSequence[] items = {"1", "4", "6", "8", "12", "18", "24"};
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Container Quantity for purchase")
                .items(items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (text == null) verifyContainerQty(parentType, label, upc, type, containerType);
                        else {
                            int tmp = changeToInt((String) text);
                            verifyAbv(parentType, label, upc, type, containerType, tmp);
                        }
                        return true;
                    }
                })
                .positiveText("NEXT")
                .negativeText("CANCEL")
                .widgetColor(m.getColorAccent())
                .positiveColor(m.getColorAccent())
                .negativeColor(m.getColorAccent())
                .build();

        dialog.show();
    }



    public void verifyAbv(final int parentType, final String label, final String upc,
                          final int type, final String containerType, final int containerQty) {

        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Input ABV")
                .customView(R.layout.input_abv, true)
                .positiveText("NEXT")
                .negativeText("CANCEL")
                .neutralText("SKIP")
                .positiveColor(m.getColorAccent())
                .negativeColor(m.getColorAccent())
                .neutralColor(m.getColorAccent())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        View view = dialog.getCustomView();

                        EditText percent = (EditText) view.findViewById(R.id.abv_dia_input);
                        String text = percent.getText().toString();
                        if (!text.equals("")) {
                            double abv = changeToDouble(percent.getText().toString().replace("%", ""));
                            verifyVolume(parentType, label, upc, type, containerType, containerQty, abv);
                        } else {
                            verifyAbv(parentType, label, upc, type, containerType, containerQty);
                        }

                    }
                }).onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        verifyVolume(parentType, label, upc, type, containerType, containerQty, -1.0);

                    }
                })
                .build();

        View view = dialog.getCustomView();
        EditText input = (EditText) view.findViewById(R.id.abv_dia_input);
        input.addTextChangedListener(makeTextWatcher(input, "%"));

        dialog.show();
    }

    public void verifyVolume(final int parentType, final String label, final String upc,
                            final int type, final String containerType, final int containerQty,
                             final double abv) {

        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Input Bottle Volume")
                .customView(R.layout.input_abv, true)
                .positiveText("FINISH")
                .negativeText("CANCEL")
                .widgetColor(m.getColorAccent())
                .positiveColor(m.getColorAccent())
                .negativeColor(m.getColorAccent())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        View view = dialog.getCustomView();

                        EditText percent = (EditText) view.findViewById(R.id.abv_dia_input);
                        String text = percent.getText().toString();
                        if (text.equals("")) text = null;

                        if (text != null) {
                            double volume = changeToDouble(percent.getText().toString().replaceAll("[oz,L,ml]", ""));
                            String volMeas = volumeMeasure;

                            if (volume >= 1000) {
                                volume = volume / (double) 1000;
                                volMeas = "L";
                            }
                            double totalVolume = containerQty * volume;

                            m.NPC.newProduct(m, label, upc, type, containerType, containerQty, totalVolume, volMeas, abv);
                        } else {
                            verifyVolume(parentType, label, upc, type, containerType, containerQty, abv);
                        }
                    }
                })
                .build();

        View view = dialog.getCustomView();
        EditText input = (EditText) view.findViewById(R.id.abv_dia_input);
        input.setHint("new Volume");
        switch (parentType) {
            case 1:
                volumeMeasure = "ml";
                setMaxLength(input, 9);
                input.addTextChangedListener(makeTextWatcher(input, "ml"));
                break;
            case 3:
                volumeMeasure = "ml";
                setMaxLength(input, 9);
                input.addTextChangedListener(makeTextWatcher(input, "ml"));
                break;
            default:
                volumeMeasure = "oz";
                input.addTextChangedListener(makeTextWatcher(input, "oz"));
                break;

        }

        dialog.show();
    }

    public void OpenRangeDialog(String title, final String units) {
        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title(title)
                .customView(R.layout.custom_range, true)
                .positiveText("SET")
                .positiveColor(m.getColorAccent())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        View view = dialog.getCustomView();

                        EditText low_input = (EditText) view.findViewById(R.id.range_low_input);
                        String low = low_input.getText().toString();

                        EditText high_input = (EditText) view.findViewById(R.id.range_high_input);
                        String high = high_input.getText().toString();

                        checkDialog(units, low, high);
                    }
                })

                .build();

        View view = dialog.getCustomView();

        EditText low_input = (EditText) view.findViewById(R.id.range_low_input);
        EditText high_input = (EditText) view.findViewById(R.id.range_high_input);

        setUnits(units, low_input, high_input, m);

        if (units.equals("$")) {
            low_input.addTextChangedListener(makeTextWatcher(low_input, units));
            high_input.addTextChangedListener(makeTextWatcher(high_input, units));
        } else if (units.equals("%")) {
            low_input.addTextChangedListener(makeTextWatcher(low_input, units));
            high_input.addTextChangedListener(makeTextWatcher(high_input, units));
        } else {
            low_input.addTextChangedListener(makeTextWatcher(low_input, units));
            high_input.addTextChangedListener(makeTextWatcher(high_input, units));
        }

        dialog.show();
    }

    public void OpenCustomMileDialog() {
        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Custom Mile Radius")
                .customView(R.layout.custom_mi, true)
                .positiveText("SET")
                .positiveColor(m.getColorAccent())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        View view = dialog.getCustomView();

                        EditText miles = (EditText) view.findViewById(R.id.custom_mi_input);
                        String radius = miles.getText().toString();

                        checkDialog(radius);
                    }
                })
                .build();

        View view = dialog.getCustomView();
        EditText miles = (EditText) view.findViewById(R.id.custom_mi_input);
        setMiles(m, miles);

        dialog.show();
    }

    public void startFlagDialog() {
        CharSequence[] items = new CharSequence[] {"Not an Alcohol Product", "Product Name is Incorrect",
                "Closest Price is unreasonable", "Cheapest Price is unreasonable"};
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Flag Product Information")
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        p.FPcon.flagProduct(p, which + 1);

                        if (which == 1) UpdateProductLabel();
                    }
                })
                .build();

        dialog.show();
    }

    public void StartProductInfoDialog() {
        CharSequence[] items;
        if (p.model.typePic == 2 || p.updatedModel.parentType == 2) items = new CharSequence[] {"Type of Product", "Volume", "Alcohol by Volume", "Product Container"};
        else items = new CharSequence[] {"Type of Product", "Volume", "Alcohol by Volume"};

        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("What must Change?")
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                UpdateProductParentType();
                                break;
                            case 1:
                                UpdateVolume();
                                break;
                            case 2:
                                UpdateAbv();
                                break;
                            case 3:
                                UpdateContainer();
                                break;
                        }
                    }
                })
                .build();

        dialog.show();
    }

    public void UpdateProductParentType() {
        CharSequence[] items = {"Wine", "Beer", "Liquor", "Mix Drinks"};
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Select Product Parent Type")
                .items(items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Log.v("TYPE", "string = " + text);
                        if (text == null) UpdateProductParentType();
                        else {
                            if (which != 3) {
                                p.updatedModel.updateParentType(which+1);
                                p.mAdapter.changeParentType(which+1);
                                p.mAdapter.notifyDataSetChanged();
                                p.PTLC.getList(p, which + 1);
                            }
                        }
                        return true;
                    }
                })
                .positiveText("OK")
                .widgetColor(p.getAccentColor())
                .positiveColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //save selected
                    }
                })
                .build();

        dialog.show();
    }

    public void UpdateProductType(List<String> productTypeName, List<Integer> productTypeID) {

        final Integer[] productID = productTypeID.toArray(new Integer[productTypeID.size()]);
        final CharSequence[] productName = productTypeName.toArray(new CharSequence[productTypeName.size()]);

        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Select Product Type")
                .items(productName)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        p.updatedModel.updateType(productID[which]);
                        return true;
                    }
                })
                .positiveText("SET")
                .widgetColor(p.getAccentColor())
                .positiveColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //save selected
                    }
                })
                .build();

        dialog.show();
    }

    public void UpdateVolume() {
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Input Bottle Volume")
                .customView(R.layout.input_abv, true)
                .positiveText("SET")
                .negativeText("CANCEL")
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .neutralColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        View view = dialog.getCustomView();

                        EditText percent = (EditText) view.findViewById(R.id.abv_dia_input);
                        String text = percent.getText().toString();
                        if (text.equals("")) text = null;

                        if (text != null) {
                            p.updatedModel.updateVolume(changeToDouble(percent.getText().toString().replaceAll("[oz,L,ml]", "")), p.model.containerQuantity, p.model.typePic);
                            p.mAdapter.changeVolume(p.updatedModel.volume, p.updatedModel.volumeMeasure);
                            p.mAdapter.notifyDataSetChanged();
                        } else {
                            UpdateVolume();
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                })
                .build();

        View view = dialog.getCustomView();
        EditText input = (EditText) view.findViewById(R.id.abv_dia_input);
        input.setHint("new Volume");
        switch (p.model.typePic) {
            case 1:
                setMaxLength(input, 9);
                input.addTextChangedListener(makeTextWatcher(input, "ml"));
                break;
            case 3:
                setMaxLength(input, 9);
                input.addTextChangedListener(makeTextWatcher(input, "ml"));
                break;
            default:
                input.addTextChangedListener(makeTextWatcher(input, "oz"));
        }

        dialog.show();
    }

    public void UpdateContainer() {

        CharSequence[] items = {"bottle","can"};
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Select Container")
                .items(items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Log.v("CONTAINER", "string = " + text);
                        if (text == null) UpdateContainer();
                        else {
                            if (!p.model.containerType.equals(text))
                                p.updatedModel.updateContainerType((String) text);
                            p.mAdapter.changeContainerType((String) text);
                            p.mAdapter.notifyDataSetChanged();
                            UpdateContainerQuant();
                        }
                        return true;
                    }
                })
                .positiveText("NEXT")
                .negativeText("CANCEL")
                .widgetColor(p.getAccentColor())
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //save selected
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                })
                .build();

        dialog.show();
    }

    public void UpdateContainerQuant() {

        CharSequence[] items = {"1", "4", "6", "8", "12", "18", "24"};
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Container Quantity for purchase")
                .items(items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Log.v("CONTAINER", "string = " + text);
                        if (text == null) UpdateContainer();
                        else {
                            int tmp = changeToInt((String) text);
                            if (p.model.containerQuantity != tmp) {
                                p.updatedModel.updateContainerQuant(tmp, p.model.volume, p.model.containerQuantity);
                                p.mAdapter.changeContainerQty(tmp);
                                p.mAdapter.notifyDataSetChanged();
                            }
                        }
                        return true;
                    }
                })
                .positiveText("SET")
                .negativeText("CANCEL")
                .widgetColor(p.getAccentColor())
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .neutralColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //save selected
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                })
                .build();

        dialog.show();
    }

    public void UpdateAbv() {

        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Input ABV")
                .customView(R.layout.input_abv, true)
                .positiveText("SET")
                .negativeText("CANCEL")
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        View view = dialog.getCustomView();

                        EditText percent = (EditText) view.findViewById(R.id.abv_dia_input);
                        String text = percent.getText().toString();
                        if (text.equals("")) text = null;

                        if (text != null) {
                            p.updatedModel.updateABV(changeToDouble(percent.getText().toString().replace("%", "")));
                            p.mAdapter.changeABV(changeToDouble(percent.getText().toString().replace("%", "")));
                            p.mAdapter.notifyDataSetChanged();
                        } else {
                            UpdateAbv();
                        }
                    }
                })
                .build();

        View view = dialog.getCustomView();
        EditText input = (EditText) view.findViewById(R.id.abv_dia_input);
        input.addTextChangedListener(makeTextWatcher(input, "%"));

        dialog.show();
    }

    public void UpdateStore() {

        final Integer[] storeIDs = p.storeIDs.toArray(new Integer[p.storeIDs.size()]);
        final CharSequence[] stores = p.stores.toArray(new CharSequence[p.stores.size()]);

        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Select Store")
                .items(stores)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (text == null) UpdateStore();
                        else {
                            p.updatedModel.updateStore((String) stores[which], storeIDs[which]);
                        }
                        return true;
                    }
                })
                .positiveText("NEXT")
                .negativeText("CANCEL")
                .neutralText("NOT FOUND")
                .widgetColor(p.getAccentColor())
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .neutralColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdatePrice();
                        //save selected
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //TODO: places and distance API
                    }
                })
                .build();

        dialog.setCancelable(false);
        dialog.show();
    }

    public void UpdatePrice() {
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Update Price")
                .customView(R.layout.input_price, true)
                .positiveText("DONE")
                .negativeText("BACK")
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .neutralColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View view = dialog.getCustomView();
                        EditText price = (EditText) view.findViewById(R.id.price_dia_input);

                        if (!price.getText().toString().isEmpty()) {
                            p.updatedModel.updateStorePrice(changeToDouble(price.getText().toString().replace("$", "")));
                            p.RPC.refreshProduct(p);
                        } else UpdatePrice();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdateStore();
                    }
                })
                .build();

        View view = dialog.getCustomView();
        final EditText price = (EditText) view.findViewById(R.id.price_dia_input);
        price.addTextChangedListener(makeTextWatcher(price, "$"));

        dialog.setCancelable(false);
        dialog.show();
    }

    public MaterialDialog progressDialog(MainActivity m, String title, String content) {
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title(title)
                .content(content)
                .progress(true, 0)
                .widgetColor(m.getColorAccent())
                .build();

        dialog.show();
        return dialog;
    }

    public MaterialDialog progressDialog(ProductActivity p, String title, String content) {
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title(title)
                .content(content)
                .progress(true, 0)
                .widgetColor(p.getAccentColor())
                .build();

        dialog.show();
        return dialog;
    }

    public void UpdateProductLabel() {
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Correct Product Label")
                .inputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_CLASS_TEXT)
                .input("Help keep our products correct", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        String text = input.toString();
                        if (!text.equals("")) {
                            p.updatedModel.updateLabel(input.toString());
                        } else {
                            UpdateProductLabel();
                        }
                    }
                })
                .positiveText("SET")
                .negativeText("CANCEL")
                .widgetColor(p.getAccentColor())
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .build();

        EditText input = dialog.getInputEditText();
        input.setSingleLine(true);
        input.setVerticalScrollBarEnabled(true);
        input.setBackground(null);
        input.setLines(1);
        input.setGravity(Gravity.TOP);

        dialog.show();
    }

    private TextWatcher makeTextWatcher(final EditText text, final String units) {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            private String current = "";
            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(current)){
                    text.removeTextChangedListener(this);

                    String cleanString;
                    String formatted;

                    if (units.equals("$")) {
                        cleanString = s.toString().replaceAll("[oz,L,ml, avg,$,%,.]", "");
                        BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
                        formatted = NumberFormat.getCurrencyInstance().format(parsed);
                        current = formatted;
                        text.setText(formatted);
                        text.setSelection(current.length());
                    }
                    else if (units.equals("%")) {
                        cleanString = s.toString().replaceAll("[oz,L,ml, avg,$,%,.]", "");
                        BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setMinimumFractionDigits(2);
                        formatted = nf.format(parsed);
                        current = formatted;
                        text.setText(formatted + "%");
                        text.setSelection(current.length());
                    } else if (units.equals("avg")){
                        cleanString = s.toString().replaceAll("[oz,L,ml, avg,$,%,.]", "");
                        Integer parsed;
                        if (cleanString.isEmpty()) parsed = new Integer("0");
                        else parsed = new Integer(cleanString);

                        if (parsed % 10 >= 5) parsed = 5;
                        else parsed = parsed % 10;

                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setMinimumIntegerDigits(1);
                        formatted = nf.format(parsed);
                        current = formatted;
                        text.setText(formatted + " avg");
                        text.setSelection(current.length());
                    } else if (units.equals("ml")) {
                        cleanString = s.toString().replaceAll("[oz,L,ml, avg,$,%,.]", "");
                        if (cleanString.isEmpty()) cleanString = "0.0";
                        BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(10), BigDecimal.ROUND_FLOOR);
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setMinimumFractionDigits(1);
                        formatted = nf.format(parsed);
                        current = formatted;
                        text.setText(formatted + "ml");
                        text.setSelection(current.length());
                    } else if (units.equals("L")) {
                        /*if (text.length() <= 3 || text.getText().toString().charAt(0) == '0') {
                            cleanString = s.toString().replaceAll("[oz,L,ml, avg,$,%,.]", "");
                            if (cleanString.isEmpty()) cleanString = "0.0";
                            BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(10), BigDecimal.ROUND_FLOOR);
                            NumberFormat nf = NumberFormat.getNumberInstance();
                            nf.setMinimumFractionDigits(1);
                            formatted = nf.format(parsed);
                            current = formatted;
                            text.setText(formatted + "L");
                            text.setSelection(current.length());
                        }*/
                        cleanString = s.toString().replaceAll("[oz,L,ml, avg,$,%,.]", "");
                        if (cleanString.isEmpty()) cleanString = "0.0";
                        BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(10), BigDecimal.ROUND_FLOOR);
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setMinimumFractionDigits(1);
                        formatted = nf.format(parsed);
                        current = formatted;
                        text.setText(formatted + "ml");
                        text.setSelection(current.length());
                    } else if (units.equals("oz")) {
                        cleanString = s.toString().replaceAll("[oz,L,ml, avg,$,%,.]", "");
                        if (cleanString.isEmpty()) cleanString = "0.0";
                        BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(10), BigDecimal.ROUND_FLOOR);
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setMinimumFractionDigits(1);
                        formatted = nf.format(parsed);
                        current = formatted;
                        text.setText(formatted + "oz");
                        text.setSelection(current.length());
                    }

                    text.addTextChangedListener(this);
                }
            }
        };

        return tw;
    }
    private void setMaxLength(EditText input, int maxLength) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
        input.setFilters(FilterArray);
    }

    private void setMiles(MainActivity m, EditText miles) {
        miles.setText(m.FMHandle.custommi_miles + "");
    }

    private void setUnits(String units, EditText low_input, EditText high_input, MainActivity m) {

        DecimalFormat formatter = new DecimalFormat("#0.00");

        switch (units) {
            case "$":
                low_input.setText("$" + formatter.format(m.FMHandle.pricerange_low));
                high_input.setText("$" + formatter.format(m.FMHandle.pricerange_high));
                break;
            case "%":
                low_input.setText(formatter.format(m.FMHandle.contentrange_low) + "%");
                high_input.setText(formatter.format(m.FMHandle.contentrange_high) + "%");
                break;
            case "avg":
                low_input.setText(m.FMHandle.ratingrange_low + " avg");
                high_input.setText(m.FMHandle.ratingrange_high + " avg");
                break;
        }
    }

    private void checkDialog(String radius) {
        //set the filterbutton model's low/high variables
        m.FMHandle.setCustommi(changeToInt(radius));
    }

    private void checkDialog(String units, String low, String high) {

        int lowInt;
        int highInt;
        int tmpInt;
        double lowDouble;
        double highDouble;
        double tmpDouble;

        //set the filterbutton model's low/high variables
        switch (units) {
            case "$":
                lowDouble = changeToDouble(low.replace("$", ""));
                highDouble = changeToDouble(high.replace("$", ""));
                //if user fucks up the input...
                if (highDouble < lowDouble) {
                    tmpDouble = lowDouble;
                    lowDouble = highDouble;
                    highDouble = tmpDouble;
                }
                m.FMHandle.setPriceRange((float)lowDouble, (float)highDouble);
                break;
            case "%":
                lowDouble = changeToDouble(low.replace("%", ""));
                highDouble = changeToDouble(high.replace("%", ""));
                //if user fucks up the input...
                if (highDouble < lowDouble) {
                    tmpDouble = lowDouble;
                    lowDouble = highDouble;
                    highDouble = tmpDouble;
                }
                m.FMHandle.setContentRange((float)lowDouble, (float)highDouble);
                break;
            case "avg":
                lowInt = changeToInt(low.replace(" avg",""));
                highInt = changeToInt(high.replace(" avg",""));
                //if user fucks up the input...
                if (highInt < lowInt) {
                    tmpInt = lowInt;
                    lowInt = highInt;
                    highInt = tmpInt;
                }
                m.FMHandle.setRatingRange(lowInt, highInt);
                break;
        }
    }

    private CharSequence[] appendCharSequences(CharSequence[] stores, CharSequence[] addresses) {
        CharSequence[] combined = new CharSequence[stores.length];

        for (int i = 0; i < stores.length; i++) {
            combined[i] = stores[i] + " at " + addresses[i];
        }
        return combined;
    }

    private int changeToInt(String str) {
        return Integer.parseInt(str);
    }
    public double changeToDouble(String str) {
        return Double.parseDouble(str);
    }
}