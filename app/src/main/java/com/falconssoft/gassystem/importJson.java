package com.falconssoft.gassystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.falconssoft.gassystem.Modle.Customer;
import com.falconssoft.gassystem.Modle.Remarks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;



public class importJson {

    private Context context;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialogSave;
    private JSONObject obj;
    DatabaseHandler dbHandler;
    String itemCode;
    String JsonResponseSave;
    String JsonResponseAssetsSave;
    String JsonResponseSaveUnite;
    String JsonResponseSaveQRCode;
    String JsonResponseSaveSwitch;
    SweetAlertDialog pd = null;
          String isAssetsIn,ip,QrUse;
          GlobelFunction globelFunction;


    public importJson(Context context, int is) {//, JSONObject obj
//        this.obj = obj;
        this.context = context;
        dbHandler = new DatabaseHandler(context);
        globelFunction=new GlobelFunction();
        this.ip=globelFunction.GlobelFunctionSetting(dbHandler);
        if(is!=0) {
            pd = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText("ImportData");
            pd.setCancelable(false);
            pd.show();
        }
//        progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);

    }

    public void startSending(String flag) {
//        Log.e("check",flag);
//        final List<MainSetting> mainSettings = dbHandler.getAllMainSetting();
//        if (mainSettings.size() != 0) {
//            this.ip = mainSettings.get(0).getIP();
//            this.isAssetsIn = mainSettings.get(0).getIsAssest();
//            this.QrUse = mainSettings.get(0).getIsQr();
//        }

        if (flag.equals("GET_CUSTOMER"))
            new SyncCustomer().execute();

        if (flag.equals("GET_REMARK"))
            new SyncRemark().execute();
//
//        if (flag.equals("ItemPrice"))
//            new SyncItemPrice().execute();
//        if (flag.equals("GetStory"))
//            new SyncGetStor().execute();
//
//        if (flag.equals("GetAssest")){
//
//            new SyncGetAssest().execute();}
//
//        if (flag.equals("itemUnite")){
//
//            new SyncItemUnite().execute();}
//
//        if (flag.equals("gETiTEM")){
//            new SyncGetItem().execute();
//        }
//
//        if (flag.equals("SyncItemQR")){
//            new SyncItemQR().execute();
//        }




    }

    private class SyncCustomer extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {

//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();
//            pd = ProgressDialog.show(context, "title", "loading", true);
            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText("يتم استيراد ال Customer ");


            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
            try {

                String link = "http://"+ip + "/GetCustomers";
                Log.e("ipAdress", "ip -->" + ip);

                // ITEM_CARD
//                String max=dbHandler.getMaxInDate("ITEM_CARD");

//                String maxInDate="";
//                if(max.equals("-1")) {
//                    maxInDate="05/03/1991";
//                }else{
//                    maxInDate=max.substring(0,10);
//                    String date[]=maxInDate.split("-");
//                    maxInDate=date[2]+"/"+date[1]+"/"+date[0];
//                    Log.e("split ",""+maxInDate);
//                }
//                String data = "MAXDATE=" + URLEncoder.encode(maxInDate, "UTF-8")  ;
//////

                URL url = new URL(link);
                Log.e("urlString = ",""+url.toString());

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("POST");



//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag_customer", "GetCustomers -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            if (JsonResponse != null && JsonResponse.contains("COUNTERNO")) {
                Log.e("COUNTERNO", "****Success");
//                progressDialog.dismiss();
                JsonResponseSave=JsonResponse;
                 new SaveCustomer().execute();
//                try {
//
//                    JSONArray parentArray = new JSONArray(JsonResponse);
//
//
//                    List<ItemCard> itemCard=new ArrayList<>();
//
//                    for (int i = 0; i < parentArray.length(); i++) {
//                        JSONObject finalObject = parentArray.getJSONObject(i);
//// "ItemOCode": "0829274512282",
////    "ItemNameA": "MEOW MIX CUPS PATE WHITE FISH & SALMON 78G*12",
////    "ItemNameE": "MEOW MIX CUPS PATE WHITE FISH & SALMON 78G*12",
////    "ItemG": "Grocery-(بقالة)",
////    "TAXPERC": "16",
////    "SalePrice": "0",
////    "LLCPrice": "0.73",
////    "AVLQTY": "0",
////    "F_D": "1",
////    "ItemK": "مستلزمات الحيوانات",
////    "ItemL": "",
////    "ITEMDIV": "",
////    "ITEMGS": ""
//
//
//
//                        ItemCard obj = new ItemCard();
//                        obj.setItemCode(finalObject.getString("ItemOCode"));
//                        obj.setItemName(finalObject.getString("ItemNameA"));
////                        obj.setit(finalObject.getString("ItemNameE"));
//                        obj.setItemG(finalObject.getString("ItemG"));
////                        obj.set(finalObject.getString("TAXPERC"));
//                        obj.setSalePrc(finalObject.getString("SalePrice"));
////
//
////                        obj.set(finalObject.getString("LLCPrice"));
//                        obj.setAVLQty(finalObject.getString("AVLQTY"));
//                        obj.setFDPRC(finalObject.getString("F_D"));
//
//                        obj.setItemK(finalObject.getString("ItemK"));
//                        obj.setItemL(finalObject.getString("ItemL"));
//                        obj.setItemDiv(finalObject.getString("ITEMDIV"));
//
//                        obj.setItemGs(finalObject.getString("ITEMGS"));
//
//
//                        itemCard.add(obj);
//
//                    }
//
////
//                    dbHandler.deleteAllItem("ITEM_CARD");
//                    for (int i = 0; i < itemCard.size(); i++) {
//                        dbHandler.addItemcardTable(itemCard.get(i));
//                    }
//
//Log.e("tag_itemCard", "****saveSuccess");
////                    intentControl.setText("@");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }else if (JsonResponse != null && JsonResponse.contains("No Data Found.")){
                new SyncRemark().execute();
            }
            else {
                Log.e("tag_itemCard", "****Failed to export data");
//                Toast.makeText(context, "Failed to Get data", Toast.LENGTH_SHORT).show();
                if(pd!=null) {
                    pd.dismiss();
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("استيراد Customer")
                            .setContentText("فشل استيراد ال Customer")
                            .show();
                }


            }

        }
    }

    private class SyncRemark extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText("يتم استيراد Remark");

        }

        @Override
        protected String doInBackground(String... params) {
            try {


//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link = "http://"+ip + "/GetNotes";
                Log.e("ipAdress", "ip -->" + ip);

                // ITEM_CARD
//                String max=dbHandler.getMaxInDate("ITEM_SWITCH");
//                String maxInDate="";
//                if(max.equals("-1")) {
//                    maxInDate="05/03/2020";
//                }else{
//                    maxInDate=max.substring(0,10);
//                    String date[]=maxInDate.split("-");
//                    maxInDate=date[2]+"/"+date[1]+"/"+date[0];
//                    Log.e("splitSwitch ",""+maxInDate);
//                }
//                String data = "MAXDATE=" + URLEncoder.encode(maxInDate, "UTF-8");
////
                URL url = new URL(link);


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
//                Log.e("url____",""+link+data);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemSwitch -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            if (JsonResponse != null && JsonResponse.contains("REMARKBODY")) {
                Log.e("REMARKBODY", "****Success");

                JsonResponseSaveSwitch=JsonResponse;
                new SaveRemark().execute();

            }
            else if (JsonResponse != null && JsonResponse.contains("No Data Found.")){
//                new SyncItemUnite().execute();
                pd.dismissWithAnimation();

            }else {
                Log.e("TAG_itemSwitch", "****Failed to export data");
//                progressDialog.dismiss();
                if(pd!=null) {
                    pd.dismiss();
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("استيراد Remark")
                            .setContentText("فشل استيراد Remark")
                            .show();
                }
            }

        }
    }
