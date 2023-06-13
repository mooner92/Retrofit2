package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Loc;
import com.example.myapplication.R;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    public interface ApiService {
        @GET("get_all_locs.php")
        Call<List<Loc>> getAllLocs();
    }
    public interface ApiService1 {
        @FormUrlEncoded
        @POST("save_chatting_to_RDS.php")
        Call<ResponseBody> addChat(
                @Field("uid") int uid,
                @Field("chatting_log") String chattingLog
        );
    }
    interface ApiService2 {
        @GET("get_chatting_log.php") // PHP 파일의 경로로 변경해야 합니다.
        Call<List<String>> getAllChats(@Query("uid") int uid);
    }


    Button myButton; // 버튼 참조 변수 선언
    TextView mytv;
    EditText mytv1;
    ExecutorService executorService; // ExecutorService 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton = findViewById(R.id.button); // 버튼 초기화
        mytv = findViewById(R.id.textView); //텍뷰 초기화
        mytv1 = findViewById(R.id.tv1); //에딧텍스트 초기화

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://18.188.245.7/") // 서버의 URL 주소 입력
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // API 인터페이스 구현체 생성
        ApiService apiService = retrofit.create(ApiService.class);
        ApiService1 apiService1 = retrofit.create(ApiService1.class);
        ApiService2 apiService2 = retrofit.create(ApiService2.class);

        executorService = Executors.newSingleThreadExecutor(); // ExecutorService 초기화

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uid와 chatting_log 가져오기
                int uid = 731; // 사용자의 uid 값

                String chattingLog = mytv1.getText().toString();

                // 서버로 데이터 전송
                Call<ResponseBody> call = apiService1.addChat(uid, chattingLog);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            // 전송 성공 처리
                            System.out.println("성공적으로 전송됨 답변이 데이터베이스에 도착함 답변을 불러오는중 . . .");
                            Call<List<String>> call1 = apiService2.getAllChats(uid);  //call1로 해줌
                            call1.enqueue(new Callback<List<String>>() {
                                @Override
                                public void onResponse(Call<List<String>> call1, Response<List<String>> response1) {
                                    if (response1.isSuccessful()) {
                                        List<String> chatLogs = response1.body();
                                        int n=1;
                                        for (String s : chatLogs){
                                            System.out.println(n++ + s);
                                        }
                                        // chatLogs를 원하는 방식으로 처리하세요
                                    } else {
                                        // 응답이 실패한 경우 처리할 로직을 작성하세요
                                        System.out.println("채팅내역 가져오기 실패");
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<String>> call, Throwable t) {
                                    // 통신 실패 처리를 작성하세요
                                    System.out.println("통신 2 실패");
                                }
                            });

                        } else {
                            // 전송 실패 처리
                            System.out.println("버튼클릭 or 첫 php실행시 오류");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // 통신 실패 처리
                        System.out.println("통신쪽 오류");
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown(); // 액티비티가 종료될 때 ExecutorService 종료
    }
}
