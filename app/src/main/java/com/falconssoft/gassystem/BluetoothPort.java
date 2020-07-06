package com.falconssoft.gassystem;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.util.Log;

import com.sewoo.jpos.request.RequestQueue;
import com.sewoo.port.PortMediator;
import com.sewoo.port.android.PortInterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;



//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


public class BluetoothPort implements PortInterface {
    private static BluetoothPort port;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private OutputStream os;
    private InputStream is;
    private BluetoothSocket socket;
    private BluetoothSocket tmp;
    private boolean connected;
    private static final String[] btDevAddr = new String[]{"00:12:6f", "00:13:7b", "74:f0:7d", "00:07:80"};

    public BluetoothPort() {
    }

    public static BluetoothPort getInstance() {
        if (port == null) {
            port = new BluetoothPort();
        }

        return port;
    }

    private void setBTSocket(BluetoothDevice device) throws IOException {
        if (Build.VERSION.SDK_INT >= 10) {
            this.setSecureSocket(device, false);
        } else {
            this.setSecureSocket(device, true);
        }

    }

    private void setSecureSocket(BluetoothDevice device, boolean secure) throws IOException {
        if (secure) {
            try {
                this.tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException var5) {
                Log.e("Bluetooth", "create() failed", var5);
            }

            this.socket = this.tmp;
        } else {
            try {
                this.tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException var4) {
                Log.e("Bluetooth", "create() failed", var4);
            }

            this.socket = this.tmp;
        }

    }

