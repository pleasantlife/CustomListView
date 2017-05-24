package android.kimjinhwan.com.customlistview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String DATA_KEY = "position";
    public static final String DATA_RES_ID = "resid";
    public static final String DATA_TITLE = "title";

    ArrayList<Data> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1. 데이터
        datas = Loader.getData(this);

        //2. 아답터
        CustomAdapter adapter = new CustomAdapter(datas, this);

        //3. 연결
        //((ListView) findViewById(R.id.listView)).setAdapter(adapter);   //(ListView 앞에 괄호를 안붙이면 findViewById()가 setAdapter와 먼저 붙음.)
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                Data data = datas.get(position);

                intent.putExtra(DATA_KEY, position);
                intent.putExtra(DATA_RES_ID, data.resId);
                intent.putExtra(DATA_TITLE, data.title);

                startActivity(intent);
            }
        });
    }

}

class CustomAdapter extends BaseAdapter {
//CustomAdapter 제작에 필요한 3가지!

    //1. 데이터
    ArrayList<Data> datas;
    //2. 인플레이터
    LayoutInflater inflater;

    //3. 연결?? 호출할 때 데이터를 받기 위해서 생성자를 사용(단계를 줄이기 위해)
    public CustomAdapter(ArrayList<Data> datas, Context context) {
        this.datas = datas;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1. 컨버터뷰를 체크해서 null 일 경우에만 item layout을 생성해준다.
        Holder holder = new Holder(); // 선언문이 if 문 안에 있으면 에러납니당.
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
            holder.no = ((TextView) convertView.findViewById(R.id.txtNo));
            holder.title = ((TextView) convertView.findViewById(R.id.txtTitle));
            holder.image = ((ImageView) convertView.findViewById(R.id.imageView));
            convertView.setTag(holder);             //holder와 convertView를 연결해준다.
        }else{
            holder = (Holder) convertView.getTag();

        }
        //2. 내 아이템에 해당하는 데이터를 세팅해준다. (그러려면 내 아이템을 꺼내야 함)
        Data data = datas.get(position);
        /*
        ((TextView) convertView.findViewById(R.id.txtNo)).setText(data.no + "");
        ((TextView) convertView.findViewById(R.id.txtTitle)).setText(data.title);
        */
        holder.no.setText(data.no+"");
        holder.title.setText(data.title);
        //id를 가져오는 작업을 Loader 클래스에서 수행한다.
        holder.image.setImageResource(data.resId);
        return convertView;
    }
}

class Holder {

    TextView no;
    TextView title;
    ImageView image;

}

class Loader {

    public static ArrayList<Data> getData(Context context){
        ArrayList<Data> result = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Data data = new Data();
            data.no = i+1;
            data.title = "자동차";
            data.setImage("carlogo" + (i+1), context);
            result.add(data);
        }
        return result;
    }
}

class Data{
    //public을 넣어서 getter setter 없이 직접 접근이 가능하도록
    public int no;         //번호
    public String title;   //타이틀
    public String image;
    public int resId;

    public void setImage(String str, Context context){       //이미지의 값을 갖는 Setter
        image = str;
        resId = context.getResources().getIdentifier(image, "mipmap", context.getPackageName());    //데이터를 로딩할 떄 작업하겠다는 의미로 여기에 씁니다.
    }

}



