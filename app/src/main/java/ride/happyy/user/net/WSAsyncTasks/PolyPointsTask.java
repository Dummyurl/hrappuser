package ride.happyy.user.net.WSAsyncTasks;


import android.os.AsyncTask;

import java.util.HashMap;

import ride.happyy.user.model.PolyPointsBean;
import ride.happyy.user.net.invokers.PolyPointsInvoker;

public class PolyPointsTask extends AsyncTask<String, Integer, PolyPointsBean> {

    private PolyPointsTaskListener polyPointsTaskListener;

    private HashMap<String, String> urlParams;

    public PolyPointsTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected PolyPointsBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        PolyPointsInvoker polyPointsInvoker = new PolyPointsInvoker(urlParams, null);
        return polyPointsInvoker.invokePolyPointsWS();
    }

    @Override
    protected void onPostExecute(PolyPointsBean result) {

        if (result != null)
            polyPointsTaskListener.dataDownloadedSuccessfully(result);
        else
            polyPointsTaskListener.dataDownloadFailed();
    }

    public static interface PolyPointsTaskListener {
        void dataDownloadedSuccessfully(PolyPointsBean polyPointsBean);

        void dataDownloadFailed();
    }

    public PolyPointsTaskListener getPolyPointsTaskListener() {
        return polyPointsTaskListener;
    }

    public void setPolyPointsTaskListener(PolyPointsTaskListener polyPointsTaskListener) {
        this.polyPointsTaskListener = polyPointsTaskListener;
    }
}
