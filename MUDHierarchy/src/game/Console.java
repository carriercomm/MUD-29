package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

@SuppressWarnings("serial")
public class Console extends JFrame
{	
	 private JEditorPane console;
	 private JTextField inputLine;
	 private JPanel stats;
	 
	public Console(String name) 
	{
        super(name);
        //this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setPreferredSize(new Dimension(1280, 720));
        this.setSize(new Dimension(1280, 720));
        this.getContentPane().setMaximumSize(new Dimension(1366, 768));
        this.getContentPane().setMinimumSize(new Dimension(720, 405));
        
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      
        this.getContentPane().setLayout(new GridBagLayout());
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
        
        this.addComponentListener(new ComponentListener()
        {
        	private final int maxHeight = 768;
        	private final int maxWidth = 1366;
        	private final int minHeight = 405;
        	private final int minWidth = 720;

			@Override
			public void componentResized(ComponentEvent e)
			{
				int w = e.getComponent().getBounds().getSize().width;
				int h = e.getComponent().getBounds().getSize().height;
				
				if(w > maxWidth)
					w = maxWidth;
				if(h > maxHeight)
					h = maxHeight;
				if(w < minWidth)
					w = minWidth;
				if(h < minHeight)
					h = minHeight;
				
				e.getComponent().setSize(new Dimension(w, h));
			}

			@Override
			public void componentMoved(ComponentEvent e){
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e){
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e){
				// TODO Auto-generated method stub
				
			}
        });
        
        //this.setBackground(Color.RED);
        
        this.console = new JEditorPane(); 
        console.setEditable(false);
        console.setContentType("html/text");
        console.setForeground(Color.WHITE);
        console.setBackground(Color.BLACK);
        console.setFont(new Font("Monospaced", Font.PLAIN, 14));
        console.setPreferredSize(new Dimension((int)(this.getWidth()*0.8),this.getHeight()));
        console.setMinimumSize(new Dimension((int)(this.getWidth()*0.8),this.getHeight()));
        console.setMaximumSize(new Dimension((int)(this.getWidth()*0.8), this.getHeight()));
        c.weightx =		0.2;
        c.gridx =		0;
        c.gridy =		0;
        c.weighty =		1.0;
        this.add(console,c);
        
        inputLine = new JTextField();
        inputLine.setEditable(true);
        inputLine.setForeground(Color.WHITE);
        inputLine.setBackground(Color.LIGHT_GRAY);
        inputLine.setFont(new Font("Monospaced", Font.PLAIN, 14));
        c.gridx =		0;
        c.weightx =		0.8;
        c.gridy =		1;
        c.weighty =		0.0;
        c.ipady =		0;
        this.add(inputLine,c);
       
        stats = new JPanel();
        stats.setForeground(Color.BLACK);
        stats.setBackground(Color.GRAY);
        stats.setFont(new Font("Monospaced", Font.PLAIN, 14));
        stats.setPreferredSize(new Dimension(175,this.getHeight()));
        stats.setMinimumSize(new Dimension(175,this.getHeight()));
        stats.setMaximumSize(new Dimension(175,this.getHeight()));
        stats.setSize(200, this.getHeight());
        c.gridx = 		1;
        c.weightx = 	0;
        c.gridy = 		0;
        c.weighty = 	0.0;
        c.ipady = 		0;
        c.gridheight = 	2;
        this.add(stats,c);
        
        append("Potato", Color.RED);
        this.pack();
        this.setVisible(true);
        
        
    }
	
	public void append(String text, Color color)
	{
		/* if (!SwingUtilities.isEventDispatchThread()) {
	            SwingUtilities.invokeLater(new Runnable() {	// what even is this?
	                @Override
	                public void run() {
	                    append(text, color);
	                }
	            });
	            return;
	        }*/
	        Document document = this.console.getDocument();
	        
	        MutableAttributeSet sas = new SimpleAttributeSet();
	        StyleConstants.setForeground(sas, color);
	        //StyleConstants.setFontFamily( sas, "Monospaced");
	        
	        try{
	            document.insertString(document.getLength(), text, sas);
	        }catch (Exception e){
	        	e.printStackTrace();
	        }
	}
}