//
//    private class SyncItemQR extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//        private HttpURLConnection urlConnection = null;
//        private BufferedReader reader = null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            progressDialog = new ProgressDialog(context,R.style.MyTheme);
////            progressDialog.setCancelable(false);
////            progressDialog.setMessage("Loading...");
////            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////            progressDialog.setProgress(0);
////            progressDialog.show();
//
//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importItemQR));
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//
//
////                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
////                String ip="";
////                if(mainSettings.size()!=0) {
////                    ip=mainSettings.get(0).getIP();
////                }
//                String link = "http://"+ip + "/GetEXPIRYVIEW";
//
//                // ITEM_CARD
//                String max=dbHandler.getMaxInDate("ITEM_SWITCH");
//                String maxInDate="";
//                if(max.equals("-1")) {
//                    maxInDate="05/03/2020";
//                }else{
//                    maxInDate=max.substring(0,10);
//                    String date[]=maxInDate.split("-");
//                    maxInDate=date[2]+"/"+date[1]+"/"+date[0];
//                    Log.e("splitSwitch ",""+maxInDate);
//                }
////                String data = "MAXDATE=" + URLEncoder.encode(maxInDate, "UTF-8");
//////
//                URL url = new URL(link);
//
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("POST");
//
////                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
////                wr.writeBytes(data);
////                wr.flush();
////                wr.close();
////                Log.e("url____",""+link+data);
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//                StringBuffer stringBuffer = new StringBuffer();
//
//
//                    while ((JsonResponse = bufferedReader.readLine()) != null) {
//                        try {
//                        stringBuffer.append(JsonResponse + "\n");
//                    }catch (Exception ex){
//                        Log.e("QRJsonResponse","Long JsonResponse "+ex.toString());
//                    }
//                    }
//
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//
//                Log.e("tag", "TAG_itemQR -->" + stringBuffer.toString());
//
//                return stringBuffer.toString();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e("tag", "Error closing stream", e);
//                    }
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String JsonResponse) {
//            super.onPostExecute(JsonResponse);
//
//            if (JsonResponse != null && JsonResponse.contains("STRNO")) {
//                Log.e("TAG_item_qr", "****Success");
//
//                JsonResponseSaveQRCode=JsonResponse;
//                new SaveItemQr().execute();
//
//            }
//            else if (JsonResponse != null && JsonResponse.contains("No Data Found.")){
//                new SyncItemUnite().execute();
//            }else {
//                Log.e("TAG_itemSwitch", "****Failed to export data");
////                progressDialog.dismiss();
//                if(pd!=null) {
//                    pd.dismiss();
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.falidedimportItemQR))
//                            .show();
//                }
//            }
//
//        }
//    }
//
//
//    private class SyncItemUnite extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//        private HttpURLConnection urlConnection = null;
//        private BufferedReader reader = null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            progressDialog = new ProgressDialog(context,R.style.MyTheme);
////            progressDialog.setCancelable(false);
////            progressDialog.setMessage("Loading...");
////            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////            progressDialog.setProgress(0);
////            progressDialog.show();
//
//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText("import item Unit");
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//
//
////                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
////                String ip="";
////                if(mainSettings.size()!=0) {
////                    ip=mainSettings.get(0).getIP();
////                }
//                String link = "http://"+ip + "/GetJRDITEMUNIT";
//
//                // ITEM_CARD
//                String max=dbHandler.getMaxInDate("ITEM_UNITS");
//                String maxInDate="";
//                if(max.equals("-1")) {
//                    maxInDate="05/03/2020";
//                }else{
//                    maxInDate=max.substring(0,10);
//                    String date[]=maxInDate.split("-");
//                    maxInDate=date[2]+"/"+date[1]+"/"+date[0];
//                    Log.e("splitSwitch ",""+maxInDate);
//                }
//                String data = "MAXDATE=" + URLEncoder.encode(maxInDate, "UTF-8");
//////
//                URL url = new URL(link);
//
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("POST");
//
//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
//                Log.e("url____",""+link+data);
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//                StringBuffer stringBuffer = new StringBuffer();
//
//                while ((JsonResponse = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(JsonResponse + "\n");
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//
//                Log.e("tag", "TAG_itemSwitch -->" + stringBuffer.toString());
//
//                return stringBuffer.toString();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e("tag", "Error closing stream", e);
//                    }
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String JsonResponse) {
//            super.onPostExecute(JsonResponse);
//
//            if (JsonResponse != null && JsonResponse.contains("ITEMOCODE")) {
//                Log.e("TAG_itemUnite", "****Success");
//
//                JsonResponseSaveUnite=JsonResponse;
//                new SaveItemUnite().execute();
//
//            }
//            else if (JsonResponse != null && JsonResponse.contains("No Data Found.")){
//                new SyncGetStor().execute();
//            }else {
//                Log.e("TAG_itemSwitch", "****Failed to export data");
////                progressDialog.dismiss();
//                if(pd!=null) {
//                    pd.dismiss();
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText("Fali to import Item Unit")
//                            .show();
//                }
//            }
//
//        }
//    }
//
//    private class SyncItemPrice extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//        private HttpURLConnection urlConnection = null;
//        private BufferedReader reader = null;
//        SweetAlertDialog pdItem=null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            progressDialog = new ProgressDialog(context,R.style.MyTheme);
////            progressDialog.setCancelable(false);
////            progressDialog.setMessage("Loading...");
////            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////            progressDialog.setProgress(0);
////            progressDialog.show();
//
//             pdItem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            pdItem.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pdItem.setTitleText(context.getResources().getString(R.string.itemprice));
//            pdItem.setCancelable(false);
//            pdItem.show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip= mainSettings.get(0).getIP();
//                }
//                String link = "http://"+ip + "/GetJRDITEMPRICE";
////                String link = controll.URL + "GetJRDITEMPRICE";
////
//                String data = "ITEMCODE=" + URLEncoder.encode(itemCode, "UTF-8") ;
//
////
//                URL url = new URL(link);
//                Log.e("TAG_itemPrice", "link -->" +link);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("POST");
//
//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//                StringBuffer stringBuffer = new StringBuffer();
//
//                while ((JsonResponse = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(JsonResponse + "\n");
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//
//                Log.e("tag", "TAG_itemPrice -->" + stringBuffer.toString());
//
//                return stringBuffer.toString();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e("tag", "Error closing stream", e);
//                    }
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String JsonResponse) {
//            super.onPostExecute(JsonResponse);
//
//            if (JsonResponse != null && JsonResponse.contains("F_D")) {
//                Log.e("TAG_itemPrice", "****Success");
//
//                try {
//
//                    JSONArray parentArray = new JSONArray(JsonResponse);
//
//                    Log.e("TAG_itemPrice", " "+parentArray.toString());
//                    Log.e("TAG_itemPriceR", " "+JsonResponse);
//                    for (int i = 0; i < parentArray.length(); i++) {
//                        JSONObject finalObject = parentArray.getJSONObject(i);
//
//
//                        controll.F_D= finalObject.getString("F_D");
//                        controll.Item_name= finalObject.getString("ItemNameA");
//                        textView.setText(controll.F_D);
//                        textItemName.setText(controll.Item_name);
//                                Log.e("TAG_itemPrice", "****getSuccess"+controll.F_D+"name= "+ controll.Item_name);
//
//                    }
//
//                    if(pdItem !=null) {
//                        pdItem.dismissWithAnimation();
////                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
////                                .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
////                                .setContentText(context.getResources().getString(R.string.importSuc))
////                                .show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            } else if(JsonResponse != null && JsonResponse.contains("No Parameter Found.")){
//                Log.e("TAG_itemPrice", "****No Parameter Found.");
//
//                if(pdItem !=null) {
//                    pdItem.dismissWithAnimation();
//                }
//
//                textView.setText("*");
//
//
//
//            } else {
//                Log.e("TAG_itemPrice", "****Failed to export data");
//
//                if(pdItem !=null) {
//                    pdItem.dismissWithAnimation();
//                }
//
//                textView.setText("-1");
//            }
////            progressDialog.dismiss();
//        }
//    }
//
//    private class SyncGetItem extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//        private HttpURLConnection urlConnection = null;
//        private BufferedReader reader = null;
//        SweetAlertDialog pdItem=null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            progressDialog = new ProgressDialog(context,R.style.MyTheme);
////            progressDialog.setCancelable(false);
////            progressDialog.setMessage("Loading...");
////            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////            progressDialog.setProgress(0);
////            progressDialog.show();
//
//            pdItem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            pdItem.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pdItem.setTitleText("Item  ");
//            pdItem.setCancelable(false);
//            pdItem.show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="",STORE="";
//                if(mainSettings.size()!=0) {
//                    ip= mainSettings.get(0).getIP();
//                    STORE=mainSettings.get(0).getStorNo();
//                }
//                String link = "http://"+ip + "/GetItemInfo";
////                String link = controll.URL + "GetJRDITEMPRICE";
////
//                String data = "ITEMCODE=" + URLEncoder.encode(itemCode, "UTF-8")+"&"
//                        +"STORENO=" + URLEncoder.encode(STORE, "UTF-8") ;
//
////
//                URL url = new URL(link);
//                Log.e("TAG_itemPrice", "link -->" +link+"     -->"+data);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("POST");
//
//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//                StringBuffer stringBuffer = new StringBuffer();
//
//                while ((JsonResponse = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(JsonResponse + "\n");
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//
//                Log.e("tag", "TAG_itemPrice -->" + stringBuffer.toString());
//
//                return stringBuffer.toString();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e("tag", "Error closing stream", e);
//                    }
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String JsonResponse) {
//            super.onPostExecute(JsonResponse);
//
//            if (JsonResponse != null && JsonResponse.contains("ItemNameA")) {
//                Log.e("TAG_itemPrice", "****Success");
//
//                try {
//
//                    JSONArray parentArray = new JSONArray(JsonResponse);
//
//                    Log.e("TAG_itemPrice", " "+parentArray.toString());
//                    Log.e("TAG_itemPriceR", " "+JsonResponse);
//                    for (int i = 0; i < parentArray.length(); i++) {
//                        JSONObject finalObject = parentArray.getJSONObject(i);
//
//
//                        String itemName= finalObject.getString("ItemNameA");
//                        String qty= finalObject.getString("REALQTY");
//                        textViewUpdate.setText(qty);
//                        textItemNameUpdate.setText(itemName);
//                        Log.e("TAG_itemPrice", "****getSuccess"+qty+"name= "+ itemName);
//
//                    }
//
//                    if(pdItem !=null) {
//                        pdItem.dismissWithAnimation();
////                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
////                                .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
////                                .setContentText(context.getResources().getString(R.string.importSuc))
////                                .show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            } else if(JsonResponse != null && JsonResponse.contains("No Parameter Found.")){
//                Log.e("TAG_itemPrice", "****No Parameter Found.");
//
//                if(pdItem !=null) {
//                    pdItem.dismissWithAnimation();
//                }
//
//                textViewUpdate.setText("*");
//
//
//
//            } else {
//                Log.e("TAG_itemPrice", "****Failed to export data");
//
//                if(pdItem !=null) {
//                    pdItem.dismissWithAnimation();
//                }
//
//                textViewUpdate.setText("-1");
//            }
////            progressDialog.dismiss();
//        }
//    }
//
//    private class SyncGetStor extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//        private HttpURLConnection urlConnection = null;
//        private BufferedReader reader = null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            progressDialog = new ProgressDialog(context,R.style.MyTheme);
////            progressDialog.setCancelable(false);
////            progressDialog.setMessage("Loading...");
////            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////            progressDialog.setProgress(0);
////            progressDialog.show();
//
//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//
////
////                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
////                String ip="";
////                if(mainSettings.size()!=0) {
////                    ip=mainSettings.get(0).getIP();
////                }
//                String link = "http://"+ip + "/GetSore";
//
//                //
////                String data = "compno=" + URLEncoder.encode("736", "UTF-8") + "&" +
////                        "compyear=" + URLEncoder.encode("2019", "UTF-8") ;
//////
//                URL url = new URL(link);
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("POST");
//
////                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
////                wr.writeBytes(data);
////                wr.flush();
////                wr.close();
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//                StringBuffer stringBuffer = new StringBuffer();
//
//                while ((JsonResponse = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(JsonResponse + "\n");
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//
//                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());
//
//                return stringBuffer.toString();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e("tag", "Error closing stream", e);
//                    }
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String JsonResponse) {
//            super.onPostExecute(JsonResponse);
//
//            if (JsonResponse != null && JsonResponse.contains("STORENO")) {
//                Log.e("TAG_GetStor", "****Success");
//
//                pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
//                pd.setTitleText(context.getResources().getString(R.string.storesave));
//                try {
//
//                    JSONArray parentArray = new JSONArray(JsonResponse);
//
//
//                    List<Stk> stks=new ArrayList<>();
//
//                    for (int i = 0; i < parentArray.length(); i++) {
//                        JSONObject finalObject = parentArray.getJSONObject(i);
//
//                        Stk obj = new Stk();
//
//                        obj.setStkNo(finalObject.getString("STORENO"));
//                        obj.setStkName(finalObject.getString("STORENAME"));
//
//
//                        stks.add(obj);
//
//                    }
//
////
//                    dbHandler.deleteAllItem("STK");
//                    for (int i = 0; i < stks.size(); i++) {
//                        dbHandler.addStory(stks.get(i));
//                    }
//
//                    Log.e("TAG_GetStor", "****SaveSuccess");
//                    pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                    pd.setTitleText(context.getResources().getString(R.string.storeSave));
//
//
//                       if(!isAssetsIn.equals("1")) {
//                           if(pd!=null) {
//                           pd.dismiss();
//
//                           new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                                   .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
//                                   .setContentText(context.getResources().getString(R.string.importSuc))
//                                   .show();
//                       }
//
//                    }else{
//                        pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                        pd.setTitleText(context.getResources().getString(R.string.storeSave));
//                        new SyncGetAssest().execute();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            } else {
//                Log.e("TAG_GetStor", "****Failed to export data");
//                if(!isAssetsIn.equals("1")) {
//                if (pd != null) {
//                    pd.dismiss();
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.faildstore))
//                            .show();
//                }
//            }else{
//                    pd.dismiss();
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.faildstore))
//                            .show();
//                    new SyncGetAssest().execute();
//                }
//            }
////            progressDialog.dismiss();
//
//        }
//    }
//
//    private class SyncGetAssest extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//        private HttpURLConnection urlConnection = null;
//        private BufferedReader reader = null;
//
//        @Override
//        protected void onPreExecute() {
//
////            progressDialog = new ProgressDialog(context,R.style.MyTheme);
////            progressDialog.setCancelable(false);
////            progressDialog.setMessage("Loading...");
////            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////            progressDialog.setProgress(0);
////            progressDialog.show();
////            pd = ProgressDialog.show(context, "title", "loading", true);
//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importassets));
//
//
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
//            try {
////                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
////                String ip="";
////                if(mainSettings.size()!=0) {
////                    ip= mainSettings.get(0).getIP();
////                }
//
////
//                String link = "http://"+ip + "/GETASSETS";
//
//
//                URL url = new URL(link);
//                Log.e("urlStringGETASSEST = ",""+url.toString());
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("POST");
//
//
////
////                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
////                wr.writeBytes(data);
////                wr.flush();
////                wr.close();
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//                StringBuffer stringBuffer = new StringBuffer();
//
//                while ((JsonResponse = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(JsonResponse + "\n");
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//
//                Log.e("tag", "GETASSEST -->" + stringBuffer.toString());
//
//                return stringBuffer.toString();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e("tag", "Error closing stream", e);
//                    }
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String JsonResponse) {
//            super.onPostExecute(JsonResponse);
//
//            if (JsonResponse != null && JsonResponse.contains("TYPENO")) {
//                Log.e("Assets", "****Success");
//                JsonResponseAssetsSave=JsonResponse;
//                new SaveItemAssets().execute();
//
//
//            }else if (JsonResponse != null && JsonResponse.contains("No Data Found.")){
//                Log.e("ASSETS", "****No Data Found.");
//            }
//            else {
//                Log.e("ASSETS", "****Failed to export data");
////                Toast.makeText(context, "Failed to Get data", Toast.LENGTH_SHORT).show();
//                if(pd!=null) {
//                    pd.dismiss();
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.fildtoimportitemAssets))
//                            .show();
//                }
//
//
//            }
//
//        }
//    }
//

    private class SaveCustomer extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialogSave = new ProgressDialog(context,R.style.MyTheme);
