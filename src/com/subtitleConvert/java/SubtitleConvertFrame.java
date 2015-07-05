package com.subtitleConvert.java;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.*;

import org.omg.PortableServer.IdUniquenessPolicyValue;

import com.subtitleConvert.java.SubtitleConvertImplement.SubtileFormat;

@SuppressWarnings("serial")
public class SubtitleConvertFrame extends JFrame implements ActionListener{
	
	
	public SubtitleConvertFrame()
	{
		setTitle("SubtitleFormatConvert");
		
		int iW = 450, iH = 400;
		setBounds(new Rectangle(0, 0, iW, iH));
		setResizable(false);
		
		Toolkit sTk = Toolkit.getDefaultToolkit();
		Dimension sScreDimension = sTk.getScreenSize();
		setLocation((sScreDimension.width - iW)/2, (sScreDimension.height - iH)/2);
		
		getContentPane().setLayout(null);
		
		
		//add component
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Help");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("About");
		mnNewMenu.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(SubtitleConvertFrame.this,
						"If you has any problem or need more format support,"
						+"\nyou can contact me."
						+"Email: zhupeiru2@sina.com"
//						"如果遇到任何问题或者有其他格式转换的需求,"
//						+ "\n可以通过下面方式联系。\n"
//						+ "Email: zhupeiru2@sina.com");
						, "About", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		JLabel labelFileChoose = new JLabel("Choose       File:");
		labelFileChoose.setBounds(50, 49, 96, 15);
		getContentPane().add(labelFileChoose);

		m_textFieldInputFile = new JTextField();
		m_textFieldInputFile.setBounds(156, 50, 198, 21);
		m_textFieldInputFile.setEditable(false);
		getContentPane().add(m_textFieldInputFile);
		m_textFieldInputFile.setColumns(10);
		
		m_btnFileChoose = new JButton("...");
		m_btnFileChoose.setBounds(369, 49, 33, 23);
		getContentPane().add(m_btnFileChoose);
		m_btnFileChoose.addActionListener(this);
				
		JLabel label_sf = new JLabel("Source Format:");
		label_sf.setBounds(50, 96, 96, 15);
		getContentPane().add(label_sf);
		
		m_comboBoxSrcFileFormat = new JComboBox();
		m_comboBoxSrcFileFormat.setBounds(156, 92, 116, 23);
		
		for(int i=0; i<m_arraySrcSupportFormat.length; i++)
		{
			m_comboBoxSrcFileFormat.addItem(m_arraySrcSupportFormat[i].m_sShowStr);
		}
		getContentPane().add(m_comboBoxSrcFileFormat);
		
		
		JLabel label_df = new JLabel("Target Format:");
		label_df.setBounds(50, 142, 101, 15);
		getContentPane().add(label_df);
		
		m_comboBoxDstFileFormat = new JComboBox();
		m_comboBoxDstFileFormat.setBounds(156, 138, 116, 23);
		
		for(int i=0; i<m_arrayDstSupportFormat.length; i++)
		{
			m_comboBoxDstFileFormat.addItem(m_arrayDstSupportFormat[i].m_sShowStr);
		}
		getContentPane().add(m_comboBoxDstFileFormat);
		
		
		m_btnTransform = new JButton("Transform");
		m_btnTransform.setBounds(50, 193, 96, 23);
		getContentPane().add(m_btnTransform);
		m_btnTransform.addActionListener(this);
		
		m_labelOutput = new JLabel("");
		m_labelOutput.setBounds(50, 246, 185, 23);
		getContentPane().add(m_labelOutput);
		

		
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object jComponent = arg0.getSource();
		if(m_btnFileChoose == jComponent)
		{
			OnFileChoose();
		}else if (m_btnTransform == jComponent) {
			OnTransform();
		}
	}
	
	
	private void OnFileChoose()
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setCurrentDirectory(new File(m_sSrcFilePath));
		
		if(JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(null))
		{
			m_sSrcFilePath = jfc.getSelectedFile().getAbsolutePath();
			m_sSrcFileName = jfc.getSelectedFile().getName();
			m_textFieldInputFile.setText(m_sSrcFileName);
			m_sSrcFilePath = m_sSrcFilePath.substring(0, m_sSrcFilePath.indexOf(m_sSrcFileName));
		}

	}
	
