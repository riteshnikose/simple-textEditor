import java.awt.BorderLayout;
import java.awt.Desktop.Action;
import java.awt.Font;
import java.awt.RenderingHints.Key;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyStore;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;


public class Texteditor extends JFrame implements ActionListener, KeyListener {
	JTextArea jta;
	JScrollPane jscroll;
	JToolBar jtBar;
	JButton btnOpen,btnNew,btnSave,btncut,btnCopy,btnPast;
	JMenuBar mBar;
	JMenu file ,edit;
	JMenuItem fnew,fexit,fopen,fsave;
	JMenuItem eCut,eCopy,ePast,eselall,eDel;
	
	ImageIcon iNew,iOpen,iSave,iCut,iCopy,iPast;
	String fname;
	boolean chg;
	
	
public static void main(String[] args) {
	new Texteditor();
}
	public  Texteditor() {
		
		fname="";
		chg =false;
		setLayout(new BorderLayout());
		
		jta=new JTextArea();
		jta.setFont(new Font("comic sans MS",Font.PLAIN,24));
		jta.addKeyListener(this);
		jscroll = new JScrollPane(jta);
		add(jscroll,BorderLayout.CENTER);
		initIcons();
		makemenu();
		setJMenuBar(mBar);
		maketoolbar();
		add(jtBar,BorderLayout.NORTH);
		setSize(400,300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	void initIcons(){
		iNew = new ImageIcon("");
		iOpen= new ImageIcon("");
		iSave=new ImageIcon("");
		iCopy=new ImageIcon("");
		iCut =new ImageIcon("");
		iPast=new ImageIcon("");
		
		
	}
		
	void makemenu(){
		mBar=new JMenuBar();
		file =new JMenu("File");
		edit=new JMenu("Edit");
		file.setMnemonic('f');
		edit.setMnemonic('E');
		
		fnew=new JMenuItem("New",iNew);
		fopen=new JMenuItem("Open",iOpen);
		fsave= new JMenuItem("Save",iSave);
		fexit= new JMenuItem("Exit");
		
		eCut=new JMenuItem("Cut",iCut);
		eCopy=new JMenuItem("copy",iCopy);
		ePast=new JMenuItem("past",iPast);
		eselall=new JMenuItem("Selectall");
		eDel=new JMenuItem("Delete");
		
		file.add(fnew);
		file.add(fopen);
		file.add(fsave);
		file.addSeparator();
		file.add(fexit);
		 
		edit.add(eCut);
		edit.add(eCopy);
		edit.add(ePast);
		edit.add(eselall);
		edit.addSeparator();
		edit.add(eDel);
		
		mBar.add(file);
		mBar.add(edit);
		
		fnew.addActionListener(this);
		fopen.addActionListener(this);
		fsave.addActionListener(this);
		fexit.addActionListener(this);
		
		eCopy.addActionListener(this);
		eCut.addActionListener(this);
		eselall.addActionListener(this);
		ePast.addActionListener(this);
		eDel.addActionListener(this);
		
		KeyStroke k;
		k = KeyStroke.getKeyStroke('N',java.awt.Event.CTRL_MASK);
		fnew.setAccelerator(k);
		
		k = KeyStroke.getKeyStroke('O',java.awt.Event.CTRL_MASK);
		fopen.setAccelerator(k);
		
		k= KeyStroke.getKeyStroke('S',java.awt.Event.CTRL_MASK);
		fsave.setAccelerator(k);
		
	}
	 
	void maketoolbar()
	 {
		btnCopy= new JButton(iCopy);
		btnNew=new JButton(iNew);
		btncut=new JButton(iCut);
		btnOpen=new JButton(iOpen);
		btnPast=new JButton(iPast);
		btnSave=new JButton(iSave);
		
		
		btnCopy.addActionListener(this);
		btncut.addActionListener(this);
		btnNew.addActionListener(this);
		btnOpen.addActionListener(this);
		btnPast.addActionListener(this);
		btnSave.addActionListener(this);
		
		jtBar= new JToolBar();
		jtBar.add(btnCopy);
		jtBar.add(btncut);
		jtBar.add(btnNew);
		jtBar.addSeparator();
		jtBar.add(btnOpen);
		jtBar.add(btnPast);
		jtBar.add(btnSave);
		
	 }
	
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){
		chg=true;
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(btncut)||e.getSource().equals(eCut)){jta.cut();}
		else if (e.getSource().equals(btnCopy)||e.getSource().equals(eCopy)) {jta.copy();}
		else if (e.getSource().equals(btnPast)||e.getSource().equals(ePast)) {jta.paste();}
		else if (e.getSource().equals(eselall) ){jta.selectAll();}
		else if (e.getSource().equals(eDel)) {jta.replaceSelection("");}
		else if (e.getSource().equals(fnew)) {
			fname="";
			chg=false;
			jta.setText("");
			}
		else if (e.getSource().equals(fexit)) { fileExit();}
		else if (e.getSource().equals(fopen)){fileOpen();}
		else if (e.getSource().equals(fsave)) {fileSave();}
			
	 

	
	}
	private void fileSave() {
		if(fname.equals("")){
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(jfc.DIRECTORIES_ONLY);
			 int res = jfc.showSaveDialog(this);
			 if (res == jfc.APPROVE_OPTION) {
				 File f = jfc.getSelectedFile();
				 fname=f.getAbsolutePath();
				 filewrite();
				
			}
		}
		else filewrite();
		
	}
	private void filewrite() {
		// TODO Auto-generated method stub
		try {
			FileWriter fw = new FileWriter(fname);
			fw.write(jta.getText());
			fw.flush();
			fw.close();
			chg= false;				
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),"file save error", JOptionPane.ERROR_MESSAGE);
			
			// TODO: handle exception
		}
	}
	private void fileOpen() {
		// TODO Auto-generated method stub
		JFileChooser jfc =new JFileChooser();
		jfc.setFileSelectionMode(jfc.FILES_ONLY);
		int res=jfc.showOpenDialog(this);
		if (res== jfc.APPROVE_OPTION) {
			File f= jfc.getSelectedFile();
			try {
				FileReader fr =new FileReader(f);
				BufferedReader br =new BufferedReader(fr);
				String data;
				jta.setText("");
				while ((data=br.readLine()) != null) {
					
					data= data +"\n";
					jta.append(data);
					}
				
				
				
				fname=f.getAbsolutePath();
				br.close();
				fr.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(),"file open error",JOptionPane.ERROR_MESSAGE);
				
				// TODO: handle exception
			}
		}
		
	}
	private void fileExit() {
		// TODO Auto-generated method stub
		if (chg==true) {
			int res=JOptionPane.showConfirmDialog(this,"do you want to save the change","file exit",JOptionPane.YES_NO_CANCEL_OPTION);
			if (res==JOptionPane.YES_OPTION) {fileSave();
			}
			else if (res==JOptionPane.NO_OPTION){return;}
			
		}
		System.exit(0);
	}
}

