package com.falconssoft.gassystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cn.pedant.SweetAlert.SweetAlertDialog;



public class ExportJeson {

    private Context context;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialogSave;
    private JSONObject obj;
    DatabaseHandler dbHandler;
    GlobelFunction globelFunction;
    String ip;


    public ExportJeson(Context context, JSONObject object) {//, JSONObject obj
        this.obj = object;
        this.context = context;
        dbHandler = new DatabaseHandler(context);
        globelFunction=new GlobelFunction();
        ip=globelFunction.GlobelFunctionSetting(dbHandler);

//        progressDialog = new ProgressDialog(context,R.style.MyTheme);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setProgress(0);





    }

    public void startSending(String flag) {
//        Log.e("check",flag);

        if (flag.equals("ExportVoucher"))
            new ExportDataSaveInvoice().execute();

        if (flag.equals("ExportRecCash"))
            new ExportRecCash().execute();

//        if (flag.equals("ExportTransferData"))
//            new ExportTransferData().execute();
//
//
//        if (flag.equals("ExportUpdate"))
//            new SyncItemUpdate().execute();
//
//        if (flag.equals("ExportAssets"))
//            new ExportAssets().execute();
//
//        if (flag.equals("ExportItemCard"))
//            new ExportItemCard().execute();

    }

    private class ExportDataSaveInvoice extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        SweetAlertDialog pdItem=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();
            pdItem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pdItem.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdItem.setTitleText("");
            pdItem.setCancelable(false);
            pdItem.show();


        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
            try {
//                final List<MainSetting> mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip= mainSettings.get(0).getIP();
//                }
                String link = "http://"+ip + "/SaveInvoice";
                Log.e("ipAdress", "ip -->" + ip);
                String data = "JSONSTR=" + URLEncoder.encode(obj.toString(), "UTF-8");
                Log.e("tag_link", "ExportData -->" + link);
                Log.e("tag_data", "ExportData -->" + data);

////
                URL url = new URL(link);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");



                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("SaveInvoice", "SaveInvoice -->" + stringBuffer.toString());

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
                        Log.e("tagSaveInvoice", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            if (JsonResponse != null && JsonResponse.contains("Saved Successfully")) {
                Log.e("ExportData", "****Success");
                dbHandler.updateIsExportVoucher();
                pdItem.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pdItem.setTitleText("SaveInvoice");

                if(pdItem!=null){
                    pdItem.dismissWithAnimation();

                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Successfully")
                        .setContentText("Successfully SaveInvoice")
                        .show();

            }


            } else {
                Log.e("ExportData", "****Failed to export data");
//                Toast.makeText(context, "Failed to ExportData", Toast.LENGTH_SHORT).show();

                if(pdItem!=null){
                pdItem.dismissWithAnimation();

                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("تصدير الفاتوره")
                            .setContentText("فشل تصدير الفاتوره ")
                            .show();

                }

            }

        }



    }

    private class ExportRecCash extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        SweetAlertDialog pdItem=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();
            pdItem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pdItem.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdItem.setTitleText("export");
            pdItem.setCancelable(false);
            pdItem.show();


        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
            try {
//
                String link = "http://"+ip+ "/SaveRECCASH";
Log.e("ipAdress", "ip -->" + ip);
                String data = "JSONSTR=" + URLEncoder.encode(obj.toString(), "UTF-8") ;
                Log.e("tag_link", "ExportData -->" + link);
                Log.e("tag_data", "ExportData -->" + data);

////
                URL url = new URL(link);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");



                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "ExportData -->" + stringBuffer.toString());

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

            if (JsonResponse != null && JsonResponse.contains("Saved Successfully")) {
                Log.e("ExportData", "****Success");
                dbHandler.updateIsExportRec();
                pdItem.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pdItem.setTitleText("Successfully");
                if(pdItem!=null){
                    pdItem.dismissWithAnimation();

                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Successfully")
                            .setContentText("Successfully")
                            .show();

                }


            } else {
                Log.e("ExportData", "****Failed to export data");
//                Toast.makeText(context, "Failed to ExportData", Toast.LENGTH_SHORT).show();

                if(pdItem!=null){
                    pdItem.dismissWithAnimation();

                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("تصدير السندات ")
                            .setContentText("فشل تصدير سندات القبض ")
                            .show();

                }

            }

        }



    }
