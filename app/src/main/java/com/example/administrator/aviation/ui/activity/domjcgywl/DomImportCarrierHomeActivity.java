package com.example.administrator.aviation.ui.activity.domjcgywl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.aviation.R;
import com.example.administrator.aviation.http.HttpCommons;
import com.example.administrator.aviation.http.HttpRoot;
import com.example.administrator.aviation.model.intjcgywl.IntImportCarrierInfo;
import com.example.administrator.aviation.model.intjcgywl.PrepareIntImportCarrierInfo;
import com.example.administrator.aviation.tool.DateUtils;
import com.example.administrator.aviation.ui.base.NavBar;
import com.example.administrator.aviation.ui.dialog.LoadingDialog;
import com.example.administrator.aviation.util.PullToRefreshView;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.administrator.aviation.R.id.edeclare_info_volume_tv;

/**
 * 国内进港业务量首页
 */

public class DomImportCarrierHomeActivity extends Activity {
    @BindView(R.id.gnjcg_tv)
    TextView gnjcgTv;
    @BindView(R.id.show_d_ly)
    LinearLayout showDLy;
    @BindView(R.id.int_edeclare_nodata_tv)
    TextView intEdeclareNodataTv;
    @BindView(R.id.edeclare_lv)
    ListView edeclareLv;
    @BindView(R.id.refresh_view)
    PullToRefreshView refreshView;
    @BindView(R.id.edeclare_pb)
    ProgressBar edeclarePb;