//            progressDialogSave.setCancelable(false);
//            progressDialogSave.setMessage("Loading Save in DataBase...");
//            progressDialogSave.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialogSave.setProgress(0);
//            progressDialogSave.show();

            pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
            pd.setTitleText("*يتم حفظ ال Customer *");

        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019

            try {
                Log.e("tag_itemCard", "****inDataBaseSave");
                JSONArray parentArray = new JSONArray(JsonResponseSave);


                List<Customer> itemCard=new ArrayList<>();
//                List<Customer> itemCard2=dbHandler.getAllCustomers();
//                boolean stopBollen=true;
//if(itemCard2.size()==0){
//    dbHandler.deleteAllItem("ITEM_CARD");
//    stopBollen=false;
//}

                dbHandler.deleteCustomer();
//{"COUNTERNO":"1401461215","ACCNO":"1110010237","CUSTOMERNAME":"محمد سمير الغزاوي","GASPRESSURE":".037","GPRICE":"538.880",
// "PRJECTNAME":"سمير الغزاوي","IS_PER":"1","BDLVAL":"4","REMARKS":"","CUSTSTS":"0","CUSTTYPE":"0","LASTREADER":"0"}

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    Customer obj = new Customer();
                    obj.setCounterNo(finalObject.getString("COUNTERNO"));
                    obj.setAccNo(finalObject.getString("ACCNO"));
                    obj.setCustName(finalObject.getString("CUSTOMERNAME"));
                    obj.setGasPressure(Double.parseDouble(finalObject.getString("GASPRESSURE")));
                    obj.setgPrice(Double.parseDouble(finalObject.getString("GPRICE")));
                    obj.setProjectName(finalObject.getString("PRJECTNAME"));
                    obj.setIsPer(Integer.parseInt(finalObject.getString("IS_PER")));
                    obj.setBadalVal(Double.parseDouble(finalObject.getString("BDLVAL")));
                    obj.setCredet(Double.parseDouble(finalObject.getString("CREDIT")));
//                    obj.setno(finalObject.getString("REMARKS"));
                    obj.setCustSts(Integer.parseInt(finalObject.getString("CUSTSTS")));
//                    obj.setCustame(finalObject.getString("CUSTTYPE"));
                    obj.setLastRead(Double.parseDouble(finalObject.getString("LASTREADER")));

                    itemCard.add(obj);
                    dbHandler.addCustomer(obj);
//                    if(stopBollen){

//                    }

//                    dbHandler.addItemcardTable(itemCard.get(i));

                }


