package com.falconssoft.gassystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.gassystem.Modle.RecCash;
import com.falconssoft.gassystem.Modle.Receipts;
import com.falconssoft.gassystem.Modle.Voucher;
import com.falconssoft.gassystem.Modle.VoucherModle;
import com.falconssoft.gassystem.Port.AlertView;
import com.sewoo.port.android.BluetoothPort;
import com.sewoo.request.android.RequestHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;


import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.falconssoft.gassystem.MakeVoucher.customer;
import static com.falconssoft.gassystem.MakeVoucher.voucherGas;
import static com.falconssoft.gassystem.Receipt.recCash;


// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)

public class BluetoothConnectMenu extends Activity {
    private static final String TAG = "BluetoothConnectMenu";
    private static final int REQUEST_ENABLE_BT = 2;
    ArrayAdapter<String> adapter;
    private BluetoothAdapter mBluetoothAdapter;
    private Vector<BluetoothDevice> remoteDevices;
    private BroadcastReceiver searchFinish;
    private BroadcastReceiver searchStart;
    private BroadcastReceiver discoveryResult;
    private BroadcastReceiver disconnectReceiver;
    private Thread hThread;
    private Context context;
    private EditText btAddrBox;
    private Button connectButton;
    private Button searchButton;
    GlobelFunction globelFunction;

    LinearLayout item;
    private ListView list;
    private BluetoothPort bluetoothPort;
    private CheckBox chkDisconnect;
    private static final String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "//temp";
    private static final String fileName;
    private String lastConnAddr;
    static String idname;

    String getData;
    String today;
    DatabaseHandler DHandler;


    DecimalFormat decimalFormat;
    DecimalFormat numberFormat = new DecimalFormat("0.00");


    static {
        fileName = dir + "//BTPrinter";
    }

    public BluetoothConnectMenu() {

    }

    private void bluetoothSetup() {
        this.clearBtDevData();
        this.bluetoothPort = BluetoothPort.getInstance();
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.mBluetoothAdapter != null) {
            if (!this.mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
                this.startActivityForResult(enableBtIntent, 2);
            }

        }
    }

    private void loadSettingFile() {
//        int rin = false;
        char[] buf = new char[128];

        try {
            FileReader fReader = new FileReader(fileName);
            int rin = fReader.read(buf);
            if (rin > 0) {
                this.lastConnAddr = new String(buf, 0, rin);
                this.btAddrBox.setText(this.lastConnAddr);
            }

            fReader.close();
        } catch (FileNotFoundException var4) {
            Log.i("BluetoothConnectMenu", "Connection history not exists.");
        } catch (IOException var5) {
            Log.e("BluetoothConnectMenu", var5.getMessage(), var5);
        }

    }

    private void saveSettingFile() {
        try {
            File tempDir = new File(dir);
            if (!tempDir.exists()) {
                tempDir.mkdir();
            }

            FileWriter fWriter = new FileWriter(fileName);
            if (this.lastConnAddr != null) {
                fWriter.write(this.lastConnAddr);
            }

            fWriter.close();
        } catch (FileNotFoundException var3) {
            Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
        } catch (IOException var4) {
            Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
        }

    }

    private void clearBtDevData() {
        this.remoteDevices = new Vector();
    }

    private void addPairedDevices() {
        Iterator iter = this.mBluetoothAdapter.getBondedDevices().iterator();

        while (iter.hasNext()) {
            BluetoothDevice pairedDevice = (BluetoothDevice) iter.next();
            if (this.bluetoothPort.isValidAddress(pairedDevice.getAddress())) {//note
                this.remoteDevices.add(pairedDevice);
                this.adapter.add(pairedDevice.getName() + "\n[" + pairedDevice.getAddress() + "] [Paired]");
            }
        }

    }

    double size_subList = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.bluetooth_menu);
        this.btAddrBox = (EditText) this.findViewById(R.id.EditTextAddressBT);
        this.connectButton = (Button) this.findViewById(R.id.ButtonConnectBT);
        BluetoothConnectMenu.this.connectButton.setEnabled(true);
        this.searchButton = (Button) this.findViewById(R.id.ButtonSearchBT);
        this.list = (ListView) this.findViewById(R.id.BtAddrListView);
        this.chkDisconnect = (CheckBox) this.findViewById(R.id.check_disconnect);
        this.chkDisconnect.setChecked(true);
        this.context = this;
        item=this.findViewById(R.id.item);
        DHandler = new DatabaseHandler(BluetoothConnectMenu.this);
        decimalFormat = new DecimalFormat("##.000");
        globelFunction=new GlobelFunction();
        globelFunction.GlobelFunctionSetting(DHandler);

        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        today = df.format(currentTimeAndDate);

//
        getData = getIntent().getStringExtra("printKey");
//        Bundle bundle = getIntent().getExtras();
//         allStudents = (List<Item>) bundle.get("ExtraData");
//
//         Log.e("all",allStudents.get(0).getBarcode());

        Log.e("printKey", "" + getData);
        this.loadSettingFile();
        this.bluetoothSetup();

        this.connectButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!BluetoothConnectMenu.this.bluetoothPort.isConnected()) {
                    try {
                        BluetoothConnectMenu.this.btConn(BluetoothConnectMenu.this.mBluetoothAdapter.getRemoteDevice(remoteDevices.get(0).getAddress()));
                    } catch (IllegalArgumentException var3) {
                        Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
                        AlertView.showAlert(var3.getMessage(), BluetoothConnectMenu.this.context);
                        return;
                    } catch (IOException var4) {
                        Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
                        AlertView.showAlert(var4.getMessage(), BluetoothConnectMenu.this.context);
                        return;
                    }
                } else {
                    BluetoothConnectMenu.this.btDisconn();
                }

            }
        });
        this.searchButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!BluetoothConnectMenu.this.mBluetoothAdapter.isDiscovering()) {
                    BluetoothConnectMenu.this.clearBtDevData();
                    BluetoothConnectMenu.this.adapter.clear();
                    BluetoothConnectMenu.this.mBluetoothAdapter.startDiscovery();
                } else {
                    BluetoothConnectMenu.this.mBluetoothAdapter.cancelDiscovery();
                }

            }
        });
        this.adapter = new ArrayAdapter(BluetoothConnectMenu.this, R.layout.cci);

        this.list.setAdapter(this.adapter);
        this.addPairedDevices();
        this.list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                BluetoothDevice btDev = (BluetoothDevice) BluetoothConnectMenu.this.remoteDevices.elementAt(arg2);

                try {
                    if (BluetoothConnectMenu.this.mBluetoothAdapter.isDiscovering()) {
                        BluetoothConnectMenu.this.mBluetoothAdapter.cancelDiscovery();
                    }

                    BluetoothConnectMenu.this.btAddrBox.setText(btDev.getAddress());
                    BluetoothConnectMenu.this.btConn(btDev);
                } catch (IOException var8) {
                    AlertView.showAlert(var8.getMessage(), BluetoothConnectMenu.this.context);
                }
            }
        });
        this.discoveryResult = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                BluetoothDevice remoteDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (remoteDevice != null) {
                    String key;
                    if (remoteDevice.getBondState() != 12) {
                        key = remoteDevice.getName() + "\n[" + remoteDevice.getAddress() + "]";
                    } else {
                        key = remoteDevice.getName() + "\n[" + remoteDevice.getAddress() + "] [Paired]";
                    }

                    if (BluetoothConnectMenu.this.bluetoothPort.isValidAddress(remoteDevice.getAddress())) {
                        BluetoothConnectMenu.this.remoteDevices.add(remoteDevice);
                        BluetoothConnectMenu.this.adapter.add(key);
                    }
                }

            }
        };
        this.registerReceiver(this.discoveryResult, new IntentFilter("android.bluetooth.device.action.FOUND"));
        this.searchStart = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                BluetoothConnectMenu.this.connectButton.setEnabled(false);
                BluetoothConnectMenu.this.btAddrBox.setEnabled(false);
