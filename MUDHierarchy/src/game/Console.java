package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
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
	 
	public Console(String name) 
	{
        super(name);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                Console.this.setVisible(false);
                Console.this.dispose();
            }
        });

        this.console = new JEditorPane() {
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                FontMetrics fm = getFontMetrics(getFont());
                int colWidth = fm.charWidth('m');
                int rowHeight = fm.getHeight();
                d.width = Math.max(d.width, colWidth * COLUMNS);
                d.height = Math.max(d.height, rowHeight * ROWS);
                return d;
            }
        };
        
        console.setEditable(false);
        console.setContentType("text");
        console.setForeground(Color.WHITE);
        console.setBackground(Color.BLACK);
        console.setFont(new Font("Monospaced", Font.PLAIN, 14));

        inputLine = new JTextField();
        
        FontMetrics fm = getFontMetrics(new Font("Monospaced", Font.PLAIN, 14));
        inputLine.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputLine.setSize(console.getWidth(), fm.getHeight());
        
        /*this.inputLine = new JEditorPane() {
        	@Override
        	public Dimension getPreferredSize(){
        		 Dimension d = super.getPreferredSize();
                 FontMetrics fm = getFontMetrics(getFont());
                 int colWidth = fm.charWidth('m');
                 int rowHeight = fm.getHeight();
                 d.width = Math.max(d.width, colWidth * COLUMNS);
                 d.height = Math.max(d.height, rowHeight * ROWS);
                 return d;
        	}
        };*/
        
        inputLine.setEditable(true);
        //inputLine.setContentType("text");
        inputLine.setForeground(Color.WHITE);
        inputLine.setBackground(Color.GRAY);
        inputLine.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        this.add(console);
        //this.add(inputLine,-1);
        this.pack();
        this.setVisible(true);
        this.append("Test Output:\n", Color.WHITE);
        this.append("GREEN\n", Color.GREEN);
        this.append("RED\n", Color.RED);
        this.append("BLUE\n", Color.BLUE);
        this.append("ORANGE\n", Color.ORANGE);
        this.append("\n", Color.WHITE);
        this.append("THHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSSSSSSSSS IIIIIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSS AAAAAAAAAAAAAAAAAAAAAAA LLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOOOOOOOOOOONNNNNNNNNNNNNNNNNNNNNNNNGGGGGGGGGGGGGGGGGGGGGG LLLLLLLLLLLLLLLLLLLLLLLIIIIIIIIIIIIIIIIIIIIIIINNNNNNNNNNNNNNNNNNNNNNNNNNNNNNEEEEEEEEEEEEEEEEEEEEEE", Color.WHITE);
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