//
//                dbHandler.deleteAllItem("ITEM_CARD");
//
//                for (int i = 0; i < itemCard.size(); i++) {
//                    dbHandler.deleteItemCardByItemCode(itemCard.get(i).getItemCode());
//                    dbHandler.addItemcardTable(itemCard.get(i));
//
//                }
//                dbHandler.addItemcardTableTest(itemCard);

                Log.e("tag_itemCard", "****saveSuccess");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);
//            Toast.makeText(context, "Save Item Card Success", Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, "Start Import Item Switch", Toast.LENGTH_SHORT).show();
            pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pd.setTitleText("* تم حفظ ال customer table بنجاح *");

            new SyncRemark().execute();

//            progressDialog.dismiss();
        }
    }


//
    private class SaveRemark extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialogSave = new ProgressDialog(context,R.style.MyTheme);
//            progressDialogSave.setCancelable(false);
//            progressDialogSave.setMessage("Loading Save in DataBase...");
//            progressDialogSave.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialogSave.setProgress(0);
//            progressDialogSave.show();
            pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
            pd.setTitleText(" * يتم حفظ ال remark table *");

        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019


                Log.e("TAG_itemSwitch", "****Success");

                try {

                    JSONArray parentArray = new JSONArray(JsonResponseSaveSwitch);

                    List<Remarks> remark=new ArrayList<>();

                    dbHandler.deleteRemark();
                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);

                        Remarks obj = new Remarks();
