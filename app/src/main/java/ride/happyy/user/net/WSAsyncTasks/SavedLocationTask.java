package ride.happyy.user.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.LocationBean;
import ride.happyy.user.net.invokers.SavedLocationInvoker;

public class SavedLocationTask extends AsyncTask<String, Integer, LocationBean> {

    private SavedLocationTaskListener savedLocationTaskListener;

    private HashMap<String, String> urlParams;
    private JSONObject posData;

    public SavedLocationTask(JSONObject posData) {
        super();
        this.posData = posData;
    }

    @Override
    protected LocationBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        SavedLocationInvoker savedLocationInvoker = new SavedLocationInvoker(null,posData);
        return savedLocationInvoker.invokeDummyWS();
    }

    @Override
    protected void onPostExecute(LocationBean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result != null)
            savedLocationTaskListener.dataDownloadedSuccessfully(result);
        else
            savedLocationTaskListener.dataDownloadFailed();
    }

    public static interface SavedLocationTaskListener {

        void dataDownloadedSuccessfully(LocationBean locationBean);

        void dataDownloadFailed();
    }

    public SavedLocationTaskListener getSavedLocationTaskListener() {
        return savedLocationTaskListener;
    }

    public void setSavedLocationTaskListener(SavedLocationTaskListener savedLocationTaskListener) {
        this.savedLocationTaskListener = savedLocationTaskListener;
    }
}