//                BluetoothConnectMenu.this.searchButton.setText(BluetoothConnectMenu.this.getResources().getString(2131034114));

                BluetoothConnectMenu.this.searchButton.setText("stop ");
            }
        };
        this.registerReceiver(this.searchStart, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_STARTED"));
        this.searchFinish = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                BluetoothConnectMenu.this.connectButton.setEnabled(true);
                BluetoothConnectMenu.this.btAddrBox.setEnabled(true);
//                BluetoothConnectMenu.this.searchButton.setText(BluetoothConnectMenu.this.getResources().getString(2131034113));
                BluetoothConnectMenu.this.searchButton.setText("search");

            }
        };
        this.registerReceiver(this.searchFinish, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
        if (this.chkDisconnect.isChecked()) {
            this.disconnectReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (!"android.bluetooth.device.action.ACL_CONNECTED".equals(action) && "android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) {
                        BluetoothConnectMenu.this.DialogReconnectionOption();
                    }

                }
            };
        }
        item.setVisibility(View.GONE);
        if(remoteDevices.size()!=0) {
            coon();
        }else{
            Toast.makeText(context, "Please Connect to Bluetooth ", Toast.LENGTH_SHORT).show();
        }

    }

    public void coon(){
        if (!BluetoothConnectMenu.this.bluetoothPort.isConnected()) {
            try {
                BluetoothConnectMenu.this.btConn(BluetoothConnectMenu.this.mBluetoothAdapter.getRemoteDevice(remoteDevices.get(0).getAddress()));
            } catch (IllegalArgumentException var3) {
                Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
                AlertView.showAlert(var3.getMessage(), BluetoothConnectMenu.this.context);
                return;
            } catch (IOException var4) {
                Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
                AlertView.showAlert(var4.getMessage(), BluetoothConnectMenu.this.context);
                return;
            }
        } else {
            BluetoothConnectMenu.this.btDisconn();
        }
    }

    protected void onDestroy() {
        try {
            if (this.bluetoothPort.isConnected() && this.chkDisconnect.isChecked()) {
                this.unregisterReceiver(this.disconnectReceiver);
            }

            this.saveSettingFile();
            this.bluetoothPort.disconnect();
        } catch (IOException var2) {
            Log.e("BluetoothConnectMenu", var2.getMessage(), var2);
        } catch (InterruptedException var3) {
            Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
        }

        if (this.hThread != null && this.hThread.isAlive()) {
            this.hThread.interrupt();
            this.hThread = null;
        }

        this.unregisterReceiver(this.searchFinish);
        this.unregisterReceiver(this.searchStart);
        this.unregisterReceiver(this.discoveryResult);
        super.onDestroy();
    }

    private void DialogReconnectionOption() {
        String[] items = new String[]{"Bluetooth printer"};
        Builder builder = new Builder(this);
        builder.setTitle("connection ...");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        }).setPositiveButton("connect", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    BluetoothConnectMenu.this.btDisconn();
                    BluetoothConnectMenu.this.btConn(BluetoothConnectMenu.this.mBluetoothAdapter.getRemoteDevice(BluetoothConnectMenu.this.btAddrBox.getText().toString()));
                } catch (IllegalArgumentException var4) {
                    Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
                    AlertView.showAlert(var4.getMessage(), BluetoothConnectMenu.this.context);
                } catch (IOException var5) {
                    Log.e("BluetoothConnectMenu", var5.getMessage(), var5);
                    AlertView.showAlert(var5.getMessage(), BluetoothConnectMenu.this.context);
                }
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                BluetoothConnectMenu.this.btDisconn();
            }
        });
        builder.show();
    }

    private void btConn(BluetoothDevice btDev) throws IOException {
        (new BluetoothConnectMenu.connTask()).execute(new BluetoothDevice[]{btDev});
    }

    private void btDisconn() {
        try {
            this.bluetoothPort.disconnect();
            if (this.chkDisconnect.isChecked()) {
                this.unregisterReceiver(this.disconnectReceiver);
            }
        } catch (Exception var2) {
            Log.e("BluetoothConnectMenu", var2.getMessage(), var2);
        }

        if (this.hThread != null && this.hThread.isAlive()) {
            this.hThread.interrupt();
        }

        this.connectButton.setText("Connect");
        this.list.setEnabled(true);
        this.btAddrBox.setEnabled(true);
        this.searchButton.setEnabled(true);
        Toast toast = Toast.makeText(this.context, "disconnect", Toast.LENGTH_SHORT);
        toast.show();
    }

    class connTask extends AsyncTask<BluetoothDevice, Void, Integer> {
        private final SweetAlertDialog dialog = new SweetAlertDialog(BluetoothConnectMenu.this, SweetAlertDialog.PROGRESS_TYPE);

        connTask() {
        }

        protected void onPreExecute() {


            dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialog.setTitleText(" Connect to printer ...");
            dialog.setCancelable(false);
            dialog.show();
            super.onPreExecute();
        }

        protected Integer doInBackground(BluetoothDevice... params) {
            Integer retVal = null;

            try {
                BluetoothConnectMenu.this.bluetoothPort.connect(params[0]);
                BluetoothConnectMenu.this.lastConnAddr = params[0].getAddress();

                retVal = 0;
            } catch (IOException var4) {
                Log.e("BluetoothConnectMenu", var4.getMessage());
                retVal = -1;
            }

            return retVal;
        }

        @SuppressLint("WrongThread")
        protected void onPostExecute(Integer result) {
            if (result == 0) {
                RequestHandler rh = new RequestHandler();
                BluetoothConnectMenu.this.hThread = new Thread(rh);
                BluetoothConnectMenu.this.hThread.start();
                BluetoothConnectMenu.this.connectButton.setText("Connect");
                BluetoothConnectMenu.this.connectButton.setEnabled(false);
                BluetoothConnectMenu.this.list.setEnabled(false);
                BluetoothConnectMenu.this.btAddrBox.setEnabled(false);
                BluetoothConnectMenu.this.searchButton.setEnabled(false);
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }


//                Toast toast = Toast.makeText(context, "Now Printing ", Toast.LENGTH_SHORT);
//                toast.show();
                //                int count = Integer.parseInt(getData);
                CPCLSample2 sample = new CPCLSample2(BluetoothConnectMenu.this);


                switch (Integer.parseInt(getData)){

                    case 0:

                        try {
                            sample.selectContinuousPaper();
                            Bitmap bitmap = null, bitmap3 = null, bitmap4 = null, bitmap5 = null;
                            bitmap = CovertVoucherTopBitmap(voucherGas);
                            bitmap3 = CovertVoucherToBitmap(voucherGas);
                            bitmap4 = CovertVoucherBoutomToBitmap(voucherGas);
//                        bitmap5=CovertVoucherEndToBitmap(voucherGas);
                            if (bitmap != null) {
                                sample.imageTestEnglishReport(1, bitmap);
                            } else {
                                Log.e("bitmap", "null");
                            }
                            Log.e("bitmap", "" + voucherGas.getAllowance());
                            if (bitmap3 != null) {
                                sample.imageTestEnglishReport(1, bitmap3);
                            } else {
                                Log.e("bitmap3", "null");
                            }

                            if (bitmap4 != null) {
                                sample.imageTestEnglishReport(1, bitmap4);
                            } else {
                                Log.e("bitmap4", "null");
                            }

//                        if(bitmap5!=null) {
//                            sample.imageTestEnglishReport(1, bitmap5);
//                        }else{
//                            Log.e("bitmap5","null");
//                        }
                        } catch (Exception e) {
                            Log.e("Error In Voucher prtint", "error 508");

                        }
                        break;

                    case 1:

                        try {
                            sample.selectContinuousPaper();
                            Bitmap bitmap2 = null, bitmap6 = null;

                            bitmap6 = CovertRecipteTopBitmap(recCash);
                            bitmap2 = CovertRecipteToBitmap(recCash);
//bitmap2 != null &&
                            if (bitmap2 != null && bitmap6 != null) {
                                sample.imageTestEnglishReport(1, bitmap6);
                                sample.imageTestEnglishReport(1, bitmap2);
                            } else {
                                Log.e("bitmap2", "null");
                            }

                        }catch (Exception e){
                            Log.e("Error In Recipt prtint","error 530");
                        }

                        break;
//                        sample.selectGapPaper();
//                        for(int i = 0; i< Item.barcodeListForPrint.size(); i++) {
//                            Bitmap bitmaps = convertLayoutToImage_Barcode(Item.barcodeListForPrint.get(i), Item.itemCardForPrint.getOrgPrice());
//                            if (bitmaps != null) {
//                                Log.e("Count = ", "" + Item.itemCardForPrint.getCostPrc());
//                                sample.imageTestEnglishBarcode(Integer.parseInt(Item.itemCardForPrint.getCostPrc()), bitmaps);
//                            } else {
////                                Toast.makeText(context, "CAN NOT PRINT", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        break;
//                    case 2:
//                        sample.selectContinuousPaper();
//                        double totalQty=0.0;
//                        if(ItemInfoListForPrint.size()!=0) {
//                            Bitmap bitmap1 = convertLayoutToImage_Report_titel(null, "-1");
//                            sample.imageTestEnglishReport(1, bitmap1);
//
//                            for (int i = 0; i < ItemInfoListForPrint.size(); i++) {
//                                Bitmap bitmap2 = convertLayoutToImage_Report(ItemInfoListForPrint.get(i), i+"");
//                                sample.imageTestEnglishReport(1, bitmap2);
//
//                                totalQty+=ItemInfoListForPrint.get(i).getItemQty();
//                            }
//
//                            Bitmap bitmap5 = convertLayoutToImage_Report(null, "-4");
//                            sample.imageTestEnglishReport(1, bitmap5);
//                            ItemInfo item=new ItemInfo();
//                            item.setItemQty((float) totalQty);
//                            Bitmap bitmap4 = convertLayoutToImage_Report(item, "-3");
//                            sample.imageTestEnglishReport(1, bitmap4);
//
//
//                            Bitmap bitmap3 = convertLayoutToImage_Report(null, "-2");
//                            sample.imageTestEnglishReport(1, bitmap3);
//                        }else{
////                            Toast.makeText(BluetoothConnectMenu.this, "No Item For Print ", Toast.LENGTH_SHORT).show();
//                        }

//                        break;


                }
                finish();

                if (BluetoothConnectMenu.this.chkDisconnect.isChecked()) {
                    BluetoothConnectMenu.this.registerReceiver(BluetoothConnectMenu.this.disconnectReceiver, new IntentFilter("android.bluetooth.device.action.ACL_CONNECTED"));
                    BluetoothConnectMenu.this.registerReceiver(BluetoothConnectMenu.this.disconnectReceiver, new IntentFilter("android.bluetooth.device.action.ACL_DISCONNECTED"));
                }
            } else {
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }

//                AlertView.showAlert("Disconnect Bluetoothُ", "Try Again ...", BluetoothConnectMenu.this.context);
                new SweetAlertDialog(BluetoothConnectMenu.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...Try Again")
                        .setContentText("Disconnect Bluetooth")
                        .show();



            }

            super.onPostExecute(result);
        }

    }

    public void finishDialog(){
        finish();
    }

    @SuppressLint("SetTextI18n")
    private Bitmap CovertRecipteTopBitmap(RecCash receipt) {
        LinearLayout linearView = null;
        final Dialog VocherDialog = new Dialog(BluetoothConnectMenu.this,R.style.Theme_Dialog);
        VocherDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        VocherDialog.setCancelable(true);
        VocherDialog.setContentView(R.layout.receipt_dialog_print_heder);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        ImageView logoPic;
        TextView taxno, voucherNo, date, projectName;
//        Log.e("bitmapSS",""+voucher.getLastValue());
        voucherNo = VocherDialog.findViewById(R.id.voucherNo);
        date = VocherDialog.findViewById(R.id.date);
        taxno = VocherDialog.findViewById(R.id.taxno);
        projectName = VocherDialog.findViewById(R.id.projectName);
        logoPic=VocherDialog.findViewById(R.id.logoPic);


//        gasReturn =VocherDialog. findViewById(R.id.gas_return);
//        serviceReturn = VocherDialog.findViewById(R.id.service_return);
//        taxService = VocherDialog.findViewById(R.id.tax_services);
//        net = VocherDialog.findViewById(R.id.net);
//        tax = VocherDialog.findViewById(R.id.tax);
//        currentConsuming = VocherDialog.findViewById(R.id.current_consuming);
//        lastValue = VocherDialog.findViewById(R.id.last_value);

        if(globelFunction.taxNo!=null){
            taxno.setText(globelFunction.taxNo);
        }else{
            taxno.setText("");
        }

        voucherNo.setText(""+receipt.getResNo());
        date.setText(""+globelFunction.DateInToday());
        projectName.setText(""+ receipt.getProjectName());

        if(globelFunction.logoPic!=null) {
            logoPic.setImageBitmap(globelFunction.logoPic);
        }else{

            logoPic.setImageBitmap(null);
            Log.e("globelFunction","null");
        }



//        taxService.setText(""+voucher.getServiceNoTax());
//        net.setText(""+voucher.getNet());
//        tax.setText(""+voucher.getTax());
//        currentConsuming.setText(""+voucher.getCurrentConsuming());
//        lastValue.setText(""+voucher.getLastValue());


        linearView = (LinearLayout) VocherDialog.findViewById(R.id.linerForPrint);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        VocherDialog.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit =linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }
    private Bitmap CovertRecipteToBitmap(RecCash receipt) {
        LinearLayout linearView = null;
        final Dialog VocherDialog = new Dialog(BluetoothConnectMenu.this,R.style.Theme_Dialog);
        VocherDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        VocherDialog.setCancelable(true);
        VocherDialog.setContentView(R.layout.recepite_dialog_print);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView counterNo, acc_no,filse,Jd,remarkRec,recDate,
                custNo;
        Log.e("bitmapsssaa",""+receipt.getCash());
        counterNo = VocherDialog.findViewById(R.id.counter_no);
        custNo = VocherDialog.findViewById(R.id.cust_no);
//        previousRead = VocherDialog.findViewById(R.id.previous_read);
//        currentRead = VocherDialog.findViewById(R.id.current_read);
//        consuming = VocherDialog.findViewById(R.id.consuming);
        acc_no = VocherDialog.findViewById(R.id.acc_no);
        filse = VocherDialog.findViewById(R.id.filse);
        Jd = VocherDialog.findViewById(R.id.jd);
        remarkRec= VocherDialog.findViewById(R.id.remarkRec);
        recDate=VocherDialog.findViewById(R.id.recDate);

        double amount=Double.parseDouble(receipt.getCash());
        Log.e("amount","Double "+amount);

        String fil=String.valueOf(amount).substring(String.valueOf(amount).indexOf(".")+1,String.valueOf(amount).length());
                String Jdes= String.valueOf(amount).substring(0,String.valueOf(amount).indexOf("."));
//        previousPalance = VocherDialog.findViewById(R.id.previous_palance);
//        prasuGas= VocherDialog.findViewById(R.id.prasuGas);
//        saleGas= VocherDialog.findViewById(R.id.saleGas);
//        extendValue= VocherDialog.findViewById(R.id.extendValue);
//        gasReturn =VocherDialog. findViewById(R.id.gas_return);
//        serviceReturn = VocherDialog.findViewById(R.id.service_return);
//        taxService = VocherDialog.findViewById(R.id.tax_services);
//        net = VocherDialog.findViewById(R.id.net);
//        tax = VocherDialog.findViewById(R.id.tax);
//        currentConsuming = VocherDialog.findViewById(R.id.current_consuming);
//        lastValue = VocherDialog.findViewById(R.id.last_value);
//customer
        filse.setText(""+fil);
        Jd.setText(""+Jdes);
        counterNo.setText(""+receipt.getCounterNo());
        acc_no.setText(""+receipt.getAccNo());
//        currentRead.setText(""+voucher.getCurrentRead());
//        extendValue.setText("1");
//        prasuGas.setText(""+customer.getGasPressure());
//        saleGas.setText(""+customer.getgPrice());
//        gasReturn.setText(""+voucher.getBadalGas());
//        serviceReturn.setText(""+voucher.getBadalService());
        custNo.setText(""+receipt.getAccName());
        remarkRec.setText(receipt.getRemarks());
        recDate.setText(receipt.getRecDate());
//        previousRead.setText(""+voucher.getPreviousRead());
//        consuming.setText(""+voucher.getConsuming());
//        consumingValue.setText(""+voucher.getConsumingValue());
//        prasuGas.setText(""+voucher.get);
//        saleGas.setText(""+voucher.getConsumingValue());
//        extendValue.setText(""+voucher.getConsumingValue());



//        previousPalance.setText(""+voucher.getPreviousPalance());
//        taxService.setText(""+voucher.getServiceNoTax());
//        net.setText(""+voucher.getNet());
//        tax.setText(""+voucher.getTax());
//        currentConsuming.setText(""+voucher.getCurrentConsuming());
//        lastValue.setText(""+voucher.getLastValue());


        linearView = (LinearLayout) VocherDialog.findViewById(R.id.linerForPrint);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        VocherDialog.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit =linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }
    private Bitmap CovertVoucherTopBitmap(VoucherModle voucher) {
        LinearLayout linearView = null;
        final Dialog VocherDialog = new Dialog(BluetoothConnectMenu.this,R.style.Theme_Dialog);
        VocherDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        VocherDialog.setCancelable(true);
        VocherDialog.setContentView(R.layout.voucher_dialog_print_heder);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        ImageView logoPic;
        TextView taxno, voucherNo, date, projectName;
        Log.e("bitmapSS",""+voucher.getAccNo());
        voucherNo = VocherDialog.findViewById(R.id.voucherNo);
        date = VocherDialog.findViewById(R.id.date);
        taxno = VocherDialog.findViewById(R.id.taxno);
        projectName = VocherDialog.findViewById(R.id.projectName);
        logoPic=VocherDialog.findViewById(R.id.logoPic);

        if(globelFunction.taxNo!=null){
            taxno.setText(globelFunction.taxNo);
        }else{
            taxno.setText("");
        }
        if(globelFunction.logoPic!=null) {
            logoPic.setImageBitmap(globelFunction.logoPic);
        }else{

            logoPic.setImageBitmap(null);
            Log.e("globelFunction","null");
        }

//        gasReturn =VocherDialog. findViewById(R.id.gas_return);
//        serviceReturn = VocherDialog.findViewById(R.id.service_return);
//        taxService = VocherDialog.findViewById(R.id.tax_services);
//        net = VocherDialog.findViewById(R.id.net);
//        tax = VocherDialog.findViewById(R.id.tax);
//        currentConsuming = VocherDialog.findViewById(R.id.current_consuming);
//        lastValue = VocherDialog.findViewById(R.id.last_value);

        voucherNo.setText(""+voucher.getInvoiceNo());
        date.setText(""+globelFunction.DateInToday());
        projectName.setText(""+ voucher.getProjectName());

//        taxService.setText(""+voucher.getServiceNoTax());
//        net.setText(""+voucher.getNet());
//        tax.setText(""+voucher.getTax());
//        currentConsuming.setText(""+voucher.getCurrentConsuming());
//        lastValue.setText(""+voucher.getLastValue());


        linearView = (LinearLayout) VocherDialog.findViewById(R.id.linerForPrint);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        VocherDialog.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit =linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }
    private Bitmap CovertVoucherToBitmap(VoucherModle voucher) {
        LinearLayout linearView = null;
        final Dialog VocherDialog = new Dialog(BluetoothConnectMenu.this,R.style.Theme_Dialog);
        VocherDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        VocherDialog.setCancelable(true);
        VocherDialog.setContentView(R.layout.voucher_dialog_print);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView counterNo, currentRead,readerDate, gasReturn, serviceReturn,accNo,
         custNo, previousRead, consuming, consumingValue, previousPalance, taxService, net, tax,
                currentConsuming, lastValue,prasuGas,saleGas,extendValue;
        counterNo = VocherDialog.findViewById(R.id.counter_no);
        custNo = VocherDialog.findViewById(R.id.cust_no);
        previousRead = VocherDialog.findViewById(R.id.previous_read);
        currentRead = VocherDialog.findViewById(R.id.current_read);
        readerDate= VocherDialog.findViewById(R.id.readerDate);
        consuming = VocherDialog.findViewById(R.id.consuming);
        consumingValue = VocherDialog.findViewById(R.id.consuming_value);
//        previousPalance = VocherDialog.findViewById(R.id.previous_palance);
        prasuGas= VocherDialog.findViewById(R.id.prasuGas);
        saleGas= VocherDialog.findViewById(R.id.saleGas);
        extendValue= VocherDialog.findViewById(R.id.extendValue);
//        gasReturn =VocherDialog. findViewById(R.id.gas_return);
//        serviceReturn = VocherDialog.findViewById(R.id.service_return);
//        taxService = VocherDialog.findViewById(R.id.tax_services);
//        net = VocherDialog.findViewById(R.id.net);
//        tax = VocherDialog.findViewById(R.id.tax);
//        currentConsuming = VocherDialog.findViewById(R.id.current_consuming);
//        lastValue = VocherDialog.findViewById(R.id.last_value);
        accNo= VocherDialog.findViewById(R.id.acc_no);
//customer
        counterNo.setText(""+voucher.getCounterNo());
        currentRead.setText(""+voucher.getCurrentReader());
        extendValue.setText("1");
        prasuGas.setText(""+voucher.getGasPressure());
        saleGas.setText(""+voucher.getGasPrice());
        readerDate.setText(""+voucher.getReaderDate());
//        gasReturn.setText(""+voucher.getBadalGas());
//        serviceReturn.setText(""+voucher.getBadalService());
        custNo.setText(""+voucher.getCustomerName());
        previousRead.setText(""+voucher.getLastReader());

        consuming.setText(""+voucher.getCCost());
        consumingValue.setText(""+voucher.getcCostVal());
        accNo.setText(""+voucher.getAccNo());

//        prasuGas.setText(""+voucher.get);
//        saleGas.setText(""+voucher.getConsumingValue());
//        extendValue.setText(""+voucher.getConsumingValue());



//        previousPalance.setText(""+voucher.getPreviousPalance());
//        taxService.setText(""+voucher.getServiceNoTax());
//        net.setText(""+voucher.getNet());
//        tax.setText(""+voucher.getTax());
//        currentConsuming.setText(""+voucher.getCurrentConsuming());
//        lastValue.setText(""+voucher.getLastValue());


        linearView = (LinearLayout) VocherDialog.findViewById(R.id.linerForPrint);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        VocherDialog.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit =linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }

    private Bitmap CovertVoucherBoutomToBitmap(VoucherModle voucher) {
        LinearLayout linearView = null;
        final Dialog VocherDialog = new Dialog(BluetoothConnectMenu.this,R.style.Theme_Dialog);
        VocherDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        VocherDialog.setCancelable(true);
        VocherDialog.setContentView(R.layout.voucher_dialog_print_bou);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView counterNo, currentRead,remarkVoucher,AccNoCompany, gasReturn, serviceReturn,
                custNo, previousRead, consuming, consumingValue, previousPalance, taxService, net, tax,
                currentConsuming, lastValue;

//        counterNo = VocherDialog.findViewById(R.id.counter_no);
//        custNo = VocherDialog.findViewById(R.id.cust_no);
//        previousRead = VocherDialog.findViewById(R.id.previous_read);
//        currentRead = VocherDialog.findViewById(R.id.current_read);
//        consuming = VocherDialog.findViewById(R.id.consuming);
//        consumingValue = VocherDialog.findViewById(R.id.consuming_value);
        previousPalance = VocherDialog.findViewById(R.id.previous_palance);
        gasReturn =VocherDialog. findViewById(R.id.gas_return);
        serviceReturn = VocherDialog.findViewById(R.id.service_return);
        taxService = VocherDialog.findViewById(R.id.tax_services);
        net = VocherDialog.findViewById(R.id.net);
        tax = VocherDialog.findViewById(R.id.tax);
        currentConsuming = VocherDialog.findViewById(R.id.current_consuming);
        lastValue = VocherDialog.findViewById(R.id.last_value);
        AccNoCompany= VocherDialog.findViewById(R.id.AccNoCompany);
        remarkVoucher=VocherDialog.findViewById(R.id.remarkVoucher);

//        counterNo.setText(""+voucher.getCounterNo());
//        currentRead.setText(""+voucher.getCurrentRead());
        gasReturn.setText(""+voucher.getGret());
        serviceReturn.setText(""+voucher.getService());
//        custNo.setText(""+voucher.getCustName());
//        previousRead.setText(""+voucher.getPreviousRead());
//        consuming.setText(""+voucher.getConsuming());
//        consumingValue.setText(""+voucher.getConsumingValue());
        previousPalance.setText(""+voucher.getCredit());
        taxService.setText(String.valueOf(Double.parseDouble(voucher.getGret())+Double.parseDouble(voucher.getService())));
        net.setText(""+voucher.getNetValue());
        tax.setText(""+voucher.getTaxValue());
        currentConsuming.setText(""+voucher.getConsumption());
        lastValue.setText(""+voucher.getReQalValue());
        remarkVoucher.setText(voucher.getRemarks());

        if(globelFunction.accNo!=null) {
            AccNoCompany.setText("" + globelFunction.accNo);
        }else{
            AccNoCompany.setText("");
        }

        linearView = (LinearLayout) VocherDialog.findViewById(R.id.linerForPrint);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        VocherDialog.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit =linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }
    private Bitmap CovertVoucherEndToBitmap(Voucher voucher) {
        LinearLayout linearView = null;
        final Dialog VocherDialog = new Dialog(BluetoothConnectMenu.this,R.style.Theme_Dialog);
        VocherDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        VocherDialog.setCancelable(true);
        VocherDialog.setContentView(R.layout.voucher_dialog_print_ending);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

//        TextView counterNo, currentRead, gasReturn, serviceReturn,
//                custNo, previousRead, consuming, consumingValue, previousPalance, taxService, net, tax,
//                currentConsuming, lastValue;
//
////        counterNo = VocherDialog.findViewById(R.id.counter_no);
////        custNo = VocherDialog.findViewById(R.id.cust_no);
////        previousRead = VocherDialog.findViewById(R.id.previous_read);
////        currentRead = VocherDialog.findViewById(R.id.current_read);
////        consuming = VocherDialog.findViewById(R.id.consuming);
////        consumingValue = VocherDialog.findViewById(R.id.consuming_value);
////        previousPalance = VocherDialog.findViewById(R.id.previous_palance);
//        gasReturn =VocherDialog. findViewById(R.id.gas_return);
//        serviceReturn = VocherDialog.findViewById(R.id.service_return);
//        taxService = VocherDialog.findViewById(R.id.tax_services);
//        net = VocherDialog.findViewById(R.id.net);
//        tax = VocherDialog.findViewById(R.id.tax);
//        currentConsuming = VocherDialog.findViewById(R.id.current_consuming);
//        lastValue = VocherDialog.findViewById(R.id.last_value);

////        counterNo.setText(""+voucher.getCounterNo());
////        currentRead.setText(""+voucher.getCurrentRead());
//        gasReturn.setText(""+voucher.getBadalGas());
//        serviceReturn.setText(""+voucher.getBadalService());
////        custNo.setText(""+voucher.getCustName());
////        previousRead.setText(""+voucher.getPreviousRead());
////        consuming.setText(""+voucher.getConsuming());
////        consumingValue.setText(""+voucher.getConsumingValue());
////        previousPalance.setText(""+voucher.getPreviousPalance());
//        taxService.setText(""+voucher.getServiceNoTax());
//        net.setText(""+voucher.getNet());
//        tax.setText(""+voucher.getTax());
//        currentConsuming.setText(""+voucher.getCurrentConsuming());
//        lastValue.setText(""+voucher.getLastValue());


        linearView = (LinearLayout) VocherDialog.findViewById(R.id.linerForPrint);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        VocherDialog.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit =linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }

    private Bitmap CovertRecToBitmap(Receipts receipts) {
        LinearLayout linearView = null;
        final Dialog VocherDialog = new Dialog(BluetoothConnectMenu.this,R.style.Theme_Dialog);
        VocherDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        VocherDialog.setCancelable(true);
        VocherDialog.setContentView(R.layout.rec_dialog_print);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView RecNo,CusName,Project,Cash,AccNo,CounterNo,ammount,note;

//

        RecNo = VocherDialog.findViewById(R.id.recNo);
        CusName = VocherDialog.findViewById(R.id.cusName);
        Project = VocherDialog.findViewById(R.id.projectName);
        Cash = VocherDialog.findViewById(R.id.cash);
        AccNo = VocherDialog.findViewById(R.id.accNo);
        CounterNo = VocherDialog.findViewById(R.id.counterNo);
        ammount = VocherDialog.findViewById(R.id.amount);
        note = VocherDialog.findViewById(R.id.note);

        RecNo.setText(""+receipts.getCounterNo());
        CusName.setText(""+receipts.getCustName());
        Project.setText(""+receipts.getProjectName());
        Cash.setText(""+receipts.getCredit());
        AccNo.setText(""+receipts.getAccNo());
        CounterNo.setText(""+receipts.getCounterNo());
        ammount.setText(""+receipts.getValue());
        note.setText(""+receipts.getNote());


        linearView = (LinearLayout) VocherDialog.findViewById(R.id.linerPrint);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        VocherDialog.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit =linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }

