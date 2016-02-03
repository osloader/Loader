package core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;

public class Loader
extends JFrame {
    private static final long serialVersionUID = 1;
    public static final String CLIENT_PATH = String.valueOf(System.getProperty("user.home")) + File.separator + "OSLoader" + File.separator;
	public static final String VERSION_FILE = System.getProperty("user.home") + "/OSLoader/Version.txt";
	public static final String VERSION_URL = "http://osloader.github.io/Version.txt";
	
    public static final void main(String[] args) throws IOException {
    	firstVersion();
		try { 
		    UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
		} catch (Exception e) {
		    e.printStackTrace();
		}
            try{
        		double newest = getNewestVersion();
        		if(newest > getCurrentVersion()){
        			int n = JOptionPane.showConfirmDialog(
        				    null,
        				    "There is an update to version " + newest + "\n" +
        					"Would you like to update?",
        				    "Current version: "+ getCurrentVersion(),
        				    JOptionPane.YES_NO_OPTION);
        			if(n == 0){
        				JPanel panel = new JPanel();
        			    JLabel jlabel = new JLabel("Updating, Please Wait.");
        			    panel.add(jlabel);
        				JFrame frame = new JFrame("Updating");
        				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        			    frame.add(panel);
        			    frame.setSize(200, 70);
        			    frame.setVisible(true);
        			    frame.pack();
        			    frame.setLocationRelativeTo(null);
        				update();
        				frame.setVisible(false);
        				alert("Client has been updated, Please restart the client!");
        				@SuppressWarnings("resource")
						OutputStream out = new FileOutputStream(VERSION_FILE);
        				out.write(String.valueOf(newest).getBytes());
        				System.exit(0);
        			}else{
        				alert(" Your client may not load correct " +
        				getCurrentVersion());
        				//System.exit(0);
        			}
        		}
        	}
            catch(Exception e){
        			handleException(e);
        	}

            String fileName1 = String.valueOf(CLIENT_PATH) + "client.jar";
            new ProcessBuilder("java", "-jar", fileName1).start();
    }
    

	
	public static double getCurrentVersion(){
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(VERSION_FILE)));
			return Double.parseDouble(br.readLine());
		} catch (Exception e) {
			return 0.1;
		}
	}
	
	
	
	public static double getNewestVersion(){
		try {
			URL tmp = new URL(VERSION_URL);
			BufferedReader br = new BufferedReader(new InputStreamReader(tmp.openStream()));
			return Double.parseDouble(br.readLine());
		} catch (Exception e) {
			handleException(e);
			return -1;
		}
	}
	
	private static void handleException(Exception e){
		StringBuilder strBuff = new StringBuilder();
		strBuff.append("Please Screenshot this message, and send it to an admin!\r\n\r\n");
        @SuppressWarnings("unused")
		StringBuilder append = strBuff.append(e.getClass().getName()).append(" \"").append(e.getMessage()).append("\"\r\n");
		for(StackTraceElement s : e.getStackTrace())
			strBuff.append(s.toString()).append("\r\n");
		alert("Exception [" + e.getClass().getSimpleName() + "]",strBuff.toString(),true);
	}

	private static void alert(String msg){
		alert("Message",msg,false);
	}
	
	private static void alert(String title,String msg,boolean error){
		JOptionPane.showMessageDialog(null,
			   msg,
			   title,
			    (error ? JOptionPane.ERROR_MESSAGE : JOptionPane.PLAIN_MESSAGE));
	}
    
    private static void update(){
    	
        try {
            int nRead;
            String fileName = String.valueOf(CLIENT_PATH) + "client.jar";
            URL link = new URL("https://osloader.github.io/client/client.jar");
            InputStream inStream = link.openStream();
            BufferedInputStream bufIn = new BufferedInputStream(inStream);
            File fileWrite = new File(fileName);
            FileOutputStream out = new FileOutputStream(fileWrite);
            BufferedOutputStream bufOut = new BufferedOutputStream(out);
            byte[] buffer = new byte[1024];
            while ((nRead = bufIn.read(buffer, 0, buffer.length)) > 0) {
                bufOut.write(buffer, 0, nRead);
            }
            bufOut.flush();
            out.close();
            inStream.close();
            //new ProcessBuilder("java", "-jar", fileName).start();
        }
        catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Error", -1);
        }
    }
	private static void firstVersion(){
		
		File file1 = new File(System.getProperty("user.home") + "/OSLoader/Version.txt");
		
		if(!file1.exists()){
		try {

			String content = "0.01";

			File file = new File(System.getProperty("user.home") + "/OSLoader/Version.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
	
}

