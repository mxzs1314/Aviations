package com.example.administrator.aviation.ui.activity.domflightcheck;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.aviation.R;
import com.example.administrator.aviation.http.HttpCommons;
import com.example.administrator.aviation.http.HttpRoot;
import com.example.administrator.aviation.model.domjcgrbb.FlightAWBPlanInfo;
import com.example.administrator.aviation.model.domjcgrbb.FlightCheckInfo;
import com.example.administrator.aviation.model.domjcgrbb.FlightPlanInfo;
import com.example.administrator.aviation.model.domjcgrbb.PrepareceFlightAWBPlanInfo;
import com.example.administrator.aviation.ui.base.NavBar;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订舱确认界面
 */

public class DomFlightCheckSureActivity extends Activity implements View.OnClickListener{
    @BindView(R.id.flight_check_sure_nodata_tv)
    TextView flightCheckSureNodataTv;
    @BindView(R.id.flight_check_sure_lv)
    ListView flightCheckSureLv;
    @BindView(R.id.zhixian_pb)
    ProgressBar zhixianPb;
    @BindView(R.id.flight_check_sure_btn)
    Button flightCheckSureBtn;
    @BindView(R.id.flight_check_cancel_btn)
    Button flightCheckCancelBtn;

    private FlightCheckInfo flightCheckInfo;
    private List<FlightAWBPlanInfo> flightAWBPlanInfoList;
    private MyAWBPlanAdapter myAWBPlanAdapter;

    private Map<Integer, FlightPlanInfo> choseMap;

