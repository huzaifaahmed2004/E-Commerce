package e.commerce;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.sql.Blob;
public class Picture {
    public String uploadPic(JLabel label) throws IOException{
         JFileChooser chooser =new JFileChooser();
      chooser.showOpenDialog(null);
      File f=chooser.getSelectedFile();
      String path=f.getAbsolutePath();
      BufferedImage bi=ImageIO.read(new File(path));
      Image img=bi.getScaledInstance(134, 150, Image.SCALE_SMOOTH);
      ImageIcon icon=new ImageIcon(img);
      label.setIcon(icon);
      return path;
    }
    public InputStream SaveToDB(String path) throws FileNotFoundException{
        InputStream is=new FileInputStream(new File(path));
        return is;
    } 
    public void fetchFromDB(Blob image,JLabel label) throws SQLException, FileNotFoundException, IOException{
        String path="C:\\Users\\huzai\\Pictures\\Pics Project\\img.jpeg";
        byte[] bytea=image.getBytes(1, (int)image.length());
        FileOutputStream fos=new FileOutputStream(path);
        fos.write(bytea);
         BufferedImage bi=ImageIO.read(new File(path));
       Image img=bi.getScaledInstance(134, 150, Image.SCALE_SMOOTH);
        ImageIcon icon=new ImageIcon(img);
        label.setIcon(icon);
    }
}