    private void connectCommon() throws IOException {
        PortMediator pm = PortMediator.getInstance();
        if (this.socket != null) {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }

            this.socket.connect();
            this.is = this.socket.getInputStream();
            this.os = this.socket.getOutputStream();
            this.connected = true;
            pm.setIs(this.is);
            pm.setOs(this.os);
        } else {
            throw new IOException("Bluetooth Socket is null.");
        }
    }

    public boolean isValidAddress(String address) {
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            return false;
        } else {
            for(int i = 0; i < btDevAddr.length; ++i) {
                if (btDevAddr[i].equalsIgnoreCase(address.substring(0, 8))) {
                    return true;
                }
            }

            return false;
        }
    }

    private String checkBTAddress(String address) {
        StringBuffer stringBuffer = new StringBuffer();
        if (address.indexOf(":") != -1) {
            return address.toUpperCase();
        } else {
            stringBuffer.append(address.charAt(0)).append(address.charAt(1)).append(':').append(address.charAt(2)).append(address.charAt(3)).append(':').append(address.charAt(4)).append(address.charAt(5)).append(':').append(address.charAt(6)).append(address.charAt(7)).append(':').append(address.charAt(8)).append(address.charAt(9)).append(':').append(address.charAt(10)).append(address.charAt(11));
            return stringBuffer.toString().toUpperCase();
        }
    }

    public void connect(String address) throws IOException {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String myAddress = this.checkBTAddress(address);
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(myAddress);
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        this.setBTSocket(device);
        this.connectCommon();


//        if (!this.isValidAddress(myAddress)) {
            throw new IOException("Bluetooth Address is not valid.");
//        } else {

//        }
    }

    public void connect(BluetoothDevice device) throws IOException {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String address = device.getAddress();
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        this.setBTSocket(device);
        this.connectCommon();

//        if (!this.isValidAddress(address)) {
//            throw new IOException("Bluetooth Address is not valid.");
//        } else {

//        }
    }

    public void connect(String address, boolean bReg) throws IOException {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String myAddress = this.checkBTAddress(address);
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(myAddress);
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        this.setBTSocket(device);
        this.connectCommon();
        if (bReg) {
            try {
                Method method = device.getClass().getMethod("createBond", (Class[])null);
                method.invoke(device, (Object[])null);
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }

//        if (!this.isValidAddress(myAddress)) {
//            throw new IOException("Bluetooth Address is not valid.");
//        } else {
//            this.setBTSocket(device);
//            this.connectCommon();
//            if (bReg) {
//                try {
//                    Method method = device.getClass().getMethod("createBond", (Class[])null);
//                    method.invoke(device, (Object[])null);
//                } catch (Exception var7) {
//                    var7.printStackTrace();
//                }
//            }
//
//        }
    }

    public void connect(BluetoothDevice device, boolean bReg) throws IOException {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String address = device.getAddress();
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        this.setBTSocket(device);
        this.connectCommon();
        if (bReg && bReg) {
            try {
                Method method = device.getClass().getMethod("createBond", (Class[])null);
                method.invoke(device, (Object[])null);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

//        if (!this.isValidAddress(address)) {
//            throw new IOException("Bluetooth Address is not valid.");
//        } else {
//            this.setBTSocket(device);
//            this.connectCommon();
//            if (bReg && bReg) {
//                try {
//                    Method method = device.getClass().getMethod("createBond", (Class[])null);
//                    method.invoke(device, (Object[])null);
//                } catch (Exception var6) {
//                    var6.printStackTrace();
//                }
//            }
//
//        }
    }

    public void connectSecure(String address) throws IOException {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        this.setSecureSocket(bluetoothDevice, true);
        this.connectCommon();
//        if (!this.isValidAddress(address)) {
//            throw new IOException("Bluetooth Address is not valid.");
//        } else {
//            this.setSecureSocket(bluetoothDevice, true);
//            this.connectCommon();
//        }
    }

    public void connectSecure(BluetoothDevice device) throws IOException {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String address = device.getAddress();
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        if (!this.isValidAddress(address)) {
            throw new IOException("Bluetooth Address is not valid.");
        } else {
            this.setSecureSocket(device, true);
            this.connectCommon();
        }
    }

    public void connectInsecure(String address) throws IOException {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        if (!this.isValidAddress(address)) {
            throw new IOException("Bluetooth Address is not valid.");
        } else {
            this.setSecureSocket(bluetoothDevice, false);
            this.connectCommon();
        }
    }

    public void connectInsecure(BluetoothDevice device) throws IOException {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String address = device.getAddress();
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        if (!this.isValidAddress(address)) {
            throw new IOException("Bluetooth Address is not valid.");
        } else {
            this.setSecureSocket(device, false);
            this.connectCommon();
        }
    }

    public void disconnect() throws IOException, InterruptedException {
        int count = 0;

        for(RequestQueue rq = RequestQueue.getInstance(); !rq.isEmpty() && count < 10; ++count) {
            Thread.sleep(1000L);
        }

        Thread.sleep(2000L);
        if (this.os != null) {
            this.os.close();
            this.os = null;
        }

        if (this.is != null) {
            this.is.close();
            this.is = null;
        }

        if (this.socket != null) {
            this.socket.close();
            this.socket = null;
        }

        this.connected = false;
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.cancelDiscovery();
    }

    public void disconnect(String address, boolean bDereg) throws IOException, InterruptedException {
        int count = 0;

        for(RequestQueue rq = RequestQueue.getInstance(); !rq.isEmpty() && count < 10; ++count) {
            Thread.sleep(1000L);
        }

        Thread.sleep(2000L);
        if (this.os != null) {
            this.os.close();
            this.os = null;
        }

        if (this.is != null) {
            this.is.close();
            this.is = null;
        }

        if (this.socket != null) {
            this.socket.close();
            this.socket = null;
        }

        this.connected = false;
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String myAddress = this.checkBTAddress(address);
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(myAddress);
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        if (!this.isValidAddress(myAddress)) {
            throw new IOException("Bluetooth Address is not valid.");
        } else {
            if (bDereg) {
                try {
                    Method method = device.getClass().getMethod("removeBond", (Class[])null);
                    method.invoke(device, (Object[])null);
                } catch (Exception var9) {
                    var9.printStackTrace();
                }
            }

        }
    }

    public void disconnect(BluetoothDevice device, boolean bDereg) throws IOException, InterruptedException {
        int count = 0;

        for(RequestQueue rq = RequestQueue.getInstance(); !rq.isEmpty() && count < 10; ++count) {
            Thread.sleep(1000L);
        }

        Thread.sleep(2000L);
        if (this.os != null) {
            this.os.close();
            this.os = null;
        }

        if (this.is != null) {
            this.is.close();
            this.is = null;
        }

        if (this.socket != null) {
            this.socket.close();
            this.socket = null;
        }

        this.connected = false;
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String address = device.getAddress();
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        if (!this.isValidAddress(address)) {
            throw new IOException("Bluetooth Address is not valid.");
        } else {
            if (bDereg) {
                try {
                    Method method = device.getClass().getMethod("removeBond", (Class[])null);
                    method.invoke(device, (Object[])null);
                } catch (Exception var8) {
                    var8.printStackTrace();
                }
            }

        }
    }

    /** @deprecated */
    public InputStream getInputStream() throws IOException {
        if (this.socket != null) {
            this.is = this.socket.getInputStream();
        }

        return this.is;
    }

    /** @deprecated */
    public OutputStream getOutputStream() throws IOException {
        if (this.socket != null) {
            this.os = this.socket.getOutputStream();
        }

        return this.os;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public int getModel() {
        byte[] ESCv = new byte[]{27, 118};
        byte[] szRep = new byte[256];

        try {
            this.os.write(ESCv);
            this.os.flush();
            Thread.sleep(100L);
            int iLen = this.is.read(szRep);
            if (iLen > 0) {
                String strModel = new String(szRep, 0, iLen);
                if (strModel.indexOf("LK-P") != -1) {
                    return 2;
                } else if (strModel.indexOf("LK-B30") != -1) {
                    return 1;
                } else {
                    return strModel.indexOf("B30") != -1 ? 1 : 0;
                }
            } else {
                return -1;
            }
        } catch (Exception var5) {
            return -1;
        }
    }
}
