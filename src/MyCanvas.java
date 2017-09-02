import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Vesh on 02/09/2017.
 */
public class MyCanvas extends JComponent{

    private static final long serialVersionUID = 1L;

    public List<Figura> getArray() {
        return array;
    }

    private List<Figura> array;

    public void setArray(List<Figura> array) {
        this.array = array;
    }

    public MyCanvas(){ };

    public MyCanvas(List<Figura> fig) {
        super();
        this.array = fig;
    }
    public void paintImage() {
        Graphics d = getGraphics();
        d.clearRect(0, 0, this.getWidth(), this.getHeight()); // pulisce il canvas
        for (Figura i : this.getArray()) { // per ogni figura nella lista di figure
            if (i.visibile == true) {// se la figura e' visibile
                i.draw(d);// chiama il metodo "disegna" della figura
            }
        }
        this.validate();
    }
}