    private String currentTime;
    private String xml;
    private List<IntImportCarrierInfo> intExportCarrierInfoList;
    private IntDayAdapter intDayAdapter;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intexportcarrier_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        NavBar navBar = new NavBar(this);
        navBar.setTitle("国内进港当天业务量详情");
        navBar.setRight(R.drawable.search);
        navBar.getRightImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DomImportCarrierHomeActivity.this, DomImportCarrierActivity.class);
                startActivity(intent);
            }
        });

        showDLy.setVisibility(View.GONE);

        intEdeclareNodataTv.setVisibility(View.GONE);
        edeclarePb.setVisibility(View.GONE);

        refreshView.disableScroolUp();

        loadingDialog = new LoadingDialog(this);

        currentTime = DateUtils.getTodayDateTime();
        xml = getXml(currentTime, currentTime, "");
        Map<String, String> params = new HashMap<>();
        params.put("awbXml", xml);
        params.put("ErrString", "");
        loadingDialog.show();
        HttpRoot.getInstance().requstAync(DomImportCarrierHomeActivity.this, HttpCommons.CGO_GET_DOM_IMPORT_REPORT_NAME,
                HttpCommons.CGO_GET_DOM_IMPORT_REPORT_ACTION, params,
                new HttpRoot.CallBack() {
                    @Override
                    public void onSucess(Object result) {
                        SoapObject object = (SoapObject) result;
                        String xmls = object.getProperty(0).toString();
                        intExportCarrierInfoList = PrepareIntImportCarrierInfo.pullImportCarrierInfoXml(xmls);
                        if (intExportCarrierInfoList != null && intExportCarrierInfoList.size() >= 1) {
                            intDayAdapter = new IntDayAdapter(DomImportCarrierHomeActivity.this, intExportCarrierInfoList);
                            edeclareLv.setAdapter(intDayAdapter);
                        } else {
                            intEdeclareNodataTv.setVisibility(View.VISIBLE);
                        }
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onFailed(String message) {
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError() {
                        loadingDialog.dismiss();
                    }
                });

        // 下拉刷新
        refreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
                xml = getXml(currentTime, currentTime, "");
                Map<String, String> params = new HashMap<>();
                params.put("awbXml", xml);
                params.put("ErrString", "");
                HttpRoot.getInstance().requstAync(DomImportCarrierHomeActivity.this, HttpCommons.CGO_GET_DOM_IMPORT_REPORT_NAME,
                        HttpCommons.CGO_GET_DOM_IMPORT_REPORT_ACTION, params,
                        new HttpRoot.CallBack() {
                            @Override
                            public void onSucess(Object result) {
                                SoapObject object = (SoapObject) result;
                                String xmls = object.getProperty(0).toString();
                                intExportCarrierInfoList = PrepareIntImportCarrierInfo.pullImportCarrierInfoXml(xmls);
                                if (intExportCarrierInfoList != null && intExportCarrierInfoList.size() >= 1) {
                                    intDayAdapter = new IntDayAdapter(DomImportCarrierHomeActivity.this, intExportCarrierInfoList);
                                    edeclareLv.setAdapter(intDayAdapter);
                                } else {
                                    intEdeclareNodataTv.setVisibility(View.VISIBLE);
                                }
                                refreshView.onHeaderRefreshComplete();
                            }

                            @Override
                            public void onFailed(String message) {
                                refreshView.onHeaderRefreshComplete();
                            }

                            @Override
                            public void onError() {
                                refreshView.onHeaderRefreshComplete();
                            }
                        });
            }
        });
    }

    private class IntDayAdapter extends BaseAdapter {
        private Context context;
        private List<IntImportCarrierInfo> intExportCarrierInfoList;

        public IntDayAdapter(Context context, List<IntImportCarrierInfo> intExportCarrierInfoList) {
            this.context = context;
            this.intExportCarrierInfoList = intExportCarrierInfoList;
        }

        @Override
        public int getCount() {
            return intExportCarrierInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return intExportCarrierInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.intexportday_item, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.carrierTv = (TextView) convertView.findViewById(R.id.edeclare_info_carrier_tv);
                viewHolder.fdateTv = (TextView) convertView.findViewById(R.id.edeclare_info_papertime_tv);
                viewHolder.fnoTv = (TextView) convertView.findViewById(R.id.edeclare_fno_tv);
                viewHolder.detTv = (TextView) convertView.findViewById(R.id.edeclare_info_dest_tv);
                viewHolder.pcTv = (TextView) convertView.findViewById(R.id.edeclare_info_pc_tv);
                viewHolder.weightTv = (TextView) convertView.findViewById(R.id.edeclare_info_weight_tv);
                viewHolder.volumeTv = (TextView) convertView.findViewById(edeclare_info_volume_tv);
                viewHolder.showLayout = (LinearLayout) convertView.findViewById(R.id.show_ly);
                viewHolder.vsLy = (LinearLayout) convertView.findViewById(R.id.vs_ly);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.showLayout.setVisibility(View.GONE);

            String carrier = intExportCarrierInfoList.get(position).getCarrier();
            if (carrier != null && !carrier.equals("")) {
                viewHolder.carrierTv.setText(carrier);
            } else {
                viewHolder.carrierTv.setText("");
            }
            String fno = intExportCarrierInfoList.get(position).getFno();
            if (fno != null && !fno.equals("")) {
                viewHolder.fnoTv.setText(fno);
            } else {
                viewHolder.detTv.setText("");
            }
            String dest = intExportCarrierInfoList.get(position).getOrigin();
            if (dest != null && !dest.equals("")) {
                viewHolder.detTv.setText(dest);
            } else {
                viewHolder.detTv.setText("");
            }
            String fdate = intExportCarrierInfoList.get(position).getFDate();
            if (fdate != null && !fdate.equals("")) {
                viewHolder.fdateTv.setText(fdate);
            } else {
                viewHolder.fdateTv.setText("");
            }
            String pc = intExportCarrierInfoList.get(position).getPc();
            if (pc != null && !pc.equals("")) {
                viewHolder.pcTv.setText(pc);
            } else {
                viewHolder.pcTv.setText("");
            }
            String weight = intExportCarrierInfoList.get(position).getWeight();
            if (weight != null && !weight.equals("")) {
                viewHolder.weightTv.setText(weight);
            } else {
                viewHolder.weightTv.setText("");
            }
            String volume = intExportCarrierInfoList.get(position).getVolume();
            if (volume != null && !volume.equals("")) {
                viewHolder.volumeTv.setText(volume);
            } else {
                viewHolder.volumeTv.setText("");
            }
            if (position % 2 == 0) {
                viewHolder.vsLy.setBackgroundColor(Color.parseColor("#ffffff"));
            } else {
                viewHolder.vsLy.setBackgroundColor(Color.parseColor("#ebf5fe"));
            }
            return convertView;
        }

        class ViewHolder {
            TextView carrierTv;
            TextView fdateTv;
            TextView fnoTv;
            TextView detTv;
            TextView pcTv;
            TextView weightTv;
            LinearLayout vsLy;
            LinearLayout showLayout;
            TextView volumeTv;
        }
    }

    private String getXml(String begainTime, String endTime, String reportType) {
        return "<GNJCarrierReport>"
                + "<ReportType>" + reportType + "</ReportType>"
                + "<StartDay>" + begainTime + "</StartDay>"
                + "<EndDay>" + endTime + "</EndDay>"
                + "</GNJCarrierReport>";
    }
}
