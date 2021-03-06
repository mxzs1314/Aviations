package com.example.administrator.aviation.ui.activity.domjcgrbb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.aviation.R;
import com.example.administrator.aviation.http.HttpCommons;
import com.example.administrator.aviation.http.HttpRoot;
import com.example.administrator.aviation.model.intjcgrbb.IntExportDayInfo;
import com.example.administrator.aviation.model.intjcgrbb.PrepareIntExportDayInfo;
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

/**
 * 国内进港日报表首次进入
 */

public class DomImportDayCarrierHomeActivity extends Activity {
    @BindView(R.id.yjTv)
    TextView yjTv;
    @BindView(R.id.sj_tv)
    TextView sjTv;
    @BindView(R.id.int_edeclare_nodata_tv)
    TextView intEdeclareNodataTv;
    @BindView(R.id.edeclare_lv)
    ListView edeclareLv;
    @BindView(R.id.jcg_refresh)
    PullToRefreshView jcgRefresh;
    @BindView(R.id.edeclare_pb)
    ProgressBar edeclarePb;

    private String xml;
    private String currentTime;
    private List<IntExportDayInfo> intExportDayInfoList;
    private ExportDayAdapter exportDayAdapter;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appexportdaydetail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        NavBar navBar = new NavBar(this);
        navBar.setTitle("当天进港日报表列表");
        navBar.setRight(R.drawable.search);
        navBar.getRightImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DomImportDayCarrierHomeActivity.this, DomImportDayCarrierActivity.class);
                startActivity(intent);
            }
        });

        loadingDialog = new LoadingDialog(this);

        yjTv.setText("预计到达");
        sjTv.setText("实际到达");

        jcgRefresh.disableScroolUp();

        intEdeclareNodataTv.setVisibility(View.GONE);
        edeclarePb.setVisibility(View.GONE);

        currentTime = DateUtils.getTodayDateTime();

        loadingDialog.show();
        xml = getXml(currentTime);
        Map<String, String> params = new HashMap<>();
        params.put("awbXml", xml);
        params.put("ErrString", "");
        HttpRoot.getInstance().requstAync(DomImportDayCarrierHomeActivity.this, HttpCommons.CGO_GET_DOM_IMPORT_DAY_REPORT_NAME,
                HttpCommons.CGO_GET_DOM_IMPORT_DAY_REPORT_ACTION, params,
                new HttpRoot.CallBack() {
                    @Override
                    public void onSucess(Object result) {
                        SoapObject object = (SoapObject) result;
                        String xmls = object.getProperty(0).toString();
                        intExportDayInfoList = PrepareIntExportDayInfo.pullExportDayInfoXml(xmls);
                        if (intExportDayInfoList != null && intExportDayInfoList.size() >= 1) {
                            exportDayAdapter = new ExportDayAdapter(DomImportDayCarrierHomeActivity.this, intExportDayInfoList);
                            edeclareLv.setAdapter(exportDayAdapter);
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

        // listView每一项点击事件
        edeclareLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                IntExportDayInfo intExportDayInfo = (IntExportDayInfo) exportDayAdapter.getItem(position);
                String dates = intExportDayInfo.getFDate();
                String fnos = intExportDayInfo.getFno();
                Intent intent = new Intent(DomImportDayCarrierHomeActivity.this, DomImportDayManifestActivity.class);
                intent.putExtra("imdate", dates);
                intent.putExtra("imfno", fnos);
                startActivity(intent);
            }
        });

        // 下拉刷新
        jcgRefresh.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
                xml = getXml(currentTime);
                Map<String, String> params = new HashMap<>();
                params.put("awbXml", xml);
                params.put("ErrString", "");
                HttpRoot.getInstance().requstAync(DomImportDayCarrierHomeActivity.this, HttpCommons.CGO_GET_DOM_IMPORT_DAY_REPORT_NAME,
                        HttpCommons.CGO_GET_DOM_IMPORT_DAY_REPORT_ACTION, params,
                        new HttpRoot.CallBack() {
                            @Override
                            public void onSucess(Object result) {
                                SoapObject object = (SoapObject) result;
                                String xmls = object.getProperty(0).toString();
                                intExportDayInfoList = PrepareIntExportDayInfo.pullExportDayInfoXml(xmls);
                                if (intExportDayInfoList != null && intExportDayInfoList.size() >= 1) {
                                    exportDayAdapter = new ExportDayAdapter(DomImportDayCarrierHomeActivity.this, intExportDayInfoList);
                                    edeclareLv.setAdapter(exportDayAdapter);
                                } else {
                                    intEdeclareNodataTv.setVisibility(View.VISIBLE);
                                }
                                jcgRefresh.onHeaderRefreshComplete();

                            }

                            @Override
                            public void onFailed(String message) {
                                jcgRefresh.onHeaderRefreshComplete();
                            }

                            @Override
                            public void onError() {
                                jcgRefresh.onHeaderRefreshComplete();
                            }
                        });
            }
        });

    }

    private class ExportDayAdapter extends BaseAdapter {
        private Context context;
        private List<IntExportDayInfo> intExportDayInfoList;

        public ExportDayAdapter(Context context, List<IntExportDayInfo> intExportDayInfoList) {
            this.context = context;
            this.intExportDayInfoList = intExportDayInfoList;
        }

        @Override
        public int getCount() {
            return intExportDayInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return intExportDayInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.intexportdaydetail_item, viewGroup, false);
                viewHolder.fnoTv = (TextView) convertView.findViewById(R.id.edeclare_info_fno_tv);
                viewHolder.sfgTv = (TextView) convertView.findViewById(R.id.edeclare_info_sf_tv);
                viewHolder.jtgTv = (TextView) convertView.findViewById(R.id.edeclare_info_jt_tv);
                viewHolder.mdgTv = (TextView) convertView.findViewById(R.id.edeclare_info_md_tv);
                viewHolder.pcTv = (TextView) convertView.findViewById(R.id.edeclare_info_pc_tv);
                viewHolder.weightTv = (TextView) convertView.findViewById(R.id.edeclare_info_weight_tv);
                viewHolder.volumeTv = (TextView) convertView.findViewById(R.id.edeclare_info_volume_tv);
                viewHolder.yjddTv = (TextView) convertView.findViewById(R.id.edeclare_info_yj_tv);
                viewHolder.sjddTv = (TextView) convertView.findViewById(R.id.edeclare_sj_tv);
                viewHolder.showLy = (LinearLayout) convertView.findViewById(R.id.flight_in_layout);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String fno = intExportDayInfoList.get(position).getFno();
            if (fno != null && !fno.equals("")) {
                viewHolder.fnoTv.setText(fno);
            } else {
                viewHolder.fnoTv.setText("");
            }
            String sfg = intExportDayInfoList.get(position).getFDep();
            if (sfg != null && !sfg.equals("")) {
                viewHolder.sfgTv.setText(sfg);
            } else {
                viewHolder.sfgTv.setText("");
            }
            String jtg = intExportDayInfoList.get(position).getJTZ();
            if (jtg != null && !jtg.equals("")) {
                viewHolder.jtgTv.setText(jtg);
            } else {
                viewHolder.jtgTv.setText("");
            }
            String mdg = intExportDayInfoList.get(position).getFDest();
            if (mdg != null && !mdg.equals("")) {
                viewHolder.mdgTv.setText(mdg);
            } else {
                viewHolder.mdgTv.setText("");
            }
            String pc = intExportDayInfoList.get(position).getPC();
            if (pc != null && !pc.equals("")) {
                viewHolder.pcTv.setText(pc);
            } else {
                viewHolder.pcTv.setText("");
            }
            String weight = intExportDayInfoList.get(position).getWeight();
            if (weight != null && !weight.equals("")) {
                viewHolder.weightTv.setText(weight);
            } else {
                viewHolder.weightTv.setText("");
            }
            String volume = intExportDayInfoList.get(position).getVolume();
            if (volume != null && !volume.equals("")) {
                viewHolder.volumeTv.setText(volume);
            } else {
                viewHolder.volumeTv.setText("");
            }
            String yjdd = intExportDayInfoList.get(position).getEstimatedArrival();
            if (yjdd != null && !yjdd.equals("")) {
                viewHolder.yjddTv.setText(yjdd);
            } else {
                viewHolder.yjddTv.setText("");
            }
            String sjdd = intExportDayInfoList.get(position).getActualArrival();
            if (sjdd != null && !sjdd.equals("")) {
                viewHolder.sjddTv.setText(sjdd);
            } else {
                viewHolder.sjddTv.setText("");
            }
            if (position % 2 == 0) {
                viewHolder.showLy.setBackgroundColor(Color.parseColor("#ffffff"));
            } else {
                viewHolder.showLy.setBackgroundColor(Color.parseColor("#ebf5fe"));
            }
            return convertView;
        }

        class ViewHolder {
            TextView fnoTv;
            TextView sfgTv;
            TextView mdgTv;
            TextView jtgTv;
            TextView pcTv;
            TextView weightTv;
            TextView volumeTv;
            TextView yjddTv;
            TextView sjddTv;
            LinearLayout showLy;
        }
    }

    private String getXml(String begainTime) {
        return "<GNJCarrierReport>"
                + "<StartDay>" + begainTime + "</StartDay>"
                + "</GNJCarrierReport>";
    }
}
