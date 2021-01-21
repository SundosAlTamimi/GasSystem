package com.falconssoft.gassystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.falconssoft.gassystem.Port.AlertView;
import com.sewoo.request.android.RequestHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.falconssoft.gassystem.MakeVoucher.voucherGas;
import static com.falconssoft.gassystem.Receipt.recCash;

// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)

public class bMITP extends Activity {
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
    private ListView list;
    private BluetoothPort bluetoothPort;
    private CheckBox chkDisconnect;
    private static final String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "//temp";
    private static final String fileName;
    private String lastConnAddr;
    static  String idname;
    DatabaseHandler DHandler;
    String getData;

    GlobelFunction globelFunction;
    String setting="";
    static {
        fileName = dir + "//BTPrinter";
    }

    public bMITP() {

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

        while(iter.hasNext()) {
            BluetoothDevice pairedDevice = (BluetoothDevice)iter.next();
//            if (this.bluetoothPort.isValidAddress(pairedDevice.getAddress())) {
                this.remoteDevices.add(pairedDevice);
                this.adapter.add(pairedDevice.getName() + "\n[" + pairedDevice.getAddress() + "] [Paired]");
            }
//        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.bluetooth_menu);
        this.btAddrBox = (EditText)this.findViewById(R.id.EditTextAddressBT);
        this.connectButton = (Button)this.findViewById(R.id.ButtonConnectBT);
        bMITP.this.connectButton.setEnabled(true);
        this.searchButton = (Button)this.findViewById(R.id.ButtonSearchBT);
        this.list = (ListView)this.findViewById(R.id.BtAddrListView);
        this.chkDisconnect = (CheckBox)this.findViewById(R.id.check_disconnect);
        this.chkDisconnect.setChecked(true);
        this.context = this;
        DHandler=new DatabaseHandler(bMITP.this);

        globelFunction=new GlobelFunction();
        setting=globelFunction.GlobelFunctionSetting(DHandler);
//
        getData = getIntent().getStringExtra("printKey");
//        Bundle bundle = getIntent().getExtras();
//         allStudents = (List<Item>) bundle.get("ExtraData");
//
//         Log.e("all",allStudents.get(0).getBarcode());

        Log.e("printKey",""+getData);
        this.loadSettingFile();
        this.bluetoothSetup();
//        this.connectButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (!bMITP.this.bluetoothPort.isConnected()) {
//                    try {
//                        bMITP.this.btConn(bMITP.this.mBluetoothAdapter.getRemoteDevice(bMITP.this.btAddrBox.getText().toString()));
//                    } catch (IllegalArgumentException var3) {
//                        Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
////                        AlertView.showAlert(var3.getMessage(), bMITP.this.context);
//                        return;
//                    } catch (IOException var4) {
//                        Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
////                        AlertView.showAlert(var4.getMessage(), bMITP.this.context);
//                        return;
//                    }
//                } else {
//                    bMITP.this.btDisconn();
//                }
//
//            }
//        });
//        this.searchButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (!bMITP.this.mBluetoothAdapter.isDiscovering()) {
//                    bMITP.this.clearBtDevData();
//                    bMITP.this.adapter.clear();
//                    bMITP.this.mBluetoothAdapter.startDiscovery();
//                } else {
//                    bMITP.this.mBluetoothAdapter.cancelDiscovery();
//                }
//
//            }
//        });
        this.adapter = new ArrayAdapter(bMITP.this , R.layout.cci );

        this.list.setAdapter(this.adapter);
        this.addPairedDevices();
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                BluetoothDevice btDev = (BluetoothDevice) bMITP.this.remoteDevices.elementAt(arg2);

                try {
                    if (bMITP.this.mBluetoothAdapter.isDiscovering()) {
                        bMITP.this.mBluetoothAdapter.cancelDiscovery();
                    }

                    bMITP.this.btAddrBox.setText(btDev.getAddress());
                    bMITP.this.btConn(btDev);
                } catch (IOException var8) {
                    AlertView.showAlert(var8.getMessage(), bMITP.this.context);
                }
            }
        });
        this.discoveryResult = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                BluetoothDevice remoteDevice = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (remoteDevice != null) {
                    String key;
                    if (remoteDevice.getBondState() != 12) {
                        key = remoteDevice.getName() + "\n[" + remoteDevice.getAddress() + "]";
                    } else {
                        key = remoteDevice.getName() + "\n[" + remoteDevice.getAddress() + "] [Paired]";
                    }

//                    if (bMITP.this.bluetoothPort.isValidAddress(remoteDevice.getAddress())) {
                        bMITP.this.remoteDevices.add(remoteDevice);
                        bMITP.this.adapter.add(key);
                    }
//                }

            }
        };
        this.registerReceiver(this.discoveryResult, new IntentFilter("android.bluetooth.device.action.FOUND"));
        this.searchStart = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                bMITP.this.connectButton.setEnabled(false);
                bMITP.this.btAddrBox.setEnabled(false);
