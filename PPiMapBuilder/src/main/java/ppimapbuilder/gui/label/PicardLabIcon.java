package ppimapbuilder.gui.label;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * PicarLab logo icon label
 */
public class PicardLabIcon extends JLinkLabel{
	private static final long serialVersionUID = 1L;
    
	public PicardLabIcon() {
		super("", "http://www.picard.ch/");
		try {
			setIcon(new ImageIcon(PicardLabIcon.class.getResource("/img/picard_lab.png")));
		} catch (Exception e) {
			setText("PicardLab");
		}
	}
}
