package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

@SuppressWarnings("serial")
public class Console extends JFrame
{
	
	 private static final int ROWS = 24;
	 private static final int COLUMNS = 80;
	
	 private JEditorPane console;
	 private JTextField inputLine;
	 private JPanel stats;
	 
	public Console(String name) 
	{
        super(name);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill=GridBagConstraints.BOTH;
        
        
        addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                Console.this.setVisible(false);
                Console.this.dispose();
            }
        });
        
        
        this.setBackground(Color.RED);
        
        this.console = new JEditorPane(); 
        console.setEditable(false);
        console.setContentType("text");
        console.setForeground(Color.WHITE);
        console.setBackground(Color.BLACK);
        console.setFont(new Font("Monospaced", Font.PLAIN, 14));
        c.weightx=0.0;
        c.gridx=0;
        c.gridy=0;
        c.weighty=1.0;
        this.add(console,c);
        
        inputLine = new JTextField();
        
        FontMetrics fm = getFontMetrics(new Font("Monospaced", Font.PLAIN, 14));
        inputLine.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputLine.setEditable(true);
        inputLine.setForeground(Color.WHITE);
        inputLine.setBackground(Color.GRAY);
        c.gridx=0;
        c.weightx=0.8;
        c.gridy=1;
        c.weighty=0.0;
        c.ipady=0;
        this.add(inputLine,c);
        
       
        stats=new JPanel(){
        	@Override
        	public Dimension getPreferredSize()
        	{
        		return new Dimension((int)(this.getWidth()*0.2),this.getHeight());
        	}
        };
        stats.setForeground(Color.WHITE);
        stats.setBackground(Color.BLUE);
        stats.setFont(new Font("Monospaced", Font.PLAIN, 14));
        c.gridx=1;
        c.weightx=0.2;
        c.gridy=0;
        c.weighty=0.0;
        c.ipady=0;
        c.gridheight=2;
        this.add(stats,c);
        
        

        
       
        
        console.setText("Potato");
        
        this.setSize(1920, 1080);
        /*console.setBounds(0, 0,(int)(this.getWidth()*0.8),(int)(this.getHeight()-fm.getHeight()));
        inputLine.setBounds(0,console.getHeight(),console.getWidth(),fm.getHeight());
        stats.setBounds(console.getWidth(),0,(int)(this.getWidth()*0.2),this.getHeight());*/
        
        
        /*this.addComponentListener(new ComponentListener()
        {
        	

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
				console.setBounds(0, 0,(int)(arg0.getComponent().getWidth()*.8),(int)(arg0.getComponent().getHeight()-fm.getHeight()-50));
		        inputLine.setBounds(0,console.getHeight(),(int)(arg0.getComponent().getWidth()*.8),fm.getHeight());
		        stats.setBounds(console.getWidth(),0,(int)(arg0.getComponent().getWidth()*.2),arg0.getComponent().getHeight());
		        leftPanel.setBounds(0, 0, (int)(arg0.getComponent().getWidth()*.8), arg0.getComponent().getHeight());
				
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        });*/
        
        this.setVisible(true);
        
        
    }
	
	public void append(String text, final Color color)
	{
		 if (!SwingUtilities.isEventDispatchThread()) {
	            SwingUtilities.invokeLater(new Runnable() {
	                @Override
	                public void run() {
	                    append(text, color);
	                }
	            });
	            return;
	        }
	        Document document = this.console.getDocument();
	        MutableAttributeSet sas = new SimpleAttributeSet();
	        StyleConstants.setForeground(sas, color);
	        StyleConstants.setFontFamily( sas, "Monospaced");
	        try {
	            document.insertString(document.getLength(), text, sas);
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
}