//                BluetoothConnectMenu.this.searchButton.setText(BluetoothConnectMenu.this.getResources().getString(2131034114));

                bMITP.this.searchButton.setText("stop ");
            }
        };
        this.registerReceiver(this.searchStart, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_STARTED"));
        this.searchFinish = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                bMITP.this.connectButton.setEnabled(true);
                bMITP.this.btAddrBox.setEnabled(true);
//                BluetoothConnectMenu.this.searchButton.setText(BluetoothConnectMenu.this.getResources().getString(2131034113));
                bMITP.this.searchButton.setText("search");

            }
        };
        this.registerReceiver(this.searchFinish, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
        if (this.chkDisconnect.isChecked()) {
            this.disconnectReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    BluetoothDevice device = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (!"android.bluetooth.device.action.ACL_CONNECTED".equals(action) && "android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) {
                        bMITP.this.DialogReconnectionOption();
                    }

                }
            };
        }

        if(remoteDevices.size()!=0) {
            coon();
        }else{
            Toast.makeText(context, "Please Connect to Bluetooth ", Toast.LENGTH_SHORT).show();
        }



    }


    public void coon(){
        if (!bMITP.this.bluetoothPort.isConnected()) {
            try {
                bMITP.this.btConn(bMITP.this.mBluetoothAdapter.getRemoteDevice(remoteDevices.get(0).getAddress()));
            } catch (IllegalArgumentException var3) {
                Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
                AlertView.showAlert(var3.getMessage(), bMITP.this.context);
                return;
            } catch (IOException var4) {
                Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
                AlertView.showAlert(var4.getMessage(), bMITP.this.context);
                return;
            }
        } else {
            bMITP.this.btDisconn();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("connection ...");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        }).setPositiveButton("connect", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    bMITP.this.btDisconn();
                    bMITP.this.btConn(bMITP.this.mBluetoothAdapter.getRemoteDevice(bMITP.this.btAddrBox.getText().toString()));
                } catch (IllegalArgumentException var4) {
                    Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
                    AlertView.showAlert(var4.getMessage(), bMITP.this.context);
                } catch (IOException var5) {
                    Log.e("BluetoothConnectMenu", var5.getMessage(), var5);
                    AlertView.showAlert(var5.getMessage(), bMITP.this.context);
                }
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                bMITP.this.btDisconn();
            }
        });
        builder.show();
    }

    private void btConn(BluetoothDevice btDev) throws IOException {
        (new bMITP.connTask()).execute(new BluetoothDevice[]{btDev});
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
        private final SweetAlertDialog dialog = new SweetAlertDialog(bMITP.this, SweetAlertDialog.PROGRESS_TYPE);

        connTask() {
        }

        protected void onPreExecute() {
            dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialog.setTitleText(" يتم الوصول للطابعه ...");
            dialog.setCancelable(false);
            dialog.show();
            super.onPreExecute();
        }

        protected Integer doInBackground(BluetoothDevice... params) {
            Integer retVal = null;

            try {
                bMITP.this.bluetoothPort.connect(params[0]);
                bMITP.this.lastConnAddr = params[0].getAddress();
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
                bMITP.this.hThread = new Thread(rh);
                bMITP.this.hThread.start();
                bMITP.this.connectButton.setText("Connect");
                bMITP.this.connectButton.setEnabled(false);
                bMITP.this.list.setEnabled(false);
                bMITP.this.btAddrBox.setEnabled(false);
                bMITP.this.searchButton.setEnabled(false);
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }

                Toast toast = Toast.makeText(bMITP.this.context, "Now Printing ", Toast.LENGTH_SHORT);
                toast.show();


                int count =Integer.parseInt(getData);
                ESCPSample2 sample = new ESCPSample2(bMITP.this);


                switch (count){

                    case 0:

                        try {
                            if(!setting.equals("noSetting")) {
                                if(globelFunction.formType.equals("1")) {
                                    Log.e("print","altahakom");
                                    sample.printMultilingualFontVoucheEsc(globelFunction.DateInToday(), globelFunction.settingModleG, 0, voucherGas);
                                }else {
                                    Log.e("print","green");
                                    sample.printMultilingualFontVoucheGreenEsc(globelFunction.DateInToday(), globelFunction.settingModleG, 0, voucherGas);

                                }
                            }else{
                                new SweetAlertDialog(bMITP.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("الاعدادات " + "!")
                                        .setContentText("الرجاء اضافة الاعدادات ")
                                        .setConfirmText("الغاء")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null).show();
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        break;
                    case 1:



                        try {
                            if(!setting.equals("noSetting")) {
                                if(globelFunction.formType.equals("1")) {
                                    Log.e("print","altahakom");
                                    sample.printMultilingualFontEsc3(globelFunction.DateInToday(), globelFunction.settingModleG, 0, recCash);
                                }else {
                                    Log.e("print","green");
                                    sample.printMultilingualFontGreenEsc3(globelFunction.DateInToday(), globelFunction.settingModleG, 0, recCash);

                                }
                            }else{
                                new SweetAlertDialog(bMITP.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("الاعدادات " + "!")
                                        .setContentText("الرجاء اضافة الاعدادات ")
                                        .setConfirmText("الغاء")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null).show();
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        break;

                }


//                    sample.printMultilingualFont();
                finish();


                if (bMITP.this.chkDisconnect.isChecked()) {
                    bMITP.this.registerReceiver(bMITP.this.disconnectReceiver, new IntentFilter("android.bluetooth.device.action.ACL_CONNECTED"));
                    bMITP.this.registerReceiver(bMITP.this.disconnectReceiver, new IntentFilter("android.bluetooth.device.action.ACL_DISCONNECTED"));
                }
            } else {
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }

//                AlertView.showAlert("Disconnect Bluetoothُ", "Try Again ,,,.", bMITP.this.context);
                new SweetAlertDialog(bMITP.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("حاول مره اخرى !!! ")
                        .setContentText("فشل الاتصال مع الطابعه ")
                        .show();
            }

            super.onPostExecute(result);
        }
    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }


}