//                        obj.se(finalObject.getString("CoNo"));
                        obj.setBody(finalObject.getString("REMARKBODY"));
                        obj.setTitle(finalObject.getString("REMARKTITLE"));


                        remark.add(obj);
                        dbHandler.addRemark(obj);

                    }
//                    dbHandler.deleteAllItem("ITEM_SWITCH");

//
//                    dbHandler.deleteAllItem("ITEM_SWITCH");
//                    for (int i = 0; i < itemCard.size(); i++) {
//                        dbHandler.deleteItemSwitchByItemCode(itemCard.get(i).getItemOCode(),itemCard.get(i).getItemNCode());
//                        dbHandler.addItemSwitch(itemCard.get(i));
//                    }

                    Log.e("TAG_itemSwitch", "****SaveSuccess");



                } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pd.setTitleText("* تم حفظ ال remark table بنجاح *");

            pd.dismissWithAnimation();


        }
    }
//
//    private class SaveItemQr extends AsyncTask<String, String, String> {
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
//            pd.setTitleText(context.getResources().getString(R.string.saveItemQr));
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//
//            Log.e("TAG_itemQR", "****Success");
//
//            try {
//
//                JSONArray parentArray = new JSONArray(JsonResponseSaveQRCode);
//
//
//                List<ItemQR> itemQRS=new ArrayList<>();
//
//                for (int i = 0; i < parentArray.length(); i++) {
//                    JSONObject finalObject = parentArray.getJSONObject(i);
//
//                    ItemQR obj = new ItemQR();
//
//                    obj.setStoreNo(finalObject.getString("STRNO"));
//                    obj.setItemCode(finalObject.getString("ITEMCODE"));
//                    obj.setItemNmae(finalObject.getString("ITEMNAME"));
//                    obj.setSalesPrice(finalObject.getString("PRICE"));
//                    obj.setQrCode(finalObject.getString("QRCODE"));
//                    obj.setLotNo(finalObject.getString("LOTNUMBER"));
//
//                    itemQRS.add(obj);
//                    dbHandler.deleteItemQRByItemCode(itemQRS.get(i).getItemCode(),itemQRS.get(i).getQrCode());
//
//                }
//
//                dbHandler.addItemQRList(itemQRS);
//                Log.e("TAG_itemQR", "****SaveSuccess");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String JsonResponse) {
//            super.onPostExecute(JsonResponse);
//
//            pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pd.setTitleText(context.getResources().getString(R.string.saveitemQR));
//            new SyncItemUnite().execute();
//        }
//    }
//
//
//    private class SaveItemUnite extends AsyncTask<String, String, String> {
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            progressDialogSave = new ProgressDialog(context,R.style.MyTheme);
////            progressDialogSave.setCancelable(false);
////            progressDialogSave.setMessage("Loading Save in DataBase...");
////            progressDialogSave.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////            progressDialogSave.setProgress(0);
////            progressDialogSave.show();
//            pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
//            pd.setTitleText("Save Item Unite");
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
//
//
//            Log.e("TAG_itemSwitch", "****Success");
//
//            try {
//
//                JSONArray parentArray = new JSONArray(JsonResponseSaveUnite);
//
//
//                List<ItemUnit> itemCard=new ArrayList<>();
//
//                for (int i = 0; i < parentArray.length(); i++) {
//                    JSONObject finalObject = parentArray.getJSONObject(i);
//
//
//
//
////                    {"ITEMOCODE":"1002839","ITEMBARCODE":"4800528456282","SALEPRICE":"1.89","ITEMU":"حبة","UQTY":"1","USERIAL":"1","CALCQTY":"1","WHOLESALEPRC":"0","PURCHASEPRICE":"0","PCLASS1":"0","PCLASS2":"0","PCLASS3":"0","INDATE":"12\/28\/2019 10:30:45 AM","UNIT_NAME":"شعيرية بيهون الخاصة هوبي 454 غم","ORG_SALEPRICE":"","OLD_SALE_PRICE":"","UPDATE_DATE":"12\/28\/2019 10:30:45 AM"}
////
////[{"ITEMOCODE":"6251001212648","ITEMBARCODE":"6251001212648","SALEPRICE":"2","ITEMU":"حبة","UQTY":"1","USERIAL":"1","CALCQTY":"1","WHOLESALEPRC":"2","PURCHASEPRICE":"0","PCLASS1":"","PCLASS2":"","PCLASS3":"","INDATE":"12\/08\/2020 4:44:53 PM","UNIT_NAME":"AAA","ORG_SALEPRICE":"0","OLD_SALE_PRICE":"0","UPDATE_DATE":""}]
//
//
//                    ItemUnit obj = new ItemUnit();
//
//                    obj.setItemOCode(finalObject.getString("ITEMOCODE"));
//                    obj.setItemBarcode(finalObject.getString("ITEMBARCODE"));
//
//                    try {
//                        obj.setSalePrice(Float.parseFloat(finalObject.getString("SALEPRICE")));
//
//
//                    }catch (Exception ex){
//                        obj.setSalePrice(0);
//                        Log.e("setSalePrice",""+ex.toString());
//                    }
//
//                    obj.setItemU(finalObject.getString("ITEMU"));
//
//                    try {
//                        obj.setUQty(Float.parseFloat(finalObject.getString("UQTY")));
//
//                    }catch (Exception ex){
//                        obj.setUQty(0);
//                        Log.e("setUQty",""+ex.toString());
//                    }
//
//                    try {
//                        obj.setUSerial(Integer.parseInt(finalObject.getString("USERIAL")));
//
//                    }catch (Exception ex){
//                        obj.setUSerial(0);
//                        Log.e("setUSerial",""+ex.toString());
//                    }
//
//
//                    try {
//                        obj.setCalcQty(Float.parseFloat(finalObject.getString("CALCQTY")));
//
//                    }catch (Exception ex){
//                        obj.setCalcQty(0);
//                        Log.e("setCalcQty",""+ex.toString());
//                    }
//
//                    try {
//                        obj.setWholeSalePrc(Float.parseFloat(finalObject.getString("WHOLESALEPRC")));
//
//                    }catch (Exception ex){
//                        obj.setWholeSalePrc(0);
//                        Log.e("setWholeSalePrc",""+ex.toString());
//                    }
//
//                    try {
//                        obj.setPurchasePrc(Float.parseFloat(finalObject.getString("PURCHASEPRICE")));
//
//                    }catch (Exception ex){
//                        obj.setPurchasePrc(0);
//                        Log.e("setPurchasePrc",""+ex.toString());
//                    }
//
//
//                    try {
//                        obj.setPclAss1(Float.parseFloat(finalObject.getString("PCLASS1")));
//
//                    }catch (Exception ex){
//                        obj.setPclAss1(0);
//                        Log.e("setPclAss1",""+ex.toString());
//                    }
//
//
//                    try {
//                        obj.setPclAss2(Float.parseFloat(finalObject.getString("PCLASS2")));
//
//                    }catch (Exception ex){
//                        obj.setPclAss2(0);
//                        Log.e("setPclAss2",""+ex.toString());
//                    }
//                    try {
//                        obj.setPclAss3(Float.parseFloat(finalObject.getString("PCLASS3")));
//                    }catch (Exception ex){
//                        obj.setPclAss3(0);
//                        Log.e("setPclAss3",""+ex.toString());
//                    }
//
//                    obj.setInDate(finalObject.getString("INDATE"));
//                    obj.setUnitName(finalObject.getString("UNIT_NAME"));
//                    obj.setOrgSalePrice(finalObject.getString("ORG_SALEPRICE"));
//
//                    obj.setOldSalePrice(finalObject.getString("OLD_SALE_PRICE"));
//                    obj.setUpdateDate(finalObject.getString("UPDATE_DATE"));
//
//
//                    itemCard.add(obj);
//                    dbHandler.deleteItemUniteByItemCode(itemCard.get(i).getItemOCode(),itemCard.get(i).getItemBarcode());
//
//                }
////                    dbHandler.deleteAllItem("ITEM_SWITCH");
//                dbHandler.addItemUniteTable(itemCard);
////
////                    dbHandler.deleteAllItem("ITEM_SWITCH");
////                    for (int i = 0; i < itemCard.size(); i++) {
////                        dbHandler.deleteItemSwitchByItemCode(itemCard.get(i).getItemOCode(),itemCard.get(i).getItemNCode());
////                        dbHandler.addItemSwitch(itemCard.get(i));
////                    }
//
//                Log.e("TAG_itemSwitch", "****SaveSuccess");
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String JsonResponse) {
//            super.onPostExecute(JsonResponse);
////            Toast.makeText(context, "Save Item Card Success", Toast.LENGTH_SHORT).show();
////            Toast.makeText(context, "Start Import Item Switch", Toast.LENGTH_SHORT).show();
////
//            pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pd.setTitleText("Save Item Unite In DataBase");
//            new SyncGetStor().execute();
////            progressDialog.dismiss();
//        }
//    }
//
//
//    private class SaveItemAssets extends AsyncTask<String, String, String> {
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            progressDialogSave = new ProgressDialog(context,R.style.MyTheme);
////            progressDialogSave.setCancelable(false);
////            progressDialogSave.setMessage("Loading Save in DataBase...");
////            progressDialogSave.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////            progressDialogSave.setProgress(0);
////            progressDialogSave.show();
//
//            pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
//            pd.setTitleText(context.getResources().getString(R.string.AssetsSave));
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
//
//            try {
//                Log.e("Assets", "****inDataBaseSave");
//                JSONArray parentArray = new JSONArray(JsonResponseAssetsSave);
//
//
//                List<AssestItem> assestItems=new ArrayList<>();
////                boolean stopBollen=true;
////if(itemCard2.size()==0){
////    dbHandler.deleteAllItem("ITEM_CARD");
////    stopBollen=false;
////}
//                    dbHandler.deleteAllItem("ASSEST_TABLE");
//
//
//                for (int i = 0; i < parentArray.length(); i++) {
//                    JSONObject finalObject = parentArray.getJSONObject(i);
//
////                    ظظ"MAINMNG":"ادارة","DEPARTMENT":"دائرة","SECTION":"القسم","AREANAME":"الموقع"
//                    AssestItem obj = new AssestItem();
//                    obj.setAssesstType(finalObject.getString("TYPE"));
//                    obj.setAssesstNo(finalObject.getString("TYPENO"));
//                    obj.setAssesstCode(finalObject.getString("CODE"));
//                    obj.setAssesstName(finalObject.getString("NAME"));
//                    obj.setAssesstAREANAME(finalObject.getString("AREANAME"));
//                    obj.setAssesstSECTION(finalObject.getString("SECTION"));
//                    obj.setAssesstDEPARTMENT(finalObject.getString("DEPARTMENT"));
//                    obj.setAssesstMangment(finalObject.getString("MAINMNG"));
//                    obj.setAssesstBarcode(finalObject.getString("ASSETBARCODE"));
//
////
//                    assestItems.add(obj);
////                    if(stopBollen){
////                    dbHandler.deleteItemCardByItemCode(assestItems.get(i).getItemCode());
////                    }
//
////                    dbHandler.addItemcardTable(itemCard.get(i));
//
//
//
//                }
//
//                dbHandler.addAssetsItemList(assestItems);
//
////
////                dbHandler.deleteAllItem("ITEM_CARD");
////
////                for (int i = 0; i < itemCard.size(); i++) {
////                    dbHandler.deleteItemCardByItemCode(itemCard.get(i).getItemCode());
////                    dbHandler.addItemcardTable(itemCard.get(i));
////
////                }
////                dbHandler.addItemcardTableTest(itemCard);
//
//                Log.e("tag_itemCard", "****saveSuccess");
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String JsonResponse) {
//            super.onPostExecute(JsonResponse);
////            Toast.makeText(context, "Save Item Assets Success", Toast.LENGTH_SHORT).show();
////            Toast.makeText(context, "Start Import Item Switch", Toast.LENGTH_SHORT).show();
//            if(pd!=null) {
//                pd.dismiss();
//                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
//                        .setContentText(context.getResources().getString(R.string.assestSave))
//                        .show();
//            }
//
//
////            progressDialog.dismiss();
//        }
//    }
}

//
//
////
//
//
//    public boolean isInternetAvailable() {
//        try {
//            final InetAddress address = InetAddress.getByName("www.google.com");
//            return !address.equals("");
//        } catch (UnknownHostException e) {
//            // Log error
//        }
//        return false;
//    }
//
//}






