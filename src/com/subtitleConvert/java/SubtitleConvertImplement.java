package com.subtitleConvert.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class SubtitleConvertImplement {
	enum SubtileFormat
	{
		SF_Non,
		SF_WebVTT,
		SF_SRT,
		SF_Max
	}
	
	private SubtitleConvertImplement()
	{
	}
	
	public static SubtitleConvertImplement GetInstance()
	{
		if(null == m_sInstance){
			m_sInstance = new SubtitleConvertImplement();
		}
		
		return m_sInstance;
	}
	
	//return 1: success; 0 source file path error; -1 source file type not match; -2 source file error
	public int SubtitleConvert(String sSrcFilePath, SubtileFormat iSrcFormat, String sSaveFilePath, SubtileFormat iDstFormat)
	{
		if ((SubtileFormat.SF_WebVTT == iSrcFormat) && (SubtileFormat.SF_SRT == iDstFormat)) {
			return Vtt2Srt(sSrcFilePath,  iSrcFormat,  sSaveFilePath,  iDstFormat);
		}else if((SubtileFormat.SF_SRT == iSrcFormat) && (SubtileFormat.SF_WebVTT == iDstFormat)){
			return Srt2Vtt( sSrcFilePath,  iSrcFormat,  sSaveFilePath,  iDstFormat);
		}
		
		return 0;
	}
	
	private int Vtt2Srt(String sSrcFilePath, SubtileFormat iSrcFormat, String sSaveFilePath, SubtileFormat iDstFormat)
	{
		File fileSrc = new File(sSrcFilePath);
		FileReader fr = null;
		try {
			fr = new FileReader(fileSrc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//source file path error
			e.printStackTrace();
			return 0;
		}
		
		File fileDst = new File(sSaveFilePath);
		FileWriter fw = null;  
		try {
			fw = new FileWriter(fileDst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
        BufferedReader br = new BufferedReader(fr);
        BufferedWriter bw = new BufferedWriter(fw);
        

        String line;
        
        String regex="\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d --> \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d\\s*";  
        try {
			while((line=br.readLine()) != null)
			{
				if (Pattern.matches(regex,line))
			    {
			    	line = line.replace('.', ',');
			    }
				bw.write(line+"\n");  
			}
			
			br.close();
			fr.close();
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
        
	

		return 1;
	}
	
	private int Srt2Vtt(String sSrcFilePath, SubtileFormat iSrcFormat, String sSaveFilePath, SubtileFormat iDstFormat)
	{
		File fileSrc = new File(sSrcFilePath);
		FileReader fr = null;
		try {
			fr = new FileReader(fileSrc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//source file path error
			e.printStackTrace();
			return 0;
		}
		
		File fileDst = new File(sSaveFilePath);
		FileWriter fw = null;  
		try {
			fw = new FileWriter(fileDst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
        BufferedReader br = new BufferedReader(fr);
        BufferedWriter bw = new BufferedWriter(fw);
        

        String line;
        
        String regex="\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d --> \\d\\d:\\d\\d:\\d\\d,\\d\\d\\d\\s*";
        try {
			while((line=br.readLine()) != null)
			{
				if (Pattern.matches(regex,line))
			    {
			    	line = line.replace(',', '.');
			    }
				bw.write(line+"\n");  
			}
			
			br.close();
			fr.close();
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
        

		return 1;

	}
	
	////////////////////////////////////////////////
	private static SubtitleConvertImplement m_sInstance = null;
}
