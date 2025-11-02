package com.example.btl;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UploadFirebase extends AppCompatActivity {

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_firebase);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        db = FirebaseFirestore.getInstance();
        uploadJsonToFirestore();
    }

    private void uploadJsonToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        try {
            InputStream is = getAssets().open("527_.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonStr = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(jsonStr);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Map<String, Object> questionData = new HashMap<>();

                questionData.put("question_number", obj.getString("question_number"));
                questionData.put("question", obj.getString("question"));

                JSONArray optionsArray = obj.getJSONArray("options");
                List<String> optionsList = new ArrayList<>();
                for (int j = 0; j < optionsArray.length(); j++) {
                    optionsList.add(optionsArray.getString(j));
                }
                questionData.put("options", optionsList);

                questionData.put("correct_answer", obj.getString("correct_answer"));
                questionData.put("explanation", obj.getString("explanation"));
                questionData.put("category", obj.getString("category"));

                // 🖼️ Nếu có trường image
                if (obj.has("image")) {
                    questionData.put("image", obj.getString("image"));
                }

                String docId = obj.getString("question_number");

                db.collection("600_cau_hoi_A1")
                        .document(docId)
                        .set(questionData)
                        .addOnSuccessListener(aVoid ->
                                Log.d("UPLOAD", "✅ Đã upload " + docId))
                        .addOnFailureListener(e ->
                                Log.e("UPLOAD", "❌ Lỗi upload " + docId, e));
            }

        } catch (Exception e) {
            Log.e("UPLOAD", "❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

}