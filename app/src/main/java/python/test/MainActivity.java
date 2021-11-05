package python.test;

import java.lang.Process;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.*;
import android.os.*;
import java.nio.file.*;
import python.test.myjavalibrary.*;

public class MainActivity extends Activity { 
    EditText result;
	CopyAssets copy_assets = new CopyAssets();
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.activitymainEditText1);
		
		copy_assets.setCtx(getApplicationContext());
		copy_assets.setFromPathDirectoryAssets("python3");
		copy_assets.setToDirectoryTarget(getFilesDir().getPath());
		copy_assets.start();
		
		String DirPackageData = getFilesDir().getPath();
		String PYTHON_EXECUTABLE_FILE = DirPackageData + "/python3/bin/python3.9";
		String PYTHON_SCRIPT = DirPackageData + "/python3/test.py";
		
		executes(String.format("chmod +x %s",PYTHON_EXECUTABLE_FILE));
		executes(String.format("chmod +x %s", PYTHON_SCRIPT));
		result.setText(executes(String.format("%s %s",PYTHON_EXECUTABLE_FILE, PYTHON_SCRIPT)));
		
			
	}String executes(String s){
        String exec_result = "";
        try {
            Process commands = Runtime.getRuntime().exec(s.split(" "));
            BufferedReader lineReader = new BufferedReader(new InputStreamReader(commands.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(commands.getErrorStream()));
            String r;
            while((r=lineReader.readLine())!=null)exec_result+=r+"\n";
            while((r=errorReader.readLine())!=null)exec_result+=r+"\n";
            
        } catch (IOException e) {
            exec_result = e.toString();
        }
       return exec_result;
    }
}
