package android.kimjinhwan.com.customlistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView textView;
    Button btnBack;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        btnBack = (Button) findViewById(R.id.btnBack);

        //Activity에서 넘어온 값 처리하기
        //1. intent를 꺼낸다.
        Intent intent = getIntent();
        //2. 값의 묶음인 bundle을 꺼낸다.
        Bundle bundle = intent.getExtras();     // bundle은 intent내에 있는 값들의 묶음. 번들에 아무것도 없으면 null로 보내짐.
        int position = -1;
        String result = "";
        //3. bundle 값이 있는지 유효성 검사를 한다.
        if(bundle != null) {
            //3.1. bundle 값이 있으면 값을 꺼내서 변수에 담는다.
            position = bundle.getInt(MainActivity.DATA_KEY);
            //result = bundle.getInt(MainActivity.DATA_KEY) + "";                   // 필요한 키 이름은 보내는 측에서 상수로 정의함.
            }
        if(position > -1) {
            imageView.setImageResource(bundle.getInt(MainActivity.DATA_RES_ID));
            textView.setText(bundle.getString(MainActivity.DATA_TITLE));
        }
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(DetailActivity.this, ListActivity.class);
                startActivity(intent);
                */
                finish();   //액티비티 종료.
            }
        });
    }
}