    private String xml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appintflight_check_sure);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        NavBar navBar = new NavBar(this);
        navBar.setTitle("订舱确认");
        navBar.hideRight();

        // 按钮点击事件
        flightCheckSureBtn.setOnClickListener(this);
        flightCheckCancelBtn.setOnClickListener(this);

        choseMap = new HashMap<>();

        // 首次进入得到数据
        showData();

        // 订舱确认列表点击到详情页
        flightCheckSureLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(DomFlightCheckSureActivity.this, DomFlightCheckSureDetailActivity.class);
                FlightAWBPlanInfo flightAWBPlanInfo = flightAWBPlanInfoList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("checksure", flightAWBPlanInfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 订舱确认
            case R.id.flight_check_sure_btn:
                Set<Integer> keys = choseMap.keySet();
                for (Integer key:keys) {
                    FlightPlanInfo flightPlanInfo = choseMap.get(key);
                    String fdate = flightCheckInfo.getFDate();
                    String fno = flightCheckInfo.getFno();
                    String istrue = "1";
                    xml = getsureCancelXml(flightPlanInfo, fdate, fno, istrue);
                    if (choseMap.isEmpty()) {
                        Toast.makeText(this, "没有选择", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, String> checkSureMap = new HashMap<>();
                        checkSureMap.put("awbXml", xml);
                        checkSureMap.put("ErrString", "");
                        HttpRoot.getInstance().requstAync(DomFlightCheckSureActivity.this, HttpCommons.CGO_DOM_EXPORT_FLIGHT_PLAN_CHECK_NAME,
                                HttpCommons.CGO_DOM_EXPORT_FLIGHT_PLAN_CHECK_ACTION, checkSureMap, new HttpRoot.CallBack() {
                                    @Override
                                    public void onSucess(Object result) {
                                        SoapObject soapObject = (SoapObject) result;
                                        String a = soapObject.getProperty(0).toString();
                                        Toast.makeText(DomFlightCheckSureActivity.this, a, Toast.LENGTH_SHORT).show();
                                        showData();
                                    }

                                    @Override
                                    public void onFailed(String message) {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    }
                }
                break;

            // 订舱取消
            case R.id.flight_check_cancel_btn:
                Set<Integer> keyes = choseMap.keySet();
                for (Integer key:keyes) {
                    FlightPlanInfo flightPlanInfo = choseMap.get(key);
                    String fdate = flightCheckInfo.getFDate();
                    String fno = flightCheckInfo.getFno();
                    String isTru = "0";
                    xml = getsureCancelXml(flightPlanInfo, fdate, fno, isTru);
                    if (choseMap.isEmpty()) {
                        Toast.makeText(this, "没有选择", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, String> checkSureMap = new HashMap<>();
                        checkSureMap.put("awbXml", xml);
                        checkSureMap.put("ErrString", "");
                        HttpRoot.getInstance().requstAync(DomFlightCheckSureActivity.this, HttpCommons.CGO_DOM_EXPORT_FLIGHT_PLAN_CHECK_NAME,
                                HttpCommons.CGO_DOM_EXPORT_FLIGHT_PLAN_CHECK_ACTION, checkSureMap, new HttpRoot.CallBack() {
                                    @Override
                                    public void onSucess(Object result) {
                                        SoapObject soapObject = (SoapObject) result;
                                        String a = soapObject.getProperty(0).toString();
                                        Toast.makeText(DomFlightCheckSureActivity.this, a, Toast.LENGTH_SHORT).show();
                                        showData();
                                    }

                                    @Override
                                    public void onFailed(String message) {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    }
                }
                break;

            default:
                break;
        }
    }

    // 适配器
    private class MyAWBPlanAdapter extends BaseAdapter {
        private Context context;
        private List<FlightAWBPlanInfo> flightAWBPlanInfoList;
        public MyAWBPlanAdapter(Context context, List<FlightAWBPlanInfo> flightAWBPlanInfoList) {
            this.context = context;
            this.flightAWBPlanInfoList = flightAWBPlanInfoList;
        }

        @Override
        public int getCount() {
            return flightAWBPlanInfoList.size();
        }

        @Override
        public FlightAWBPlanInfo getItem(int position) {
            return flightAWBPlanInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            final FlightAWBPlanInfo item = getItem(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.flight_check_sure_item, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.planCb = (CheckBox) convertView.findViewById(R.id.flight_plan_checkbox);
                viewHolder.mawbTv = (TextView) convertView.findViewById(R.id.flight_plan_mawb_tv);
                viewHolder.nameTv = (TextView) convertView.findViewById(R.id.flight_plan_name_tv);
                viewHolder.pcTv = (TextView) convertView.findViewById(R.id.flight_plan_pc_tv);
                viewHolder.weightTv = (TextView) convertView.findViewById(R.id.flight_plan_weight_tv);
                viewHolder.volumeTv = (TextView) convertView.findViewById(R.id.flight_plan_volume_tv);
                viewHolder.userTv = (TextView) convertView.findViewById(R.id.flight_plan_user_tv);
                viewHolder.timeTv = (TextView) convertView.findViewById(R.id.flight_plan_time_tv);
                viewHolder.sureStateTv = (TextView) convertView.findViewById(R.id.flight_plan_surestate_tv);
                viewHolder.showLy = (LinearLayout) convertView.findViewById(R.id.awb_linear_layout);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String mawb = flightAWBPlanInfoList.get(position).getMawb();
            if (mawb != null && !mawb.equals("")) {
                viewHolder.mawbTv.setText(mawb);
            } else {
                viewHolder.mawbTv.setText("");
            }
            String name = flightAWBPlanInfoList.get(position).getAgentName();
            if (name != null && !name.equals("")) {
                viewHolder.nameTv.setText(name);
            } else {
                viewHolder.nameTv.setText("");
            }
            String pc = flightAWBPlanInfoList.get(position).getPC();
            if (pc != null && !pc.equals("")) {
                viewHolder.pcTv.setText(pc);
            } else {
                viewHolder.pcTv.setText("");
            }
            String weight = flightAWBPlanInfoList.get(position).getWeight();
            if (weight != null && !weight.equals("")) {
                viewHolder.weightTv.setText(weight);
            } else {
                viewHolder.weightTv.setText("");
            }
            String volume = flightAWBPlanInfoList.get(position).getVolume();
            if (volume != null && !volume.equals("")) {
                viewHolder.volumeTv.setText(volume);
            } else {
                viewHolder.volumeTv.setText("");
            }
            String user = flightAWBPlanInfoList.get(position).getCheckID();
            if (user != null && !user.equals("")) {
                viewHolder.userTv.setText(user);
            } else {
                viewHolder.userTv.setText("");
            }
            String time = flightAWBPlanInfoList.get(position).getCheckTime();
            if (time != null && !time.equals("")) {
                viewHolder.timeTv.setText(time);
            } else {
                viewHolder.timeTv.setText("");
            }
            String sureState = flightAWBPlanInfoList.get(position).getFlightChecked();
            if (sureState != null && !sureState.equals("")) {
                viewHolder.sureStateTv.setText(sureState);
                if (sureState.equals("1")) {
                    viewHolder.showLy.setBackgroundColor(Color.parseColor("#fbd0cf"));
                }
            } else {
                viewHolder.sureStateTv.setText("");
            }

            viewHolder.planCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        FlightPlanInfo info = new FlightPlanInfo(item.getFDate(),item.getFno(),item.getFlightChecked(),item.getMawb());
                        choseMap.put(position, info);
                    } else {
                        choseMap.remove(position);
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            CheckBox planCb;
            TextView mawbTv;
            TextView nameTv;
            TextView pcTv;
            TextView weightTv;
            TextView volumeTv;
            TextView userTv;
            TextView timeTv;
            TextView sureStateTv;
            LinearLayout showLy;
        }
    }

    // 请求数据信息
    private String getXml(String fdate, String fno, String jTg, String fDest) {
        return "<GNCAWBPlan>"
                + "<FDate>" + fdate + "</FDate>"
                + "<Fno>" + fno + "</Fno>"
                + " <JTZ>" + jTg + "</JTZ>"
                + "  <FDest>" + fDest + "</FDest>"
                + "</GNCAWBPlan>";
    }

    // 订舱确认与取消订舱xml
    private String getsureCancelXml(FlightPlanInfo flightPlanInfo, String fdate, String fno, String isTrue) {
        String pre = "<GNCAWBPlan>";
        String after ="</GNCAWBPlan>";
        String result = pre;
            result += "<FDate>"+fdate+"</FDate>"
                    +"<Fno>" + fno + "</Fno>"
                    + "<FlightChecked>"+isTrue+"</FlightChecked>"
                    +"<Mawb>" + flightPlanInfo.getMawb() + "</Mawb>";
        result = result+after;
        return result;
    }

    // 展示订舱确认数据界面
    private void showData() {
        // 得到待确认航班计划列表传递过来的数据
        flightCheckInfo = (FlightCheckInfo) getIntent().getSerializableExtra("domflightsure");
        String fDate = flightCheckInfo.getFDate();
        String fno = flightCheckInfo.getFno();
        String jTg = flightCheckInfo.getJTZ();
        String fDest = flightCheckInfo.getFDest();
        String xml = getXml(fDate, fno, jTg, fDest);
        // 解析数据
        Map<String, String> params = new HashMap<>();
        params.put("awbXml", xml);
        params.put("ErrString", "");
        HttpRoot.getInstance().requstAync(DomFlightCheckSureActivity.this, HttpCommons.CGO_GET_DOM_FLIGHT_AWB_PLAN_NAME,
                HttpCommons.CGO_GET_DOM_FLIGHT_AWB_PLAN_NACTION, params, new HttpRoot.CallBack() {
                    @Override
                    public void onSucess(Object result) {
                        zhixianPb.setVisibility(View.VISIBLE);
                        SoapObject object = (SoapObject) result;
                        String awbXml = object.getProperty(0).toString();
                        flightAWBPlanInfoList = PrepareceFlightAWBPlanInfo.parseFlightAWBPlanInfoXml(awbXml);
                        assert flightAWBPlanInfoList != null;
                        if (flightAWBPlanInfoList.size() >= 1) {
                            myAWBPlanAdapter = new MyAWBPlanAdapter(DomFlightCheckSureActivity.this, flightAWBPlanInfoList);
                            flightCheckSureLv.setAdapter(myAWBPlanAdapter);
                            zhixianPb.setVisibility(View.GONE);
                        } else {
                            zhixianPb.setVisibility(View.GONE);
                            flightCheckSureNodataTv.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailed(String message) {
                        zhixianPb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        zhixianPb.setVisibility(View.GONE);
                    }
                });
    }
}