//    private Bitmap convertLayoutToImage_shelfTag_Design2(ItemCard itemCard) {
//        LinearLayout linearView = null;
//        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
//        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_Header.setCancelable(false);
//        dialog_Header.setContentView(R.layout.shlf_tag_dialog_design2);
////        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//
//        TextView itemName,price,BarcodeText,exp ;
//
//        LinearLayout ExpLiner,priceLiner;
//
//        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
//        priceLiner= (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);
//
//        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
//        price = (TextView) dialog_Header.findViewById(R.id.price);
//        BarcodeText=(TextView) dialog_Header.findViewById(R.id.BarcodeText);
//        exp=(TextView) dialog_Header.findViewById(R.id.exp);
//
//        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);
//
//        BarcodeText.setText(itemCard.getItemCode());
//        itemName.setText(itemCard.getItemName());
//        if(itemCard.getSalePrc().equals("**")){
//            priceLiner.setVisibility(View.INVISIBLE);
//        }else{
//            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC())))+"JD");
//        }
//
//        if(itemCard.getDepartmentId().equals("**")){
//            ExpLiner.setVisibility(View.INVISIBLE);
//        }else{
//            exp.setText(itemCard.getDepartmentId());
//        }
//
//
//        try {
//            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 90);
//            barcode.setImageBitmap(bitmaps);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//
//
//        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        dialog_Header.show();
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//
//
//    }
//    private Bitmap convertLayoutToImage_shelfTag_Design3(ItemCard itemCard) {
//        LinearLayout linearView = null;
//        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
//        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_Header.setCancelable(false);
//        dialog_Header.setContentView(R.layout.shlf_tag_dialog_design3);
////        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//
//        TextView itemName,price;//,BarcodeText;//,exp ;
//
//        LinearLayout priceLiner;//ExpLiner,
//
////        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
//        priceLiner= (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);
//
//        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
//        price = (TextView) dialog_Header.findViewById(R.id.price);
////        BarcodeText=(TextView) dialog_Header.findViewById(R.id.BarcodeText);
////        exp=(TextView) dialog_Header.findViewById(R.id.exp);
//
//        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);
//
////        BarcodeText.setText(itemCard.getItemCode());
//        itemName.setText(itemCard.getItemName());
//        if(itemCard.getSalePrc().equals("**")){
//            priceLiner.setVisibility(View.INVISIBLE);
//        }else{
//            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC())))+"JD");
//        }
//
////        if(itemCard.getDepartmentId().equals("**")){
////            ExpLiner.setVisibility(View.INVISIBLE);
////        }else{
////            exp.setText(itemCard.getDepartmentId());
////        }
//
//
//        try {
//            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 40);
//            barcode.setImageBitmap(bitmaps);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//
//
//        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        dialog_Header.show();
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//
//
//    }
//
//    private Bitmap convertLayoutToImage_Barcode(ItemCard itemCard,String index) {
//        LinearLayout linearView = null;
//        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
//        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_Header.setCancelable(false);
//        dialog_Header.setContentView(R.layout.barcode_print_dialog);
//
//
//        TextView itemName,price,BarcodeText,exp ;
//
//        LinearLayout ExpLiner,priceLiner;
//
////        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
//        priceLiner= (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);
//
//        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
//        price = (TextView) dialog_Header.findViewById(R.id.price);
//        BarcodeText=(TextView) dialog_Header.findViewById(R.id.BarcodeText);
////        exp=(TextView) dialog_Header.findViewById(R.id.exp);
//
//        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);
//
//        BarcodeText.setText(itemCard.getItemCode());
//        itemName.setText(itemCard.getItemName());
//        if(index.equals("**")){
//            priceLiner.setVisibility(View.INVISIBLE);
//        }else{
//            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC())))+" JD");
//        }
//
////        if(itemCard.getDepartmentId().equals("**")){
////            ExpLiner.setVisibility(View.INVISIBLE);
////        }else{
////            exp.setText(itemCard.getDepartmentId());
////        }
//
//
//        try {
//            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 50, 50);
//            barcode.setImageBitmap(bitmaps);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//
//
//        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        dialog_Header.show();
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//
//
//    }
//
//
//    private Bitmap convertLayoutToImage_Assesst(AssestItem itemCard, String index) {
//        LinearLayout linearView = null;
//        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
//        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_Header.setCancelable(false);
//        dialog_Header.setContentView(R.layout.barcode_print_dialog);
//
//
//        TextView itemName,price,BarcodeText,exp ;
//
//        LinearLayout ExpLiner,priceLiner;
//
////        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
//        priceLiner= (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);
//
//        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
//        price = (TextView) dialog_Header.findViewById(R.id.price);
//        BarcodeText=(TextView) dialog_Header.findViewById(R.id.BarcodeText);
////        exp=(TextView) dialog_Header.findViewById(R.id.exp);
//
//        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);
//
//        BarcodeText.setText(itemCard.getAssesstCode());
//        itemName.setText(itemCard.getAssesstName());
//
//        priceLiner.setVisibility(View.INVISIBLE);
//
////        if(index.equals("**")){
////            priceLiner.setVisibility(View.INVISIBLE);
////        }else{
////            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC())))+" JD");
////        }
//
////        if(itemCard.getDepartmentId().equals("**")){
////            ExpLiner.setVisibility(View.INVISIBLE);
////        }else{
////            exp.setText(itemCard.getDepartmentId());
////        }
//
//
//        try {
//            Bitmap bitmaps = encodeAsBitmap(itemCard.getAssesstCode(), BarcodeFormat.CODE_128, 50, 50);
//            barcode.setImageBitmap(bitmaps);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//
//
//        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        dialog_Header.show();
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//
//
//    }
//
//    private Bitmap convertLayoutToImage_Report(ItemInfo itemInfo, String index) {
//        LinearLayout linearView = null;
//        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
//        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_Header.setCancelable(false);
//        dialog_Header.setContentView(R.layout.row_for_print);
////        List list2 = ((List) ((ArrayList) long_listItems).clone());
//
//        TextView itemName,price,ItemCode,Qty ;
//
//        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
//        price = (TextView) dialog_Header.findViewById(R.id.price);
//        ItemCode=(TextView) dialog_Header.findViewById(R.id.itemCode);
//        Qty=(TextView) dialog_Header.findViewById(R.id.qty);
//
//
//        if(Integer.parseInt(index)==-1){
//            ItemCode.setText("Item Code");
//            itemName.setText("Item Name");
//            price.setText("Price");
//            Qty.setText("Qty");
//        }else if (Integer.parseInt(index)==-2){
//                ItemCode.setText(" ");
//                itemName.setText("   ");
//                price.setText(" ");
//                Qty.setText(" ");
//        } else if (Integer.parseInt(index)==-3){
//                ItemCode.setText(" ");
//                itemName.setText("Total Qty =");
//                price.setText(" ");
//                Qty.setText(itemInfo.getItemQty()+"");
//
//        }else if (Integer.parseInt(index)==-4){
//            ItemCode.setText("----------------------");
//            itemName.setText("----------------------");
//            price.setText("----------------");
//            Qty.setText("----------------");
//        }else{
//            ItemCode.setText(itemInfo.getItemCode());
//            itemName.setText(itemInfo.getItemName());
//            price.setText(itemInfo.getSalePrice()+"");
//            Qty.setText(itemInfo.getItemQty()+"");
//
//        }
//
//
//
//
//        linearView = (LinearLayout) dialog_Header.findViewById(R.id.LinerRaw);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        dialog_Header.show();
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//
//
//    }
//
//
//    private Bitmap convertLayoutToImage_Report_titel(ItemInfo itemInfo, String index) {
//        LinearLayout linearView = null;
//        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
//        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_Header.setCancelable(false);
//        dialog_Header.setContentView(R.layout.title_for_print);
//
//
//        TextView itemName,price,ItemCode,Qty,accu,date ;
//
//        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
//        price = (TextView) dialog_Header.findViewById(R.id.price);
//        ItemCode=(TextView) dialog_Header.findViewById(R.id.itemCode);
//        Qty=(TextView) dialog_Header.findViewById(R.id.qty);
//        accu=(TextView) dialog_Header.findViewById(R.id.accu);
//        date=(TextView) dialog_Header.findViewById(R.id.date);
//        date.setText(today);
//
//        if(preparAc){
//            accu.setText("Not Accum");
//        }else{
//            accu.setText("Accum");
//        }
//
//
//        if(Integer.parseInt(index)==-1){
//            ItemCode.setText("Item Code");
//            itemName.setText("Item Name");
//            price.setText("Price");
//            Qty.setText("Qty");
//        }
//
//
//
//
//        linearView = (LinearLayout) dialog_Header.findViewById(R.id.LinerRaw);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        dialog_Header.show();
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//
//
//    }