	private void OnTransform()
	{
		int iSrcFormatSelect, iDstFormatSelect;
		iSrcFormatSelect = m_comboBoxSrcFileFormat.getSelectedIndex();
		iDstFormatSelect = m_comboBoxDstFileFormat.getSelectedIndex();
		if(m_sSrcFilePath.isEmpty() || m_sSrcFileName.isEmpty()){
			m_labelOutput.setText("Please choose file.");
		}else if((iSrcFormatSelect <= 0) || (iDstFormatSelect <= 0)){
			m_labelOutput.setText("Please choose format");
		}else if(m_arraySrcSupportFormat[iSrcFormatSelect].m_sShowStr.equals(m_arrayDstSupportFormat[iDstFormatSelect].m_sShowStr)){
			m_labelOutput.setText("The format of source and target are same.");
		}else{
			String sSaveFileName = "";
			int iRe = m_sSrcFileName.indexOf('.');
			if(-1 == iRe){
				sSaveFileName = m_sSrcFileName+ "." +m_arrayDstSupportFormat[iDstFormatSelect].m_sSuffixStr;
			}else{
				sSaveFileName = m_sSrcFileName.substring(0, iRe+1) + m_arrayDstSupportFormat[iDstFormatSelect].m_sSuffixStr;
			}
			if(sSaveFileName.equals(m_sSrcFileName)){
				sSaveFileName = m_sSrcFileName.substring(0, iRe) + "-trans" + m_sSrcFileName.substring(iRe, m_sSrcFileName.length());
			}
			
			iRe = SubtitleConvertImplement.GetInstance().SubtitleConvert(m_sSrcFilePath+"/"+m_sSrcFileName, m_arraySrcSupportFormat[iSrcFormatSelect].m_sSF,
					m_sSrcFilePath+"/"+sSaveFileName, m_arrayDstSupportFormat[iDstFormatSelect].m_sSF);
			
			if(1 == iRe){
				m_labelOutput.setText("Success");
			}else if(0 == iRe){
				m_labelOutput.setText("the error of input file path!");
			}else if(-2 == iRe){
				m_labelOutput.setText("the error of input file format!");
			}
		}//endIf
	}
	
	///////////////////////////////////////////////////////////////////
	private FormatStringPair[] m_arraySrcSupportFormat = {new FormatStringPair(SubtileFormat.SF_Non,"", "Please choose"),
			new FormatStringPair(SubtileFormat.SF_WebVTT, "vtt", "VTT"), new FormatStringPair(SubtileFormat.SF_SRT, "srt", "SRT")};
	
	private FormatStringPair[] m_arrayDstSupportFormat = {new FormatStringPair(SubtileFormat.SF_Non, "", "Please choose"),
		new FormatStringPair(SubtileFormat.SF_WebVTT, "vtt", "VTT"), new FormatStringPair(SubtileFormat.SF_SRT, "srt", "SRT")};
	
	private JTextField m_textFieldInputFile;
	private JButton m_btnFileChoose;
	private JComboBox m_comboBoxSrcFileFormat;
	private JComboBox m_comboBoxDstFileFormat;
	private JButton m_btnTransform;
	private JLabel m_labelOutput;
	
	private String m_sSrcFileName = "";
	private String m_sSrcFilePath = "";
	private String m_sSaveFilePath = "";


	
}

/*
 * class FormatStringPair
 * */
class FormatStringPair
{
	public SubtitleConvertImplement.SubtileFormat m_sSF;
	public String m_sSuffixStr;
	public String m_sShowStr;
	public FormatStringPair(SubtileFormat sf, String suffixStr, String sShowStr)
	{
		m_sSF = sf;
		m_sSuffixStr = suffixStr;
		m_sShowStr = sShowStr;
	}
}