//
//    private class ExportTransferData extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//        private HttpURLConnection urlConnection = null;
//        private BufferedReader reader = null;
//        SweetAlertDialog TransferDialog =null;
//        @Override
//        protected void onPreExecute() {
//
//            TransferDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            TransferDialog.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            TransferDialog.setTitleText(context.getResources().getString(R.string.ex_item_transfer));
//            TransferDialog.setCancelable(false);
//            TransferDialog.show();
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
//            try {
//                final List<MainSetting> mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip= mainSettings.get(0).getIP();
//                }
//                String link = "http://"+ip + "/ExportTransfer";
////
//                String data = "JSONSTR=" + URLEncoder.encode(obj.toString(), "UTF-8") ;
//                Log.e("tag_link", "ExportData -->" + link);
//                Log.e("tag_data", "ExportData -->" + data);
//
//////
//                URL url = new URL(link);
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("POST");
//
//
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
//                Log.e("tag", "ExportData -->" + stringBuffer.toString());
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
//            if (JsonResponse != null && JsonResponse.contains("Saved Successfully")) {
//                Log.e("ExportData", "****Success");
//                dbHandler.updateIsExportTransfer();
//
//                if(TransferDialog !=null){
//                    TransferDialog.dismissWithAnimation();
//
//                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.sucess))
//                            .setContentText(context.getResources().getString(R.string.exporttransfersucess))
//                            .show();
//
//                }
//
//            } else {
//                Log.e("ExportData", "****Failed to export data");
//                Toast.makeText(context, "Failed to ExportData", Toast.LENGTH_SHORT).show();
//
//                if(TransferDialog !=null){
//                    TransferDialog.dismissWithAnimation();
//
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.faildExportTransfer))
//                            .show();
//
//                }
//            }
//
//        }
//
//
//
//    }
//
//    private class SyncItemUpdate extends AsyncTask<String, String, String> {
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
//            pdItem.setTitleText("Item UPDATE Qty ");
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
//                String link = "http://"+ip + "/uPDATEqTY";
////                String link = controll.URL + "GetJRDITEMPRICE";
////
//                String data = "JSONSTR=" + URLEncoder.encode(obj.toString(), "UTF-8") ;
//
////
//                URL url = new URL(link);
//                Log.e("TAG_itemPrice", "link -->" +link+ "   "+obj.toString());
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
//            if (JsonResponse != null && JsonResponse.contains("Successfully")) {
//                Log.e("TAG_itemPExport", "****Success");
//
//                //                    JSONArray parentArray = new JSONArray(JsonResponse);
////
////                    Log.e("TAG_itemPrice", " "+parentArray.toString());
////                    Log.e("TAG_itemPriceR", " "+JsonResponse);
////                    for (int i = 0; i < parentArray.length(); i++) {
////                        JSONObject finalObject = parentArray.getJSONObject(i);
////
//////
//////                        controll.F_D= finalObject.getString("F_D");
//////                        controll.Item_name= finalObject.getString("ItemNameA");
////                        textViewUpdate.setText("upSu");
////
//////                        textItemName.setText(controll.Item_name);
//////                        Log.e("TAG_itemPrice", "****getSuccess"+controll.F_D+"name= "+ controll.Item_name);
////
////                    }
//                        textViewUpdate.setText("upSu");
//
//                if(pdItem !=null) {
//                    pdItem.dismissWithAnimation();
//                     new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.upsucess))
//                            .setContentText(context.getResources().getString(R.string.importSuc))
//                            .show();
//                }
//
//            } else if(JsonResponse != null && JsonResponse.contains("No Parameter Found.")){
//                Log.e("TAG_itemPrice", "****No Parameter Found.");
//
//                if(pdItem !=null) {
//                    pdItem.dismissWithAnimation();
//                }
//
//                textViewUpdate.setText("up");
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
//                textViewUpdate.setText("-up");
//            }
////            progressDialog.dismiss();
//        }
//    }
//
//    private class ExportAssets extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//        private HttpURLConnection urlConnection = null;
//        private BufferedReader reader = null;
//        SweetAlertDialog AssetsDialog =null;
//        @Override
//        protected void onPreExecute() {
//
//            AssetsDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            AssetsDialog.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            AssetsDialog.setTitleText(context.getResources().getString(R.string.assetsExport));
//            AssetsDialog.setCancelable(false);
//            AssetsDialog.show();
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
//            try {
//                final List<MainSetting> mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip= mainSettings.get(0).getIP();
//                }
//                String link = "http://"+ip + "/SaveAssets";
////
//                String data = "JSONSTR=" + URLEncoder.encode(obj.toString(), "UTF-8") ;
//                Log.e("tag_link", "ExportData -->" + link);
//                Log.e("tag_data", "ExportData -->" + data);
//
//////
//                URL url = new URL(link);
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("POST");
//
//
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
//                Log.e("tag", "ExportData -->" + stringBuffer.toString());
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
//            if (JsonResponse != null && JsonResponse.contains("Saved Successfully")) {
//                Log.e("ExportData", "****Success");
//                dbHandler.updateIsExportAssets();
//
//                if(AssetsDialog !=null){
//                    AssetsDialog.dismissWithAnimation();
//
//                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.sucess))
//                            .setContentText(context.getResources().getString(R.string.assestSave))
//                            .show();
//
//                }
//
//            } else {
//                Log.e("ExportData", "****Failed to export data");
//                Toast.makeText(context, "Failed to ExportData", Toast.LENGTH_SHORT).show();
//
//                if(AssetsDialog !=null){
//                    AssetsDialog.dismissWithAnimation();
//
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.assfailedtoExport))
//                            .show();
//
//                }
//            }
//
//        }
//
//
//
//    }
//
//
//    private class ExportItemCard extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//        private HttpURLConnection urlConnection = null;
//        private BufferedReader reader = null;
//        SweetAlertDialog ItemCardDialog =null;
//        @Override
//        protected void onPreExecute() {
//
//            ItemCardDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            ItemCardDialog.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            ItemCardDialog.setTitleText(context.getResources().getString(R.string.assetsExport));
//            ItemCardDialog.setCancelable(false);
//            ItemCardDialog.show();
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
//            try {
//                final List<MainSetting> mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip= mainSettings.get(0).getIP();
//                }
//                String link = "http://"+ip + "/SaveAssets";
////
//                String data = "JSONSTR=" + URLEncoder.encode(obj.toString(), "UTF-8") ;
//                Log.e("tag_link", "ExportData -->" + link);
//                Log.e("tag_data", "ExportData -->" + data);
//
//////
//                URL url = new URL(link);
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("POST");
//
//
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
//                Log.e("tag", "ExportData -->" + stringBuffer.toString());
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
//            if (JsonResponse != null && JsonResponse.contains("Saved Successfully")) {
//                Log.e("ExportData", "****Success");
//                dbHandler.updateIsExportItemCard();
//
//                if(ItemCardDialog !=null){
//                    ItemCardDialog.dismissWithAnimation();
//
//                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.sucess))
//                            .setContentText(context.getResources().getString(R.string.assestSave))
//                            .show();
//
//                }
//
//            } else {
//                Log.e("ExportData", "****Failed to export data");
//                Toast.makeText(context, "Failed to ExportData", Toast.LENGTH_SHORT).show();
//
//                if(ItemCardDialog !=null){
//                    ItemCardDialog.dismissWithAnimation();
//
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.assfailedtoExport))
//                            .show();
//
//                }
//            }
//
//        }
//
//
//
//    }

}