//    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
//        String contentsToEncode = contents;
//        if (contentsToEncode == null) {
//            return null;
//        }
//        Map<EncodeHintType, Object> hints = null;
//        String encoding = guessAppropriateEncoding(contentsToEncode);
//        if (encoding != null) {
//            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
//            hints.put(EncodeHintType.CHARACTER_SET, encoding);
//        }
//        MultiFormatWriter writer = new MultiFormatWriter();
//        BitMatrix result;
//        try {
//            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
//        } catch (IllegalArgumentException iae) {
//            // Unsupported format
//            return null;
//        }
//        int width = result.getWidth();
//        int height = result.getHeight();
//        int[] pixels = new int[width * height];
//        for (int y = 0; y < height; y++) {
//            int offset = y * width;
//            for (int x = 0; x < width; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(width, height,
//                Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        return bitmap;
//    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫","."));
        return newValue;
    }

//    public String convertToEnglish(String value) {
//        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
//        return newValue;
//    }
//
//    private Bitmap convertLayoutToImage_HEADER(Voucher voucher) {
//        LinearLayout linearView = null;
//        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
//        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_Header.setCancelable(false);
//        dialog_Header.setContentView(R.layout.header_voucher_print);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//        TextView doneinsewooprint = (TextView) dialog_Header.findViewById(R.id.done);
//
//        TextView compname, tel, taxNo, vhNo, date, custname, note, vhType, paytype,salesName     ;
//        ImageView img = (ImageView) dialog_Header.findViewById(R.id.img);
//        compname = (TextView) dialog_Header.findViewById(R.id.compname);
//        tel = (TextView) dialog_Header.findViewById(R.id.tel);
//        taxNo = (TextView) dialog_Header.findViewById(R.id.taxNo);
//        vhNo = (TextView) dialog_Header.findViewById(R.id.vhNo);
//        date = (TextView) dialog_Header.findViewById(R.id.date);
//        custname = (TextView) dialog_Header.findViewById(R.id.custname);
//        note = (TextView) dialog_Header.findViewById(R.id.note);
//        vhType = (TextView) dialog_Header.findViewById(R.id.vhType);
//        paytype = (TextView) dialog_Header.findViewById(R.id.paytype);
//        salesName = (TextView) dialog_Header.findViewById(R.id.salesman_name);
//        String voucherTyp = "";
//        switch (voucher.getVoucherType()) {
//            case 504:
//                voucherTyp = "فاتورة بيع";
//                break;
//            case 506:
//                voucherTyp = "فاتورة مرتجعات";
//                break;
//            case 508:
//                voucherTyp = "طلب جديد";
//                break;
//        }
//        if (companyInfo.getLogo()!=(null))
//        {
//        img.setImageBitmap(companyInfo.getLogo());
//        }
//        else{img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));}
//        compname.setText(companyInfo.getCompanyName());
//        tel.setText("" + companyInfo.getcompanyTel());
//        taxNo.setText("" + companyInfo.getTaxNo());
//        vhNo.setText("" + voucher.getVoucherNumber());
//        date.setText(voucher.getVoucherDate());
//        custname.setText(voucher.getCustName());
//        note.setText(voucher.getRemark());
//        vhType.setText(voucherTyp);
//        salesName.setText(obj.getAllSettings().get(0).getSalesMan_name());
//        paytype.setText((voucher.getPayMethod() == 0 ? "ذمم" : "نقدا"));
//        dialog_Header.show();
//
//        linearView = (LinearLayout) dialog_Header.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//
//
//    }
//    private Bitmap convertLayoutToImage_HEADER_Ejabe(Voucher voucher) {
//        LinearLayout linearView = null;
//        final Dialog dialog_Header = new Dialog(BluetoothConnectMenu.this);
//        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_Header.setCancelable(false);
//        dialog_Header.setContentView(R.layout.header_voucher_print_ejabe);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//        TextView doneinsewooprint = (TextView) dialog_Header.findViewById(R.id.done);
//
//        TextView compname, store,tel, taxNo, vhNo, date, custname, note, vhType, paytype,salesName     ;
//        ImageView img = (ImageView) dialog_Header.findViewById(R.id.img);
//        compname = (TextView) dialog_Header.findViewById(R.id.compname);
//        tel = (TextView) dialog_Header.findViewById(R.id.tel);
//        taxNo = (TextView) dialog_Header.findViewById(R.id.taxNo);
//        vhNo = (TextView) dialog_Header.findViewById(R.id.vhNo);
//        date = (TextView) dialog_Header.findViewById(R.id.date);
//        custname = (TextView) dialog_Header.findViewById(R.id.custname);
//        note = (TextView) dialog_Header.findViewById(R.id.note);
//        vhType = (TextView) dialog_Header.findViewById(R.id.vhType);
//        paytype = (TextView) dialog_Header.findViewById(R.id.paytype);
//        store= (TextView) dialog_Header.findViewById(R.id.store);
//        salesName = (TextView) dialog_Header.findViewById(R.id.salesman_name);
//        String salesmaname=obj.getSalesmanName();
//        salesName.setText(salesmaname);
//        String voucherTyp = "";
//        switch (voucher.getVoucherType()) {
//            case 504:
//                voucherTyp = "Sales Invoice";
//                break;
//            case 506:
//                voucherTyp = "Return Invoice";
//                break;
//            case 508:
//                voucherTyp = "New Order";
//                break;
//        }
//        if (companyInfo.getLogo()!=(null))
//        {
//            img.setImageBitmap(companyInfo.getLogo());
//        }
//        else{img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));}
//        compname.setText(companyInfo.getCompanyName());
//        tel.setText("" + companyInfo.getcompanyTel());
//        taxNo.setText("" + companyInfo.getTaxNo());
//        vhNo.setText("" + voucher.getVoucherNumber());
//        date.setText(voucher.getVoucherDate());
//        custname.setText(voucher.getCustName());
//        note.setText(voucher.getRemark());
//        vhType.setText(voucherTyp);
//        store.setText(Login.salesMan);
////        salesName.setText(obj.getAllSettings().get(0).getSalesMan_name());
//        paytype.setText((voucher.getPayMethod() == 0 ? "Credit" : "Cash"));
//        dialog_Header.show();
//
//        linearView = (LinearLayout) dialog_Header.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//
//
//    }
//
//    private Bitmap convertLayoutToImage_Footer(Voucher voucher,List<Item> items) {
//        LinearLayout linearView = null;
//
//        final Dialog dialog_footer = new Dialog(BluetoothConnectMenu.this);
//        dialog_footer.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_footer.setCancelable(false);
//        dialog_footer.setContentView(R.layout.footer_voucher_print);
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//        TextView doneinsewooprint = (TextView) dialog_footer.findViewById(R.id.done);
//
//        TextView total, discount, tax, ammont, Total_qty_total;
//        total = (TextView) dialog_footer.findViewById(R.id.total);
//        discount = (TextView) dialog_footer.findViewById(R.id.discount);
//        tax = (TextView) dialog_footer.findViewById(R.id.tax);
//        ammont = (TextView) dialog_footer.findViewById(R.id.ammont);
//        total.setText("" + voucher.getSubTotal());
//        discount.setText(convertToEnglish(String.valueOf(decimalFormat.format( voucher.getTotalVoucherDiscount()))));
//        tax.setText("" + voucher.getTax());
//        ammont.setText("" + voucher.getNetSales());
//        Total_qty_total=(TextView) dialog_footer.findViewById(R.id.total_qty);
//        Total_qty_total.setText(count+"");
//        linearView = (LinearLayout) dialog_footer.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//    }
//    private Bitmap convertLayoutToImage_Footer_ejabe(Voucher voucher,List<Item> items) {
//        LinearLayout linearView = null;
//
//        final Dialog dialog_footer = new Dialog(BluetoothConnectMenu.this);
//        dialog_footer.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_footer.setCancelable(false);
//        dialog_footer.setContentView(R.layout.footer_voucher_print_ejabe);
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//        TextView doneinsewooprint = (TextView) dialog_footer.findViewById(R.id.done);
//
//        TextView total, discount, tax, ammont, Total_qty_total;
//        total = (TextView) dialog_footer.findViewById(R.id.total);
//        discount = (TextView) dialog_footer.findViewById(R.id.discount);
//        tax = (TextView) dialog_footer.findViewById(R.id.tax);
//        ammont = (TextView) dialog_footer.findViewById(R.id.ammont);
//        total.setText("" + voucher.getSubTotal());
//        discount.setText(convertToEnglish(String.valueOf(decimalFormat.format( voucher.getTotalVoucherDiscount()))));
//        tax.setText("" + voucher.getTax());
//        ammont.setText("" + voucher.getNetSales());
//        Total_qty_total=(TextView) dialog_footer.findViewById(R.id.total_qty);
//        Total_qty_total.setText(count+"");
//        linearView = (LinearLayout) dialog_footer.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//    }
//
//
//    private Bitmap convertLayoutToImage(Voucher voucher,List<Item> items) {
//        LinearLayout linearView = null;
//
//        final Dialog dialogs = new Dialog(BluetoothConnectMenu.this);
//        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogs.setCancelable(false);
//        dialogs.setContentView(R.layout.sewo30_printer_layout);
////            fill_theVocher( voucher);
//
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//
//       TextView doneinsewooprint = (TextView) dialogs.findViewById(R.id.done);
//
//        TextView compname, tel, taxNo, vhNo, date, custname, note, vhType, paytype, total, discount, tax, ammont, textW,total_qty_text,salesName;
//        ImageView img = (ImageView) dialogs.findViewById(R.id.img);
////
//        compname = (TextView) dialogs.findViewById(R.id.compname);
//        tel = (TextView) dialogs.findViewById(R.id.tel);
//        taxNo = (TextView) dialogs.findViewById(R.id.taxNo);
//        vhNo = (TextView) dialogs.findViewById(R.id.vhNo);
//        date = (TextView) dialogs.findViewById(R.id.date);
//        custname = (TextView) dialogs.findViewById(R.id.custname);
//        salesName = (TextView) dialogs.findViewById(R.id.salesman_name);
//        note = (TextView) dialogs.findViewById(R.id.note);
//        vhType = (TextView) dialogs.findViewById(R.id.vhType);
//        paytype = (TextView) dialogs.findViewById(R.id.paytype);
//        total = (TextView) dialogs.findViewById(R.id.total);
//        discount = (TextView) dialogs.findViewById(R.id.discount);
//        tax = (TextView) dialogs.findViewById(R.id.tax);
//        ammont = (TextView) dialogs.findViewById(R.id.ammont);
//        textW = (TextView) dialogs.findViewById(R.id.wa1);
//        total_qty_text= (TextView) dialogs.findViewById(R.id.total_qty);
//        //total_qty
//
//        TableLayout tabLayout = (TableLayout) dialogs.findViewById(R.id.tab);
//        String voucherTyp = "";
//        switch (voucher.getVoucherType()) {
//            case 504:
//                voucherTyp = "فاتورة بيع";
//                break;
//            case 506:
//                voucherTyp = "فاتورة مرتجعات";
//                break;
//            case 508:
//                voucherTyp = "طلب جديد";
//                break;
//        }
////        img.setImageBitmap(companyInfo.getLogo());
//        compname.setText(companyInfo.getCompanyName());
//        if (companyInfo.getLogo()!=(null))
//        {
//            img.setImageBitmap(companyInfo.getLogo());
//        }
//        else{img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));}
//
//        tel.setText("" + companyInfo.getcompanyTel());
//        taxNo.setText("" + companyInfo.getTaxNo());
//        vhNo.setText("" + voucher.getVoucherNumber());
//        date.setText(voucher.getVoucherDate());
//        custname.setText(voucher.getCustName());
//        salesName.setText(obj.getAllSettings().get(0).getSalesMan_name());
//        note.setText(voucher.getRemark());
//        vhType.setText(voucherTyp);
//        paytype.setText((voucher.getPayMethod() == 0 ? "ذمم" : "نقدا"));
//        total.setText("" + voucher.getSubTotal());
//        discount.setText(convertToEnglish(String.valueOf(decimalFormat.format( voucher.getTotalVoucherDiscount()))));
//        tax.setText("" + voucher.getTax());
//        ammont.setText("" + voucher.getNetSales());
//
//       int count=0;
//
//        if (obj.getAllSettings().get(0).getUseWeightCase() != 1) {
//            textW.setVisibility(View.GONE);
//        } else {
//            textW.setVisibility(View.VISIBLE);
//        }
//
//
//        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        lp2.setMargins(0, 7, 0, 0);
//        lp3.setMargins(0, 7, 0, 0);
//        for (int j = 0; j < items.size(); j++) {
//            if (voucher.getVoucherNumber() == items.get(j).getVoucherNumber()) {
//                count+=items.get(j).getQty();
//                final TableRow row = new TableRow(BluetoothConnectMenu.this);
//
//
//                for (int i = 0; i <= 7; i++) {
////                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                    TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//                    lp.setMargins(0, 10, 0, 0);
//                    row.setLayoutParams(lp);
//
//                    TextView textView = new TextView(BluetoothConnectMenu.this);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setTextSize(14);
//                    textView.setTypeface(null, Typeface.BOLD);
//                    textView.setTextColor(getResources().getColor(R.color.text_view_color));
//
//                    switch (i) {
//                        case 0:
//                            textView.setText(items.get(j).getItemName());
//                            textView.setLayoutParams(lp3);
//                            break;
//
//
//                        case 1:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getUnit());
//                                textView.setLayoutParams(lp2);
//                            } else {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                            }
//                            break;
//
//                        case 2:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                                textView.setVisibility(View.VISIBLE);
//                            } else {
//                                textView.setVisibility(View.GONE);
//                            }
//                            break;
//
//                        case 3:
//                            textView.setText("" + items.get(j).getPrice());
//                            textView.setLayoutParams(lp2);
//                            break;
//
//
//                        case 4:
//                            String amount = "" + (items.get(j).getQty() * items.get(j).getPrice() - items.get(j).getDisc());
////                            amount = convertToEnglish(amount);
//                            amount =String.valueOf(decimalFormat.format(Double.parseDouble(amount)));
//                            textView.setText(convertToEnglish(amount));
////                            textView.setText(amount);
//                            textView.setLayoutParams(lp2);
//                            break;
//                    }
//                    row.addView(textView);
//                }
//
//
//
//                tabLayout.addView(row);
//            }
//        }
//
//
//        total_qty_text.setText(count+"");
//        Log.e("countItem",""+count);
//
////        linearView  = (LinearLayout) this.getLayoutInflater().inflate(R.layout.printdialog, null, false); //you can pass your xml layout
//        linearView = (LinearLayout) dialogs.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        dialogs.show();
//
////        linearView.setDrawingCacheEnabled(true);
////        linearView.buildDrawingCache();
////        Bitmap bit =linearView.getDrawingCache();
//
////        Bitmap bitmap = Bitmap.createBitmap(linearView.getWidth(), linearView.getHeight(), Bitmap.Config.ARGB_8888);
////        Canvas canvas = new Canvas(bitmap);
////        Drawable bgDrawable = linearView.getBackground();
////        if (bgDrawable != null) {
////            bgDrawable.draw(canvas);
////        } else {
////            canvas.drawColor(Color.WHITE);
////        }
////        linearView.draw(canvas);
//
//        return bit;// creates bitmap and returns the same
//    }
//
//    private Bitmap convertLayoutToImageEjape(Voucher voucher,List<Item> items) {
//        LinearLayout linearView = null;
//
//        final Dialog dialogs = new Dialog(BluetoothConnectMenu.this);
//        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogs.setCancelable(false);
//        dialogs.setContentView(R.layout.sewo30_printer_layout_ejaby);
////            fill_theVocher( voucher);
//
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//
//        TextView doneinsewooprint = (TextView) dialogs.findViewById(R.id.done);
//
//        TextView compname,store, tel, taxNo, vhNo, date, custname, note, vhType, paytype, total, discount, tax, ammont, textW,total_qty_text,salesName;
//        ImageView img = (ImageView) dialogs.findViewById(R.id.img);
////
//        compname = (TextView) dialogs.findViewById(R.id.compname);
//        tel = (TextView) dialogs.findViewById(R.id.tel);
//        taxNo = (TextView) dialogs.findViewById(R.id.taxNo);
//        vhNo = (TextView) dialogs.findViewById(R.id.vhNo);
//        date = (TextView) dialogs.findViewById(R.id.date);
//        custname = (TextView) dialogs.findViewById(R.id.custname);
//        salesName = (TextView) dialogs.findViewById(R.id.salesman_name);
//        note = (TextView) dialogs.findViewById(R.id.note);
//        vhType = (TextView) dialogs.findViewById(R.id.vhType);
//        paytype = (TextView) dialogs.findViewById(R.id.paytype);
//        total = (TextView) dialogs.findViewById(R.id.total);
//        discount = (TextView) dialogs.findViewById(R.id.discount);
//        tax = (TextView) dialogs.findViewById(R.id.tax);
//        ammont = (TextView) dialogs.findViewById(R.id.ammont);
//        textW = (TextView) dialogs.findViewById(R.id.wa1);
//        store= (TextView) dialogs.findViewById(R.id.store);
//        total_qty_text= (TextView) dialogs.findViewById(R.id.total_qty);
//        String salesmaname=obj.getSalesmanName();
//        salesName.setText(salesmaname);
//        //total_qty
//
//        TableLayout tabLayout = (TableLayout) dialogs.findViewById(R.id.tab);
//        String voucherTyp = "";
//        switch (voucher.getVoucherType()) {
//            case 504:
//                voucherTyp = "Sales Invoice";
//                break;
//            case 506:
//                voucherTyp = "Return Invoice";
//                break;
//            case 508:
//                voucherTyp = "New Order";
//                break;
//        }
////        img.setImageBitmap(companyInfo.getLogo());
//        compname.setText(companyInfo.getCompanyName());
//        if (companyInfo.getLogo()!=(null))
//        {
//            img.setImageBitmap(companyInfo.getLogo());
//        }
//        else{img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));}
//
//        tel.setText("" + companyInfo.getcompanyTel());
//        taxNo.setText("" + companyInfo.getTaxNo());
//        vhNo.setText("" + voucher.getVoucherNumber());
//        date.setText(voucher.getVoucherDate());
//        custname.setText(voucher.getCustName());
////        salesName.setText(obj.getAllSettings().get(0).getSalesMan_name());
//        note.setText(voucher.getRemark());
//        vhType.setText(voucherTyp);
//        paytype.setText((voucher.getPayMethod() == 0 ? "Credit" : "Cash"));
//        total.setText("" + voucher.getSubTotal());
//        discount.setText(convertToEnglish(String.valueOf(decimalFormat.format( voucher.getTotalVoucherDiscount()))));
//        tax.setText("" + voucher.getTax());
//        ammont.setText("" + voucher.getNetSales());
//        store.setText(Login.salesMan);
//        int count=0;
//
//        if (obj.getAllSettings().get(0).getUseWeightCase() != 1) {
//            textW.setVisibility(View.GONE);
//        } else {
//            textW.setVisibility(View.VISIBLE);
//        }
//
//
//        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        lp2.setMargins(0, 7, 0, 0);
//        lp3.setMargins(0, 7, 0, 0);
//        for (int j = 0; j < items.size(); j++) {
//            if (voucher.getVoucherNumber() == items.get(j).getVoucherNumber()) {
//                count+=items.get(j).getQty();
//                final TableRow row = new TableRow(BluetoothConnectMenu.this);
//
//
//                for (int i = 0; i <= 7; i++) {
////                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                    TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//                    lp.setMargins(0, 10, 0, 0);
//                    row.setLayoutParams(lp);
//
//                    TextView textView = new TextView(BluetoothConnectMenu.this);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setTextSize(14);
////                    textView.setTypeface(null, Typeface.BOLD);
//                    textView.setTextColor(getResources().getColor(R.color.text_view_color));
//
//                    switch (i) {
//                        case 0:
//                            textView.setText(items.get(j).getItemNo());
//                            textView.setLayoutParams(lp3);
//                            break;
//
//
//                        case 1:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getUnit());
//                                textView.setLayoutParams(lp2);
//                            } else {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                            }
//                            break;
//
//                        case 2:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                                textView.setVisibility(View.VISIBLE);
//                            } else {
//                                textView.setVisibility(View.GONE);
//                            }
//                            break;
//
//                        case 3:
//                            textView.setText("" + items.get(j).getPrice());
//                            textView.setLayoutParams(lp2);
//                            break;
//
//
//                        case 4:
//                            String amount = "" + (items.get(j).getQty() * items.get(j).getPrice() - items.get(j).getDisc());
////                            amount = convertToEnglish(amount);
//                            amount =String.valueOf(decimalFormat.format(Double.parseDouble(amount)));
//                            textView.setText(convertToEnglish(amount));
////                            textView.setText(amount);
//                            textView.setLayoutParams(lp2);
//                            break;
//                    }
//                    row.addView(textView);
//
//
//                }
////                final TableRow rows = new TableRow(BluetoothConnectMenu.this);
////                TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
////                lp.setMargins(0, 10, 0, 0);
////                rows.setLayoutParams(lp);
//                TextView textViews = new TextView(BluetoothConnectMenu.this);
//                textViews.setTextSize(14);
//                textViews.setPadding(0,0,0,5);
////                textViews.setTypeface(null, Typeface.BOLD);
//                textViews.setTextColor(getResources().getColor(R.color.text_view_color));
//                textViews.setText(items.get(j).getItemName());
////                rows.addView(textView);
//
//                tabLayout.addView(row);
//                tabLayout.addView(textViews);
//            }
//        }
//
//
//        total_qty_text.setText(count+"");
//        Log.e("countItem",""+count);
//
////        linearView  = (LinearLayout) this.getLayoutInflater().inflate(R.layout.printdialog, null, false); //you can pass your xml layout
//        linearView = (LinearLayout) dialogs.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
////        dialogs.show();
//
////        linearView.setDrawingCacheEnabled(true);
////        linearView.buildDrawingCache();
////        Bitmap bit =linearView.getDrawingCache();
//
////        Bitmap bitmap = Bitmap.createBitmap(linearView.getWidth(), linearView.getHeight(), Bitmap.Config.ARGB_8888);
////        Canvas canvas = new Canvas(bitmap);
////        Drawable bgDrawable = linearView.getBackground();
////        if (bgDrawable != null) {
////            bgDrawable.draw(canvas);
////        } else {
////            canvas.drawColor(Color.WHITE);
////        }
////        linearView.draw(canvas);
//
//        return bit;// creates bitmap and returns the same
//    }
//    private Bitmap convertLayoutToImageEjape_Stock(Voucher voucher,List<Item> items) {
//        LinearLayout linearView = null;
//
//        final Dialog dialogs = new Dialog(BluetoothConnectMenu.this);
//        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogs.setCancelable(false);
//        dialogs.setContentView(R.layout.print_stock_request_sewo30);
////            fill_theVocher( voucher);
//
//
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);
//
//        TextView doneinsewooprint = (TextView) dialogs.findViewById(R.id.done);
//
//        TextView compname,store, vhNo, date, custname, note,total_qty_text,salesName;
//        ImageView img = (ImageView) dialogs.findViewById(R.id.img);//
//        compname = (TextView) dialogs.findViewById(R.id.compname);
//        vhNo = (TextView) dialogs.findViewById(R.id.vhNo);
//        date = (TextView) dialogs.findViewById(R.id.date);
//        salesName = (TextView) dialogs.findViewById(R.id.salesman_name);
//        note = (TextView) dialogs.findViewById(R.id.note);
//        store= (TextView) dialogs.findViewById(R.id.store);
//        total_qty_text= (TextView) dialogs.findViewById(R.id.total_qty);
//        // total_qty
//
//        TableLayout tabLayout = (TableLayout) dialogs.findViewById(R.id.tab);
//
////        img.setImageBitmap(companyInfo.getLogo());
//        compname.setText(companyInfo.getCompanyName());
//        if (companyInfo.getLogo()!=(null))
//        {
//            img.setImageBitmap(companyInfo.getLogo());
//        }
//        else{img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));}
//        vhNo.setText("" + voucher.getVoucherNumber());
//        date.setText(voucher.getVoucherDate());
//        String salesmaname=obj.getSalesmanName();
//        salesName.setText(salesmaname);
//        note.setText(voucher.getRemark());
//
//        store.setText(Login.salesMan);
//        int count=0;
//        String s="";
//
//
//
//        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        lp2.setMargins(0, 7, 0, 0);
//        lp3.setMargins(0, 7, 0, 0);
//        for (int j = 0; j < items.size(); j++) {
//            if (voucher.getVoucherNumber() == items.get(j).getVoucherNumber()) {
//                count+=items.get(j).getQty();
//                final TableRow row = new TableRow(BluetoothConnectMenu.this);
//
//
//                for (int i = 0; i <3; i++) {
////                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                    TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//                    lp.setMargins(0, 10, 0, 0);
//                    row.setLayoutParams(lp);
//
//                    TextView textView = new TextView(BluetoothConnectMenu.this);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setTextSize(14);
////                    textView.setTypeface(null, Typeface.BOLD);
//                    textView.setTextColor(getResources().getColor(R.color.text_view_color));
//
//                    switch (i) {
//                        case 0:
//                            textView.setText(items.get(j).getItemNo());
//                            textView.setLayoutParams(lp3);
//                            break;
//
//
//                        case 1:
//                            textView.setText("" + items.get(j).getQty());
//                            textView.setLayoutParams(lp2);
////                            textView.setText("" + items.get(j).getItemName().substring(0,6));
////                            textView.setLayoutParams(lp2);
//                            break;
//
//                        case 2:
//
//                            break;
//
//                    }
//                    row.addView(textView);
//
//
//                }
//                TextView textViews = new TextView(BluetoothConnectMenu.this);
//                textViews.setTextSize(14);
//                textViews.setPadding(0,0,0,5);
////                textViews.setTypeface(null, Typeface.BOLD);
//                textViews.setTextColor(getResources().getColor(R.color.text_view_color));
//                textViews.setText(items.get(j).getItemName());
////                rows.addView(textView);
//
//                tabLayout.addView(row);
//                tabLayout.addView(textViews);
//            }
//        }
//
//
//        total_qty_text.setText(count+"");
//        Log.e("countItem",""+count);
//        linearView = (LinearLayout) dialogs.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//
//        return bit;// creates bitmap and returns the same
//    }
//
//    int count=0;
//    private Bitmap convertLayoutToImage_Body(Voucher voucher,List<Item> items,int visible) {
//        LinearLayout linearView = null;
//        final Dialog dialogs = new Dialog(BluetoothConnectMenu.this);
//        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogs.setCancelable(false);
//        dialogs.setContentView(R.layout.body_voucher_print);
////            fill_theVocher( voucher);
//        TextView doneinsewooprint = (TextView) dialogs.findViewById(R.id.done);
//        TextView  total, discount, tax, ammont, textW;
//        textW = (TextView) dialogs.findViewById(R.id.wa1);
////        int count=0;
//        TableLayout tabLayout = (TableLayout) dialogs.findViewById(R.id.tab);
//        TableRow row_header=(TableRow)dialogs.findViewById(R.id.row_header);
//        if(visible==0)
//        {
//            row_header.setVisibility(View.VISIBLE);
//        }
//        else {
//            row_header.setVisibility(View.INVISIBLE);
//        }
//
//        if (obj.getAllSettings().get(0).getUseWeightCase() != 1) {
//            textW.setVisibility(View.GONE);
//        } else {
//            textW.setVisibility(View.VISIBLE);
//        }
//
//
//        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        lp2.setMargins(0, 7, 0, 0);
//        lp3.setMargins(0, 7, 0, 0);
//        Log.e("itemSize",""+items.size());
//
//        for (int j = 0; j < items.size(); j++) {
//
//            if (voucher.getVoucherNumber() == items.get(j).getVoucherNumber()) {
//                count+=items.get(j).getQty();
//                final TableRow row = new TableRow(BluetoothConnectMenu.this);
//
//
//                for (int i = 0; i <= 7; i++) {
//                    TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
////                    TableRow.LayoutParams lp = new TableRow.LayoutParams(4);
//                    lp.setMargins(0, 10, 0, 0);
//                    row.setLayoutParams(lp);
//
//                    TextView textView = new TextView(BluetoothConnectMenu.this);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setTextSize(14);
//                    textView.setTypeface(null, Typeface.BOLD);
//                    textView.setTextColor(getResources().getColor(R.color.text_view_color));
//
//                    switch (i) {
//                        case 0:
//                            textView.setText(items.get(j).getItemName());
//                            textView.setLayoutParams(lp3);
//                            break;
//
//
//                        case 1:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getUnit());
//                                textView.setLayoutParams(lp2);
//                            } else {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                            }
//                            break;
//
//                        case 2:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                                textView.setVisibility(View.VISIBLE);
//                            } else {
//                                textView.setVisibility(View.GONE);
//                            }
//                            break;
//
//                        case 3:
//                            textView.setText("" + items.get(j).getPrice());
//                            textView.setLayoutParams(lp2);
//                            break;
//
//
//                        case 4:
//                            String amount = "" + (items.get(j).getQty() * items.get(j).getPrice() - items.get(j).getDisc());
////                            amount = convertToEnglish(amount);
//                            amount =String.valueOf(decimalFormat.format(Double.parseDouble(amount)));
//                            textView.setText(convertToEnglish(amount));
//                            textView.setLayoutParams(lp2);
//                            break;
//                    }
//                    row.addView(textView);
//                }
//
//
//                tabLayout.addView(row);
//            }
//        }
//        linearView = (LinearLayout) dialogs.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//    }
//    private Bitmap convertLayoutToImage_Body_ejabi(Voucher voucher,List<Item> items,int visible) {
//        LinearLayout linearView = null;
//        final Dialog dialogs = new Dialog(BluetoothConnectMenu.this);
//        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogs.setCancelable(false);
//        dialogs.setContentView(R.layout.body_voucher_print_ejabe);
////            fill_theVocher( voucher);
//        TextView doneinsewooprint = (TextView) dialogs.findViewById(R.id.done);
//        TextView  total, discount, tax, ammont, textW;
//        textW = (TextView) dialogs.findViewById(R.id.wa1);
////        int count=0;
//        TableLayout tabLayout = (TableLayout) dialogs.findViewById(R.id.tab);
//        TableRow row_header=(TableRow)dialogs.findViewById(R.id.row_header);
//        if(visible==0)
//        {
//            row_header.setVisibility(View.VISIBLE);
//        }
//        else {
//            row_header.setVisibility(View.INVISIBLE);
//        }
//
//        if (obj.getAllSettings().get(0).getUseWeightCase() != 1) {
//            textW.setVisibility(View.GONE);
//        } else {
//            textW.setVisibility(View.VISIBLE);
//        }
//
//
//        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
//        lp2.setMargins(0, 7, 0, 0);
//        lp3.setMargins(0, 7, 0, 0);
//        Log.e("itemSize",""+items.size());
//
//        for (int j = 0; j < items.size(); j++) {
//
//            if (voucher.getVoucherNumber() == items.get(j).getVoucherNumber()) {
//                count+=items.get(j).getQty();
//                final TableRow row = new TableRow(BluetoothConnectMenu.this);
//
//
//                for (int i = 0; i <= 7; i++) {
//                    TableRow.LayoutParams lp = new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
////                    TableRow.LayoutParams lp = new TableRow.LayoutParams(4);
//                    lp.setMargins(0, 10, 0, 0);
//                    row.setLayoutParams(lp);
//
//                    TextView textView = new TextView(BluetoothConnectMenu.this);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setTextSize(14);
////                    textView.setTypeface(null, Typeface.BOLD);
//                    textView.setTextColor(getResources().getColor(R.color.text_view_color));
//
//                    switch (i) {
//                        case 0:
//                            textView.setText(items.get(j).getItemNo());
//                            textView.setLayoutParams(lp3);
//                            break;
//
//
//                        case 1:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getUnit());
//                                textView.setLayoutParams(lp2);
//                            } else {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                            }
//                            break;
//
//                        case 2:
//                            if (obj.getAllSettings().get(0).getUseWeightCase() == 1) {
//                                textView.setText("" + items.get(j).getQty());
//                                textView.setLayoutParams(lp2);
//                                textView.setVisibility(View.VISIBLE);
//                            } else {
//                                textView.setVisibility(View.GONE);
//                            }
//                            break;
//
//                        case 3:
//                            textView.setText("" + items.get(j).getPrice());
//                            textView.setLayoutParams(lp2);
//                            break;
//
//
//                        case 4:
//                            String amount = "" + (items.get(j).getQty() * items.get(j).getPrice() - items.get(j).getDisc());
////                            amount = convertToEnglish(amount);
//                            amount =String.valueOf(decimalFormat.format(Double.parseDouble(amount)));
//                            textView.setText(convertToEnglish(amount));
//                            textView.setLayoutParams(lp2);
//                            break;
//                    }
//                    row.addView(textView);
//                }
//
//                TextView textViews = new TextView(BluetoothConnectMenu.this);
//                textViews.setTextSize(14);
//                textViews.setPadding(0,0,0,5);
////                textViews.setTypeface(null, Typeface.BOLD);
//                textViews.setTextColor(getResources().getColor(R.color.text_view_color));
//                textViews.setText(items.get(j).getItemName());
//
//                tabLayout.addView(row);
//                tabLayout.addView(textViews);
//            }
//        }
//        linearView = (LinearLayout) dialogs.findViewById(R.id.ll);
//
//        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());
//
//        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//
//        linearView.setDrawingCacheEnabled(true);
//        linearView.buildDrawingCache();
//        Bitmap bit =linearView.getDrawingCache();
//
//        return bit;// creates bitmap and returns the same
//    }

}
